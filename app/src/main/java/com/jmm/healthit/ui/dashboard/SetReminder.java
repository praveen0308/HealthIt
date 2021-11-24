package com.jmm.healthit.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.jmm.healthit.R;
import com.jmm.healthit.databinding.ActivityMainDashboardBinding;
import com.jmm.healthit.databinding.ActivitySetReminderBinding;
import com.jmm.healthit.utils.AlarmReceiver;
import com.jmm.healthit.utils.MusicControl;
import com.jmm.healthit.utils.ReminderPreferenceUtils;

import java.util.Calendar;

public class SetReminder extends AppCompatActivity {

    private ActivitySetReminderBinding binding;
    private NavController navController;

    private String[] mins = {"15min.","30min.","45min.","60min."};

    private int WATER_REMINDER_CODE = 101;
    private int MEDICINE_REMINDER_CODE = 102;

    private Calendar rCalendar;

    private int aSecond = 1000;
    private int aMinute = 60*aSecond;
    private int mMultiplier = 15;


    private AlarmManager waterAlarmManager;
    private PendingIntent waterAlarmIntent;

    private AlarmManager medicineAlarmManager;
    private PendingIntent medicineAlarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MusicControl.getInstance(getApplicationContext()).stopMusic();
        initMedicineReminder();
        initWaterReminder();
        binding.imageView2.setOnClickListener(view -> finish());
        setupMinuteSpinner();

        binding.tvTime.setOnClickListener(view -> showHourPicker());
        getReminderData();

        binding.switchWater.setOnCheckedChangeListener((compoundButton, b) -> {
            ReminderPreferenceUtils.changeWaterReminderStatus(b,getApplicationContext());
            if (b){
                if (waterAlarmManager!= null) {
                    int id = ReminderPreferenceUtils.getWaterReminderTime(getApplicationContext());
                    setWaterReminder(id);
                }

            }else {
                if (waterAlarmManager!= null) {
                    waterAlarmManager.cancel(waterAlarmIntent);
                }

            }
        });

        binding.switchMedicine.setOnCheckedChangeListener((compoundButton, b) -> {
            ReminderPreferenceUtils.changeMedicineReminderStatus(b,getApplicationContext());
            if (b){
                if (medicineAlarmManager!= null) {
                    setMedicineReminder();
                }

            }else {
                if (medicineAlarmManager!= null) {
                    medicineAlarmManager.cancel(medicineAlarmIntent);
                }

            }
        });
    }

    private void initWaterReminder(){
        waterAlarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Water Reminder");
        intent.putExtra("body","It's time to drink a glass of water.");
        waterAlarmIntent = PendingIntent.getBroadcast(this, WATER_REMINDER_CODE, intent, 0);
    }

    private void initMedicineReminder(){
        medicineAlarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title","Medicine Reminder");
        intent.putExtra("body","It's time to take your medicine.");
        medicineAlarmIntent = PendingIntent.getBroadcast(this, MEDICINE_REMINDER_CODE, intent, 0);
    }

    private void setMedicineReminder(){
        medicineAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,rCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, medicineAlarmIntent);

    }

    private void setWaterReminder(int id){
        int mInterval = (id+1)*mMultiplier*aMinute;
        waterAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,mInterval,
                mInterval, waterAlarmIntent);

    }

    private void getReminderData(){
        binding.switchWater.setChecked(ReminderPreferenceUtils.getWaterReminderStatus(getApplicationContext()));
        binding.switchMedicine.setChecked(ReminderPreferenceUtils.getMedicineReminderStatus(getApplicationContext()));
        binding.spnMins.setSelection(ReminderPreferenceUtils.getWaterReminderTime(getApplicationContext()));
        int hour = ReminderPreferenceUtils.getMedicineReminderHour(getApplicationContext());
        int minute = ReminderPreferenceUtils.getMedicineReminderMinute(getApplicationContext());
        String selectedTime;
        if (hour<12){
            selectedTime = String.format("%02d",hour) + ":"+
                    String.format("%02d",minute)+" AM";

        }
        else {
            selectedTime = String.format("%02d",hour) + ":"+
                    String.format("%02d",minute)+" PM";
        }

        setRCalendar();
        binding.tvTime.setText(selectedTime);

    }

    private void setRCalendar(){
        int hour = ReminderPreferenceUtils.getMedicineReminderHour(getApplicationContext());
        int minute = ReminderPreferenceUtils.getMedicineReminderMinute(getApplicationContext());
        rCalendar = Calendar.getInstance();
        rCalendar.set(Calendar.HOUR_OF_DAY,hour);
        rCalendar.set(Calendar.MINUTE,minute);
        rCalendar.set(Calendar.SECOND,0);
        rCalendar.set(Calendar.MILLISECOND,0);
    }

    private void setupMinuteSpinner(){
        ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mins);
        binding.spnMins.setAdapter(arrayAdapter);

        binding.spnMins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ReminderPreferenceUtils.changeWaterReminderTime(i,getApplicationContext());

                boolean isEnabled = ReminderPreferenceUtils.getWaterReminderStatus(getApplicationContext());
                if (isEnabled) setWaterReminder(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText("Select time")
                .build();
        materialTimePicker.show(getSupportFragmentManager(),materialTimePicker.getTag());

        materialTimePicker.addOnPositiveButtonClickListener(view -> {
            String selectedTime;
            if (materialTimePicker.getHour()<12){
                selectedTime = String.format("%02d",materialTimePicker.getHour()) + ":"+
                String.format("%02d",materialTimePicker.getMinute())+" AM";

            }
            else {
                selectedTime = String.format("%02d",materialTimePicker.getHour()) + ":"+
                        String.format("%02d",materialTimePicker.getMinute())+" PM";
            }

            ReminderPreferenceUtils.changeMedicineReminderHour(materialTimePicker.getHour(),getApplicationContext());
            ReminderPreferenceUtils.changeMedicineReminderMinute(materialTimePicker.getMinute(),getApplicationContext());
            binding.tvTime.setText(selectedTime);
            setRCalendar();
            boolean isEnabled = ReminderPreferenceUtils.getMedicineReminderStatus(getApplicationContext());
            if (isEnabled) setMedicineReminder();

        });

    }
}