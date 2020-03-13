package m2dl.mobe.challengemobe;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private View backgroundView;
    private ImageView carView;
    private ObjectAnimator objectAnimator;

    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carView = findViewById(R.id.carView);

        initApp();

        // startGameRover();

    }

    private void initApp() {
         backgroundOne = (ImageView) findViewById(R.id.background_one);
         backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
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
        animator.start();
    }

    private void startGameRover(){
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.start();
    }

    private void testinfiniteBackground() {
        /*val display = context.getDisplayMetrics()
        // % 18 because my tile size is 18px
        // and to avoid partial crop when both image are joined together
        val width = display.widthPixels + display.widthPixels % 18
        background1ImageView.layoutParams.width = width
        background2ImageView.layoutParams.width = width

        // start animation
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 20000L // this will control speed of scrolling
        animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
            val width = background1ImageView.getWidth()
            val translationX = width * progress
            background1ImageView.setTranslationX(translationX)
            background2ImageView.setTranslationX(translationX - width)
        }
        animator.start()*/
    }
}
