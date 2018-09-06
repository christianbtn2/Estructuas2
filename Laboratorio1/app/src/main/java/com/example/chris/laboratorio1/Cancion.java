package com.example.chris.laboratorio1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cancion {

    public String nombre;
    public double duracion;
    public Map<String,Cancion> Libreria = new HashMap<String, Cancion>();
    public List<Cancion> playlist = new ArrayList<>();
    public List<String> adaptador = new ArrayList<>();
    public List<String> mostrarPlaylist = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void Agregar(){

        Cancion c = new Cancion();

        c.nombre = "No es justo";
        adaptador.add(c.nombre);
        c.duracion = 4.11;
        Libreria.put("No es justo",c);

        c.nombre = "Vaina Loca";
        adaptador.add(c.nombre);
        c.duracion = 2.56;
        Libreria.put("Vaina loca",c);

        c.nombre = "Sin pijama";
        adaptador.add(c.nombre);
        c.duracion = 3.08;
        Libreria.put("Sin pijama",c);

        c.nombre = "La player";
        adaptador.add(c.nombre);
        c.duracion = 4.09;
        Libreria.put("La player",c);

        c.nombre = "Te bote";
        adaptador.add(c.nombre);
        c.duracion = 6.57;
        Libreria.put("Te bote",c);

        c.nombre = "Por perro";
        adaptador.add(c.nombre);
        c.duracion = 4.05;
        Libreria.put("Por perro",c);

        c.nombre = "Quiere beber";
        adaptador.add(c.nombre);
        c.duracion = 3.07;
        Libreria.put("Quiere beber",c);

        c.nombre = "Bebe";
        adaptador.add(c.nombre);
        c.duracion = 3.37;
        Libreria.put("Bebe",c);

        c.nombre = "No me acuerdo";
        adaptador.add(c.nombre);
        c.duracion = 3.38;
        Libreria.put("No me acuerdo",c);

        c.nombre = "Cuando te bese";
        adaptador.add((c.nombre));
        c.duracion = 4.14;
        Libreria.put("Cuando te bese",c);



    }


    public void AgregarCancion(String cancion){

        if (Libreria.containsKey(cancion)){
            Cancion puente = Libreria.get(cancion);
            playlist.add(puente);
            mostrarPlaylist.add(cancion);
        }

    }




    public void OrdenarNombreA(){
    Collections.sort(playlist, new Comparator<Cancion>() {
        @Override
        public int compare(Cancion o1, Cancion o2) {
            return o1.getNombre().compareTo(o2.getNombre());
        }
    });
    int i = 0;
    mostrarPlaylist.clear();
    for (i = 0; i < playlist.size(); i++){
        mostrarPlaylist.add(playlist.get(i).nombre);
    }


    }



    public void OrdenarDuracionD(){




        int i = 0;
        mostrarPlaylist.clear();

        for (i = 0; i < playlist.size() ; i++){
            mostrarPlaylist.add(playlist.get(i).nombre.toString());
        }

    }


}
