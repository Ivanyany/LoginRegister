package com.ivan.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ivan.loginregister.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Activity获取xml布局文件中控件的步骤
     * 1.写完xml文件,并且给控件添加id
     * 2.在Activity中定义成员变量
     * 3.在onCreate中书写代码,需要写在setContentView后面
     */

    private Button btn_login;
    private Button btn_register;
    private Button btn_forget;

    public static List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println(users);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //页面跳转
        Intent intent = new Intent();
        switch (id) {
            case R.id.btn_login:
                break;
            case R.id.btn_register:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_forget:
                intent.setClass(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
        }
    }
}