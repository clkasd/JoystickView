package com.developwear.joystickview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Aykut on 05/11/15.
 */
public class JoystickView extends View {
    Bitmap background;
    Bitmap joystick;
    private int mDrawBitmapWidth = 600; //!< Default width of view
    private int mDrawBitmapHeight = 600; //!< Default height of view
    private int mDrawJoystickWidth = (mDrawBitmapWidth / 10) * 6; //!< Default width of view
    private int mDrawJoystickHeight = (mDrawBitmapHeight / 10) * 6; //!< Default height of vie
    private Rect mDrawRect;
    private Paint mDrawingPaint;
    int centerX;
    int centerY;
    Rect inner_joy;

    int absX;
    int absY;
    int boundX=(mDrawJoystickWidth/2);
    int boundY=(mDrawJoystickHeight/2);
    String TAG = this.getClass().getName();
    boolean isBeingDragged = false;
    int scale=(mDrawBitmapWidth-mDrawJoystickWidth)/2;
    int power=0;
    int degree=0;
    public JoystickView(Context context) {
        super(context);
        init();
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.joystick_outer);
        joystick = BitmapFactory.decodeResource(getResources(), R.drawable.joystick_inner);
        mDrawingPaint = new Paint();
        mDrawingPaint.setAntiAlias(true);
        mDrawingPaint.setDither(true);
        mDrawingPaint.setFilterBitmap(true);
        mDrawRect = new Rect(0, 0, mDrawBitmapWidth, mDrawBitmapHeight);
        centerX = mDrawBitmapWidth / 2 - mDrawJoystickWidth / 2;
        centerY = mDrawBitmapHeight / 2 - mDrawJoystickHeight / 2;
        inner_joy = new Rect(centerX, centerY, mDrawBitmapWidth - centerX, mDrawBitmapHeight - centerY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Get size requested and size mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height = 0;

        //Determine Width
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(mDrawBitmapWidth, widthSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                width = mDrawBitmapWidth;
                break;
        }

        //Determine Height
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(mDrawBitmapHeight, heightSize);
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                height = mDrawBitmapHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawBitmapWidth = w; //!< Default width of view
        mDrawBitmapHeight = h; //!< Default height of view
        mDrawJoystickWidth = (mDrawBitmapWidth / 10) * 6; //!< Default width of view
        mDrawJoystickHeight = (mDrawBitmapHeight / 10) * 6; //!< Default height of vie
        mDrawRect = new Rect(0, 0, mDrawBitmapWidth, mDrawBitmapHeight);
        centerX = mDrawBitmapWidth / 2 - mDrawJoystickWidth / 2;
        centerY = mDrawBitmapHeight / 2 - mDrawJoystickHeight / 2;
        inner_joy = new Rect(centerX, centerY, mDrawBitmapWidth - centerX, mDrawBitmapHeight - centerY);
        absX=0;
        absY=0;
        boundX=(mDrawJoystickWidth/2);
        boundY=(mDrawJoystickHeight/2);
        scale=(mDrawBitmapWidth-mDrawJoystickWidth)/2;
        mDrawRect.set(0, 0, w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawBitmap(background, null, mDrawRect, mDrawingPaint);
        canvas.drawBitmap(joystick, null, inner_joy, mDrawingPaint);
        absX=0;
        absY=0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            isBeingDragged = true;
            power=0;
            degree=0;
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            isBeingDragged = false;
            absX=0;
            absY=0;
            inner_joy = new Rect(centerX, centerY, mDrawBitmapWidth - centerX, mDrawBitmapHeight - centerY);
            this.invalidate();
            power=0;
            degree=0;
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            if (isBeingDragged) {
                int xPosition = (int) event.getX();
                int yPosition = (int) event.getY();
                if (xPosition < mDrawJoystickWidth / 2)
                    xPosition = mDrawJoystickWidth / 2;
                else if (xPosition > (mDrawBitmapWidth - mDrawJoystickWidth / 2))
                    xPosition = (mDrawBitmapWidth - mDrawJoystickWidth / 2);
                if (yPosition < mDrawJoystickWidth / 2)
                    yPosition = mDrawJoystickWidth / 2;
                else if (yPosition > (mDrawBitmapHeight - mDrawJoystickHeight / 2))
                    yPosition = (mDrawBitmapHeight - mDrawJoystickHeight / 2);
                int left = xPosition - mDrawJoystickWidth / 2;
                int top = yPosition - mDrawJoystickHeight / 2;
                int right = inner_joy.left + mDrawJoystickWidth;
                int bottom = inner_joy.top + mDrawJoystickHeight;
                inner_joy.left = left;
                inner_joy.right = right;
                inner_joy.bottom = bottom;
                inner_joy.top = top;
                absX=xPosition-boundX-(mDrawBitmapWidth-mDrawJoystickWidth)/2;
                absY=yPosition-boundY-(mDrawBitmapHeight-mDrawJoystickHeight)/2;
                absY=absY*-1;

                double hypo=Math.sqrt(Math.pow(absX, 2)+Math.pow(absY,2));
                double doublePower=(hypo/scale)*100;
                power=(int)doublePower>100?100:(int)doublePower;
                degree=(int)Math.toDegrees(Math.atan2(absY, absX));
                this.invalidate();

            }
        } else {
            isBeingDragged = false;
            absX=0;
            absY=0;
            inner_joy = new Rect(centerX, centerY, mDrawBitmapWidth - centerX, mDrawBitmapHeight - centerY);
            power=0;
            degree=0;
            this.invalidate();
        }

        if(joystickChangeListener!=null)
            joystickChangeListener.onJoystickChanged(power,degree);
        return true;
    }

    public interface JoystickChangeListener
    {
        void onJoystickChanged(int power,int degree);
    }
    JoystickChangeListener joystickChangeListener;
    public void setJoystickChangeListener(JoystickChangeListener listener)
    {
        joystickChangeListener=listener;
    }
}
