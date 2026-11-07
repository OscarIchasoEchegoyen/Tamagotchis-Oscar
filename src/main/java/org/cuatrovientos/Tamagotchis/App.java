package org.cuatrovientos.Tamagotchis;

/**
 * Hello world!
 */
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Cuidador cuidador = new Cuidador();

		cuidador.crearTamagotchi("Pikachu", 2000);
		cuidador.crearTamagotchi("Charmander", 4000);
		cuidador.crearTamagotchi("Bulbasaur", 6000);

		int opcion;

		do {

			System.out.println("\n========== CUIDADOR ==========");
			System.out.println("1. Ver Tamagotchis");
			System.out.println("2. Dar de comer");
			System.out.println("3. Jugar");
			System.out.println("4. Limpiar");
			System.out.println("5. Responder a un Tamagotchi");
			System.out.println("6. Destruir Tamagotchi");
			System.out.println("0. Salir");
			System.out.println("==============================");

			System.out.print("Selecciona una opción: ");
			opcion = sc.nextInt();

			int id;

			switch (opcion) {

			case 1:

				cuidador.mostrarTamagotchis();

				break;

			case 2:

				System.out.print("ID del Tamagotchi: ");
				id = sc.nextInt();

				cuidador.alimentar(id);

				break;

			case 3:

				System.out.print("ID del Tamagotchi: ");
				id = sc.nextInt();

				cuidador.jugar(id);

				break;

			case 4:

				System.out.print("ID del Tamagotchi: ");
				id = sc.nextInt();

				cuidador.limpiar(id);

				break;

			case 5:

				System.out.print("ID del Tamagotchi: ");
				id = sc.nextInt();

				System.out.print("Introduce tu respuesta: ");
				int respuesta = sc.nextInt();

				cuidador.responder(id, respuesta);

				break;

			case 6:

				System.out.print("ID del Tamagotchi: ");
				id = sc.nextInt();

				cuidador.destruir(id);

				break;

			case 0:

				System.out.println("El cuidador se marcha.");

				break;

			default:

				System.out.println("Opción incorrecta.");
			}

		} while (opcion != 0);

		sc.close();
		System.exit(0);
	}
}
