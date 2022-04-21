package uz.isytem.puzzle15.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.app.App;
import uz.isytem.puzzle15.core.cache.MemoryHelper;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch musicSwitch, nightSwitch, soundBtnSwitch;
    private Button clearButton;
    private RadioButton theme1, theme2;
    private FrameLayout frameLayout;
    private LinearLayout backButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backButton = findViewById(R.id.back_btn);
        musicSwitch = findViewById(R.id.sound_switch);
        //nightSwitch=findViewById(R.id.night_switch);
        clearButton = findViewById(R.id.clear_button);
        soundBtnSwitch = findViewById(R.id.sound_btn_switch);
        frameLayout = findViewById(R.id.contest);

        theme1 = findViewById(R.id.theme1);
        theme2 = findViewById(R.id.theme2);
        setThemeData();
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_button);

        setLastDate();
        setListeners();

    }

    public void onClickBack(View view) {
        finish();
    }

    private void setListeners() {
        soundBtnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MemoryHelper.getHelper().setSoundStateBtn(b);
                mediaPlayer.start();
                setLastDate();
            }
        });
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MemoryHelper.getHelper().setSoundState(b);
                if (b) {
                    App.startMedia();
                    mediaPlayer.start();
                } else {
                    App.stopMedia();
                }
                setLastDate();
            }
        });

 /*       nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MemoryHelper.getHelper().setThemeState(b);
                setLastDate();
            }
        });*/
        theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemoryHelper.getHelper().setThemeId(R.drawable.again_pexels);
                setThemeData();
                mediaPlayer.start();
            }
        });
        theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemoryHelper.getHelper().setThemeId(R.drawable.wood);
                setThemeData();
                mediaPlayer.start();
            }
        });
    }

    private void setLastDate() {
        /*boolean isNight=MemoryHelper.getHelper().getThemeState();
        nightSwitch.setChecked(isNight);
        if (isNight){
            nightSwitch.setText(getString(R.string.night_on));
        }else {
            nightSwitch.setText(getString(R.string.night_off));
        }*/
        boolean isMusic = MemoryHelper.getHelper().getSoundState();
        musicSwitch.setChecked(isMusic);
        if (isMusic) {
            musicSwitch.setText(getString(R.string.sound_on));
        } else {
            musicSwitch.setText(getString(R.string.sound_off));
        }
        boolean isSoundBtn = MemoryHelper.getHelper().getSoundStateBtn();
        soundBtnSwitch.setChecked(isSoundBtn);
        if (isSoundBtn) {
            soundBtnSwitch.setText(getString(R.string.sound_on));
        } else {
            soundBtnSwitch.setText(getString(R.string.sound_off));
        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cleaning!!!")
                .setMessage("Are you sure to clean all the data?");

        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MemoryHelper.getHelper().clearData();
                Toast.makeText(SettingActivity.this, "Cleaned", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingActivity.this, "not cleaned", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create()
                .show();
    }

    public void onClickClear(View view) {
        mediaPlayer.start();
        showAlert();
    }

    @Override
    public boolean shouldStopMedia() {
        return false;
    }

    @Override
    public void setThemeData() {
        frameLayout.setBackgroundResource(MemoryHelper.getHelper().getThemeId());
    }

    @Override
    public void onClick(View view) {

    }
}