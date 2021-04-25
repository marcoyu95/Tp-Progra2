package prog2Recu;

public class Ejemplo {

	public static void main(String[] args) {
		// habitación de 20x10
		Habitacion h1 = new Habitacion(20,10);
		@SuppressWarnings("unused")
		int cant = h1.cantCajas();
		
		for (int i = 1; i< 10; i++) {
			if (h1.puedoAgregarCaja(i, i))
				h1.agregarCaja(i, i, i);
		}
		
		System.out.println(h1);

	}

}
