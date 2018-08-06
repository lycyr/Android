package com.example.a14496.sy4app;
import com.example.a14496.sy4app.web.WebService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private static boolean logpd=false;
    private static String info="";
    private void initview(){
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setcontentview用来给当前活动加载一个布局，此处是加载的是主要的布局
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initview();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "现在执行登录操作", Toast.LENGTH_SHORT).show();
                dologin();
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "现在执行注册操作", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,RegActivity.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);

    }

    private void dologin() {
        String user=username.getText().toString();
        String pass=password.getText().toString();
        if(user.equals("")||pass.equals("")){
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else{
//            new Thread(new MainActivity.MyThread()).start();
            Thread t2=new Thread(new MainActivity.MyThread());
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(info.contains("success")){
            Intent intent=new Intent(MainActivity.this,WelActivity.class);
            intent.putExtra("username",user);
            startActivity(intent);
            }
            else if(info.contains("userfailed")){
                Toast.makeText(MainActivity.this, "无该用户信息", Toast.LENGTH_SHORT).show();
                info="";
            }
            else {
                Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                info = "";
            }
            Log.e("error",info);
        }
    }
    public class MyThread implements Runnable {
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}