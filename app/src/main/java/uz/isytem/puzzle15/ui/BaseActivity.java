package uz.isytem.puzzle15.ui;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
//        if(shouldStopMedia()){
//            App.stopMedia();
//        }
        super.onDestroy();
        shouldStopMedia();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThemeData();
    }

    public abstract boolean shouldStopMedia();

    public abstract void setThemeData();
}
