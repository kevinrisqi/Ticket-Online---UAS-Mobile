package com.zenai.ticketonline;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zenai.ticketonline.models.data_mahasiswa;
import com.zenai.ticketonline.models.data_wisata;

public class UpdateData extends AppCompatActivity {

    //Deklarasi Variable
    private EditText namaBaru, lokasiBaru, hargaBaru, deskripsiBaru;
    private Button update;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekNama, cekLokasi, cekDeskripsi;
    private Integer cekHarga;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        namaBaru = findViewById(R.id.new_name);
        lokasiBaru = findViewById(R.id.new_location);
        hargaBaru = findViewById(R.id.new_price);
        deskripsiBaru = findViewById(R.id.new_description);
        update = findViewById(R.id.update);
        title = findViewById(R.id.title);
        title.setText("Update Data");

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Mahasiswa yang akan dicek
                cekNama = namaBaru.getText().toString();
                cekLokasi = lokasiBaru.getText().toString();
                cekHarga = Integer.parseInt(hargaBaru.getText().toString());
                cekDeskripsi = deskripsiBaru.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if(isEmpty(cekLokasi) || isEmpty(cekNama) || isEmpty(cekDeskripsi)){
                    Toast.makeText(UpdateData.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    //Menjalankan proses update data
                    data_wisata setWisata = new data_wisata();
                    setWisata.setNama(namaBaru.getText().toString());
                    setWisata.setLokasi(lokasiBaru.getText().toString());
                    setWisata.setHarga(Integer.parseInt(hargaBaru.getText().toString()));
                    setWisata.setDeskripsi(deskripsiBaru.getText().toString());
                    updateMahasiswa(setWisata);
                }
            }
        });
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getLokasi = getIntent().getExtras().getString("dataLocation");
        final Integer getHarga = getIntent().getExtras().getInt("dataPrice");
        final String getDeskripsi = getIntent().getExtras().getString("dataDescription");
        namaBaru.setText(getNama);
        lokasiBaru.setText(getLokasi);
        hargaBaru.setText(String.valueOf(getHarga));
        deskripsiBaru.setText(getDeskripsi);
    }

    //Proses Update data yang sudah ditentukan
    private void updateMahasiswa(data_wisata wisata){
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Ticket Online")
                .child(userID)
                .child("Tours")
                .child(getKey)
                .setValue(wisata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        namaBaru.setText("");
                        lokasiBaru.setText("");
                        hargaBaru.setText("");
                        deskripsiBaru.setText("");
                        Toast.makeText(UpdateData.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}