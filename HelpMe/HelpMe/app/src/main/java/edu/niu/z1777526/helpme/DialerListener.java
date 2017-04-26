package edu.niu.z1777526.helpme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by momin on 11/6/2016.
 */
public class DialerListener extends BroadcastReceiver {
    String type = null;
    @Override
    public void onReceive(Context context, Intent intent) {

        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Log.d("DEBUG", "RINGING (outgoing call) with number: " + number);
   //     Toast.makeText(context, "Outgoing call number: " + number, Toast.LENGTH_LONG).show();
        if(number.equals("116")) {

            sendMessage(context);

        }
    }

    private void sendMessage(Context context)
    {
        SharedPreferences pref=context.getSharedPreferences("MYPREF", 0);
        String num1=pref.getString("num1","");
        String num2=pref.getString("num2","");
        String sm=pref.getString("sm","Emergency");


        if(num1.equals("") && num2.equals("")){
            Toast.makeText(context,"Set the Numbers and the message to be sent",Toast.LENGTH_SHORT).show();
        }
        else {
            GPSTracker gps = new GPSTracker(context);
            if(gps.canGetLocation())
            {
                String lat=String.valueOf(gps.getLatitude()); // returns latitude
                String lon=String.valueOf(gps.getLongitude()); // returns longitude
                type=gps.getType();

                String sms=sm.concat("\nMy Location \n" +
                        "http://maps.google.com/maps?q="+lat+","+lon);

                Toast.makeText(context,type+" : "+lat+","+lon,Toast.LENGTH_SHORT).show();
                Log.d("hey",lat+","+lon);

                if(lat.equals("0"))
                {
                    Toast.makeText(context,
                            "GPS co-ordinates not yet ready!! pls keep trying.",
                            Toast.LENGTH_LONG).show();
                }
                else
                {

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(num1, null, sms, null, null);
                        smsManager.sendTextMessage(num2, null, sms, null, null);
                        Toast.makeText(context, "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
 //                       Toast.makeText(context,
 //                               "SMS faild, please try again later!",
 //                               Toast.LENGTH_LONG).show();
  //                      e.printStackTrace();
                    }
                }
            }
            else{
                gps.showSettingsAlert();
            }


        }



    }
}
