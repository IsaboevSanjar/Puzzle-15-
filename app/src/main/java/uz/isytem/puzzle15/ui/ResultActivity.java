package uz.isytem.puzzle15.ui;

import static android.graphics.Color.YELLOW;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;

import java.util.ArrayList;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;
import uz.isytem.puzzle15.core.models.UserData;

public class ResultActivity extends BaseActivity {
    private LinearLayout rootGroup;
    private ConstraintLayout frameLayout;
    private LinearLayout back;
    private ImageView backI;
    private TextView backT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        rootGroup = findViewById(R.id.result_group);
        frameLayout = findViewById(R.id.contest);
        back = findViewById(R.id.back_result);
        backI = findViewById(R.id.back_result_2);
        backT = findViewById(R.id.back_result_3);
        ArrayList<UserData> list = MemoryHelper.getHelper().getLastResults();
        for (UserData data : list) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResultActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            backI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            backT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            StringBuilder builder = new StringBuilder();
            builder.append("<b>&#160&#160Name:</b> &#160");
            builder.append(data.getName());
            builder.append("<br>&#160&#160<b>Time:</b> ");
            builder.append(getTime(data.getTime()));
            builder.append("&#160&#160<b>Step:</b> ");
            builder.append(data.getStep());
            textView.setText(builder.toString());
            textView.setTextColor(YELLOW);
            textView.setText(HtmlCompat.fromHtml(builder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY));

            textView.setLayoutParams(param);
            int size = getResources().getDimensionPixelSize(R.dimen.textSize);
            textView.setTextSize(size);
            rootGroup.addView(textView);
        }
    }

    private String getTime(int time) {
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;

        return (String.format("⌛%02d:%02d:%02d", hour, minute, second));

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