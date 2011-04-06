package com.teamjava.tankwar.ui;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * User: rak
 */
public class GameView extends View
    implements View.OnTouchListener
{
    Paint paint = new Paint();

    private float x = 0;
    private float y = 0;

    public GameView(Context context)
    {
        super(context);

        this.setOnTouchListener(this);
        setBackgroundColor(R.color.black);

        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
	public void onDraw(Canvas canvas) {
	   canvas.drawRect(x,y, x + 20, y + 20, paint);
    }

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        x = motionEvent.getX();
        y = motionEvent.getY();
        invalidate();

        return true;
    }
}
