package com.example.administrator.mediaplayer;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.rtp.AudioStream;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends Activity implements SurfaceHolder.Callback {

    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;
    @Bind(R.id.play_back)
    TextView playBack;

    private File videoFile;
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private int width;
    private int height;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();

        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().setFixedSize(width, height);
        //  surfaceView.getHolder().addCallback(this);
    }

    @OnClick(R.id.start_record)
    public void startRecording(View view) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "external storage does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            videoFile = generateTempoaryFile();
            boolean fuck = videoFile.exists();
            recorder = new MediaRecorder();
            recorder.reset();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setVideoFrameRate(24);
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            recorder.setOutputFile(videoFile.getAbsolutePath());
            recorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
            recorder.prepare();
            recorder.start();
            isRecording = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File generateTempoaryFile() throws IOException {
        File rootFile = Environment.getExternalStorageDirectory().getCanonicalFile();
        String dir = Environment.getExternalStorageDirectory().getCanonicalPath();
        String filename = System.currentTimeMillis() + Long.toString(System.currentTimeMillis()) + ".3gp";
        File file = new File(dir, filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    @OnClick(R.id.stop_record)
    public void stopRecording(View view) {
        if (isRecording) {
            isRecording = true;
            recorder.stop();
            recorder.release();
        }
    }

    @OnClick(R.id.play_back)
    public void playBack(View view) {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceView.getHolder());
        try {
            player.setDataSource(videoFile.getAbsolutePath());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
        videoFile.deleteOnExit();
    }
}
