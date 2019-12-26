package com.zenai.ticketonline.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zenai.ticketonline.R;
import com.zenai.ticketonline.models.data_mahasiswa;
import com.zenai.ticketonline.models.data_wisata;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class WisataFragment extends Fragment implements View.OnClickListener{

    private EditText name,location, price, description;
    private FirebaseAuth auth;
    private Button save;

    public WisataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wisata, container, false);
        name = view.findViewById(R.id.nameText);
        location = view.findViewById(R.id.locationText);
        price = view.findViewById(R.id.priceText);
        description = view.findViewById(R.id.descriptionText);

        auth = FirebaseAuth.getInstance(); //Mendapakan Instance Firebase Autentifikasi

        save = view.findViewById(R.id.buttonsubmit);
        save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonsubmit:
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                String getUserID = auth.getCurrentUser().getUid();

                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference getReference;

                //Menyimpan Data yang diinputkan User kedalam Variable
                String getName = name.getText().toString();
                String getLocation = location.getText().toString();
                Integer getPrice = Integer.parseInt(price.getText().toString());
                String getDescription = description.getText().toString();

                getReference = database.getReference(); // Mendapatkan Referensi dari Database

                // Mengecek apakah ada data yang kosong
                if (isEmpty(getName) || isEmpty(getLocation) ||  isEmpty(getDescription)) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(getActivity(), "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
                    Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
                    */
                    getReference.child("TicketOnline").child(getUserID).child("Tours").push()
                            .setValue(new data_wisata(getName, getLocation, getDescription, getPrice))
                            .addOnSuccessListener(this, new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                    name.setText("");
                                    location.setText("");
                                    price.setText("");
                                    description.setText("");
                                    Toast.makeText(getActivity(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
    }
}
