package com.myjamgame.epitech;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameplayScene implements Scene {
    private Context context;
    private Rect r = new Rect();
    private Background background;

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;

    public GameplayScene(Context context) {
        this.context = context;
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        this.background = new Background(context);

        obstacleManager = new ObstacleManager(context, 300, 700, 100, Color.BLACK);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(context, 300, 700, 100, Color.BLACK);
        background = new Background(context);
        movingPlayer = false;
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(), (int)event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        background.draw(canvas);
        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Game Over");
            Button btnShow = new Button(context);
            int state = obstacleManager.getScore() / 26;
            String text = "Restart Game";
            if (state == 0) {
                text += "\nCitation past";
            } else if (state == 1) {
                text += "\nCitation Present";
            } else {
                text += "\nCitation Future";
            }
            btnShow.setText(text);
            btnShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnShow.draw(canvas);
            btnShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameOver = false;
                    reset();
                }
            });
        }
    }

    @Override
    public void update() {
        if(!gameOver) {
            if(frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;

                playerPoint.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed*elapsedTime : 0;
            }

            if(playerPoint.x < 0)
                playerPoint.x = 0;
            else if(playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            if(playerPoint.y < 0)
                playerPoint.y = 0;
            else if(playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;

            player.update(playerPoint);
            obstacleManager.update();
            background.update();

            if(obstacleManager.playerCollide(player) || obstacleManager.getScore() >= 160) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}