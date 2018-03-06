package com.william.jtfilepicker;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangwei on 04/01/2018.
 */

public class NormalDecoration extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint();

    public NormalDecoration(int footerDecorationColor) {
        this.paint.setColor(footerDecorationColor);
        this.paint.setStrokeWidth(1f);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    public NormalDecoration() {
        this.paint.setColor(Color.parseColor("#e0e0e0"));
        this.paint.setStrokeWidth(1f);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(0, 0, 0, 1);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getLeft();
        int right = parent.getRight();
        int count = parent.getChildCount();
        View childView = null;
        for (int i = 0; i < count; i++) {
            childView = parent.getChildAt(i);
            float bottom = childView.getBottom();
            c.drawLine(left, bottom, right, bottom, paint);
        }
    }
}
