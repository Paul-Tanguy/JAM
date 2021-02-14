package com.myjamgame.epitech;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;

public class Background implements GameObject {
    private Context context;
    private Bitmap bg;
    private Rect rect;
    private Rect sub;
    private long startTime;
    private long initTime;

    public Background(Context context) {
        this.context = context;
        this.bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        this.rect = new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.sub = new Rect(0, this.bg.getHeight() - Constants.SCREEN_HEIGHT, this.bg.getWidth(), this.bg.getHeight());
    }

    @Override
    public void update() {
        this.sub.top -= 7;
        this.sub.bottom -= 7;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bg, sub, rect, null);
    }
}
