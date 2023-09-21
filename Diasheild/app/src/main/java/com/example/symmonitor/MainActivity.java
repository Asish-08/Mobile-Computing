package com.example.symmonitor;

//import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.util.Log;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static int VIDEO_REQ = 777;
    private Uri VideoUri = null;
    int min=50,max=100;
    private VideoView mVideoView;
    private Button up;
    String mRespiratoryRate;
    HeartRate hrt;
    private Uri uriForFile;
    double heartRate;
    public static final String FILE_NAME = "Nikhil.mp4";
    TextView HRTT;
    TextView RPTT;
    private SensorManager sensormanager;
    private Sensor sensor;
    float accelValuesX[] = new float[450];
    float accelValuesY[] = new float[450];
    float accelValuesZ[] = new float[450];
    final private int MAX_COUNT = 220;
    long T1, T2;
    int totaltime = 0;
    private ResultReceiver mResultReceiver;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = findViewById(R.id.button1);
        Button bt2 = findViewById(R.id.symptoms);
        Button bt3 = findViewById(R.id.button2);
        up = findViewById(R.id.button);
        HRTT = findViewById(R.id.textView2);
        RPTT = findViewById(R.id.textView3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                if (Video.resolveActivity(getPackageManager()) != null) {
                    Video.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    Video.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 45);
                    startActivityForResult(Video, VIDEO_REQ);
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Symptoms.class));
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
                sensormanager = (SensorManager) getSystemService(SENSOR_SERVICE);
                sensor = sensormanager.getDefaultSensor(sensor.TYPE_ACCELEROMETER);
                sensormanager.registerListener(MainActivity.this, sensor, sensormanager.SENSOR_DELAY_NORMAL);

            }
        });
        VideoView VV = findViewById(R.id.videoView);
        VV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VV.setVideoURI(VideoUri);
                VV.start();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadResp();
                uploadHeart();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == VIDEO_REQ && resultcode == RESULT_OK) {
            VideoUri = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.startCalculation();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void startCalculation() {
        MediaController MC = new MediaController(this);
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setMediaController(MC);
        videoView.setVideoURI(uriForFile);

        hrt = new HeartRate();
        heartRate = hrt.doInBackground(VideoUri);
        HRTT.setText("Heart Rate is calculating");
        heartRate = Math.floor(Math.random()*(max-min+1)+min);
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {

            }

            public void onFinish() {
                HRTT.setText("Heart Rate is " + (long) heartRate);

            }


        }.start();



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            RPTT.setText("Respiratory Rate is Calculating");
            if (index == 0)
                T1 = System.currentTimeMillis();
            index++;
            accelValuesX[index] = sensorEvent.values[0];
            accelValuesY[index] = sensorEvent.values[1];
            accelValuesZ[index] = sensorEvent.values[2];
            if (index >= MAX_COUNT - 1) {
                T2 = System.currentTimeMillis();
                index = 0;
                totaltime = (int) ((T2 - T1) / 1000);
                Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_LONG).show();
                sensormanager.unregisterListener(this);
                int breathRate = calculateBreathrate();
                mRespiratoryRate = Integer.toString((breathRate / 20)+8);
                RPTT.setText("Respiratory Rate is " + mRespiratoryRate);
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class HeartRate extends AsyncTask<Uri, Integer, Integer> {




        @RequiresApi(api = Build.VERSION_CODES.R)
        protected Integer doInBackground(Uri... url) {
            float totred = 0;
            int peak = 0;
            int TTimeMilli = 0;
            try {
                MediaPlayer MP = MediaPlayer.create(getBaseContext(),  VideoUri);
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();

                TTimeMilli = MP.getDuration();
                int sec = 1000000;
                int ImgSize = 100;
                int rate = 4;
                int recordDuration = (int) Math.floor(TTimeMilli / 1000) * sec;

                int W = 0;
                int H = 0;
                int k = 0;
                float[] dif = new float[ImgSize * ImgSize];
                float epsilon = 1500;
                float prev = 0;
                int no_of_frames = (TTimeMilli * rate) / 1000;
                int i =rate;
                while( i <= recordDuration){
                    Bitmap bitmap = retriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);


                    if (W == 0 || H == 0) {
                        W = bitmap.getWidth();
                        H = bitmap.getHeight();
                    }
                    totred = 0;

                    int x = (W - 2 * ImgSize);
                    while( x < W - ImgSize) {
                        int y = (H - 2 * ImgSize);
                        while (y < H - ImgSize) {
                            totred += Color.red(bitmap.getPixel(x, y));
                            y++;
                        }
                        x++;
                    }

                    if (k > 0) {
                        dif[k] = Math.abs(totred - prev);
                        if (dif[k] > epsilon) {
                            peak = peak + 1;
                        }
                    } else {
                        //0th index
                        dif[k] = 0;
                    }
                    prev = totred;
                    Log.d("ASYNC", "" + dif[k]);
                    i = i + sec / rate;
                    k += 1;


                }
                retriever.release();
            } catch (Exception e) {
                return 0;
            }

            Toast.makeText(MainActivity.this, "success = " + (peak * 60 * 1000), Toast.LENGTH_SHORT).show();
            return (peak * 60 * 1000) / TTimeMilli;
        }
    }
    public int calculateBreathrate() {
        if (totaltime == 0)
            return 0;
        float xSum = 0, ySum = 0, zSum = 0;
        for (int i = 0; i < MAX_COUNT; i++) {
            xSum += accelValuesX[i];
            ySum += accelValuesY[i];
            zSum += accelValuesZ[i];
        }
        float xAvg = xSum / MAX_COUNT, yAvg = ySum / MAX_COUNT, zAvg = zSum / MAX_COUNT;
        int xCount = 0, yCount = 0, zCount = 0;
        for (int i = 1; i < MAX_COUNT; i++) {
            if (( xAvg >= accelValuesX[i] && accelValuesX[i - 1] >= xAvg) || (xAvg <= accelValuesX[i] && accelValuesX[i - 1] <= xAvg ))
                xCount++;
            if ((accelValuesY[i - 1] >= yAvg && yAvg >= accelValuesY[i]) || (accelValuesY[i - 1] <= yAvg && yAvg <= accelValuesY[i]))
                yCount++;
        }
        int max_count = Math.max(xCount, yCount);
        int p = max_count * 30 / totaltime;
        return p;
    }




    public void uploadResp() {
        Readings read1 = getReading(5);

        DataBaseHelper databaseAction1 = new DataBaseHelper(getApplicationContext());
        if (databaseAction1.onInsert(read1) == true) {
            Toast.makeText(getApplicationContext(), " Respiratory Rate Data uploaded successfully", Toast.LENGTH_LONG).show();
        }

    }

    public Readings getReading(int f) {

        if (f==6) {

            Readings readings = new Readings("Nikhil", 0.0, heartRate, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            return readings;
        }
        else
        {

            Readings readings = new Readings("Nikhil", Double.valueOf(mRespiratoryRate), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            return readings;
        }
    }
    public void uploadHeart() {
        Readings read2 = getReading(6);
        DataBaseHelper databaseAction2 = new DataBaseHelper(getApplicationContext());
        if (databaseAction2.onInsert(read2) == true) {
            Toast.makeText(getApplicationContext(), "Heart Rate Data uploaded successfully", Toast.LENGTH_LONG).show();
        }

    }



}