package org.cuatrovientos.Tamagotchis;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Tamagotchi implements Runnable {

    private int id;
    private String nombre;
    private int suciedad = 0;
    private int tiempoComer;

    private long nacimiento;
    private long ultimoAumentoSuciedad;

    private boolean vivo = true;
    private boolean ocupado = false;
    private boolean avisoSuciedad = false;

    private Thread hilo;
    private ExecutorService acciones;

    private BlockingQueue<Integer> respuestas = new LinkedBlockingQueue<>();

    public Tamagotchi(int id, String nombre, int tiempoComer) {
        this.id = id;
        this.nombre = nombre;
        this.tiempoComer = tiempoComer;

        this.nacimiento = System.currentTimeMillis();
        this.ultimoAumentoSuciedad = nacimiento;

        acciones = Executors.newSingleThreadExecutor();

        hilo = new Thread(this);
        hilo.start();

        System.out.println(nombre + " ha nacido.");
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public synchronized boolean estaVivo() {
        return vivo;
    }

    public synchronized int getSuciedad() {
        return suciedad;
    }

    public synchronized boolean estaOcupado() {
        return ocupado;
    }

    @Override
    public void run() {

        while (estaVivo()) {

            try {
                Thread.sleep(1000);

                synchronized (this) {

                    if (!vivo) {
                        break;
                    }

                    long ahora = System.currentTimeMillis();

                    if (ahora - nacimiento >= 300000) {
                        System.out.println(nombre + " ha cumplido 5 minutos de vida.");
                        morir();
                        break;
                    }

                    if (ahora - ultimoAumentoSuciedad >= 20000) {

                        suciedad++;
                        ultimoAumentoSuciedad += 20000;

                        System.out.println(nombre + " tiene suciedad: " + suciedad + "/10");

                        if (suciedad == 5 && !avisoSuciedad) {
                            avisoSuciedad = true;
                            System.out.println(nombre + ": ¡Estoy empezando a estar muy sucio!");
                        }

                        if (suciedad >= 10) {
                            System.out.println(nombre + " ha muerto por exceso de suciedad.");
                            morir();
                            break;
                        }
                    }
                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private synchronized void ejecutarAccion(Runnable accion) {

        if (!vivo) {
            System.out.println(nombre + " ya está muerto.");
            return;
        }

        if (ocupado) {
            System.out.println(nombre + " está ocupado. No puede realizar otra acción.");
            return;
        }

        ocupado = true;

        acciones.submit(() -> {
            try {
                accion.run();
            } finally {
                synchronized (this) {
                    ocupado = false;
                }
            }
        });
    }

    public void comer() {

        ejecutarAccion(() -> {

            try {
                System.out.println(nombre + " empieza a comer una manzana.");

                Thread.sleep(tiempoComer);

                if (vivo) {
                    System.out.println(nombre + " ha terminado de comer.");
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void limpiar() {

        ejecutarAccion(() -> {

            try {
                System.out.println(nombre + " empieza a bañarse.");

                Thread.sleep(5000);

                synchronized (this) {
                    if (vivo) {
                        suciedad = 0;
                        avisoSuciedad = false;
                        ultimoAumentoSuciedad = System.currentTimeMillis();

                        System.out.println(nombre + " ha terminado de bañarse. Suciedad: 0/10.");
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void jugar() {

        ejecutarAccion(() -> {

            try {

                while (vivo) {

                    int numero1 = (int) (Math.random() * 9) + 1;
                    int numero2 = (int) (Math.random() * (10 - numero1));

                    int resultadoCorrecto = numero1 + numero2;

                    System.out.println("\n" + nombre + " pregunta:");
                    System.out.println("¿Cuánto es " + numero1 + " + " + numero2 + "?");
                    System.out.println("El cuidador debe responder mediante el menú.");

                    int respuesta = respuestas.take();

                    if (!vivo) {
                        break;
                    }

                    if (respuesta == resultadoCorrecto) {
                        System.out.println(nombre + ": ¡Correcto! Me gusta jugar contigo.");
                        break;
                    } else {
                        System.out.println(nombre + ": ¡Incorrecto! Vamos a intentarlo otra vez.");
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public void responder(int respuesta) {

        if (estaVivo() && estaOcupado()) {
            respuestas.offer(respuesta);
        } else {
            System.out.println("Este Tamagotchi no está jugando.");
        }
    }

    public synchronized void matar() {

        if (!vivo) {
            System.out.println(nombre + " ya está muerto.");
            return;
        }

        if (ocupado) {
            System.out.println(nombre + " está ocupado. No puede ser destruido ahora.");
            return;
        }

        System.out.println(nombre + " está siendo destruido.");
        morir();
    }

    private synchronized void morir() {

        if (!vivo) {
            return;
        }

        vivo = false;

        System.out.println(nombre + " ha muerto.");

        acciones.shutdownNow();

        hilo.interrupt();
    }
}
