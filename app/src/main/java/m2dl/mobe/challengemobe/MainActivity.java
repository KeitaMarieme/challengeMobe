package m2dl.mobe.challengemobe;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ObstacleManager obstacleManager;

    private ImageView carView;
    private ImageView obstacle;

    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //setContentView(R.layout.activity_main);
        setContentView(new GamePanel(this));

        //for obstacle manager


       // carView = findViewById(R.id.carView);
      //  obstacle = findViewById(R.id.obstacleView);

      //  initApp();

    }

   /** private void initApp() {

         backgroundOne = findViewById(R.id.background_one);
         backgroundTwo = findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = backgroundOne.getHeight();
                final float translationY = height * progress;
                backgroundOne.setTranslationY(translationY);
                backgroundTwo.setTranslationY(translationY - height);
            }
        });


      //  obstacleManager.draw();
        animator.start();
    }

    private Point getRandomCoordinate() { // À TESTER
        Display screen = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Point randomPoint = new Point();
        double xCoordinate;
        double yCoordinate;
        screen.getSize(size);
        float width = size.x;
        float height = size.y;
        xCoordinate = Math.random() * (width);
        yCoordinate = Math.random() * (height);
        randomPoint.set((int)xCoordinate, (int) yCoordinate);
        return randomPoint;
    }

    **/
}
