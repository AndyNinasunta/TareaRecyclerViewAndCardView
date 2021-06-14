package com.example.tarearecyclerviewandcardview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.tarearecyclerviewandcardview.Model.Revista;
import com.example.tarearecyclerviewandcardview.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RevistaViewHolder> {
    private Context ctx;
    public List<Revista> revistaLista;
    private RequestQueue request;
    public RecyclerAdapter(View.OnClickListener onClickListener, List<Revista> revistaLista) {
        this.revistaLista = revistaLista;
    }
    public static class RevistaViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitle,txtDescription;
        private ImageView imgUrlCover;




        public RevistaViewHolder(View itemView) {
            super(itemView);

            txtTitle= (TextView)itemView.findViewById(R.id.txtTitle);
            txtDescription= (TextView)itemView.findViewById(R.id.txtDescr);
            imgUrlCover=(ImageView)itemView.findViewById(R.id.imgCover);

        }
    }


    public RecyclerAdapter(Context mCtx, List<Revista> revistaLista) {
        this.ctx = mCtx;
        this.revistaLista = revistaLista;
    }




    @Override
    public RevistaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cuadro_revista,parent,false);
        RevistaViewHolder reViewHolder = new RevistaViewHolder(view);
        return reViewHolder;
    }

    //Modificación del contenido para cada cardView

    @Override
    public void onBindViewHolder(RevistaViewHolder holder, int position) {

        holder.txtTitle.setText(revistaLista.get(position).getTitle());
        holder.txtDescription.setText("Vol. "+revistaLista.get(position).getVolume()+" Núm."+revistaLista.get(position).getNumber()+" ("+revistaLista.get(position).getYear()+")" );

        Glide.with(ctx).load(revistaLista.get(position).getUrlImgCover()).into(holder.imgUrlCover);

    }

    @Override
    public int getItemCount() {
        return revistaLista.size();
    }

}
