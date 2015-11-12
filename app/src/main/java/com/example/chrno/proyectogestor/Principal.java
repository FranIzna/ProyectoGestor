package com.example.chrno.proyectogestor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Principal extends Activity {
    private ListView lv;
    private Adaptador ad;
    private  List<File> aux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        init();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_principal, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    public void init(){
        String ruta="";
        lv=(ListView)findViewById(R.id.lv);
        ImageView iv=(ImageView)findViewById(R.id.imageView2);
        Intent i=getIntent();
        Bundle b=i.getExtras();
            if(b==null)
                ruta="/";
            else ruta=b.getString("ruta");
            if(ruta.equals("/"))
                iv.setVisibility(View.GONE);
            else iv.setVisibility(View.VISIBLE);

        TextView tv=(TextView)findViewById(R.id.ruta);
        tv.setText(ruta);
        generaAdaptador(ruta);
    }

    public void generaAdaptador(String ruta){
        aux=getArchivos(ruta);
        ad=new Adaptador(this,R.layout.elemento_lista, aux);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                File f=aux.get(posicion);
                if(f.isDirectory()){
                String s=f.getAbsolutePath();
                    Intent i=new Intent(Principal.this,Principal.class);
                    Bundle b=new Bundle();
                    b.putString("ruta",s);
                    i.putExtras(b);
                    startActivity(i);
                }else
                    Toast.makeText(v.getContext(),R.string.v2,Toast.LENGTH_SHORT).show();
            }
        });
        registerForContextMenu(lv);
    }
    private static List<File> getArchivos(String ruta){
        File f=new File(ruta);
        File lista[]=f.listFiles();
            List<File> al=new ArrayList<>();
        if(lista!=null){
            for(File fichero:lista)
                al.add(fichero);
            Collections.sort(al, OrdenarArchivo.getComparador());
        }
        return al;
    }
    public void atras(View v){
        this.finish();
    }
}