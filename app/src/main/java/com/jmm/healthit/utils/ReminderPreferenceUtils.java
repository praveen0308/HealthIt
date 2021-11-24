package com.jmm.healthit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class ReminderPreferenceUtils {

    public ReminderPreferenceUtils(){

    }

    public static boolean changeWaterReminderStatus(boolean status,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(Constants.WATER_REMINDER_STATUS, status);
        prefsEditor.apply();
        return true;
    }

    public static boolean getWaterReminderStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(Constants.WATER_REMINDER_STATUS, false);
    }

    public static boolean changeWaterReminderTime(int time,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.WATER_REMINDER_TIME, time);
        prefsEditor.apply();
        return true;
    }

    public static int getWaterReminderTime(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.WATER_REMINDER_TIME, 0);
    }

    public static boolean changeMedicineReminderStatus(boolean status,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(Constants.MEDICINE_REMINDER_STATUS, status);
        prefsEditor.apply();
        return true;
    }

    public static boolean getMedicineReminderStatus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(Constants.MEDICINE_REMINDER_STATUS, false);
    }



    public static boolean changeMedicineReminderHour(int hour,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.MEDICINE_REMINDER_HOUR, hour);
        prefsEditor.apply();
        return true;
    }

    public static int getMedicineReminderHour(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.MEDICINE_REMINDER_HOUR, 12);
    }


    public static boolean changeMedicineReminderMinute(int minute,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.MEDICINE_REMINDER_MINUTE, minute);
        prefsEditor.apply();
        return true;
    }

    public static int getMedicineReminderMinute(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(Constants.MEDICINE_REMINDER_MINUTE, 0);
    }

}
