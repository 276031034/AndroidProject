package com.sh.shprojectdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jerei.im.timchat.model.Message;
import com.jerei.im.timchat.model.VoiceMessage;


import com.jereibaselibrary.tools.JRMediaUtil;
import com.jereibaselibrary.tools.JRRecorderUtil;
import com.jruilibarary.widget.VoiceSendingView;
import com.sh.shprojectdemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AudioRecordActivity extends AppCompatActivity {
    private VoiceSendingView voiceSendingView;
    Button button;
    TextView audioPath;
    private JRRecorderUtil recorder = new JRRecorderUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        button = (Button) findViewById(R.id.button);
        audioPath = (TextView) findViewById(R.id.audio_path);
        voiceSendingView = (VoiceSendingView) findViewById(R.id.voice_sending);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下 开始录像
                        button.setText("松开结束");
                        voiceSendingView.setVisibility(View.VISIBLE);
                        voiceSendingView.showRecording();
                        recorder.startRecording();

                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起 停止录像
                        button.setText("按住说话");
                        endSendVoice();
                        break;

                }
                return true;
            }

        });
    }

    /**
     * 结束发送语音消息
     */
    String path = "";
    public void endSendVoice() {
        voiceSendingView.release();
        voiceSendingView.setVisibility(View.GONE);
        recorder.stopRecording();
        if (recorder.getTimeInterval() < 1) {
            Toast.makeText(this, getResources().getString(com.jerei.im.timchat.R.string.chat_audio_too_short), Toast.LENGTH_SHORT).show();
        }
        path = recorder.getFilePath();
        audioPath.setText( path);
        audioPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JRMediaUtil.getInstance().play(path);
            }
        });


    }
}
