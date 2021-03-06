package com.teamjava.tankwar;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamjava.tankwar.engine.RobotCommunicator;
import com.teamjava.tankwar.entities.Robot;
import com.teamjava.tankwar.ui.GameView;

public class MainActivity
    extends Activity
        implements SeekBar.OnSeekBarChangeListener
{
    private TextView titleAngleBarView;
    private TextView selectedPowerView;
    private TextView bombStrengthView;

    // A listener for the Robot(Tank).
    private RobotCommunicator robotCommunicator;
    private int selectedPower = 5;
    private float zoomLevel = 3f;
    private TextView titleZoomView;
    private DecimalFormat zoomTextFormat = new DecimalFormat("#,##0.00");
	private GameView gameView;

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
     * This method will be called whenever a Seekbar has changed.
     * We are using only one Seekbar at the moment, so no need to distinguish
     * SeekBars, just pass the angle to other methods.
     * */
    public void onProgressChanged(SeekBar seekBar, int angle, boolean b)
    {
         setTitleAngleText(Integer.toString(angle));
         robotCommunicator.robotTurretAngleChanged(angle);
    }

    public void onStartTrackingTouch(SeekBar seekBar)
    {
        // todo, not need atm.
    }

    public void onStopTrackingTouch(SeekBar seekBar)
    {
        // todo, not need atm.
    }

     /**
     * This is the title panel of this game.
     * todo implement widgets when we know what we want. :)
     */
    private void initTitlePanelView()
    {
        LinearLayout titlePanelView =
            (LinearLayout) findViewById(R.id.layout_title_panel);

        titleZoomView = (TextView) titlePanelView.
            findViewById(R.id.id_title_panel_zoom_view);

        updateZoomText();

        titleAngleBarView = (TextView) titlePanelView.
            findViewById(R.id.id_title_panel_angle_view);

        selectedPowerView = (TextView) titlePanelView.
            findViewById(R.id.id_title_panel_last_action_view);

		bombStrengthView = (TextView) titlePanelView.findViewById(R.id.id_title_panel_bomb_strength_view);
    }

    private void updateZoomText()
    {
        String formatedZoomLevel = zoomTextFormat.format(zoomLevel);

        String playerText = "<b>Zoom: </b><u>" + formatedZoomLevel + "</u>";
        titleZoomView.setText(Html.fromHtml(playerText));
    }

    /**
     * Update the selected power view in title view.
     *
     * @param powerAsString text to be displayed
     */
    private void setSelectedPowerText(String powerAsString)
    {
        String lastActionText = "<b>Fire power: </b><u>" + powerAsString + "</u>";
        selectedPowerView.setText(Html.fromHtml(lastActionText));
    }

	private void setSelectedBombStrengthText(int strength) {
		String lastActionText = "<b>Bomb strength: </b><u>" + strength + "</u>";
        bombStrengthView.setText(Html.fromHtml(lastActionText));
	}

    /**
     * Update the angle view in title view.
     *
     * @param angle the turrets angle
     */
    private void setTitleAngleText(String angle)
    {
        String angleText = "<b>Turret angle: </b><u>" + angle + "</u> &deg;";
        titleAngleBarView.setText(Html.fromHtml(angleText));
    }

    /**
     * This is the game panel of this game.
     * All gaming graphics will be used in this view.
     */
    private void initGamePanelView()
    {
        LinearLayout gamePanelView =
            (LinearLayout) findViewById(R.id.layout_game_panel);
		gameView = new GameView(this);
        robotCommunicator = gameView;

        gamePanelView.addView(gameView);

		displayInitialGameValues();
    }

	private void displayInitialGameValues() {
		setSelectedBombStrengthText(gameView.getRobotPlayer().getBombStrength());
		setTitleAngleText("0");
		setSelectedPowerText("5");
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

        ImageView gameControllerPowerButton =
            (ImageView) gameControllerPanelView.
                findViewById(R.id.id_controller_power_button);
        gameControllerPowerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (selectedPower >= 6) {
                    selectedPower = 1;
                }
                else {
                    selectedPower ++;
                }
                setSelectedPowerText(Integer.toString(selectedPower));
                robotCommunicator.robotSetFirePower(selectedPower);
            }
        });

		ImageView gameControllerBombStrengthButton = (ImageView) gameControllerPanelView. findViewById(R.id.id_controller_bombstrength_button);
        gameControllerBombStrengthButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
				Robot robotPlayer = gameView.getRobotPlayer();
				robotPlayer.increaseBombStrength();

                setSelectedBombStrengthText(robotPlayer.getBombStrength());
                robotCommunicator.robotSetFirePower(selectedPower);
            }
        });

        ImageView gameControllerZoomButton =
            (ImageView) gameControllerPanelView.
                findViewById(R.id.id_controller_zoom_button);

        gameControllerZoomButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (zoomLevel >= 5) {
                    zoomLevel = 0.2f;
                }
                else {
                  zoomLevel = zoomLevel * 1.1f;
                }

                updateZoomText();
                robotCommunicator.setWorldZoomLevel(zoomLevel);
            }
        });

        ImageView gameControllerLeftButton =
            (ImageView) gameControllerPanelView.
                findViewById(R.id.id_controller_left_button);

        gameControllerLeftButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                robotCommunicator.robotMoveLeft();
            }
        });

        ImageView gameControllerRightButton =
            (ImageView) gameControllerPanelView.
                findViewById(R.id.id_controller_right_button);

        gameControllerRightButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                robotCommunicator.robotMoveRight();
            }
        });

        ImageView gameControllerFireButton =
            (ImageView) gameControllerPanelView.
                findViewById(R.id.id_controller_fire_button);

        gameControllerFireButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                robotCommunicator.robotFire();
            }
        });
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
}
