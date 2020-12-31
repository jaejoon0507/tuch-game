package com.hosun0507.touchgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;




public class MainActivity extends AppCompatActivity {

    com.hosun0507.touchgame.databinding.ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = com.hosun0507.touchgame.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    private  Thread timeThread = null;      //Thread 사용을 위한 변수
    private  Boolean isRunning = false;      // 시간초 세기 정지와 시작을 하기위한 변수
    int startbutton = 2; // 시작 버튼을 눌렀는지 알기 위한 변수
    int leftvol = -1;          //다시 하기 버튼 확인 변수
    int i = 0;          // thread( 시간) 하나씩 모으는 변수
    int time = 3;         // 남은 시간 변수
    int[] buttonx = new int[9];
    int textcolor = 0;           // 색상 색갈 변경
    int ask = 0;                  // 색상 변경 중복 제거
    int point = 0;                // 점수 저장 변수


    int a = 123;

    public  void rand(){
        int a;
        for (a = 0; a < 9; a++) {
            buttonx[a] = (int) ((Math.random() * 10000) % 5 +1);
        }                                                         // 버튼 위치만 변경
        ask = textcolor;
        textcolor = (int) ((Math.random() * 10000) % 5 + 1);                               // 텍스트 뷰 색상 변경
        for(a=0;a<9;a++){
            if(ask == textcolor){
                textcolor = (int) ((Math.random() * 10000) % 5 + 1);
                a = 0;
        }else{
                a = 100;
            }                                        // 중복 방지
       }



    }

    public void start(View view) {             //시작 버튼

        activityMainBinding.colorView.setBackgroundColor(getResources().getColor(R.color.white));
        if (startbutton == 2 || startbutton == 3) {
            isRunning = true;
            timeThread = new Thread(new timeThread()); // 시간초 세기
            timeThread.start();         //thread 실행
            activityMainBinding.startbutton.setText("일시 정지");

            if(startbutton == 2) {
                rand();
            }else{
                leftvol = 4;
            }
            startbutton = 0;
        }else{
            if(startbutton == 5){
                point = 0;
                time = 3;
                leftvol = -1;
                isRunning = true;
            }else{
                leftvol = 4;
                activityMainBinding.startbutton.setText("다시 시작");
                isRunning = false;
                startbutton = 3;
            }
        }
        button1color();                                 // 버튼 색깔 보이게

    }     // 시작 버튼 눌를 때 실행되는 함수



    Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message masg) {
            int putE = 0;
            int q = 0;
            if (leftvol <= -1) {
                activityMainBinding.timeview.setText("남은시간: ");
                activityMainBinding.timeview.append(String.format("%d", time));    // 화면에 표시
                time = time - masg.arg1;                                             // 남은 시간 카운터
                if (time <= 0) {                                                //남은 시간이 없어지면 나오는 기능
                    for (int a = 0; a < 9; a++) {
                        if (textcolor == buttonx[a]) {
                            activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.black));
                            activityMainBinding.colorView.setBackgroundColor(getResources().getColor(R.color.red));
                            activityMainBinding.colorView.setText("꽝 ! 놓치셨어요");
                            activityMainBinding.startbutton.setText("다시 하기");
                            isRunning = false;
                            startbutton = 3;
                            leftvol = 4;
                            point = 0;
                            q = 1;
                            time = 4;
                        }
                    }
                    if (q == 0) {
                        rand();
                        button1color();
                        time = 3;
                        if(point > 10) {
                            time = 2;
                        }else if(point > 20){
                                time = 1;
                        }
                    }
                }
            } else if(leftvol >= 0) {
                leftvol = leftvol - masg.arg1;
                activityMainBinding.colorView.setText(String.format("%d", leftvol));
                if (leftvol == 0) {
                    for(int a = 0; a < 9; a++) {
                        int randdom = (int) ((Math.random() * 10000) % 9);
                        putE = buttonx[a];
                        buttonx[a] = buttonx[randdom];
                        buttonx[randdom] = putE;
                    }
                    leftvol = leftvol - masg.arg1;
                    button1color();
                }

            }

        }


    };

    public class timeThread implements Runnable{
        int Sec;
        public void run(){
            while (isRunning){
                try{
                    Thread.sleep(10);           // 0.001초에 한번씩 Thread 발생
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message masg = new Message();       //초가 저장될 공간생성
                masg.arg1 = i++;                   //0.001초마다 i의 값 1씩 증가
                Sec = (masg.arg1 / 100) % 100;    // 1초마다 Sec 값 1씩 증가
                if(Sec == 1){
                    masg.arg1 = Sec;
                    i = 0;                       // 1초마 i값 초기화
                    handler.sendMessage(masg);  // 1초마다 값 1씩 전송
                }

            }
        }
    }     // thread (시간) 코드

    public void button1color(){
        for(int a = 0; a < 9; a++ ){
            if(a == 0){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 1){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 2){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 3){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 4){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 5){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 6){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 7){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
            if(a == 8){
                switch (buttonx[a]){
                    case 0:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.gray));
                        break;
                    case 1:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case 3:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.purple));
                        break;
                    case 4:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.black));
                        break;
                    case 5:
                        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }
            }
        }       // 버튼 색상 변경
        switch (textcolor){
            case 1:
                activityMainBinding.colorView.setText("빨강색");
                break;
            case 2:
                activityMainBinding.colorView.setText("파랑색");
                break;
            case 3:
                activityMainBinding.colorView.setText("보라색");
                break;
            case 4:
                activityMainBinding.colorView.setText("검은색");
                break;
            case 5:
                activityMainBinding.colorView.setText("초록색");
                break;

        }               // 텍스트 뷰 색상 변경
        ask = (int) ((Math.random() * 10000) % 5 + 1);
        for(int a=0;a<8;a++){
            if(ask == textcolor){
                ask = (int) ((Math.random() * 10000) % 5);
                a = 0;
            }else{
                a = 100;
            }                                        // 중복 방지
        }
        switch (ask){
            case 0:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 1:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.red));
                break;
            case 2:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 3:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.purple));
                break;
            case 4:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.black));
                break;
            case 5:
                activityMainBinding.colorView.setTextColor(getResources().getColor(R.color.green));
                break;
        }


    }   // 색갈 변경 코드


    public void end(){
        activityMainBinding.colorView.setText("꽝 ! 게임오버");
        activityMainBinding.startbutton.setText("다시 시작");
        isRunning = false;
        startbutton = 3;
        point = 0;
        time = 4;
    }
    public void creal(){
        if(isRunning == true) {
            point = point + 1;
            activityMainBinding.pointview.setText("점수:");
            activityMainBinding.pointview.append(String.format("%d", point));
        }
    }

    public void button1(View view){
        if(textcolor == buttonx[0]){
            creal();
            buttonx[0] = 0;
        }else{
            end();
        }
        activityMainBinding.button.setBackgroundColor(getResources().getColor(R.color.gray));
    }
    public void button2(View view){
        if(textcolor == buttonx[1]){
            creal();
            buttonx[1] = 0;
        }else{
            end();
        }
        activityMainBinding.button2.setBackgroundColor(getResources().getColor(R.color.gray));

      }
    public void button3(View view){
        if(textcolor == buttonx[2]){
            creal();
            buttonx[2] = 0;
        }else{
           end();
        }
        activityMainBinding.button3.setBackgroundColor(getResources().getColor(R.color.gray));

    }
    public void button4(View view){
        if(textcolor == buttonx[3]){
            buttonx[3] = 0;
            creal();
        }else{
            end();

        }
        activityMainBinding.button4.setBackgroundColor(getResources().getColor(R.color.gray));


    }
    public void button5(View view){
        if(textcolor == buttonx[4]){
            buttonx[4] = 0;
            creal();
        }else{
            end();

        }
        activityMainBinding.button5.setBackgroundColor(getResources().getColor(R.color.gray));


    }
    public void button6(View view){
        if(textcolor == buttonx[5]){
            buttonx[5] = 0;
            creal();
        }else{
            end();

        }
        activityMainBinding.button6.setBackgroundColor(getResources().getColor(R.color.gray));

    }
    public void button7(View view){
        if(textcolor == buttonx[6]){
            buttonx[6] = 0;
            creal();
        }else{
            end();

        }
        activityMainBinding.button7.setBackgroundColor(getResources().getColor(R.color.gray));

    }
    public void button8(View view){
        if(textcolor == buttonx[7]){
            buttonx[7] = 0;
            creal();
        }else{
            end();
        }
        activityMainBinding.button8.setBackgroundColor(getResources().getColor(R.color.gray));

    }
    public void button9(View view){
        if(textcolor == buttonx[8]){
            buttonx[8] = 0;
            creal();
        }else{
            end();
        }
        activityMainBinding.button9.setBackgroundColor(getResources().getColor(R.color.gray));

    }

}