package com.thursday.badgeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.thursday.badgeview.view.BadgeView;

public class MainActivity extends AppCompatActivity {
    private TextView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        badgeView= (TextView) findViewById(R.id.text);

        BadgeView badge = new BadgeView(this);
        badge.setTargetView(badgeView);
        badge.setBadgeCount(42);
        badge.setOnClickListener(v -> {
            Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();

        });
    }
}
