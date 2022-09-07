package com.example.fanmanager;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SwitchCompat enable_control_switch;
    private View temp_controls;
    private TextView always_on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/// current CPU temp request every n sec.
        CpuTempHandler.postDelayed(CpuTempTextPrintRunnable, 0);
/// spinners start
        Spinner spin_min = findViewById(R.id.spinner_min);
        ArrayAdapter<CharSequence> dataAdapterMin = ArrayAdapter.createFromResource(this,R.array.temp_array, R.layout.spinner_item);
        dataAdapterMin.setDropDownViewResource(R.layout.spinner_item);
        spin_min.setAdapter(dataAdapterMin);
        spin_min.setOnItemSelectedListener(this);
        Spinner spin_max = findViewById(R.id.spinner_max);
        ArrayAdapter<CharSequence> dataAdapterMax = ArrayAdapter.createFromResource(this,R.array.temp_array, R.layout.spinner_item);
        dataAdapterMax.setDropDownViewResource(R.layout.spinner_item);
        spin_max.setAdapter(dataAdapterMax);
        spin_max.setOnItemSelectedListener(this);
/// spinners end
        enable_control_switch = findViewById(R.id.enable_control);
        temp_controls = findViewById(R.id.temp_control_unit);
        always_on = findViewById(R.id.always_on);

        enable_control_switch.setChecked(true); // by default temperature control is ON
/// Switch function start
        enable_control_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                if (enable_control_switch.isChecked()) {
                    temp_controls.startAnimation(animFadeIn);
                    temp_controls.setVisibility(View.VISIBLE);
                    always_on.startAnimation(animFadeOut);
                    Toast.makeText(getApplicationContext(), "Fan control :" + enable_control_switch.getTextOn().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    temp_controls.startAnimation(animFadeOut);
                    temp_controls.setVisibility(View.GONE);
                    always_on.startAnimation(animFadeIn);
                    Toast.makeText(getApplicationContext(), "Fan control :" + enable_control_switch.getTextOff().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
/// end switch
    }
/// Retrieve CPU temp and show in IU
    private Handler CpuTempHandler = new Handler();
    private Runnable CpuTempTextPrintRunnable = new Runnable() {
        public void run() {
            TextView textView = (TextView) findViewById(R.id.cur_temp_value);
            textView.setText("55 C");
            Toast.makeText(getApplicationContext(), "get_cputemp_will be here", Toast.LENGTH_SHORT).show();
            CpuTempHandler.postDelayed(this, 10000); // Run again after 1 second
        }
    };
///
     @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        if (id==R.id.spinner_min) {
            String choice_min = adapterView.getItemAtPosition(i).toString();
//            Toast.makeText(getApplicationContext(), "MIN: " + choice_min, Toast.LENGTH_SHORT).show();
        }if (id==R.id.spinner_max){
            String choice_max = adapterView.getItemAtPosition(i).toString();
//            Toast.makeText(getApplicationContext(), "MAX: " + choice_max, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}