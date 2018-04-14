package BungeeMaster.Librerias;
public class Cola<T> implements EstructuraDatos<T>
{
	private Nodo primero;
	private Nodo ultimo;
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
	
	public Cola()
	{
		primero = null;
		ultimo = null;
		size = 0;
	}
	
	public void add(T d)
	{
		Nodo aux = new Nodo(d, null);
		if(size==0)
			primero = ultimo = aux;
		else
		{
			ultimo.siguiente = aux;
			ultimo = aux;
		}
		size++;
	}
	
	public void remove()
	{
		if(size>0)
		{
			primero = primero.siguiente;
			if(primero==null)
				ultimo=null;
			size--;
		}
	}
	
	public T get()
	{
		return ((size>0)?primero.dato:null);
	}
	
	public int size()
	{
		return size;
	}

	@Override
	public void clear()
	{
		primero = null;
		ultimo = null;
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
