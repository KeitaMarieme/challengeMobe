package m2dl.mobe.challengemobe;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView carView;
    private ImageView backgroundOne;
    private ImageView backgroundTwo;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;

    private Sensor lightSensor;
    private Sensor accelerometerSensor;

    private float lightValue;
    private float maxLightValue;
    private float mSpeedBackground;
    private float accelerometerValue;
    private float maxAccelerometerValue;

    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenWidth = getScreenWidth();
        carView = findViewById(R.id.carView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        /** INIT LIGHT SENSOR **/
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor!", Toast.LENGTH_SHORT);
            finish();
        }
        maxLightValue = (int) (lightSensor.getMaximumRange());

        /** INIT GYROSCOPE SENSOR **/
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor == null) {
            Toast.makeText(this, "This device has no accelerometer!", Toast.LENGTH_SHORT);
            finish();
        }
        maxAccelerometerValue = (int)accelerometerSensor.getMaximumRange();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_LIGHT :
                        lightValue = sensorEvent.values[0];
                        mSpeedBackground = (lightValue / maxLightValue);
                        break;
                    case Sensor.TYPE_ACCELEROMETER :
                        float x = sensorEvent.values[0]*100 + carView.getWidth();
                        moveCar(x);
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
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        initApp();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void moveCar(float sensedValuedOnx) {
        // TODO bouger la voiture sur l'axe des x
        if (sensedValuedOnx > backgroundTwo.getWidth()) {
            sensedValuedOnx = backgroundTwo.getWidth();
        } else if (sensedValuedOnx < (-1 * backgroundTwo.getWidth())) {
            sensedValuedOnx = (-1 * backgroundTwo.getWidth());
        } else {
            carView.setTranslationX(sensedValuedOnx);
        }
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

    private int getScreenWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
