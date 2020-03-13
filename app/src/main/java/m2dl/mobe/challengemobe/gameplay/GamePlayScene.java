package m2dl.mobe.challengemobe.gameplay;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import m2dl.mobe.challengemobe.R;


public class GamePlayScene {

    private Rect r = new Rect();

    private CarPlayer player;
    private int carXPosition;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private GyroscopeEventListener gyroscopeEventListener;
    private long frameTime;

    public GamePlayScene(GamePanel gamePanel) {
        player = new CarPlayer(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.car_blue));
        carXPosition = Constants.SCREEN_WIDTH / 2;
        player.update(carXPosition);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

        gyroscopeEventListener = new GyroscopeEventListener();
        gyroscopeEventListener.register();
        frameTime = System.currentTimeMillis();
    }

    public void reset() {
        player.update(Constants.SCREEN_WIDTH / 2);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
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

                carXPosition += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
            }

            if (carXPosition < 0)
                carXPosition = 0;
            else if (carXPosition > Constants.SCREEN_WIDTH)
                carXPosition = Constants.SCREEN_WIDTH;

            player.update(500);
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
