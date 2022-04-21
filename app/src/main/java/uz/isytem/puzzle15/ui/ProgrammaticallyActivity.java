package uz.isytem.puzzle15.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import uz.isytem.puzzle15.R;

public class ProgrammaticallyActivity extends AppCompatActivity {

    private LinearLayout rootGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmically);
        rootGroup = findViewById(R.id.root_layout);
        for (int i = 1; i <= 10; i++) {
            Button button = new Button(this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            button.setText("Button " + i);
            button.setLayoutParams(param);
            rootGroup.addView(button);
        }
    }
}