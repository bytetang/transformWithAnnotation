package com.ctrip.ibu.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ctrip.ibu.autotrace.annotation.AutoTrace;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AutoTrace(key = "key.test")
    public void test(){
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
    }

    public void clickBtn(View view){
        test();
    }
}
