package com.hosun0507.touchgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class introMainActivity extends AppCompatActivity {

    private  Thread timeThread = null;
    private Boolean isRunning = true;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_main);

        timeThread = new Thread(new timeThread());
        timeThread.start();
        isRunning = true;
    }



    public class timeThread implements Runnable{
        public void run(){
            while (isRunning){
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message masg = new Message();       //초가 저장될 공간생성
                masg.arg1 = i++;
                if((masg.arg1 / 100) % 60 >= 4){
                    isRunning = false;
                    Intent intent = new Intent(introMainActivity.this, secondActivity.class);
                    startActivity(intent);
                    i = 0;
                }
            }
        }
    }
}