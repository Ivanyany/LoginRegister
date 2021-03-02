package com.ivan.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

    //登录、注册、忘记密码
    private Button btn_login;
    private Button btn_register;
    private Button btn_forget;

    //账户、密码
    private EditText et_account;
    private EditText et_password;

    //记住密码
    private CheckBox cb_remember;

    public static List<User> users = new ArrayList<>();

    //保存账号、密码
    SharedPreferences sp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        users.add(new User("1","1","1"));

        //登录
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        //注册
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        //忘记密码
        btn_forget = findViewById(R.id.btn_forget);
        btn_forget.setOnClickListener(this);

        //账户、密码
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);

        //记住密码
        cb_remember = findViewById(R.id.cb_remember);

        sp = getSharedPreferences("login_remember.txt", MODE_PRIVATE);
        if (sp != null) {
            String account = sp.getString("account", null);
            String password = sp.getString("password", null);

            //记住密码默认为勾选
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                //赋值
                et_account.setText(account);
                et_password.setText(password);
                cb_remember.setChecked(true);
            }
        }
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
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();

                if (account.length() == 0) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                if (password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = login(account, password);
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "登录失败,账号或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor edit = sp.edit();
                    if (cb_remember.isChecked()) {
                        //勾选了记住密码，保存账号、密码
                        edit.putString("account", account);
                        edit.putString("password", password);
                    } else {
                        //删除账号、密码
                        edit.clear();
                    }
                    //提交
                    edit.commit();
                    //跳转到首页
                    intent.setClass(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                }
                break;
            case R.id.btn_register:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_forget:
                intent.setClass(LoginActivity.this, ForgetActivity.class);
//                startActivity(intent);
                break;
        }
        startActivity(intent);
    }

    /**
     * 登录的方法
     * @param account
     * @param password
     * @return
     */
    private User login(String account, String password) {
        for (User user : users) {
            if (account.equals(user.getAccount()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}