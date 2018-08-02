package com.textmessaging.sau.textmessaging.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.textmessaging.sau.textmessaging.R;

public class OTPScreen extends AppCompatActivity {

    EditText edtotp;
    Button btnverify;
    String otp,mEdtOtp;
    private static final String TAG = "OTPScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        edtotp = findViewById(R.id.edtotp);
        btnverify = findViewById(R.id.btnverify);
        Intent intent = getIntent();

        otp = intent.getStringExtra("otp");

        Log.d(TAG, "otp: "+otp);



        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate(edtotp)) {
                    mEdtOtp = edtotp.getText().toString();
                   VerifyOTP();
                }

            }
        });


    }

    private void VerifyOTP() {

        if (otp.equals(mEdtOtp))
        {
            Intent i = new Intent(getApplicationContext(),HomeScreen.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(OTPScreen.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returs true if field is not empty
        }
        editText.setError("Please Enter OTP");
        editText.requestFocus();
        return false;
    }
}
