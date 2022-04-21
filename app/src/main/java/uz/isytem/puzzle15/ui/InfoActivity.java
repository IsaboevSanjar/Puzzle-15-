package uz.isytem.puzzle15.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.cache.MemoryHelper;


public class InfoActivity extends BaseActivity {
    private ImageView exitGame;
    private LinearLayout frameLayout, telegram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        exitGame = findViewById(R.id.exit_game);
        frameLayout = findViewById(R.id.contest);
        telegram = findViewById(R.id.telegram);
        setListeners();
    }

    private void setListeners() {
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://t.me/sanjar_isaboev");
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public boolean shouldStopMedia() {
        return false;
    }

    @Override
    public void setThemeData() {
        frameLayout.setBackgroundResource(MemoryHelper.getHelper().getThemeId());
    }

    /*public static void openTelegram(InfoActivity activity, String userName) {
        Intent general = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.com/" + "sanjar_isaboev"));
        HashSet<String> generalResolvers = new HashSet<>();
        List<ResolveInfo> generalResolveInfo = activity.getPackageManager().queryIntentActivities(general, 0);
        for (ResolveInfo info : generalResolveInfo) {
            if (info.activityInfo.packageName != null) {
                generalResolvers.add(info.activityInfo.packageName);
            }
        }

        Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + "sanjar_isaboev"));
        int goodResolver = 0;
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(telegram, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName != null && !generalResolvers.contains(info.activityInfo.packageName)) {
                    goodResolver++;
                    telegram.setPackage(info.activityInfo.packageName);
                }
            }
        }
        //TODO: if there are several good resolvers create custom chooser
        if (goodResolver != 1) {
            telegram.setPackage(null);
        }
        if (telegram.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(telegram);
        }
    }*/
}