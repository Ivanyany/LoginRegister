package com.ivan.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.loginregister.entity.User;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_back;
    private Button btn_query;

    private EditText et_account;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        et_account = findViewById(R.id.et_account);
        et_phone = findViewById(R.id.et_phone);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //返回
            case R.id.btn_back:
                finish();
                break;
            //查询
            case R.id.btn_query:
                String account = et_account.getText().toString();
                String phone = et_phone.getText().toString();
                if (account.length() == 0){
                    Toast.makeText(ForgetActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;//中断方法
                }
                if (phone.length() == 0){
                    Toast.makeText(ForgetActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = findPassword(account, phone);
                if (password == null) {
                    Toast.makeText(ForgetActivity.this, "账号和手机无法查询到密码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(ForgetActivity.this, "您的密码为:" + password, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 找回密码的方法
     * @param account
     * @param phone
     * @return
     */
    private String findPassword(String account, String phone) {
        for (User user : LoginActivity.users) {
            if (account.equals(user.getAccount()) && phone.equals(user.getPhone())) {
                return user.getPassword();
            }
        }
        return null;
    }
}