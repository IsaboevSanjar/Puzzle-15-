package uz.isytem.puzzle15.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;

public class StartActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout startButton, settingButton, resultButton, exitButton;
    private String TAG = "StartActivityTAG";
    private FrameLayout frameLayout;
    //    private  MediaPlayer audioPlayer;
    private ImageView infoView;
    private boolean musicOn = true;
/*    private void audioSetting(){
        if(MemoryHelper.getHelper().getSoundState()){
            audioPlayer.start();
            audioPlayer.setLooping(true);
        }else {
            audioPlayer.release();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG, "onCreate: ");
        frameLayout = findViewById(R.id.contest);
        startButton = findViewById(R.id.start_game);
        resultButton = findViewById(R.id.result_game);
        settingButton = findViewById(R.id.setting_game);
        exitButton = findViewById(R.id.exit_game);
        infoView = findViewById(R.id.info_view);
        infoView.setOnClickListener(this);

        startButton.setOnClickListener(this);
        resultButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        setThemeData();
//        audioPlayer=MediaPlayer.create(this,R.raw.pianoback);
//        audioSetting();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_game: {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.result_game: {
                Intent intent = new Intent(StartActivity.this, ResultActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_game: {
                Intent intent = new Intent(StartActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.exit_game: {
                finish();
                break;
            }
            case R.id.info_view: {
                Intent intent = new Intent(StartActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public boolean shouldStopMedia() {
        return musicOn;
    }

    @Override
    public void setThemeData() {
        frameLayout.setBackgroundResource(MemoryHelper.getHelper().getThemeId());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}