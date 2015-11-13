package com.example.chrno.proyectogestor;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        String ruta=""; //ruta en la que estamos
        lv=(ListView)findViewById(R.id.lv);
        ImageView iv=(ImageView)findViewById(R.id.imageView2);
        Intent i=getIntent();
        Bundle b=i.getExtras();
            if(b==null)
                ruta="/";
            else ruta=b.getString("ruta"); // si bundle no esta vacio es que pasamos una ruta
            if(ruta.equals("/"))
                iv.setVisibility(View.GONE);//si la ruta es '/' pongo en invisible el boton de atras
            else iv.setVisibility(View.VISIBLE);

        TextView tv=(TextView)findViewById(R.id.ruta);
        tv.setText(ruta);//pongo la ruta en el text view
        generaAdaptador(ruta);//genero el adaptador pasandole la ruta del fichero que debe abrir
    }

    public void generaAdaptador(final String ruta){
        aux=getArchivos(ruta);//recojo el array de archivos que hay en la ruta que le pasamos
        ad=new Adaptador(this,R.layout.elemento_lista, aux);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                File f=aux.get(posicion);
                if(f.isDirectory()){// si es un directorio pongo su ruta en el bundle y empiezo otra actividad
                String s=f.getAbsolutePath();
                    Intent i=new Intent(Principal.this,Principal.class);
                    Bundle b=new Bundle();
                    b.putString("ruta",s);
                    i.putExtras(b);
                    startActivity(i);
                }else{ // si no abriremos el fichero
//                    Toast.makeText(v.getContext(),R.string.v2,Toast.LENGTH_SHORT).show();
                    if(f.canExecute()){//si se puede ejecutar
                        List<String> fichero=new ArrayList<>();
                        String fic=lectura(f.getAbsolutePath());
                            fichero.add(""+fic);
                        AdaptadorFichero af=
                                new AdaptadorFichero(Principal.this,R.layout.elemento_lista,fichero);
                        lv.setAdapter(af);
                        TextView tv=(TextView)findViewById(R.id.ruta);
                        tv.setText(f.getAbsolutePath());//pongo la ruta en el text view
                    }
                }
            }
        });
        registerForContextMenu(lv);
    }
    public String lectura(String ruta){
        String s ="";
        try {
            File file = new File(ruta);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                s+=(line);
                Log.i("Test", "text : "+s+" : end");
                s+=('\n');
            } }
        catch (IOException e) {}

        return s;
    }
    private static List<File> getArchivos(String ruta){//metodo que devuelve el array de archivos
        File f=new File(ruta);
        File lista[]=f.listFiles();
            List<File> al=new ArrayList<>();
        if(lista!=null){// si la lista esta vacia es que dentro del archivo no hay nada que mostrar
            for(File fichero:lista)
                al.add(fichero);
            Collections.sort(al, OrdenarArchivo.getComparador());//ordeno los archivos (directorios primero)
        }
        return al;
    }
    // BOTONES
    public void atras(View v){
        this.finish();
    }
}
