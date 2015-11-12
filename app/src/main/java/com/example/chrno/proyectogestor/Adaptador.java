package com.example.chrno.proyectogestor;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.io.File;
import java.util.*;

/**
 * Created by Chrno on 10/11/2015.
 */
public class Adaptador  extends ArrayAdapter<File> {
    private Context c;
    private int res;
    private List<File> archivos;
    private LayoutInflater i;
    private File f;

    public Adaptador(Context context, int resource, List<File> array) {
        super(context, resource, array);
        this.c=context;
        this.res=resource;
        this.archivos=array;
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
            convertView.setTag(gv);
        }else gv=(GuardaLista) convertView.getTag();

        f=archivos.get(position);
        if(f.isFile())
            gv.iv.setImageResource(R.drawable.arc);
        else if(f.isDirectory())
            gv.iv.setImageResource(R.drawable.fic);

        String nombre=f.getName();
        if(nombre.length()>20)
            nombre=nombre.substring(0,18)+"...";
        gv.tv1.setText(nombre);

        Date d=new Date();
        d.setTime(f.lastModified());
        String fecha=(d.getDay()+1)+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900);
        gv.tv2.setText(""+fecha);

        String s="";
        if(f.canRead())
            s+="r ";
        if(f.canWrite())
            s+="w ";
        if(f.canExecute())
            s+="x ";
        if(!f.isDirectory() ){
            gv.tv4.setVisibility(View.VISIBLE);
            long tam=f.length()/1024;
            if(tam!=0)
                gv.tv4.setText(tam+"kB");
        }
        gv.tv3.setText(s);
        return convertView;
    }

}

