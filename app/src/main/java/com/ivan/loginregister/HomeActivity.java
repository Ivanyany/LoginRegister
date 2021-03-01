package com.ivan.loginregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    /**
     * Android数据存储
     *  1、IO流，属于java基础内容，直接使用IO来操作Android上的文件，需要使用权限（输入流、输出流）
     *  2、SharedPreferences存储，本质上就是IO流操作，但是做了封装，更加简单
     *  3、Sqlite数据库存储，是一个轻小型的数据库，在各种手持设备上大量使用
     */

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * getSharedPreferences(String name, int mode)
         *  name:文件名,如果文件不存在会自动创建
         *  mode:权限——MODE_PRIVATE:只能本应用自己用
         */
        sp = getSharedPreferences("data",MODE_PRIVATE);
    }

    /**
     * 保存
     * @param view
     */
    public void save(View view) {
        //获取操作对象
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("account", "Ivan");
        edit.putString("password", "123");

        //提交保存
        edit.commit();
    }

    /**
     * 读取
     * @param view
     */
    public void read(View view) {
        String account = sp.getString("account", null);
        String password = sp.getString("password", null);

        System.out.println("从SharedPreferences读取到的account=" + account);
        System.out.println("从SharedPreferences读取到的password=" + password);
    }

    public class MySqliteOpenHelper extends SQLiteOpenHelper {
        //创建数据库mydb
        public MySqliteOpenHelper(@Nullable Context context) {
            super(context, "mydb.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //创建表，创建成功会产生一个对应的.db文件，在应用的目录下
            //SQLiteDatabase db 就是数据库对象
            db.execSQL("create table user(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "account VARCHAR(20)," +
                    "password VARCHAR(20)," +
                    "phone VARCHAR(11))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //修改数据库：比如表结构或其他，判断.db文件是否存在，如果存在则执行onUpgrade方法
        }
    }

    //数据库操作对象
    SQLiteOpenHelper dbHelper;
    //数据库对象
    SQLiteDatabase db;

    /**
     * 创建数据库
     */
    public void create(View view) {
        dbHelper = new MySqliteOpenHelper(HomeActivity.this);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 插入数据
     * @param view
     */
    public void insert(View view) {
        ContentValues values = new ContentValues();
        values.put("account", "Ivan");
        values.put("password", "123");
        values.put("phone", "13611111111");
        long result = db.insert("user", null, values);
        System.out.println("成功插入 " + result + " 条数据");
        //关闭数据库连接
//        db.close();
    }

    /**
     * 删除数据
     * @param view
     */
    public void delete(View view) {
        long result = db.delete("user", "id=?", new String[]{"1"});
        System.out.println("成功删除 " + result + " 条数据");
        //关闭数据库连接
//        db.close();
    }

    /**
     * 修改数据
     * @param view
     */
    public void update(View view) {
        ContentValues values = new ContentValues();
        values.put("phone", "13622222222");
        long result = db.update("user", values,"id=?", new String[]{"1"});
        System.out.println("成功修改 " + result + " 条数据");
        //关闭数据库连接
//        db.close();
    }

    /**
     * 查询数据
     * @param view
     */
    public void query(View view) {
        //查询所有数据
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            //查询到了数据
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                System.out.println("id=" + id);
                System.out.println("account=" + account);
                System.out.println("password=" + password);
                System.out.println("phone=" + phone);
                System.out.println("===================================");
            }
        }
        //关闭数据库连接
//        db.close();
    }
}