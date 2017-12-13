package com.omaoyu.omaoyutimer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.omaoyu.timer.Timer;


public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * 开始
     * */
    private Button btnStart;
    /**
     * 暂停
     * */
    private Button btnPause;
    /**
     * 停止
     * */
    private Button btnStop;
    /**
     * 内容展示
     * */
    private TextView tvContent;

    private static Handler handler;

    /**
     * 自定义时间处理器
     */
    private static Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
    }

    private void initView(){
        btnStart = findView(R.id.btn_start);
        btnPause = findView(R.id.btn_pause);
        btnStop = findView(R.id.btn_stop);
        tvContent = findView(R.id.tv_content);
    }
    private void initEvent(){
        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }
    private void initData(){
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        int time = (int) msg.obj;
                        tvContent.setText(time+"");
                        break;
                }
            }
        };
        timer = new Timer(handler,1000);
        timer.startTimer();
    }

    private <T extends View> T findView(int id){
        return (T) findViewById(id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.stopTimer();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                timer.startTimer();
                break;
            case R.id.btn_pause:
                if(btnPause.getText().toString().trim().equals(this.getString(R.string.pause))){
                    btnPause.setText(this.getString(R.string.carry_on));
                } else {
                    btnPause.setText(this.getString(R.string.pause));
                }
                timer.pause();
                break;
            case R.id.btn_stop:
                timer.stopTimer();
                break;
        }
    }
}
