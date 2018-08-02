package com.textmessaging.sau.textmessaging.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rilixtech.CountryCodePicker;
import com.textmessaging.sau.textmessaging.R;
import com.textmessaging.sau.textmessaging.pojo.LogInResponse;
import com.textmessaging.sau.textmessaging.retrofit.ApiClient;
import com.textmessaging.sau.textmessaging.retrofit.ApiInterface;


import retrofit2.Call;

public class LoginScreen extends AppCompatActivity {

    LogInResponse logInResponsesData;
    EditText edtnumber;
    Button signUp;
    ApiInterface apiService;
    CountryCodePicker ccp;
    String Mob=null;

    private static final String TAG = "LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        edtnumber = findViewById(R.id.edtnumber);
        signUp = findViewById(R.id.btnsignUp);




        //  System.out.println("Mobile Number"+Mob);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate the fields and call sign method to implement the api
                if (validate(edtnumber)) {
                    Mob = edtnumber.getText().toString();
                    apicall();
                }
            }
        });


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
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {

            }
        });
    }


    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returs true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }


}
