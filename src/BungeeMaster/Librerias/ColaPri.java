package BungeeMaster.Librerias;

public class ColaPri<T> implements EstructuraDatos<T>
{
	private int size;
	private Nodo pri;
	private ListaOrdenada<Nodo> ult;
	
	private class Nodo implements Comparable<Nodo>
	{
		private T dato;
		private Nodo siguiente;
		private int prioridad;
		
		private Nodo(T dato, Nodo siguiente, int prioridad)
		{
			this.dato = dato;
			this.siguiente = siguiente;
			this.prioridad = prioridad;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj)
		{
			if(obj instanceof ColaPri.Nodo)
				return ((Nodo)obj).prioridad==prioridad;
			else if(obj instanceof Integer)
				return ((Integer)obj)==prioridad;
			else
				return false;
		}
		
		@Override
		public String toString()
		{
			return "dato="+dato+", prioridad="+prioridad;
		}

		@Override
		public int compareTo(ColaPri<T>.Nodo o)
		{
			if(this.prioridad > o.prioridad)
				return 1;
			else if(this.prioridad < o.prioridad)
				return -1;
			else
				return 0;
		}
	}
	
	public ColaPri()
	{
		size = 0;
		pri = null;
		ult = new ListaOrdenada<Nodo>();
	}

	@Override
	public void add(T d)
	{
		add(d, 0);
	}
	
	public void add(T d, int prioridad)
	{
		Nodo aux = new Nodo(d, null, prioridad);
		
		if(size==0)
		{
			ult.add(aux);
			pri = aux;
		}
		else
		{
			if(ult.find(aux))
			{
				//System.out.println("D: "+aux+" | "+ult.get().siguiente);
				aux.siguiente = ult.get().siguiente;
				ult.get().siguiente = aux;
				ult.set(aux);
			}
			else
			{
				ult.addInOrder(aux);
				if(ult.anterior())
				{
					//System.out.println("D: "+aux);
					aux.siguiente = ult.get().siguiente;
					ult.get().siguiente = aux;
				}
				else
				{
					//System.out.println("D: "+aux+" | 22 | "+pri.siguiente);
					aux.siguiente = pri;
					pri = aux;
				}
			}
		}
		
		size++;
	}

	@Override
	public void remove()
	{
		if(size>0)
		{
			ult.inicio();
			if(pri==ult.get())
				ult.remove();
			pri = pri.siguiente;
			size--;
		}
	}

	@Override
	public T get() {
		return ((size>0)?pri.dato:null);
	}
	
	public int getPri()
	{
		return ((size>0)?pri.prioridad:Integer.MIN_VALUE);
	}

	@Override
	public int size() {
		return size;
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

	@Override
	public void clear()
	{
		size = 0;
		pri = null;
		ult.clear();
	}
}
