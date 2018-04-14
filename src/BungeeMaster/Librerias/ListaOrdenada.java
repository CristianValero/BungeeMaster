package BungeeMaster.Librerias;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaOrdenada<T extends Comparable<T>> implements Iterable<T>, EstructuraDatos<T>
{
	private Lista2<T> lista;
	
	public ListaOrdenada()
	{
		lista = new Lista2<T>();
	}
	
	@SuppressWarnings("hiding")
	private class Lista2<T extends Comparable<T>> implements Iterator<T>
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
			clear();
		}
		
		private void clear()
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
				fin = inicio = aux;
			else
			{
				aux.anterior = lugar;
				if(esFinal())
					fin = lugar.siguiente = aux;
				else
				{
					aux.siguiente = lugar.siguiente;
					lugar.siguiente.anterior = aux;
					lugar.siguiente = aux;
				}
			}
			lugar = aux;
			size++;
		}
		
		private void addBack(T d)
		{
			Nodo aux = new Nodo(null, d, null);
			if(size==0)
				fin = inicio = aux;
			else
			{
				aux.siguiente = lugar;
				if(esInicio())
					inicio = lugar.anterior = aux;
				else
				{
					aux.anterior = lugar.anterior;
					lugar.anterior.siguiente = aux;
					lugar.anterior = aux;
				}
			}
			lugar = aux;
			size++;
		}
		
		private void addInOrder(T d, boolean pendiente, boolean norepeticion)
		{
			if(size==0)
				add(d);
			else
			{
				if(lugar==null)
					inicio();
				
				if(lugar.dato.compareTo(d)==0)
				{
					if(!norepeticion)
						add(d);
				}
				else if(pendiente && lugar.dato.compareTo(d)<0)
				{
					if(esFinal() || lugar.siguiente.dato.compareTo(d)>0)
						add(d);
					else
					{
						siguiente();
						addInOrder(d, pendiente, norepeticion);
					}
				}
				else if(!pendiente && lugar.dato.compareTo(d)>0)
				{
					if(esFinal() || lugar.siguiente.dato.compareTo(d)<0)
						add(d);
					else
					{
						siguiente();
						addInOrder(d, pendiente, norepeticion);
					}
				}
				else
				{
					if(esInicio())
						addBack(d);
					else
					{
						anterior();
						addInOrder(d, pendiente, norepeticion);
					}
				}
			}
		}
		
		private T get()
		{
			return ((size>0)?lugar.dato:null);
		}
		
		private void set(T d)
		{
			if(lugar!=null)
				lugar.dato = d;
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
		
		private void sort(boolean orden)
		{
			T aux;
			Nodo auxinit = inicio, auxinit2 = inicio, max;
			for (int i = 0; i < lista.size; i++)
			{
				max = auxinit;
				auxinit2 = auxinit;
				for (int j = i+1; j < lista.size; j++)
				{
					if(auxinit2!=fin)
						auxinit2 = auxinit2.siguiente;
					
					//System.out.print("comparar: "+max.dato+" <> "+auxinit2.dato+" max: ");
					
					if(orden)
					{
						if(max.dato.compareTo(auxinit2.dato)>0)
							max = auxinit2;
					}
					else
					{
						if(max.dato.compareTo(auxinit2.dato)<0)
							max = auxinit2;
					}
					
					//System.out.println(max.dato);
				}
				
				if(auxinit!=max)
				{
					aux = auxinit.dato;
					auxinit.dato = max.dato;
					max.dato = aux;
					
					//System.out.println("Intercambio "+max.dato+" <- "+auxinit.dato);
				}
				
				if(auxinit!=fin)
					auxinit = auxinit.siguiente;
				
				/*lugar = inicio;
				for (int j = 0; j < size; j++)
				{
					System.out.println("Lista -> "+lugar.dato);
					lugar = lugar.siguiente;
				}
				System.out.println();*/
			}
		}
		
		private void noRepeat()
		{
			Nodo aux = lugar = inicio;
			while (aux.siguiente!=null)
			{
				lugar = aux;
				while (lugar.siguiente!=null)
				{
					lugar = lugar.siguiente;
					
					if(aux.dato.equals(lugar.dato))
						remove();
				}
				if(aux!=fin)
					aux = aux.siguiente;	
			}
			lugar = inicio;
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
	
	@Override
	public void clear()
	{
		lista.clear();
	}
	
	public void add(T d)
	{
		lista.add(d);
	}
	
	public void addBack(T d)
	{
		lista.addBack(d);
	}
	
	public void addInOrder(T d)
	{
		lista.addInOrder(d, true, true);
	}
	public void addInOrder(T d, boolean pendiente)
	{
		lista.addInOrder(d, pendiente, true);
	}
	public void addInOrder(T d, boolean pendiente, boolean norepeticion)
	{
		lista.addInOrder(d, pendiente, norepeticion);
	}
	
	public T get()
	{
		return lista.get();
	}
	
	public void set(T d)
	{
		lista.set(d);
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

	public void sort() {
		lista.sort(true);
	}
	
	public void sort(boolean orden) {
		lista.sort(orden);
	}
	
	public void noRepeat()
	{
		lista.noRepeat();
	}

	@Override
	public void clone(EstructuraDatos<T> d)
	{
		lista.clone(d);
	}
}
