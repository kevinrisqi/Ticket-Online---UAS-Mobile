package com.zenai.ticketonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zenai.ticketonline.models.data_mahasiswa;
import com.zenai.ticketonline.models.data_wisata;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Deklarasi Variable
    private ProgressBar progressBar;
    private EditText name, location, price, description;
    private FirebaseAuth auth;
    private Button Logout, Simpan, ShowData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        //Inisialisasi ID (Button)

        Logout = findViewById(R.id.logout);
        Logout.setOnClickListener(this);
        Simpan = findViewById(R.id.buttonsubmit);
        Simpan.setOnClickListener(this);
        ShowData = findViewById(R.id.showdata);
        ShowData.setOnClickListener(this);

        auth = FirebaseAuth.getInstance(); //Mendapakan Instance Firebase Autentifikasi

        //Inisialisasi ID (EditText)
        name = findViewById(R.id.nameText);
        location = findViewById(R.id.locationText);
        price = findViewById(R.id.priceText);
        description = findViewById(R.id.descriptionText);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonsubmit:
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                String getUserID = auth.getCurrentUser().getUid();

                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference getReference;

                //Menyimpan Data yang diinputkan User kedalam Variable
                String getName = name.getText().toString();
                String getLocation = location.getText().toString();
                int getPrice = Integer.parseInt(price.getText().toString());
                String getDescription = description.getText().toString();

                getReference = database.getReference(); // Mendapatkan Referensi dari Database

                // Mengecek apakah ada data yang kosong
                if (isEmpty(getName) || isEmpty(getLocation) || isEmpty(getDescription) ) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(MainActivity.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
                    Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
                    */
                    getReference.child("Ticket Online").child(getUserID).child("Tours").push()
                            .setValue(new data_wisata(getName, getLocation, getDescription,getPrice))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                    name.setText("");
                                    location.setText("");
                                    price.setText("");
                                    description.setText("");
                                    Toast.makeText(MainActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;

            case R.id.showdata:
                startActivity(new Intent(MainActivity.this, MyListData.class));
                break;

            case R.id.logout:
                // Statement program untuk logout/keluar
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(MainActivity.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                break;
        }

    }

    // Mengecek apakah ada data yang kosong
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }


}
