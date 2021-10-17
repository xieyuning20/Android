package com.example.lab3_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 加载login.xml文件
                View loginForm =View.inflate(getApplication(),R.layout.login,null);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setView(loginForm);
//                添加按钮的方法
//                setPositiveButton是实现android.content.DialogInterface.OnClickListener接口后的方法普通的onclick()是view下的，完全是两个不同的实现。又在View类中也有OnClickListener（）方法，而我们要使用的是DialogInterface的，所以要加上DialogInterface.OnClickListener()。
//                .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
                builder.create().show();
            }
        });

    }
}
