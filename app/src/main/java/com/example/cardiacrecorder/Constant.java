package com.example.cardiacrecorder;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class containing constants used in the Cardiac Recorder application.
 */
public class Constant {
    // Variables to store character values
    public static char ch = 'n';
    public static char point = 'n';
    public static char error = 'n';

    // ArrayLists to store temporary values
    public static ArrayList<String> tmp_sys = new ArrayList<>();
    public static ArrayList<String> tmp_dia = new ArrayList<>();
    public static ArrayList<String> tmp_hr = new ArrayList<>();
    public static ArrayList<String> tmp_date = new ArrayList<>();
    public static ArrayList<String> tmp_time = new ArrayList<>();
    public static ArrayList<String> tmp_cmnt = new ArrayList<>();
    public static ArrayList<String> key = new ArrayList<>();

    /**
     * Interface for handling item deletion.
     */
    public interface OnItemDeleteListener {
        /**
         * Called when an item is deleted.
         *
         * @param position The position of the deleted item.
         */
        void onItemDelete(int position);
    }
}
