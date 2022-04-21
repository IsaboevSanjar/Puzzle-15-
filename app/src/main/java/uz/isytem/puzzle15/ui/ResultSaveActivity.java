package uz.isytem.puzzle15.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;
import uz.isytem.puzzle15.core.models.UserData;

public class ResultSaveActivity extends BaseActivity implements View.OnClickListener {
    String name;
    int step = 0;
    int time = 0;
    private TextView timeView;
    private TextView stepView;

    private EditText editText;
    private ConstraintLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_save);
        editText = findViewById(R.id.name_input);
        timeView = findViewById(R.id.time_view);
        stepView = findViewById(R.id.step_view);
        frameLayout = findViewById(R.id.contest);
        /*UserData userData=MemoryHelper.getHelper().getData();
        timeView.setText("Time: "+userData.getTime());
        stepView.setText("Step: "+userData.getStep());
        editText.setText(userData.getName());*/
        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*time=(int) (Math.random()*100);
                step=(int)(Math.random()*100);*/
                name = editText.getText().toString();
                if (name.length() == 0) {
                    Toast.makeText(ResultSaveActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    MemoryHelper.getHelper().saveDate(new UserData(
                            name,
                            step,
                            time
                    ));
                    Toast.makeText(ResultSaveActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        step = intent.getIntExtra("step", 0);
        time = intent.getIntExtra("time", 0);
        setStep();
        setTime();
    }

    private void setTime() {
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;
        timeView.setText(String.format("Time : %02d:%02d:%02d", hour, minute, second));
    }

    private void setStep() {
        stepView.setText(String.format("Step :%d", step));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean shouldStopMedia() {
        return false;
    }

    @Override
    public void setThemeData() {
        frameLayout.setBackgroundResource(MemoryHelper.getHelper().getThemeId());
    }
}
/*
public class ResultShowActivity extends AppCompatActivity {
    private TextView stepResult, timeResult;
    private EditText editText;
    private int time;
    private int step;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_list);
        editText=findViewById(R.id.edit_text_save_user_name);
        stepResult=findViewById(R.id.result_list_text_step);
        timeResult=findViewById(R.id.result_list_text_time);


        findViewById(R.id.save_results_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=editText.getText().toString();
                SaveResultActivity.getHelper().saveData(new UserData(
                        name,step,time
                ));

            }
        });
        findViewById(R.id.home_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultShowActivity.this,StartActivity.class);
                finish();
                startActivity(intent);
            }
        });


        Intent intent=getIntent();
        step=intent.getIntExtra("step",0);
        time=intent.getIntExtra("time",0);
        setStep();
        setTime();
    }

    private void setTime() {
        int hour=time/3600;
        int minute=time%3600/60;
        int second=time%60;
        timeResult.setText(String.format("Time : %02d:%02d:%02d",hour,minute,second));
    }

    private void setStep() {
        stepResult.setText(String.format("Step :%d",step));
    }
}*/
