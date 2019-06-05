package com.example.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements android.widget.MediaController.MediaPlayerControl {

    int[] songs=new int[5];
    int[] images=new int[5];
    int currentsong=0;
    MediaPlayer mp;
    boolean continuous=true;
    Button btn1,btn2,btn3,btn4,btn5;
    ImageView iv;
    android.widget.MediaController mc;
    String status="Stopped";
    boolean indisplaymode=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp=new MediaPlayer();

        songs[0]=R.raw.song1;
        songs[1]=R.raw.song2;
        songs[2]=R.raw.song3;
        songs[3]=R.raw.song4;
        songs[4]=R.raw.song5;


        images[0]=R.drawable.ic_launcher_background;
        images[1]=R.drawable.ic_launcher_background;
        images[2]=R.drawable.ic_launcher_background;
        images[3]=R.drawable.ic_launcher_background;
        images[4]=R.drawable.ic_launcher_background;




        mc=new android.widget.MediaController(this);
        mc.setEnabled(true);
        mc.setMediaPlayer(this);

    }

    public void Display(int index){
        setContentView(R.layout.display);
        indisplaymode=true;
        iv=findViewById(R.id.iv);
        iv.setBackgroundResource(images[index]);
        iv.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent me){
                mc.show(10000);
                return true;
            }
        });

        mp.reset();
        mp=MediaPlayer.create(this,songs[index]);
        setController();
        ListenToCompletion(mp);

        mp.start();
        mc.show(10000);
        status="Playing";
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn1:
                mp.stop();
                currentsong=0;
                Display(currentsong);
                break;

            case R.id.btn2:
                mp.stop();
                currentsong=1;
                Display(currentsong);
                break;

            case R.id.btn3:
                mp.stop();
                currentsong=2;
                Display(currentsong);
                break;

            case R.id.btn4:
                mp.stop();
                currentsong=3;
                Display(currentsong);
                break;

            case R.id.btn5:
                mp.stop();
                currentsong=4;
                Display(currentsong);
                break;
//
//            case R.id.btnback:
//                mp.stop();
//                mp.reset();
//                currentsong=0;
//                setContentView(R.layout.main);

        }
    }

    @Override
    public void onBackPressed() {
        Log.i("Playing", status);
        if(indisplaymode){
            setContentView(R.layout.activity_main);
            indisplaymode=false;

        }else{
            mp.stop();
            finish();
        }
    }

    @Override
    public void start() {
        if(status=="Stopped"){
            mp=MediaPlayer.create(this,songs[currentsong]);
            setController();
        }
        mp.start();
        status="Playing";
    }

    @Override
    public void pause() {
        if(status=="Playing"){
            mp.pause();
            status="Paused";
        }
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }
    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }
    @Override
    public void seekTo(int i) {
        mp.seekTo(i);
    }
    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }
    @Override
    public int getBufferPercentage() {
        return 0;
    }
    @Override
    public boolean canPause() {
        return true;
    }
    @Override
    public boolean canSeekBackward() {

        return true;
    }
    @Override
    public boolean canSeekForward() {

        return true;
    }

    @Override
    public int getAudioSessionId()
    {
        return mp.getAudioSessionId();
    }

    public void playNext(){
        currentsong+=1;
        if(currentsong==4)
            currentsong=0;
        Display(currentsong);
    }

    public void playPrevious(){
        currentsong-=1;
        if(currentsong==-1)
            currentsong=0;
        Display(currentsong);

    }

    private void setController(){
        mc = new android.widget.MediaController(this);

        mc.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrevious();
            }
        });


        mc.setMediaPlayer(this);
        mc.setAnchorView(iv);
        mc.setEnabled(true);
    }


    public void ListenToCompletion(MediaPlayer mp){
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if(continuous){
                    currentsong++;
                    if (currentsong==4){
                        currentsong=0;
                    }
                    Display(currentsong);
                }
            }
        });
    }
}
