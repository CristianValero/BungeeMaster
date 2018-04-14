package BungeeMaster.Librerias;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lista<T> implements Iterable<T>, EstructuraDatos<T>
{
	private Lista2<T> lista;
	
	public Lista()
	{
		lista = new Lista2<T>();
	}
	
	@SuppressWarnings("hiding")
	private class Lista2<T> implements Iterator<T>
	{
		private int size;
		private Nodo inicio;
		private Nodo fin;
		private Nodo lugar;
		
		private class Nodo
		{
			private T dato;
			private Nodo anterior;
			private Nodo siguiente;
			
			private Nodo(Nodo a, T d, Nodo b)
			{
				dato = d;
				anterior = a;
				siguiente = b;
			}
		}
		
		public Lista2()
		{
			size = 0;
			inicio = null;
			fin = null;
			lugar = null;
		}
		
		private void add(T d)
		{
			Nodo aux = new Nodo(null, d, null);
			if(size==0)
				lugar = fin = inicio = aux;
			else
			{
				if(esFinal())
				{
					lugar.siguiente = fin = aux;
					aux.anterior = lugar;
					lugar = aux;
				}
				else
				{
					aux.siguiente = lugar.siguiente;
					aux.anterior = lugar;
					lugar.siguiente.anterior = aux;
					lugar.siguiente = aux;
					lugar = aux;
				}
			}
			size++;
		}
		
		private void addBack(T d)
		{
			Nodo aux = new Nodo(null, d, null);
			if(size==0)
				lugar = fin = inicio = aux;
			else
			{
				if(esInicio())
				{
					aux.siguiente = lugar;
					inicio = lugar.anterior = aux;
					lugar = aux;
				}
				else
				{
					aux.siguiente = lugar;
					aux.anterior = lugar.anterior;
					lugar.anterior.siguiente = aux;
					lugar.anterior = aux;
					lugar = aux;
				}
			}
			size++;
		}
		
		private T get()
		{
			return ((size>0)?lugar.dato:null);
		}
		
		private void clear()
		{
			size = 0;
			inicio = null;
			fin = null;
			lugar = null;
		}
		
		public void remove()
		{
			if(size>0)
			{
				if(esInicio())
				{
					inicio = lugar.siguiente;
					lugar = inicio;
					
					if(lugar==null)
						fin = null;
					else
						lugar.anterior = null;
					size--;
				}
				else if(lugar!=null)
				{
					lugar.anterior.siguiente = lugar.siguiente;
					
					if(lugar==fin)
						fin = lugar.anterior;
					else
						lugar.siguiente.anterior = lugar.anterior;
					lugar = lugar.anterior;
					size--;
				}
			}
		}
		
		private int size()
		{
			return size;
		}
		
		private boolean esFinal()
		{
			return (lugar==fin);
		}
		
		private boolean esInicio()
		{
			return (lugar==inicio);
		}
		
		private void inicio()
		{
			lugar = inicio;
		}
		
		private void fin()
		{
			lugar = fin;
		}
		
		private boolean siguiente()
		{
			if(size>0)
			{
				if(esFinal())
					return false;
				else
				{
					lugar = lugar.siguiente;
					return true;
				}
			}
			else
				return false;
		}
		
		private boolean anterior()
		{
			if(size>0)
			{
				if(esInicio())
					return false;
				else
				{
					lugar = lugar.anterior;
					return true;
				}
			}
			else
				return false;
		}
		
		private boolean find(T d)
		{
			boolean enc = false;
			if(size>0)
			{
				inicio();
				while(!(enc = d.equals(get())) && siguiente()){}
			}
			return enc;
		}
		
		private void clone(EstructuraDatos<T> d)
		{
			clear();
			while(d.size()>0)
			{
				add(d.get());
				d.remove();
			}
		}
	
		@Override
		public boolean hasNext()
		{
			return lugar!=null;
		}
	
		@Override
		public T next()
		{
			T d;
			if(hasNext())
			{
				d = lugar.dato;
				lugar = lugar.siguiente;
				return d;
			}
			else
			{
				lugar = inicio;
				throw new NoSuchElementException();
			}
		}
	}
	
	public void add(T d)
	{
		lista.add(d);
	}
	
	public void addBack(T d)
	{
		lista.addBack(d);
	}
	
	public T get()
	{
		return lista.get();
	}
	
	public void clear()
	{
		lista.clear();
	}
	
	public void remove()
	{
		lista.remove();
	}
	
	public int size()
	{
		return lista.size();
	}
	
	public boolean esFinal()
	{
		return lista.esFinal();
	}
	
	public boolean esInicio()
	{
		return lista.esInicio();
	}
	
	public void inicio()
	{
		lista.inicio();
	}
	
	public void fin()
	{
		lista.fin();
	}
	
	public boolean siguiente()
	{
		return lista.siguiente();
	}
	
	public boolean anterior()
	{
		return lista.anterior();
	}
	
	public boolean find(T d)
	{
		return lista.find(d);
	}

	@Override
	public Iterator<T> iterator()
	{
		lista.inicio();
		return lista;
	}

	@Override
	public void clone(EstructuraDatos<T> d)
	{
		lista.clone(d);
	}
}
