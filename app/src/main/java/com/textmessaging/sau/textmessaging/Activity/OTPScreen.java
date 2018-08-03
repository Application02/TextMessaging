package com.textmessaging.sau.textmessaging.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.pojo.LogInResponse;
import com.textmessaging.sau.textmessaging.retrofit.ApiClient;
import com.textmessaging.sau.textmessaging.retrofit.ApiInterface;
import com.textmessaging.sau.textmessaging.pojo.StoreNumber;

import retrofit2.Call;

public class OTPScreen extends AppCompatActivity {

    EditText edtotp;
    TextView txttimer,txtretry;
    Button btnverify;
    String otp,mEdtOtp;
    int time=30;
    ApiInterface apiService;
    String Mob;
    private static final String TAG = "OTPScreen";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        edtotp = findViewById(R.id.edtotp);
        btnverify = findViewById(R.id.btnverify);
        txttimer = findViewById(R.id.txttimer);
        txtretry = findViewById(R.id.txtretry);
        Intent intent = getIntent();

        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        otp = intent.getStringExtra("otp");
        Mob = intent.getStringExtra("number");

        Log.d(TAG, "otp: "+otp);



        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check ediText Null OR Not
                if (validate(edtotp)) {
                    mEdtOtp = edtotp.getText().toString();
                   VerifyOTP();
                }

            }
        });

        //Timer Start
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                txttimer.setText("0:"+checkDigit(time));
                time--;
            }

            public void onFinish() {
                Log.e(TAG, "onFinish: " );
                txttimer.setVisibility(View.INVISIBLE);
                txtretry.setVisibility(View.VISIBLE);
                txtretry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        apicall();
                    }
                });
            }

        }.start();




    }

    private void init() {
        Call<StoreNumber> storeNumberCall = apiService.getstorenumber(Mob);
        storeNumberCall.enqueue(new retrofit2.Callback<StoreNumber>() {
            @Override
            public void onResponse(Call<StoreNumber> call, retrofit2.Response<StoreNumber> response) {


                Log.e(TAG, "StoreNumber " + response.body().getSuccess());

                if (response.body().getSuccess().equalsIgnoreCase("true"))
                {

                    Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(OTPScreen.this, "Not Verify! Try Again...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<StoreNumber> call, Throwable t) {

            }
        });
    }

    //timer
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }



    private void apicall() {
        System.out.println("Mobile Number" + Mob);
        Call<LogInResponse> sign = apiService.getCountryList(Mob);
        sign.enqueue(new retrofit2.Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, retrofit2.Response<LogInResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().getOtp());


                Intent intent = new Intent(getApplicationContext(),OTPScreen.class);
                intent.putExtra("otp",response.body().getOtp());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {

            }
        });





    }

    @Override
    public void onResume() {
        //this code for auto detect otp
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //this code for  auto detect otp
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    //this code for  auto detect otp
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                // message is the fetching OTP

                edtotp.setText(message);

                Log.d(TAG, "Auto Fetch OTP " + message);
            }
        }
    };

    //check otp is currect or not
    private void VerifyOTP() {

        if (otp.equals(mEdtOtp))
        {
           init();
        }
        else
        {
            Toast.makeText(OTPScreen.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
        }


    }

    //when EditText is empty then show message
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
