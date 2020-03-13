package m2dl.mobe.challengemobe.gameplay;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightEventListener implements SensorEventListener {

    private SensorManager manager;
    private Sensor lightSensor;

    public LightEventListener() {
        manager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (manager == null || lightSensor == null)
            throw new RuntimeException("No light sensor available");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final int maxLightValue = (int) (lightSensor.getMaximumRange());
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            Constants.SPEED_LIGHT_RATIO = 0.5f + (event.values[0] / maxLightValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void register() {
        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
