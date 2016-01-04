package com.example.administrator.mediaplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    @Bind(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<String> menu = new ArrayList<>();
        menu.add("sound");
        menu.add("video");
        menu.add("recorder");
        Adapter adapter = new Adapter(menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    private Class[] menus = {
            SoundActivity.class,
            VideoActivity.class,
            RecordActivity.class
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, menus[position]);
        startActivity(intent);
    }
}
