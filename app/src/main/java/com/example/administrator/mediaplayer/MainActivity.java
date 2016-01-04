package com.example.administrator.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Runnable, SeekBar.OnSeekBarChangeListener {


    @Bind(R.id.play)
    TextView play;
    @Bind(R.id.pause)
    TextView pause;
    @Bind(R.id.stop)
    TextView stop;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int currrentPosition = 0;
    private Thread thread = new Thread(this);
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            seekBar.setProgress(msg.what);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.bandari_annie_song);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(this);
        thread.start();
    }

    @OnClick(R.id.play)
    public void play(View view) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.bandari_annie_song);

        mediaPlayer.start();
    }

    @OnClick(R.id.resume)
    public void resume(View view) {
        mediaPlayer.seekTo(currrentPosition);
        mediaPlayer.start();
    }

    @OnClick(R.id.pause)
    public void pause(View view) {
        mediaPlayer.pause();
        currrentPosition = mediaPlayer.getCurrentPosition();
    }

    @OnClick(R.id.stop)
    public void stop(View view) {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    @Override
    public void run() {
        try {
            while (mediaPlayer != null) {
                int currrentPosition = mediaPlayer.getCurrentPosition();
                Message message = new Message();
                message.what = currrentPosition;
                handler.sendMessage(message);
            }
            thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekBar.setProgress(progress);
        if (mediaPlayer != null && fromUser) {
            mediaPlayer.seekTo(progress);
            seekBar.setProgress(progress);
            mediaPlayer.start();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
