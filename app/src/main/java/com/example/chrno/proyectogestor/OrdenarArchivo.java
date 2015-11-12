package com.example.chrno.proyectogestor;

import java.io.File;
import java.util.Comparator;

/**
 * Created by 2dam on 13/10/2015.
 */

public class OrdenarArchivo{
    public static Comparator<File> getComparador(){
        Comparator<File> c=new Comparator<File>() {
            @Override
            public int compare(File ar1, File ar2) {
                if(ar1.isDirectory() && ar2.isFile())
                    return -1;
                if((ar2.isDirectory() && ar1.isFile()))
                    return 1;
                return ar1.getName().compareTo(ar2.getName());
            }
        };
        return c;
    }
}
