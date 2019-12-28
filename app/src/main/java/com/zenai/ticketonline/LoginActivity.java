package com.zenai.ticketonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText username,password;
    private Button login,loginGoogle;
    private FirebaseAuth auth;

    //Membuat Kode Permintaan
    private int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
        loginGoogle = findViewById(R.id.btnLoginGoogle);
        loginGoogle.setOnClickListener(this);

        auth = FirebaseAuth.getInstance(); //Mendapakan Instance Firebase Autentifikasi

        if(auth.getCurrentUser() == null){

        }
        else {
            startActivity(new  Intent(this,MainActivity.class));
        }
    }

    public void authenticateLogin(View view) {
        if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(), "Login Sukses!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
//            finish();
        } else{
            Toast.makeText(getApplicationContext(), "Username/Password Anda Salah", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLoginGoogle:
                // Statement program untuk login/masuk
                startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()

                                //Memilih Provider atau Method masuk yang akan kita gunakan
                                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN adalah kode permintaan yang Anda berikan ke startActivityForResult, saat memulai masuknya arus.
        if (requestCode == RC_SIGN_IN) {

            //Berhasil masuk
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
            }else {
                Toast.makeText(this, "Login Dibatalkan", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
