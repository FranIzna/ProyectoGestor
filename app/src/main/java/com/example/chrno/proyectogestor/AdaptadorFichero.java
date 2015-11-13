package com.example.chrno.proyectogestor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Chrno on 13/11/2015.
 */
public class AdaptadorFichero extends ArrayAdapter<String> {
    private Context c;
    private int res;
    private List<String> array;
    private LayoutInflater i;

    public AdaptadorFichero(Context context, int resource, List<String> array) {
        super(context, resource, array);
        this.c=context;
        this.res=resource;
        this.array=array;
        i= (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    static class GuardaLista{
        public TextView tv1,tv2,tv3,tv4;
        public ImageView iv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GuardaLista gv=new GuardaLista();
        if(convertView==null){
            convertView =i.inflate(res,null);

            TextView tv = (TextView)convertView.findViewById(R.id.tvNombre);
            gv.tv1=tv;
            tv=(TextView)convertView.findViewById(R.id.tvFecha);
            gv.tv2=tv;
            tv=(TextView)convertView.findViewById(R.id.tvPermisos);
            gv.tv3=tv;
            tv=(TextView)convertView.findViewById(R.id.tvTam);
            gv.tv4=tv;
            gv.tv4.setVisibility(View.INVISIBLE);

            ImageView iv=(ImageView)convertView.findViewById(R.id.imageView);
            gv.iv=iv;
gv.tv2.setVisibility(View.GONE);
gv.tv3.setVisibility(View.GONE);
gv.tv4.setVisibility(View.GONE);
gv.iv.setVisibility(View.GONE);
        convertView.setTag(gv);
    }else gv=(GuardaLista) convertView.getTag();
    String s="";
        for(String s1:array)
            s+=s1+"\n";

        gv.tv1.setText(s);
        return convertView;
    }

}