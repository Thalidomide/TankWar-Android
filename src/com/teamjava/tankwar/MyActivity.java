package com.teamjava.tankwar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.teamjava.tankwar.ui.GameView;

public class MyActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout mainRootView =
            (LinearLayout) findViewById(R.id.layout_root);
        GameView gameView = new GameView(this);

        mainRootView.addView(gameView);
    }
}
