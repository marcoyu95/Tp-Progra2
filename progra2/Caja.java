package prog2Recu;

public class Caja {
	private int _ancho;
	private int _largo;
	private int _elemento;
	
	public Caja( int ancho, int largo, int elemento) {
		_ancho = ancho;
		_largo = largo;
		_elemento = elemento;
	}
	
	public int area(){
		return _ancho*_largo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Caja [ancho=");
		builder.append(_ancho);
		builder.append(", largo=");
		builder.append(_largo);
		builder.append(", elemento=");
		builder.append(_elemento);
		builder.append("]");
		return builder.toString();
	}
	
	// no importa el elemento
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _ancho;
		result = prime * result + _largo;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		if (_ancho != other._ancho)
			return false;
		if (_elemento != other._elemento)
			return false;
		if (_largo != other._largo)
			return false;
		return true;
	}

	public int get_ancho() {
		return _ancho;
	}

	public void set_ancho(int _ancho) {
		this._ancho = _ancho;
	}

	public int get_largo() {
		return _largo;
	}

	public void set_largo(int _largo) {
		this._largo = _largo;
	}

	public int get_elemento() {
		return _elemento;
	}

	public void set_elemento(int _elemento) {
		this._elemento = _elemento;
	}
	
	
	
	
}
