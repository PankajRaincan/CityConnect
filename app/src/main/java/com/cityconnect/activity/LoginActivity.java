package com.cityconnect.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cityconnect.api.APIHelper;
import com.cityconnect.utils.CommonUtils;
import com.cityconnect.R;
import com.cityconnect.model.SignInDTO;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    APIHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        apiHelper = APIHelper.init();


        AppCompatButton mEmailSignInButton = (AppCompatButton) findViewById(R.id.btn_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(TextUtils.isEmpty(email) || !CommonUtils.validateEmail(email)){

            Toast.makeText(this, ""+"Invalid Email", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(password)){

            Toast.makeText(this, ""+"Invalid Password", Toast.LENGTH_SHORT).show();

        }else {

            SignInDTO signInDTO = new SignInDTO();
            signInDTO.setEmail(email);
            signInDTO.setPassword(password);

            final Dialog dialog = CommonUtils.getCircularProgressDialog(LoginActivity.this,"Loading",false);
            dialog.show();
            apiHelper.signIn(signInDTO, new APIHelper.OnRequestComplete() {
                @Override
                public boolean onSuccess(Object object) {

                    dialog.dismiss();

                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                    return false;
                }

                @Override
                public void onFailure(String errorMessage) {

                    Log.v("LOGIN",""+errorMessage);

                    dialog.dismiss();
                }
            });


        }
    }


}

