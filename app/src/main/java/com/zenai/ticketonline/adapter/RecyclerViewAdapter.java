package com.zenai.ticketonline.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zenai.ticketonline.MyListData;
import com.zenai.ticketonline.R;
import com.zenai.ticketonline.UpdateData;
import com.zenai.ticketonline.models.data_mahasiswa;
import com.zenai.ticketonline.models.data_wisata;

import java.util.ArrayList;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    //Deklarasi Variable
    private ArrayList<data_wisata> listWisata;
    private Context context;

    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(data_wisata data, int position);
    }

    //Deklarasi objek dari Interfece
    dataListener listener;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter(ArrayList<data_wisata> listWisata, Context context) {
        this.listWisata = listWisata;
        this.context = context;
        listener = (MyListData)context;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, location, price, description;
        private LinearLayout ListItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String nama = listWisata.get(position).getNama();
        final String location = listWisata.get(position).getLokasi();
        final int price = listWisata.get(position).getHarga();
        final String description = listWisata.get(position).getDeskripsi();

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.name.setText("Name: "+nama);
        holder.location.setText("Location: "+location);
        holder.price.setText("Price: "+price);
        holder.description.setText("Description: "+description);

        //Menampilkan Menu Update dan Delete saat user melakukan long klik pada salah satu item
        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                /*
                                  Berpindah Activity pada halaman layout updateData
                                  dan mengambil data pada listMahasiswa, berdasarkan posisinya
                                  untuk dikirim pada activity updateData
                                 */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataNama", listWisata.get(position).getNama());
                                bundle.putString("dataLocation", listWisata.get(position).getLokasi());
                                bundle.putInt("dataPrice", listWisata.get(position).getHarga());
                                bundle.putString("dataDescription", listWisata.get(position).getDeskripsi());
                                bundle.putString("getPrimaryKey", listWisata.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateData.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                //Menggunakan interface untuk mengirim data mahasiswa, yang akan dihapus
                                listener.onDeleteData(listWisata.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listWisata.size();
    }

}
