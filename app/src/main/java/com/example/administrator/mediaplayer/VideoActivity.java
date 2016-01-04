package com.example.administrator.mediaplayer;

import android.app.Activity;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/4.
 */
public class VideoActivity extends Activity {

    @Bind(R.id.video_view)
    VideoView videoView;
    @Bind(R.id.play_video)
    TextView play;
    @Bind(R.id.stop_video)
    TextView stop;

    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        //     MediaController controller = new MediaController();
        String path = "android.resource://" + getPackageName() + "/" + R.raw.eagle_beast;
        uri = Uri.parse(path);
    }

    @OnClick(R.id.play_video)
    public void play(View view) {
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @OnClick(R.id.stop_video)
    public void stop(View view){
        videoView.stopPlayback();
    }



}
