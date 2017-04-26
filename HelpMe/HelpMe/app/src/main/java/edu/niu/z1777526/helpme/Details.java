package edu.niu.z1777526.helpme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    String num1=null;
    String num2=null;
    String num3=null;
    String sm=null;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        b=(Button) findViewById(R.id.buttonSave);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                EditText tv1=(EditText) findViewById(R.id.editTextNum1);
                num1=tv1.getText().toString();

                EditText tv2=(EditText) findViewById(R.id.editTextNum2);
                num2=tv2.getText().toString();



                EditText tv4=(EditText) findViewById(R.id.editTextMessage);
                sm=tv4.getText().toString();
                if(num1.length() ==0 && num2.length()==0)
                {
                    Toast.makeText(Details.this,"Enter At least one phone number ",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences pref=getSharedPreferences("MYPREF", 0);
                final SharedPreferences.Editor editor=pref.edit();
                editor.putString("num1",num1);
                editor.putString("num2",num2);
                editor.putString("num3",num3);
                editor.putString("sm",sm);
                editor.commit();

                Toast.makeText(Details.this,"Saved the Settings",Toast.LENGTH_SHORT).show();

                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToMain);


            }
        });

    }
}
