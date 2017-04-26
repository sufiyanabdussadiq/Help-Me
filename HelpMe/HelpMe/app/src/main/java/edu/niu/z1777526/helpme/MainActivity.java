/******************************************************************************
 * NIU HUSKIE HACK 2016
 * DEVELOPERS: SHOAIB MOMIN, SUFIYAN ABDUS SADIQ, ABDUL BARI
 * DATE: NOVEMBER 6, 2016
 * PURPOSE: This application allows the user to send his/her coordinates to trusted contacts during an emergency situation.
 The trusted contacts are stored before-hand. You can also dial HelpMe from contacts or press the Power Button twice  to send your location.
 */

package edu.niu.z1777526.helpme;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String type=null;
    public Button b;
    String lat;
    String lon;
    private PowerButtonListener mReceiver ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mReceiver = new PowerButtonListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //intentFilter.addAction(Intent.ACTION_SHUTDOWN); // won't work unless you're the device vendor

        registerReceiver(mReceiver, intentFilter);


        Button b=(Button) findViewById(R.id.buttonAlert);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences pref=getSharedPreferences("MYPREF", 0);
                String num1=pref.getString("num1","");
                String num2=pref.getString("num2","");
                String sm=pref.getString("sm","Emergency");


                if(num1.equals("") && num2.equals("")){
                    Toast.makeText(MainActivity.this,"Set the Numbers and the message to be sent",Toast.LENGTH_SHORT).show();
                }
                else {
                    GPSTracker gps = new GPSTracker(MainActivity.this);
                    if(gps.canGetLocation())
                    {
                        lat=String.valueOf(gps.getLatitude()); // returns latitude
                        lon=String.valueOf(gps.getLongitude()); // returns longitude
                        type=gps.getType();

                        String sms=sm.concat("\nMy Location \n" +
                                "http://maps.google.com/maps?q="+lat+","+lon);

                        Toast.makeText(MainActivity.this,type+" : "+lat+","+lon,Toast.LENGTH_SHORT).show();
                        Log.d("hey",lat+","+lon);

                        if(lat.equals("0"))
                        {
                            Toast.makeText(getApplicationContext(),
                                    "GPS co-ordinates not yet ready!! pls keep trying.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(num1, null, sms, null, null);
                                smsManager.sendTextMessage(num2, null, sms, null, null);
                                Toast.makeText(getApplicationContext(), "SMS Sent!",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
//                                Toast.makeText(getApplicationContext(),
  //                                      "SMS faild, please try again later!",
  //                                      Toast.LENGTH_LONG).show();
    //                            e.printStackTrace();
                            }
                        }
                    }
                    else{
                        gps.showSettingsAlert();
                        if(gps.canGetLocation) {
                            lat=String.valueOf(gps.getLatitude()); // returns latitude
                            lon=String.valueOf(gps.getLongitude()); // returns longitude

                        }



                    }


                }


            }
        });





    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mReceiver);
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
        if (id == R.id.action_settings) {

            Intent i =new Intent(this, Details.class);
            startActivity(i);
        }
        if(id == R.id.about)
        {
            Intent j = new Intent(this, About.class);
            startActivity(j);
        }




        return super.onOptionsItemSelected(item);
    }
}
