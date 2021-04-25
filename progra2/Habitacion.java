package prog2Recu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Habitacion implements Iterable<Caja> {
	private int _ancho;
	private int _largo;
	private HashMap<Coordenada, Caja> _cajas;
	boolean[][] _habitacion;
	
	public Habitacion(int ancho, int largo) {
		
		if(ancho<=0 || largo<=0) {
			throw new RuntimeException("Habitacion no valida");
		}
		_ancho = ancho;
		_largo = largo;
		_cajas = new HashMap<Coordenada, Caja>();
		//redundante, para agregar cajas mas eficientemente
		_habitacion = new boolean[largo][ancho];
	}
	
	boolean puedoAgregarCaja(int ancho, int largo) {
		Coordenada c = new Coordenada(0,0);
		return factibilidad(ancho, largo, c);
	}
	
	void agregarCaja(int ancho, int largo, int elemento) {
		
		if(ancho<=0 || largo<=0) {//las cajas no pueden tener longuitud negativa ni cero
			throw new RuntimeException("Caja infactible!!!");
		}
		
		Coordenada c = new Coordenada(0,0);
		if (factibilidad(ancho, largo, c)) {
			//"pinto" la caja
			for (int i=c.y; i<c.y+largo; i++) {
				for (int j=c.x; j<c.x+ancho; j++) {
					_habitacion[i][j] = true;
				}
			}
			
			_cajas.put(c, new Caja( ancho, largo, elemento));
			
		}else {
			throw new RuntimeException("Caja infactible!!!");
		}
	}
	
	public int cantCajas() {
		return _cajas.size();
	}
	

	public int reacomodarCajas() {
		
		Habitacion hab = new Habitacion(_ancho,_largo);

		//copio las cajas
		ArrayList<Caja> listaCop = new ArrayList<Caja>(_cajas.values());
		Caja Cmax;//busco la caja con mayor area y la agrego a la habitacion

		while(listaCop.size()!=0){
			
			Cmax=CajaGrande(listaCop);

			int flag=0;
			int i=0;
			int j=0;

			while(i<hab._largo && flag==0) {
				
				j=0;
			
				while(j<hab._ancho && flag==0) {
					Coordenada c = new Coordenada(j,i);

					if(c.x+Cmax.get_ancho()<=hab._ancho && c.y+Cmax.get_largo()<=hab._largo) {
						if(hab._habitacion[i][j]==false) {
							
							if(hab.verificarLugar(c.x, c.y, Cmax.get_ancho(), Cmax.get_largo())) {
								
								for (int k=c.y; k<c.y+Cmax.get_largo(); k++) {
									for (int l=c.x; l<c.x+Cmax.get_ancho(); l++) {
										
										hab._habitacion[k][l] = true;
										
									}
								}
								hab._cajas.put(c, Cmax);
								flag=1;
							}

						}
					
					}
					else {
						int auxi=Cmax.get_ancho(); //giro la caja
						Cmax.set_ancho(Cmax.get_largo());
						Cmax.set_largo(auxi);
						
						if(c.x+Cmax.get_ancho()<=hab._ancho && c.y+Cmax.get_largo()<=hab._largo) {
							if(hab._habitacion[i][j]==false) {
								
								if(hab.verificarLugar(c.x, c.y, Cmax.get_ancho(), Cmax.get_largo())) {
									
									for (int k=c.y; k<c.y+Cmax.get_largo(); k++) {
										for (int l=c.x; l<c.x+Cmax.get_ancho(); l++) {
											
											hab._habitacion[k][l] = true;
											
										}
									}
									hab._cajas.put(c, Cmax);
									flag=1;
								}

							}
						
						}
								
					}

					j++;
				}
				
				i++;
					
			}

			listaCop.remove(Cmax);	//elimina la caja de la listacop		
		}
		
		if(cantCajas()==hab.cantCajas()) {//cambio la habitacion ya que reacomode.
			_habitacion=hab._habitacion;
			_cajas=hab._cajas;
			
			return cantCajas();
		}
		else {
			return 0;//no reacomode ninguna caja, salgo y dejo todo igual
		}

	}

	
	boolean puedoPasarCajas(Habitacion h) {
	
		int SumaCajas=cantCajas()+h.cantCajas();
		Habitacion haux=new Habitacion(_ancho,_largo);
		haux._cajas = new HashMap<Coordenada, Caja>(_cajas);
		
		//copio la matriz
		for(int i=0;i<_habitacion.length;i++) {
			for(int j=0;j<_habitacion[0].length;j++) {
				haux._habitacion[i][j]=_habitacion[i][j];
			}
		}
		//tengo copiado habitacion(this) en haux
		
		Iterator<Caja> it = h.iterator();
		Caja caja1;
		
		while (it.hasNext()) {
			
			caja1=it.next();
			Coordenada c = new Coordenada(0,0);
			
			if (haux.factibilidad(caja1.get_ancho(), caja1.get_largo(), c)) {
				//"pinto" la caja
				for (int i=c.y; i<c.y+caja1.get_largo(); i++) {
					for (int j=c.x; j<c.x+caja1.get_ancho(); j++) {
						haux._habitacion[i][j] = true;
						
					}
				}
				
				haux._cajas.put(c, new Caja( caja1.get_ancho(), caja1.get_largo(), caja1.get_elemento()));
			}	
		}
		
		if(haux.cantCajas()==SumaCajas) {
			return true;
		}else {
			return false;
		}	
		
	}
	
	
	boolean verificarLugar(int x, int y, int Cancho, int Calto) {//verifica que la caja a dibujar tenga false
		boolean ret=true;

		for(int i=x;i<x+Cancho;i++) {
			
			for(int j=y;j<y+Calto;j++) {
				
				ret=ret&& _habitacion[j][i]==false;			
			}
		}
		
		return ret;
	}
	
	
	private Caja CajaGrande(ArrayList<Caja> lista) {//retorna la caja con mayor area
		Caja Cmax=lista.get(0);
		int i=1;
		
		while(i<lista.size()){
			
			if(lista.get(i).area()>Cmax.area())
				Cmax=lista.get(i);
			i++;					
		}
		
		return Cmax;
	}

	// ver las dimenensiones y dentro del diccionario
	private boolean factibilidad(int ancho, int largo, Coordenada c) {
		if (ancho > _ancho || largo > _largo)
			return false;
		//busco el 1er lugar posible
		c.x = 0;
		c.y = 0;
		boolean ok = false;
		//busca una coordanada factible y la asigna.
		while (!ok && c.y < _largo){
			ok = fact2(c.x,c.y, ancho, largo);
			if (!ok) incrementar(c);
		}
		return ok;
	}
	
	private void incrementar(Coordenada c) {
		if (c.x < _ancho-1)
			c.x=c.x+1;
			else {
				c.x=0;
				c.y=c.y+1;
			}
	}
	
	private boolean fact2(int x ,int y, int ancho,int largo) {
		boolean ret = false;
		if (x+ancho < _ancho && y+largo < _largo) {
			ret = true;
			for (int i=y; i<y+largo; i++) {
				for (int j=x; j<x+ancho; j++) {
					ret = ret && !_habitacion[i][j];
				}
			}
		}
		return ret;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitacion other = (Habitacion) obj;
		if (_ancho != other._ancho)
			return false;
		if (_largo != other._largo)
			return false;
		if (_cajas.size() != other._cajas.size())
			return false;
		for (Coordenada c: _cajas.keySet()) {
			if (other._cajas.keySet().contains(c)) {
			// tengo la misma coordenada en la otra hab,
			// tengo q ver si es la misma caja
				if (!_cajas.get(c).equals(other._cajas.get(c))) {
			// la caja era distinta
					return false;
				}
			}else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		//copio las cajas
		ArrayList<Caja> listaCop = new ArrayList<Caja>(_cajas.values());

		
		StringBuilder builder = new StringBuilder();

		builder.append("Tamaño total: "+_largo*_ancho+"\n");
		builder.append("Tamaño total utilizado: "+tamUsado()+"\n");
		builder.append("Cajas mas grandes:\n");
		builder.append(cajasMasGrandes(listaCop));

		
		for (int i=0; i<_largo; i++) {
			for (int j=0; j<_ancho; j++) {
				builder.append(_habitacion[i][j] ? 1:0);
			}
			builder.append("\n");
		}
		
		return builder.toString();
	}

	private StringBuilder cajasMasGrandes(ArrayList<Caja> listaCop) {
		
		int cont=1;
		StringBuilder builder=new StringBuilder();
		Caja Cmax;
		while(listaCop.size()!=0 && cont<=3) {
			Cmax=CajaGrande(listaCop);
			builder.append(Cmax+"\n");
			listaCop.remove(Cmax);
			cont++;
		}
		return builder;
	}
	
	private int tamUsado() {
		int	cont=0;
		for (Caja c: this) {
			cont+=c.area();
		}
		return cont;
	}

	@Override
	public Iterator<Caja> iterator() {
		
		return _cajas.values().iterator();
	}

}
