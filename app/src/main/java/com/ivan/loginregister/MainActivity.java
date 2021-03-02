package com.ivan.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toAdd(View view) {
        //跳转到添加联系人页面
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);

    }
}