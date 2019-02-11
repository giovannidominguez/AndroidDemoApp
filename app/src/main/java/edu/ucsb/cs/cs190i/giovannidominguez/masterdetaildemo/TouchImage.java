package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
    Created by Giovanni on 5/1/17
 */
public class TouchImage extends ImageView {

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float newScale = saveScale * scaleFactor;
            if (newScale < maxScale && newScale > minScale) {
                saveScale = newScale;
                float width = getWidth();
                float height = getHeight();
                right = (originalBitmapWidth * saveScale) - width;
                bottom = (originalBitmapHeight * saveScale) - height;

                float scaledBitmapWidth = originalBitmapWidth * saveScale;
                float scaledBitmapHeight = originalBitmapHeight * saveScale;

                if (scaledBitmapWidth <= width || scaledBitmapHeight <= height) {
                    matrix.postScale(scaleFactor, scaleFactor, width / 2, height / 2);
                } else {
                    matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                }
            }
            return true;
        }

    }

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int CLICK = 3;

    private int mode = NONE;

    private Matrix matrix = new Matrix();

    private PointF last = new PointF();
    private PointF start = new PointF();
    private float minScale = 0.5f;
    private float maxScale = 4f;
    private float[] m;

    private float redundantXSpace, redundantYSpace;
    private float saveScale = 1f;
    private float right, bottom, originalBitmapWidth, originalBitmapHeight;

    private ScaleGestureDetector mScaleDetector;

    public TouchImage(Context context) {
        super(context);
        init(context);
    }

    public TouchImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        super.setClickable(true);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        m = new float[9];
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int bmHeight = getBmHeight();
        int bmWidth = getBmWidth();
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        //Fit to screen.
        float scale = width > height ? height / bmHeight :  width / bmWidth;
        matrix.setScale(scale, scale);
        saveScale = 1f;
        originalBitmapWidth = scale * bmWidth;
        originalBitmapHeight = scale * bmHeight;

        // Center the image
        redundantYSpace = (float).2*(height - originalBitmapHeight);
        redundantXSpace = (float).2*(width - originalBitmapWidth);
        matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);
        setImageMatrix(matrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);

        matrix.getValues(m);
        float x = m[Matrix.MTRANS_X];
        float y = m[Matrix.MTRANS_Y];
        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
         //DRAG
            case MotionEvent.ACTION_DOWN:
                last.set(event.getX(), event.getY());
                start.set(last);
                mode = DRAG;
                break;
        //Zoom
            case MotionEvent.ACTION_POINTER_DOWN:
                last.set(event.getX(), event.getY());
                start.set(last);
                mode = ZOOM;
                break;

            case MotionEvent.ACTION_MOVE:
                //if the mode is ZOOM or
                //if the mode is DRAG and already zoomed
                if (mode == ZOOM || (mode == DRAG && saveScale > minScale)) {
                    float deltaX = curr.x - last.x;
                    float deltaY = curr.y - last.y;
                    float scaleWidth = Math.round(originalBitmapWidth * saveScale);
                    float scaleHeight = Math.round(originalBitmapHeight * saveScale);

                    boolean limitX = false;
                    boolean limitY = false;

                    if (scaleWidth < getWidth() && scaleHeight < getHeight()) {
                    }
                    else if (scaleWidth < getWidth()) {
                        deltaX = 0;
                        limitY = true;
                    }

                    else if (scaleHeight < getHeight()) {
                        deltaY = 0;
                        limitX = true;
                    }

                    else {
                        limitX = true;
                        limitY = true;
                    }

                    if (limitY) {
                        if (y + deltaY > 0) {
                            deltaY = -y;
                        } else  if (y + deltaY < -bottom) {
                            deltaY = -(y + bottom);
                        }

                    }

                    if (limitX) {
                        if (x + deltaX > 0) {
                            deltaX = -x;
                        } else if (x + deltaX < -right) {
                            deltaX = -(x + right);
                        }

                    }

                    matrix.postTranslate(deltaX, deltaY);
                    last.set(curr.x, curr.y);
                }
                break;

            case MotionEvent.ACTION_UP:
                mode = NONE;
                int xDiff = (int) Math.abs(curr.x - start.x);
                int yDiff = (int) Math.abs(curr.y - start.y);
                if (xDiff < CLICK && yDiff < CLICK)
                    performClick();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        }
        setImageMatrix(matrix);
        invalidate();
        return true;
    }


    private int getBmWidth() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return 0;
    }

    private int getBmHeight() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return 0;
    }
}