package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.text);
        final Handler handler = new Handler(){
            @Override
          public void handleMessage(Message message){
                textView.setText(message.arg1 + "");
            }
        };
        final Runnable myWork = new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while(progress<=100){
                    Message message = new Message();
                    message.arg1 = progress;
                    progress += 10;
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Message message = handler.obtainMessage();
                message.arg1 = -1;
                handler.sendMessage(message);
            }
        };
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(null,myWork,"WorkThread");
                handler.post(thread);
                thread.start();
            }
        });
    }
}
