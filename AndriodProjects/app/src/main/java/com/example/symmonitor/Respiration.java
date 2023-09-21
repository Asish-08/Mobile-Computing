package com.example.symmonitor;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.widget.Toast;

public class Respiration extends Service implements SensorEventListener {
    private SensorManager sensor_manager;
    final private int MAXIMUM_COUNT = 10;
    float accelValuesX[] = new float[450];
    float accelValuesY[] = new float[450];
    float accelValuesZ[] = new float[450];
    long TB1, TB2;
    int totaltime = 0;
    private Sensor sensor;
    private ResultReceiver ResultReceiver;
    int index = 0;

    public Respiration() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensor_manager.getDefaultSensor(sensor.TYPE_ACCELEROMETER);
        sensor_manager.registerListener(this, sensor, sensor_manager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int calculateBreathrating() {
        if (totaltime == 0)
            return 0;
        float xSum = 0, ySum = 0, zSum = 0;
        for (int i = 0; i < MAXIMUM_COUNT; i++) {
            xSum += accelValuesX[i];
            ySum += accelValuesY[i];
            zSum += accelValuesZ[i];
        }
        float xAvg = xSum / MAXIMUM_COUNT, yAvg = ySum / MAXIMUM_COUNT, zAvg = zSum / MAXIMUM_COUNT;
        int xCount = 0, yCount = 0, zCount = 0;
        for (int i = 1; i < MAXIMUM_COUNT; i++) {
            if ((accelValuesX[i - 1] <= xAvg && xAvg <= accelValuesX[i]) || (accelValuesX[i - 1] >= xAvg && xAvg >= accelValuesX[i]))
                xCount++;
            if ((accelValuesY[i - 1] <= yAvg && yAvg <= accelValuesY[i]) || (accelValuesY[i - 1] >= yAvg && yAvg >= accelValuesY[i]))
                yCount++;
            if ((accelValuesZ[i - 1] <= zAvg && zAvg <= accelValuesZ[i]) || (accelValuesZ[i - 1] >= zAvg && zAvg >= accelValuesZ[i]))
                zCount++;
        }
        int max_count = Math.max(xCount, Math.max(yCount, zCount));

        return max_count * 30 / totaltime;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Toast.makeText(Respiration.this, "success = 2 ", Toast.LENGTH_SHORT).show();
            if (index == 0)
                TB1 = System.currentTimeMillis();
            index++;
            accelValuesX[index] = sensorEvent.values[0];
            accelValuesY[index] = sensorEvent.values[1];
            accelValuesZ[index] = sensorEvent.values[2];
            if (index >= MAXIMUM_COUNT - 1) {
                //Toast.makeText(Respiration.this, "suc = 1 ", Toast.LENGTH_SHORT).show();
                TB2 = System.currentTimeMillis();
                index = 0;
                totaltime = (int) ((TB2 - TB1) / 1000);
                sensor_manager.unregisterListener(this);
                //Toast.makeText(Respiration.this, "success = 1 ", Toast.LENGTH_SHORT).show();
                int breathRate = calculateBreathrating();
                String mRespiratoryRate = String.valueOf(breathRate / 20);
               // Toast.makeText(getApplicationContext(), "buttonCLicked" + mRespiratoryRate, Toast.LENGTH_LONG).show();
                // String currentValue = String.format(Locale.getDefault(), "RESPIRATORY_RATE: " + mRespiratoryRate);
                //sendDataToActivity(mRespiratoryRate);
                //this.setResultReceiver(intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));
            }
        }
    }

public void setResultReceiver(ResultReceiver mResultReceiver) {
    this.ResultReceiver = mResultReceiver;
}


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
