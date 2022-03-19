package com.example.appsselfhy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appsselfhy.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    List<ItemList> mItems;

    public GridAdapter() {
        super();
        mItems = new ArrayList<ItemList>();

        ItemList nama = new ItemList();
        nama.setName("CosRx -Rp.103.000");
        nama.setThumbnail(R.drawable.item1);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Somebymi -Rp.250.000");
        nama.setThumbnail(R.drawable.item2);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Neutrogena -Rp.150.000");
        nama.setThumbnail(R.drawable.item3);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Iphone 11 Pro 256GB");
        nama.setThumbnail(R.drawable.item4);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Harry Potter Books");
        nama.setThumbnail(R.drawable.item5);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Case Iphone -Rp.50.000");
        nama.setThumbnail(R.drawable.item6);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Foreo Luna -Rp.3.000.000");
        nama.setThumbnail(R.drawable.item7);
        mItems.add(nama);

        nama = new ItemList();
        nama.setName("Cetaphil Moisturizer");
        nama.setThumbnail(R.drawable.item8);
        mItems.add(nama);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemList nature = mItems.get(i);
        viewHolder.tvspecies.setText(nature.getName());
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {


        public ImageView imgThumbnail;
        public TextView tvspecies;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.status);

        }
    }

}
