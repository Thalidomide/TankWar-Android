package com.teamjava.tankwar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.teamjava.tankwar.ui.GameView;

public class MainActivity
    extends Activity
        implements SeekBar.OnSeekBarChangeListener
{
    private TextView titleAngleBarView;
    private TextView titleLastActionView;

    /**
     * Called when the activity is first created.
     * */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initTitlePanelView();
        initGamePanelView();
        initGameControllerView();
    }

    /**
     * This is the title panel of this game.
     * todo implement widgets when we know what we want. :)
     */
    private void initTitlePanelView()
    {
        LinearLayout titlePanelView =
            (LinearLayout) findViewById(R.id.layout_title_panel);

        titleAngleBarView = (TextView) titlePanelView.
                findViewById(R.id.id_title_panel_angle_view);

        titleLastActionView = (TextView) titlePanelView.
            findViewById(R.id.id_title_panel_last_action_view);
    }

    /**
     * This is the game panel of this game.
     * All gaming graphics will be used in this view.
     */
    private void initGamePanelView()
    {
        LinearLayout gamePanelView =
            (LinearLayout) findViewById(R.id.layout_game_panel);
        GameView gameView = new GameView(this);

        gamePanelView.addView(gameView);
    }

    /**
     * This is the game controller panel of this game.
     * todo implement widgets when we know what we want. :)
     */
    private void initGameControllerView()
    {
        LinearLayout gameControllerPanelView =
            (LinearLayout) findViewById(R.id.layout_game_control_panel);

        SeekBar angleBar =
            (SeekBar) gameControllerPanelView.findViewById(R.id.id_angle_bar);
        angleBar.setOnSeekBarChangeListener(this);

        Button gameControllerLeftButton =
            (Button) gameControllerPanelView.
                findViewById(R.id.id_controller_left_button);

        gameControllerLeftButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                titleLastActionView.setText("Last action: left");
            }
        });

        Button gameControllerRightButton =
            (Button) gameControllerPanelView.
                findViewById(R.id.id_controller_right_button);

        gameControllerRightButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                titleLastActionView.setText("Last action: right");
            }
        });

        Button gameControllerFireButton =
            (Button) gameControllerPanelView.
                findViewById(R.id.id_controller_fire_button);

        gameControllerFireButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                titleLastActionView.setText("Last action: fire");
            }
        });
    }


    /**
     * This method will inflate the main_view_menu.xml and add this as the
     * menu for this Activity.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_view_menu, menu);

        return true;
    }


    /**
     * This method will handle the event triggered by the user selection
     * on menu item from the menu.
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.main_view_menu_new_game:
               displayToast("New game comming soon!");
                return true;

            case R.id.main_view_menu_exit:
                exitApplication();
                return true;

            case R.id.main_view_menu_help:
                {
                    final String title = "Tank War help";
                    final String displayText = "Help!<br> " +
                        "More help will be displayed here.<br>" +
                        "And here.";

                    displayAboutDialog(title, displayText);
                }
                return true;

            case R.id.main_view_menu_about:
                {
                    final String title = "About Tank War!";
                    final String displayText = "Hello!<br> " +
                        "This is the great Tank War game! <br><br>" +
                        "Created by Team Java!";

                    displayAboutDialog(title, displayText);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method will display a dialog with title and body text provided
     * by parameters.
     *
     * @param title the title of this dialog.
     * @param bodyText the body text of this dialog.
     * */
    private void displayAboutDialog(String title, String  bodyText)
    {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.about_dialog);
        dialog.setTitle(title);

        TextView textView = (TextView)
            dialog.findViewById(R.id.about_dialog_text_view);
        textView.setText(Html.fromHtml(bodyText));

        dialog.show();
    }

    /*
    * Show a dialog to confirm that the user want to exit this application.
    * The user will get two options, yes and no.
    * Pressing "Yes" will exit this application.
    * Pressing "No" will close this dialog.
    * */
    private void exitApplication()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    MainActivity.this.finish();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.cancel();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Display a Toast. A toast is a message that will be displayed in the
     * phone, on top of all application.
     *
     * @param text the text to be displayed.
     * */
    private void displayToast(String text){
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
    }

    public void onProgressChanged(SeekBar seekBar, int angle, boolean b)
    {
        Spanned spanned = Html.fromHtml("Angle = <u>" + angle + "</u>");
        titleAngleBarView.setText(spanned);
    }

    public void onStartTrackingTouch(SeekBar seekBar)
    {
        // todo, not need atm.
    }

    public void onStopTrackingTouch(SeekBar seekBar)
    {
        // todo, not need atm.
    }
}
