package m2dl.mobe.challengemobe;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{


    private MainThread thread;

    private RectPlayer rectPlayer;
    private Point playerPoint;
    private ObstacleManager obstacleManager;


    public GamePanel(Context context) {

        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        rectPlayer = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));

        playerPoint = new Point(150,150);

        obstacleManager = new ObstacleManager(20, 30, 75, Color.WHITE);

        setFocusable(true);

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry)
        {
            try {
                thread.setRunning(false);
                thread.join();


            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(),(int) event.getY());

        }


        return true;
    }

    public void update()
    {

        rectPlayer.update(playerPoint);
        obstacleManager.update();
    }


    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        rectPlayer.draw(canvas);
        obstacleManager.draw(canvas);
    }

}
