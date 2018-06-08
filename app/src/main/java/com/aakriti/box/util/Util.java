package com.aakriti.box.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.aakriti.box.R;
import com.aakriti.box.model.Room;
import com.aakriti.box.model.UserClass;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ritwik on 02-05-2018.
 */

public class Util {

    private static String USERCLASS = "USERCLASS";
    private static final String PREF_NAME = "BoxPrefs";

    private static String ROOMCLASS = "ROOMCLASS";
    private static final String PREF_NAME_ROOMS = "BoxRoomPrefs";

    /**
     * Shows custom alert dialog with OK option.
     **/
    public static void showMessageWithOk(final Activity mContext, final String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setMessage(message);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });
    }

    /**
     * Shows custom alert dialog with OK option and a callback
     **/
    public static void showCallBackMessageWithOkCallback(final Context mContext, final String message,
                                                         final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onSubmit();
                    }
                });

                alert.show();
            }
        });
    }

    /**
     * Shows custom alert dialog with OK and cancel
     **/
    public static void showCallBackMessageWithOkCancel(final Context mContext, final String message,
                                                       final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onSubmit();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onCancel();
                    }
                });
                alert.show();
            }
        });
    }

    /**
     * Saving UserClass details
     **/
    public static void saveUserClass(final Context mContext, UserClass userClass) {
        SharedPreferences boxPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = boxPrefs.edit();
        try {
            prefsEditor.putString(USERCLASS, ObjectSerializer.serialize(userClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefsEditor.commit();
    }

    /**
     * Fetching UserClass details
     **/
    public static UserClass fetchUserClass(final Context mContext) {
        SharedPreferences boxPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        UserClass userClass = null;
        String serializeOrg = boxPrefs.getString(USERCLASS, null);
        try {
            if (serializeOrg != null) {
                userClass = (UserClass) ObjectSerializer.deserialize(serializeOrg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userClass;
    }

    /**
     * Saving Room details
     **/
    public static void saveRooms(final Context mContext, ArrayList<Room> rooms) {
        SharedPreferences boxPrefs = mContext.getSharedPreferences(PREF_NAME_ROOMS, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = boxPrefs.edit();
        try {
            prefsEditor.putString(ROOMCLASS, ObjectSerializer.serialize(rooms));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefsEditor.commit();
    }

    /**
     * Fetching Room details
     **/
    public static ArrayList<Room> fetchRooms(final Context mContext) {
        SharedPreferences boxPrefs = mContext.getSharedPreferences(PREF_NAME_ROOMS, Context.MODE_PRIVATE);
        ArrayList<Room> rooms = null;
        String serializeOrg = boxPrefs.getString(ROOMCLASS, null);
        try {
            if (serializeOrg != null) {
                rooms = (ArrayList<Room>) ObjectSerializer.deserialize(serializeOrg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    /**
     * Method to validate whether the password is at least 8 characters long and has atleast
     * one alphabet, 0ne number and one special character.
     **/
    public static boolean isPasswordValid(String text) {
        // REG EXP ===>> ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$ (Works-
        //  but need to remove Uppercase expresion)
        // REG EXP ===>> ^([a-zA-Z+]+[0-9+]+[&@!#+]+)$ (This is bogus)
        return text.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
}
