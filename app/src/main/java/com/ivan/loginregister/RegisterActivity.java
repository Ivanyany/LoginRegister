package com.ivan.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.loginregister.entity.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //按钮
    private Button btn_save;
    private Button btn_reset;
    private Button btn_back;

    //输入框
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_confirm;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //按钮实例化并注册点击事件
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        //输入框实例化
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        et_password_confirm = findViewById(R.id.et_password_confirm);
        et_phone = findViewById(R.id.et_phone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //获取输入框的值
        String account = et_account.getText().toString().trim();
        String password = et_password.getText().toString();
        String passwordConfirm = et_password_confirm.getText().toString();
        String phone = et_phone.getText().toString().trim();
        switch (id) {
            //保存
            case R.id.btn_save:
                if (account.length() == 0){
                    Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                if (password.length() == 0){
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() == 0){
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isExistAccountAndPhone(account, phone)) {
                    Toast.makeText(RegisterActivity.this, "账号或手机号已存在,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //封装用户数据
                User user = new User(account, password, phone);
                //添加到用户集合中
                LoginActivity.users.add(user);
                //跳转到登录页面
                finish();//关闭当前页面即可
                break;
            //重置
            case R.id.btn_reset:
                et_account.setText("");
                et_password.setText("");
                et_password_confirm.setText("");
                et_phone.setText("");
                break;
            //返回
            case R.id.btn_back:
                finish();
                break;
        }
    }

    /**
     * 判断当前输入的账号和手机号是否存在
     * @param account
     * @param phone
     * @return
     */
    public boolean isExistAccountAndPhone(String account, String phone) {
        for (User user : LoginActivity.users) {
            if (account.equals(user.getAccount()) || phone.equals(user.getPhone())) {
                return true;
            }
        }
        return false;
    }
}