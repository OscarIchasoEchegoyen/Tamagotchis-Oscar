package org.cuatrovientos.Tamagotchis;

import java.util.ArrayList;

public class Cuidador {

    private ArrayList<Tamagotchi> tamagotchis = new ArrayList<>();

    private int siguienteId = 1;

    public void crearTamagotchi(String nombre, int tiempoComer) {

        Tamagotchi t = new Tamagotchi(
                siguienteId++,
                nombre,
                tiempoComer
        );

        tamagotchis.add(t);
    }

    public Tamagotchi buscarTamagotchi(int id) {

        for (Tamagotchi t : tamagotchis) {

            if (t.getId() == id) {
                return t;
            }
        }

        return null;
    }

    public void mostrarTamagotchis() {

        System.out.println("\n====== ESTADO DE LOS TAMAGOTCHIS ======");

        for (Tamagotchi t : tamagotchis) {

            System.out.println("------------------------------");
            System.out.println("ID: " + t.getId());
            System.out.println("Nombre: " + t.getNombre());
            System.out.println("Vivo: " + (t.estaVivo() ? "Sí" : "No"));
            System.out.println("Suciedad: " + t.getSuciedad() + "/10");
            System.out.println("Estado: " + (t.estaOcupado() ? "Ocupado" : "Libre"));
        }

        System.out.println("========================================");
    }

    public void alimentar(int id) {

        Tamagotchi t = buscarTamagotchi(id);

        if (t != null) {
            t.comer();
        } else {
            System.out.println("No existe ese Tamagotchi.");
        }
    }

    public void jugar(int id) {

        Tamagotchi t = buscarTamagotchi(id);

        if (t != null) {
            t.jugar();
        } else {
            System.out.println("No existe ese Tamagotchi.");
        }
    }

    public void limpiar(int id) {

        Tamagotchi t = buscarTamagotchi(id);

        if (t != null) {
            t.limpiar();
        } else {
            System.out.println("No existe ese Tamagotchi.");
        }
    }

    public void destruir(int id) {

        Tamagotchi t = buscarTamagotchi(id);

        if (t != null) {
            t.matar();
        } else {
            System.out.println("No existe ese Tamagotchi.");
        }
    }

    public void responder(int id, int respuesta) {

        Tamagotchi t = buscarTamagotchi(id);

        if (t != null) {
            t.responder(respuesta);
        } else {
            System.out.println("No existe ese Tamagotchi.");
        }
    }
}

