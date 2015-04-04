package brian.com.test412;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


import static android.widget.SeekBar.*;

public class Main2 extends Activity {

    private Button btnSlowWork;
    private TextView txtMsg;
    private Long startingMillis;
    private RatingBar ratingBar;
    private RadioButton radioOn;
    private  RadioButton radioOff;
    private CheckBox checkBox;
    private SeekBar seekBar;
    private Switch aSwitch;

    private Button buttonIncreaseSeekBar;
    private Button buttonDecreaseSeekBar;

    private TextView textProgress,textAction;
    private ScrollView scrollView;

    private SensorManager sensorManager;
    TextView xCoor; // declare X axis object
    TextView yCoor; // declare Y axis object
    TextView zCoor; // declare Z axis object
    //ServerSocket serv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtMsg = (TextView) findViewById(R.id.button2);
        btnSlowWork = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        radioOn = (RadioButton) findViewById(R.id.radioOn);
        radioOff = (RadioButton) findViewById(R.id.radioOff);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        aSwitch = (Switch) findViewById(R.id.switch1);
        txtMsg.setText("Aidez nous a ameliorer l'application en votant ");




        xCoor=(TextView)findViewById(R.id.xcoor); // create X axis object
        yCoor=(TextView)findViewById(R.id.ycoor); // create Y axis object
        zCoor=(TextView)findViewById(R.id.zcoor); // create Z axis object

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        // add listener. The listener will be HelloAndroid (this) class
        sensorManager.registerListener((android.hardware.SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        //	More sensor speeds (taken from api docs)
        // SENSOR_DELAY_FASTEST get sensor data as fast as possible
        //    SENSOR_DELAY_GAME	rate suitable for games
        // 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes




        textProgress = (TextView)findViewById(R.id.textViewProgress);
        textAction = (TextView)findViewById(R.id.text_Accueil);
        buttonIncreaseSeekBar =(Button) findViewById(R.id.buttonIncreaseSeekBar);
        buttonDecreaseSeekBar =(Button) findViewById(R.id.buttonDecreaseSeekBar);
        //scrollView =(ScrollView) findViewById(R.id.scrollView);

        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textProgress.setText("The value is: " + progress);
                textAction.setText("changing");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAction.setText("starting to track touch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setSecondaryProgress(seekBar.getProgress()); // set the shade of the previous value.
                textAction.setText("ended tracking touch");
            }
        });
        this.buttonIncreaseSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                textAction.append("button Increase SeekBar");

                seekBar.setProgress(seekBar.getProgress() + 1);
            }
        });
        this.buttonDecreaseSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                textAction.append("button Decrease SeekBar");

                seekBar.setProgress(seekBar.getProgress()-1);
            }
        });

        this.buttonDecreaseSeekBar.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textAction.append("on focus");

            }
        });
        this.buttonDecreaseSeekBar.setOnLongClickListener( new OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                textAction.append("on LOng Click");
                seekBar.setProgress(seekBar.getProgress()-30);
                return false;
            }
        });

        this.btnSlowWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new VerySlowTask().execute();
            }
        });

        this.aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //new VerySlowTask().alert();
                radioOn.setChecked(aSwitch.isChecked());
                radioOff.setChecked(!aSwitch.isChecked());
                // radioOn.setChecked(!radioOn.isChecked());
                checkBox.setChecked(aSwitch.isChecked());
            }
        });

        this.radioOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                aSwitch.setChecked(true);
                radioOff.setChecked(!radioOn.isChecked());
                checkBox.setChecked(true);
            }
        });

        this.radioOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                aSwitch.setChecked(false);
                radioOn.setChecked(!radioOff.isChecked());
                checkBox.setChecked(false);
            }
        });

        this.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                aSwitch.setChecked(checkBox.isChecked());
                radioOn.setChecked(checkBox.isChecked());
                radioOff.setChecked(!checkBox.isChecked());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new VerySlowTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return (id == R.id.action_settings) ? true : super.onOptionsItemSelected(item);
    }





    public void onAccuracyChanged(Sensor sensor,int accuracy){}

    public void onSensorChanged(SensorEvent event){
        // check sensor type
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            // assign directions
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];


            textAction.setText("X : " + x + "  Y :" + y);
            //xCoor.setText("X: "+x);
            // yCoor.setText("Y: "+y);
            //zCoor.setText("Z: "+z);
        }
    }







    class VerySlowTask extends AsyncTask<String, Long, Void> {

        private final ProgressDialog dialog = new ProgressDialog(Main2.this);

        protected void onPreExecute() {
            startingMillis = System.currentTimeMillis();
            //txtMsg.setText("Start Time : "+ startingMillis);
            this.dialog.setMessage("Votre vote est entrain d'etre envoyé ...");
            this.dialog.show();
        }

        protected Void doInBackground(final String... params) {
            try {
                for (Long i = 0L; i < 2L; i++) {
                    Thread.sleep(1000);
                    publishProgress((Long) i);
                }
            } catch (InterruptedException e) {Log.v("slow-job interrupted", e.getMessage()); }

            return null;
        }

        protected void onProgressUpdate(Long... value) {
            super.onProgressUpdate(value);
            //txtMsg.append(" . ");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.dialog.isShowing())
                this.dialog.dismiss();
            //cleaning-up, all done
            txtMsg.setText("Merci d'avoir voté ! vous avez attribué la note de : " + ratingBar.getRating() + "/" + ratingBar.getNumStars());
            //ratingBar.setEnabled(false);
            ratingBar.setBackgroundColor(ratingBar.getRating() < 2.5 ? Color.RED : Color.WHITE);
            //String MessageChecked = radioOn.isChecked() ? " Votre vote sera rendu public." : "Votre vote reste anonyme";

            txtMsg.append("\n");
            txtMsg.append(checkBox.isChecked() ? " Votre vote sera rendu public." : "Votre vote reste anonyme");
            txtMsg.append("\nseekbar rate : " + seekBar.getAlpha()+"\n");
            txtMsg.append(aSwitch.isChecked() ? "on" : "off");
        }
    }

}