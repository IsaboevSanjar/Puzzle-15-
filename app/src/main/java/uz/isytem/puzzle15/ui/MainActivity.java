package uz.isytem.puzzle15.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final ArrayList<Integer> numbers = new ArrayList<>();
    private final Button[][] buttons = new Button[4][4];
    boolean isPaused = false;
    private TextView stepView;
    private TextView timeView;
    private LinearLayout restart;
    private LinearLayout pause;
    private LinearLayout exit;
    private TextView pausedView, stopView;
    private Timer timer;
    private int emptyI = 3;
    private int emptyJ = 3;
    private RelativeLayout buttonGroup;
    private int step = 0;
    private int time = 0;
    private FrameLayout relative, frameLayout;
    private MediaPlayer mediaPlayer;

    //    private Timer timing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadViews();
        if (savedInstanceState != null) {
            loadLastDate(savedInstanceState);
        } else {
            loadNumbers();
            setDataToView();
            setListeners();
        }
        setThemeData();

        /*audioPlayer.release();*/

        createTimer();
        setStopListener();
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_button);


    }

    private void loadLastDate(Bundle bundle) {
        time = bundle.getInt("time");
        setTime(time);
        step = bundle.getInt("step");
        setStep();
        emptyI = bundle.getInt("emptyI");
        emptyJ = bundle.getInt("emptyJ");
        buttons[3][3].setBackgroundResource(R.drawable.fillbutton);
        buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.emtpty_button);
        ArrayList<String> buttonText = bundle.getStringArrayList("buttons");
        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4].setText(buttonText.get(i));
        }
    }

    private void setListeners() {
        restart.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           restartGame();
                                       }
                                   }
        );
        //Result oynasi uchun mahsus step va time


    }

    private void setStopListener() {
        stopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
                onBackPressed();
                //showAlertStop();
            }
        });
    }

    private void createTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        setTime(time);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    private void setDataToView() {
        setStep();
        for (int i = 0; i < 15; i++) {
            Button button = buttons[i / 4][i % 4];
            button.setText(String.valueOf(numbers.get(i)));
            button.setBackgroundResource(R.drawable.fillbutton);
        }
        buttons[emptyI][emptyJ].setText("");
        buttons[emptyI][emptyJ].setBackgroundResource(R.drawable.emtpty_button);


    }

    private void loadViews() {
        exit = findViewById(R.id.exit_game);
        pause = findViewById(R.id.pause_btn);
        restart = findViewById(R.id.restart_btn);
        stepView = findViewById(R.id.step_view);
        timeView = findViewById(R.id.time_view);
        buttonGroup = findViewById(R.id.btn_group);
        relative = findViewById(R.id.pause_view);
        pausedView = findViewById(R.id.paused_btn);
        frameLayout = findViewById(R.id.contest);
        stopView = findViewById(R.id.stop_pause);
        /* 00 01 02 03;
          10 11 12 13*/
        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4] = (Button) buttonGroup.getChildAt(i);
        }

    }

    public void loadNumbers() {
        numbers.clear();
        for (int i = 1; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }

    public void buttonClick(View view) {

        Button button = (Button) view;

        String tag = button.getTag().toString();

        String[] indexes = tag.split(":");

        int i = Integer.parseInt(indexes[0]);

        int j = Integer.parseInt(indexes[1]);

        int deltaI = Math.abs(i - emptyI);
        int deltaJ = Math.abs(j - emptyJ);

        if ((deltaI == 1 && deltaJ == 0) || (deltaI == 0 && deltaJ == 1)) {

            buttons[emptyI][emptyJ].setText(button.getText());
            buttons[emptyI][emptyJ].setBackground(button.getBackground());

            button.setText("");
            button.setBackgroundResource(R.drawable.emtpty_button);

            emptyI = i;
            emptyJ = j;
            step++;
            setStep();
            if (MemoryHelper.getHelper().getSoundStateBtn()) {
                mediaPlayer.start();
            }

            if (emptyI == 3 && emptyJ == 3) {
                checkToWin();
            }

        }

//        1:1
//        1:0  0:1


//        0:0
//         0:1 1:0  0:2  3:3

    }

    private void setStep() {

        stepView.setText(String.format("Steps\uD83D\uDC63: %d", step));
        //find qilib set qilasiz
    }

    private void setTime(int time) {
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;

        timeView.setText(String.format("⌛%02d:%02d:%02d", hour, minute, second));

    }

    private void checkToWin() {

        boolean isWin = true;

        for (int i = 0; i < 15; i++) {
            Button button = (Button) buttonGroup.getChildAt(i);
            if (!button.getText().toString().equals(i + 1 + "")) {
                isWin = false;
                break;
            }
        }

        if (isWin) {
            Intent intent = new Intent(this, ResultSaveActivity.class);
            intent.putExtra("time", time);
            intent.putExtra("step", step);
            startActivity(intent);
            finish();
        }

    }

    private void restartGame() {
        stopTimer();
        time = 0;
        step = 0;
        emptyJ = 3;
        emptyI = 3;
        setTime(time);
        setStep();
        createTimer();
        loadNumbers();
        setDataToView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("step", step);
        outState.putInt("time", time);
        outState.putInt("emptyI", emptyI);
        outState.putInt("emptyJ", emptyJ);
        ArrayList<String> buttonText = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            buttonText.add(buttons[i / 4][i % 4].getText().toString());
        }
        outState.putStringArrayList("buttons", buttonText);
        super.onSaveInstanceState(outState);

    }

    public void onClickRestart(View view) {
        restartGame();
    }

    public void onClickPause(View view) {
        changeStateGame();
    }

    public void onClickPaused(View view) {
        changeStateGame();
        /*if(!carrying){
            exit.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            buttonGroup.setVisibility(View.VISIBLE);
            relative.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            timer.cancel();
            createTiming();
        }else {
            relative.setVisibility(View.VISIBLE);
            buttonGroup.setVisibility(View.INVISIBLE);
            restart.setVisibility(View.INVISIBLE);
            exit.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.INVISIBLE);
            timing.cancel();
        }
        carrying=!carrying;*/
    }

    private void changeStateGame() {
        if (!isPaused) {
            relative.setVisibility(View.VISIBLE);
            buttonGroup.setVisibility(View.INVISIBLE);
            restart.setVisibility(View.INVISIBLE);
            exit.setVisibility(View.INVISIBLE);
            pause.setVisibility(View.INVISIBLE);
            stopTimer();
        } else {
            pause.setVisibility(View.VISIBLE);
            exit.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            buttonGroup.setVisibility(View.VISIBLE);
            relative.setVisibility(View.GONE);
            createTimer();
        }
        isPaused = !isPaused;
    }

    public void showAlertStop() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop!!!")
                .setMessage("Are you sure to stop?\nIf you are, the game will be restarted.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create()
                .show();
    }

    public void onClickExit(View view) {
        showAlert();
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit!!!")
                .setMessage("Are you sure to back?\nIf you are, the game will be restarted.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create()
                .show();
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

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createTimer();
    }
}