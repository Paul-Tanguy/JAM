package com.myjamgame.epitech;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
/*
public class Obstacle implements GameObject{
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public boolean playerCollide(RectPlayer player) {
        return rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().left, player.getRectangle().bottom)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().bottom);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }
}
*/
public class Obstacle implements GameObject{
    private Rect rectangle;
    private Rect rectangle2;
    private Rect sub;
    private Bitmap wall;
    private Bitmap wall2;
    private Context context;
    private int color;

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public Obstacle(Context context, int rectHeight, int color, int startX, int startY, int playerGap, int score) {
        this.context = context;
        wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.walls);
        wall2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.walls);
        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
        int top = (5 - ((int) score / 26)) * wall.getHeight() / 6;
        this.sub = new Rect(0, top, wall.getWidth(),  top + wall.getHeight() / 6);
    }

    public Rect getRectangle(){
        return rectangle;
    }

    public boolean playerCollide(RectPlayer player) {
        return rectangle.contains(player.getRectangle().left, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().top)
                || rectangle.contains(player.getRectangle().left, player.getRectangle().bottom)
                || rectangle.contains(player.getRectangle().right, player.getRectangle().bottom);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        //canvas.drawRect(rectangle2, paint);
        canvas.drawBitmap(wall, sub, rectangle, null);
        canvas.drawBitmap(wall2, sub, rectangle2, null);
    }
}