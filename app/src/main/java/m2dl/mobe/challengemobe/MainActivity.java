package m2dl.mobe.challengemobe;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView carView;
    private ImageView obstacle;
    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;

    private Sensor lightSensor;
    private Sensor gyroscopeSensor;

    private float lightValue;
    private float maxLightValue;
    private float mSpeedBackground;
    private float gyroscopeValue;
    private float maxGyroscopeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carView = findViewById(R.id.carView);
        obstacle = findViewById(R.id.obstacleView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /** INIT LIGHT SENSOR **/
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor!", Toast.LENGTH_SHORT);
            finish();
        }
        maxLightValue = (int) (lightSensor.getMaximumRange());

        /** INIT GYROSCOPE SENSOR **/
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor == null) {
            Toast.makeText(this, "This device has no gyroscope!", Toast.LENGTH_SHORT);
            finish();
        }
        maxGyroscopeValue = (int)gyroscopeSensor.getMaximumRange();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_LIGHT :
                        lightValue = sensorEvent.values[0];
                        mSpeedBackground = (lightValue / maxLightValue);
                        break;
                    case Sensor.TYPE_GYROSCOPE :
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        /** REGISTER SENSORS IN SENSOR MANAGER + EVENT LISTENER **/
        sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        initApp();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void initApp() {
        mSpeedBackground = 0.01f;
        backgroundOne = findViewById(R.id.background_one);
        backgroundTwo = findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = backgroundOne.getHeight();
                final float translationY = (mSpeedBackground * 50) + backgroundOne.getTranslationY(); // nb de pixel qu'on défile
                if (translationY >= height) {
                    backgroundOne.setTranslationY(0);
                    backgroundTwo.setTranslationY(-height);
                } else {
                    backgroundOne.setTranslationY(translationY);
                    backgroundTwo.setTranslationY(translationY - height);
                }
            }
        });
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
        randomPoint.set((int) xCoordinate, (int) yCoordinate);
        return randomPoint;
    }
}
