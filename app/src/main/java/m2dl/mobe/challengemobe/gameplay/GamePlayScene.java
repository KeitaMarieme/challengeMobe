package m2dl.mobe.challengemobe.gameplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import m2dl.mobe.challengemobe.R;


public class GamePlayScene {
    private Rect r = new Rect();
    private Drawable car;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private GyroscopeEventListener gyroscopeEventListener;
    private LightEventListener lightEventListener;
    private long frameTime;

    public GamePlayScene(Context context) {
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        // Bitmap bMap = BitmapFactory.decodeFile(String.valueOf(R.drawable.ic_directions_car_black_24dp));
        //Bitmap bMap = BitmapFactory.decodeResource( context.getResources(), R.drawable.ic_directions_car_black_24dp);
        car = context.getResources().getDrawable(R.drawable.ic_car_top_view_svgrepo_com, null);
        //car = new BitmapDrawable(bMap);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        car.setBounds(player.getRectangle());
        // player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

        gyroscopeEventListener = new GyroscopeEventListener();
        gyroscopeEventListener.register();
        lightEventListener = new LightEventListener();
        lightEventListener.register();
        frameTime = System.currentTimeMillis();
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        car.setBounds(player.getRectangle());
        // player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
    }


    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                    gyroscopeEventListener.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        /*Paint p=new Paint();
        Bitmap b = BitmapFactory.decodeResource( MainActivity.getContext().getResources(), R.drawable.ic_directions_car_black_24dp);
        p.setColor(Color.RED);
        canvas.drawBitmap(b, 0, 0, p);*/
        //car.setColorFilter(new ColorFilter());
        car.draw(canvas);

        // player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "Game Over");
        }
    }

    public void update() {
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if (gyroscopeEventListener.getOrientation() != null && gyroscopeEventListener.getStartOrientation() != null) {
                float pitch = gyroscopeEventListener.getOrientation()[1] - gyroscopeEventListener.getStartOrientation()[1];
                float roll = gyroscopeEventListener.getOrientation()[2] - gyroscopeEventListener.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT / 1000f;

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
            }

            if (playerPoint.x < 0)
                playerPoint.x = 0;
            else if (playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            if (playerPoint.y < 0)
                playerPoint.y = 0;
            else if (playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;

            player.update(playerPoint);
            car.setBounds(player.getRectangle());
            obstacleManager.update();

            if (obstacleManager.playerCollide(player)) {
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