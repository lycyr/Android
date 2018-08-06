package com.example.a14496.sy4app;
import com.example.a14496.sy4app.web.WebService2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {
    private EditText name;
    private EditText pass;
    private EditText makesure;
    private Button golog;
    private Button reg;
    private static String info2="";
    private static boolean regpd=false;
    public void init(){
        name=(EditText)findViewById(R.id.name);
        pass=(EditText)findViewById(R.id.pass);
        makesure=(EditText)findViewById(R.id.makesure);
        golog=(Button)findViewById(R.id.golog);
        reg=(Button)findViewById(R.id.reg);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        init();
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doreg();
            }
        });
        golog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(RegActivity.this, "现在返回登录页面", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(RegActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void doreg() {
        String newname=name.getText().toString();
        if(newname.length()<5||newname.length()>10){
            Toast.makeText(RegActivity.this,"用户名至少5位，最多10位",Toast.LENGTH_SHORT).show();
        }
        else if (!newname.matches("^[a-zA-Z][a-zA-Z0-9_]*$")){
            Toast.makeText(RegActivity.this,"以英文字母开头，只允许包含英文字母、数字以及_",Toast.LENGTH_SHORT).show();
        }
        else if (!atleastA(newname)){
            Toast.makeText(RegActivity.this,"至少有一个大写英文字母",Toast.LENGTH_SHORT).show();
        }
        else{
            String mima=pass.getText().toString();
            if(mima.length()<6||mima.length()>12){
                Toast.makeText(RegActivity.this,"密码应该为6-12位",Toast.LENGTH_SHORT).show();
            }
            else if (!mima.matches("[a-zA-Z0-9_]*$")){
                Toast.makeText(RegActivity.this,"密码只允许包含英文字母、数字以及_",Toast.LENGTH_SHORT).show();
            }
            else if(!mima.equals(makesure.getText().toString())){
                Toast.makeText(RegActivity.this,"两次密码的输入不一致",Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(RegActivity.this, "用户名，密码输入格式正确，正在进行注册", Toast.LENGTH_SHORT).show();
                Thread t3=new Thread(new RegActivity.MyThread1());
                t3.start();
                try {
                    t3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(regpd==true){
                    Intent intent2=new Intent(RegActivity.this,WelActivity.class);
                    intent2.putExtra("username",newname);
                    startActivity(intent2);
                }
                else if(info2.contains("fail")) {
                    Toast.makeText(RegActivity.this, "用户名已被其他人使用", Toast.LENGTH_SHORT).show();
                    info2="";
                }
            }
        }
    }
    public class MyThread1 implements Runnable {
        @Override
        public void run() {
            info2=WebService2.executeHttpGet(name.getText().toString(), pass.getText().toString());
            if(info2.contains("success"))
                regpd=true;
            else
                regpd=false;
        }
    }
    private boolean atleastA(String n) {
        for (int i=0;i<n.length();i++){
            if(n.charAt(i)>='A'&&n.charAt(i)<='Z')
                return true;
        }
        return  false;
    }
}
