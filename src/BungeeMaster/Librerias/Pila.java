package BungeeMaster.Librerias;
public class Pila<T> implements EstructuraDatos<T>
{
	private Nodo cima;
	private int size;
	
	private class Nodo
	{
		private T dato;
		private Nodo siguiente;
		
		private Nodo(T dato, Nodo siguiente)
		{
			this.dato = dato;
			this.siguiente = siguiente;
		}
	}
	
	public Pila()
	{
		cima = null;
		size = 0;
	}
	
	public void add(T d)
	{
		Nodo aux = new Nodo(d, null);
		if(size==0)
			cima = aux;
		else
		{
			aux.siguiente = cima;
			cima = aux;
		}
		size++;
	}
	
	public void remove()
	{
		if(size>0)
		{
			if(cima.siguiente==null)
				cima = null;
			else
				cima = cima.siguiente;
			size--;
		}
	}
	
	public T get()
	{
		return ((size>0)?cima.dato:null);
	}
	
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		cima = null;
		size = 0;
	}

	@Override
	public void clone(EstructuraDatos<T> d)
	{
		clear();
		while(d.size()>0)
		{
			add(d.get());
			d.remove();
		}
	}
}