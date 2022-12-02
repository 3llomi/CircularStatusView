package com.devlomi.circularstatusview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircularStatusView extends View {

    private static final float DEFAULT_PORTION_WIDTH = 10;
    private static final int DEFAULT_PORTION_SPACING = 5;
    private static final int DEFAULT_COLOR = Color.parseColor("#D81B60");
    private static final float DEFAULT_PORTIONS_COUNT = 1;
    private static final float START_DEGREE = -90;

    private float radius;
    private float portionWidth = DEFAULT_PORTION_WIDTH;
    private int portionSpacing = DEFAULT_PORTION_SPACING;
    private int portionColor = DEFAULT_COLOR;
    private float portionsCount = DEFAULT_PORTIONS_COUNT;

    private RectF mBorderRect = new RectF();
    private Paint paint;
    private SparseIntArray portionToUpdateMap = new SparseIntArray();


    public CircularStatusView(Context context) {
        super(context);
        init(context, null, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularStatusView, defStyle, 0);

        if (a != null) {
            portionColor = a.getColor(R.styleable.CircularStatusView_portion_color, DEFAULT_COLOR);
            portionWidth = a.getDimensionPixelSize(R.styleable.CircularStatusView_portion_width, (int) DEFAULT_PORTION_WIDTH);
            portionSpacing = a.getDimensionPixelSize(R.styleable.CircularStatusView_portion_spacing, DEFAULT_PORTION_SPACING);
            portionsCount = a.getInteger(R.styleable.CircularStatusView_portions_count, (int) DEFAULT_PORTIONS_COUNT);

            a.recycle();
        }

        paint = getPaint();

    }

    public CircularStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CircularStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorderRect.set(calculateBounds());
        radius = Math.min((mBorderRect.height() - portionWidth) / 2.0f, (mBorderRect.width() - portionWidth) / 2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = this.radius;
        float center_x = mBorderRect.centerX();
        float center_y = mBorderRect.centerY();


        final RectF oval = getOval(radius, center_x, center_y);

        float degree = 360 / portionsCount;
        float percent = 100 / portionsCount;


        for (int i = 0; i < portionsCount; i++) {
            paint.setColor(getPaintColorForIndex(i));
            float startAngle = START_DEGREE + (degree * i);
            canvas.drawArc(oval, (getSpacing() / 2) + startAngle, getProgressAngle(percent) - getSpacing(), false, paint);
        }


    }

    private int getPaintColorForIndex(int i) {
        if (portionToUpdateMap.indexOfKey(i) >= 0) { //if key is exists
            return portionToUpdateMap.get(i);
        } else {
            return portionColor;
        }
    }

    @NonNull
    private RectF getOval(float radius, float center_x, float center_y) {
        final RectF oval = new RectF();
        oval.set(center_x - radius,
                center_y - radius,
                center_x + radius,
                center_y + radius);
        return oval;
    }

    @NonNull
    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setColor(portionColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(portionWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private int getSpacing() {
        return portionsCount == 1 ? 0 : portionSpacing;
    }

    private RectF calculateBounds() {
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    private float getProgressAngle(float percent) {
        return percent / (float) 100 * 360;
    }

    public void setPortionsCount(int portionsCount) {
        this.portionsCount = (float) portionsCount;
    }

    public void setPortionSpacing(int spacing) {
        portionSpacing = spacing;
    }

    public void setPortionWidth(float portionWidth) {
        this.portionWidth = portionWidth;
    }

    public void setCustomPaint(Paint paint) {
        this.paint = paint;
    }

    public void setPortionsColor(int color) {
        this.portionColor = color;
        portionToUpdateMap.clear();
        invalidate();
    }

    public void setPortionColorForIndex(int index, int color) {
        if (index > portionsCount - 1) {
            throw new IllegalArgumentException("Index is Bigger than the count!");
        } else {
            Log.d("3llomi", "adding index to map " + index);
            portionToUpdateMap.put(index, color);
            invalidate();
        }
    }


}
