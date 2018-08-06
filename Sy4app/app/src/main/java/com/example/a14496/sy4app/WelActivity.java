package com.example.a14496.sy4app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WelActivity extends AppCompatActivity {
    private  Button quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView weltext=(TextView)findViewById( R.id.weltext);
        Intent intent=getIntent();
//        EditText u=(EditText) intent.getSerializableExtra("username");
//        weltext.setText(u.toString());
        Bundle data=intent.getExtras();
        String u=data.getString("username");
        weltext.setText("欢迎"+u);
        quit=(Button)findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(WelActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }

}
