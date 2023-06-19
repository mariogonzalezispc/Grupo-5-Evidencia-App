package com.cdp.ecodoctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class activity_reproductor_video extends AppCompatActivity {

    private static final String VIDEO_SAMPLE = "ecoaliados";
    private VideoView mVideoView;

    private TextView messagge;

    private Uri getMedia(String mediaName) {
        return Uri.parse( "android.resource://"+getPackageName()+"/raw/"+mediaName);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_video);
        mVideoView = findViewById(R.id.videoV);

        if (savedInstanceState !=null) {
            mCurrentPosition=savedInstanceState.getInt(PLAYBACK_TIME);
        }

       /* int orientacion = getResources().getConfiguration().orientation;
        if (orientacion== Configuration.ORIENTATION_LANDSCAPE){
                    getSupportActionBar().hide();
        }else {
            getSupportActionBar().show();
        }*/

        messagge = findViewById(R.id.message_main);
        String msg = getIntent().getStringExtra("Mensaje");
        messagge.setText(msg);
        messagge.setVisibility(View.VISIBLE);



        MediaController mediaC = new MediaController(this);
        mediaC.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(mediaC);
        mediaC.setAnchorView(mVideoView);

        mVideoView.start();
    }

        private int mCurrentPosition = 0;
        private static final String PLAYBACK_TIME="play_time";

        private void initializePlayer(){
             Uri videoUri=getMedia(VIDEO_SAMPLE);
             //mVideoView.setVideoURI(videoURI);
             mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ecoaliados ));
            if (mCurrentPosition>0){
                 mVideoView.seekTo(mCurrentPosition);
            } else {
                /*saltar a 1, muestra el primer cuadro del video.*/
                 mVideoView.seekTo(1);
            }

            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(activity_reproductor_video.this,"Playback completed",
                            Toast.LENGTH_SHORT).show();
                    mVideoView.seekTo(1);
                }
            });
         }

        private void releasePlayer(){mVideoView.stopPlayback();}

        @Override
        protected void onStart(){
            super.onStart();
            initializePlayer();
        }

        @Override
        protected void onStop(){
            super.onStop();
            releasePlayer();
        }

        @Override
        protected void onPause(){
          super.onPause();
             if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
              mVideoView.pause();
              }
        }

        @Override
        protected void onSaveInstanceState(Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putInt(PLAYBACK_TIME,mVideoView.getCurrentPosition());
        }

}
