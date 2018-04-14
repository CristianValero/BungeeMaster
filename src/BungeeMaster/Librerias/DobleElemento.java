package BungeeMaster.Librerias;

public class DobleElemento<T, E>
{
	private T dato1;
	private E dato2;
	
	public DobleElemento(T dato1, E dato2)
	{
		this.dato1 = dato1;
		this.dato2 = dato2;
	}
	
	public T getDato1() {
		return dato1;
	}
	public void setDato1(T dato1) {
		this.dato1 = dato1;
	}
	
	public E getDato2() {
		return dato2;
	}
	public void setDato2(E dato2) {
		this.dato2 = dato2;
	}
}
