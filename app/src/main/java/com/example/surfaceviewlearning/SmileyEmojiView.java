package com.example.surfaceviewlearning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SmileyEmojiView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "SmileyEmojiView";
    Path path;
    private SurfaceHolder holder;
    private boolean isAnimating;
    private int controlY;
    private int controlX;
    private Paint mouthBlackPaint;
    private boolean isHappy = true;
    private AnimationListener listener;

    public SmileyEmojiView(Context context) {
        super(context);
        Log.i(TAG, "SmileyEmojiView: constructor called");
        holder = getHolder();
        holder.addCallback(this);
    }

    public SmileyEmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            controlX = canvas.getWidth() / 2;
            controlY = canvas.getHeight() / 4 + 150;
            mouthBlackPaint = new Paint();
            mouthBlackPaint.setStyle(Paint.Style.STROKE);
            mouthBlackPaint.setStrokeWidth(5);
            // Set the canvas background to white
            canvas.drawColor(Color.WHITE);

            // Draw the big yellow circle
            Paint yellowPaint = new Paint();
            yellowPaint.setColor(Color.YELLOW);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 4, 200, yellowPaint);

            // Draw the black eyes
            Paint blackPaint = new Paint();
            blackPaint.setColor(Color.BLACK);
            canvas.drawCircle(canvas.getWidth() / 2 - 50, canvas.getHeight() / 4 - 50, 25, blackPaint);
            canvas.drawCircle(canvas.getWidth() / 2 + 50, canvas.getHeight() / 4 - 50, 25, blackPaint);

            // Draw the mouth as an arc
            path = new Path();
            path.moveTo(canvas.getWidth() / 2 - 100, canvas.getHeight() / 4 + 50);
            path.quadTo(canvas.getWidth() / 2, canvas.getHeight() / 4 + 150, canvas.getWidth() / 2 + 100, canvas.getHeight() / 4 + 50);
            canvas.drawPath(path, mouthBlackPaint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if needed
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Cleanup resources if needed
    }

    public void startAnimation() {
        isAnimating = true;
        // Start a separate thread for animation
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isAnimating) {
                    updatePath(); // Update the path for the curved line
                    drawOnCanvas(); // Draw the updated path on the canvas
                    try {
                        Thread.sleep(16); // Adjust the delay as needed for the desired frame rate
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isHappy = !isHappy;
                if (listener != null)
                    listener.onAnimationEnd();
            }
        }).start();
    }

    private void drawOnCanvas() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            path.reset(); // Clear the existing path
            path.moveTo(canvas.getWidth() / 2 - 100, canvas.getHeight() / 4 + 50);
            path.quadTo(controlX, controlY, canvas.getWidth() / 2 + 100, canvas.getHeight() / 4 + 50);
            canvas.drawPath(path, mouthBlackPaint); // Draw the updated path
            if ((!isHappy && (controlY + 5) > (canvas.getHeight() / 4 + 150)) ||
                    (isHappy && (controlY - 5) < (canvas.getHeight() / 4))) {
                isAnimating = false;
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }


    private void updatePath() {
        controlY = isHappy ? controlY - 5 : controlY + 5;
    }

    public void setAnimationListener(AnimationListener listener) {
        this.listener = listener;
    }

    public interface AnimationListener {
        public void onAnimationEnd();
    }

}
