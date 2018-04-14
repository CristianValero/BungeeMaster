package BungeeMaster.Librerias;
public class Grupo<T extends Comparable<T>> implements Comparable<Grupo<T>>
{
	private T dato;
	private int cantidad;
	
	public Grupo(T dato)
	{
		this.dato = dato;
		cantidad = 1;
	}
	
	public Grupo(T dato, int cantidad)
	{
		this.dato = dato;
		this.cantidad = cantidad;
	}
	
	public T getDato()
	{
		return dato;
	}
	
	public void setDato(T dato)
	{
		this.dato = dato;
	}
	
	public int getCantidad()
	{
		return cantidad;
	}

	public void setCantidad(int cantidad)
	{
		this.cantidad = cantidad;
	}
	
	public void inc()
	{
		this.cantidad++;
	}
	
	public void dec()
	{
		this.cantidad--;
	}
	
	public void print()
	{
		System.out.println(getCantidad()+" -> ("+getDato().toString()+")");
	}
	
	@Override
	public boolean equals(Object obj)
	{	
		return dato.equals(obj);
	}
	
	@Override
	public String toString()
	{
		return dato.toString();
	}
	
	@Override
	public int compareTo(Grupo<T> o)
	{
		return getDato().compareTo(o.getDato());
	}
}