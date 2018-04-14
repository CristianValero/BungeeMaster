package BungeeMaster.Librerias;

import java.util.ArrayList;

/**
 * Implementacion de �rbol binario de b�squeda.
 * @version 1.0
 * @author
 * Asignatura Desarrollo de Programas<br/>
 * <b> Profesores DP </b><br>
 * Curso 14/15
 */
public class Arbol<T extends Comparable<T>>
{
	/** Dato almacenado en cada nodo del �rbol. */
	private T datoRaiz;
	
	/** Atributo que indica si el �rbol est� vac�o. */
	boolean esVacio;
	
	/** Hijo izquierdo del nodo actual */
	private Arbol<T> hIzq;
	
	/** Hijo derecho del nodo actual */
	private Arbol<T> hDer;
	
	/** Almacena el tama�o del �rbol */
	private int size;
	
	/**
	 * Constructor por defecto de la clase. Inicializa un �rbol vac�o.
	 */
	public Arbol()
	{
	    clear();
	}

	/**
	 * Crea un nuevo �rbol a partir de los datos pasados por par�metro.
	 *
	 * @param hIzq El hijo izquierdo del �rbol que se est� creando 
	 * @param datoRaiz Ra�z del �rbol que se est� creando
	 * @param hDer El hijo derecho del �rbol que se est� creando
	 */
	public Arbol (Arbol<T> hIzq, T datoRaiz, Arbol<T> hDer)
	{
		this.esVacio=false;
		this.datoRaiz = datoRaiz;
		this.hIzq = hIzq;
		this.hDer=hDer;
	}
	
	public void clear()
	{
		this.esVacio=true;
	    this.datoRaiz = null;
	    this.hIzq = null;
	    this.hDer = null;
	    this.size = 0;
	}
	
	/**
	 * Devuelve el hijo izquierdo del �rbol
	 *
	 * @return El hijo izquierdo
	 */
	public Arbol<T> getHijoIzq(){
		return hIzq;
	}
	
	/**
	 * Devuelve el hijo derecho del �rbol
	 *
	 * @return Hijo derecho del �rbol
	 */
	public Arbol<T> getHijoDer(){
		return hDer;
	}
	
	/**
	 * Devuelve la ra�z del �rbol
	 *
	 * @return La ra�z del �rbol
	 */
	public T getRaiz(){
		return datoRaiz;
	}
	
	/**
	 * Comprueba si el �rbol est� vac�o.
	 * @return verdadero si el �rbol est� vac�o, falso en caso contrario
	 */
	public boolean vacio(){
		return esVacio;
	}
	
	/**
	 * Devuelve el n�mero de nodos del �rbol
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserta un nuevo dato en el �rbol.
	 *
	 * @param dato El dato a insertar
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso contrario
	 */
	public boolean insertar(T dato)
	{
	    boolean resultado=true;
	    if (vacio())
	    {
	        datoRaiz = dato;
			esVacio = false;
			size++;
		}
	    else
	    {
	        if (!(this.datoRaiz.equals(dato)))
	        {
	            Arbol<T> aux;
	            if (dato.compareTo(this.datoRaiz)<0) //dato < datoRaiz
	            {
	                if ((aux=getHijoIzq())==null)
	                    hIzq = aux = new Arbol<T>();
	            }
	            else //dato > datoRaiz
	            {
	                if ((aux=getHijoDer())==null)
	                    hDer = aux = new Arbol<T>();
	            }
	            resultado = aux.insertar(dato);
	            if(resultado)
	            	size++;
	        }
	        else
	            resultado=false;
	    }
	    return resultado;
	}
	
	/**
	 * Inserta un nuevo dato en el �rbol.
	 *
	 * @param dato El dato a insertar
	 * @return verdadero si el dato se ha insertado correctamente, falso en caso contrario
	 */
	public boolean add(T x)
	{
		return insertar(x);
	}
	
	/**
	 * Comprueba si un dato se encuentra almacenado en el �rbol
	 *
	 * @param dato El dato a buscar
	 * @return verdadero si el dato se encuentra en el �rbol, falso en caso contrario
	 */
	public boolean pertenece(T dato)
	{
	    Arbol<T> aux=null;
	    boolean encontrado=false;
	    if (!vacio())
	    {
	        if (this.datoRaiz.equals(dato))
	            encontrado = true;
	        else
	        {
	            if (dato.compareTo(this.datoRaiz)<0) //dato < datoRaiz
	                aux = getHijoIzq();
	            else //dato > datoRaiz
	                aux = getHijoDer();
	            
	            if (aux!=null)
	                encontrado = aux.pertenece(dato);
	        }
	    }
	    return encontrado;
	}
	
	/**
	 * Borrar un dato del �rbol.
	 *
	 * @param dato El dato que se quiere borrar
	 */
	public void borrar(T dato)
	{
	    if (!vacio())
	    {
	        if (dato.compareTo(this.datoRaiz)<0) //dato<datoRaiz
	        {
	        	if(hIzq!=null)
					hIzq = hIzq.borrarOrden(dato);
			}	
	        else if (dato.compareTo(this.datoRaiz)>0) //dato>datoRaiz
	        {
	        	if(hDer!=null)
	        		hDer = hDer.borrarOrden(dato);
			}
            else //En este caso el dato es datoRaiz
            {
                if (hIzq==null && hDer==null)
                {
                    esVacio=true;
                    size--;
                }
                else
                    borrarOrden(dato);
            }
	    }
	}
	

	/**
	 * Borrar un dato. Este m�todo es utilizado por el m�todo borrar anterior.
	 *
	 * @param dato El dato a borrar
	 * @return Devuelve el �rbol resultante despu�s de haber realizado el borrado
	 */
	private Arbol<T> borrarOrden(T dato)
	{
	    T datoaux;
	    Arbol<T> retorno=this;
	    Arbol<T> aborrar, candidato, antecesor;

	    if (!vacio())
	    {
	        if (dato.compareTo(this.datoRaiz)<0) // dato<datoRaiz
	        {
	        	if(hIzq!=null)
	        		hIzq = hIzq.borrarOrden(dato);
	        }
			else if (dato.compareTo(this.datoRaiz)>0) // dato>datoRaiz
			{
				if(hDer!=null)
    	           hDer = hDer.borrarOrden(dato);
            }
			else
			{
				size--;
                aborrar=this;
                if ((hDer==null)&&(hIzq==null))/*si es hoja*/
                    retorno=null;
                else
                {
                    if (hDer==null) /*Solo hijo izquierdo*/
                    {
                        aborrar=hIzq;
                        datoaux=this.datoRaiz;
                        datoRaiz=hIzq.getRaiz();
                        hIzq.datoRaiz = datoaux;
                        hIzq=hIzq.getHijoIzq();
                        hDer=aborrar.getHijoDer();

                        retorno=this;
                    }
                    else if (hIzq==null) /*Solo hijo derecho*/
                    {
                        aborrar=hDer;
                        datoaux=datoRaiz;
                        datoRaiz=hDer.getRaiz();
                        hDer.datoRaiz = datoaux;
                        hDer=hDer.getHijoDer();
                        hIzq=aborrar.getHijoIzq();

                        retorno=this;
                    }
                    else /* Tiene dos hijos */
                    {
                        candidato = this.getHijoIzq();
                        antecesor = this;
                        while (candidato.getHijoDer()!=null)
                        {
                            antecesor = candidato;
                            candidato = candidato.getHijoDer();
                        }

                        /*Intercambio de datos de candidato*/
                        datoaux = datoRaiz;
                        datoRaiz = candidato.getRaiz();
                        candidato.datoRaiz=datoaux;
                        aborrar = candidato;
                        if (antecesor==this)
                            hIzq=candidato.getHijoIzq();
                        else
                            antecesor.hDer=candidato.getHijoIzq();
                    }
                    //Eliminar solo ese nodo, no todo el subarbol
                    aborrar.hIzq=null;
                    aborrar.hDer=null;
                }
            }
	    }
	    return retorno;
	}
	
	
	/**
	 * Recorrido inOrden del �rbol.
	 */
	public void inOrden()
	{
	    Arbol<T> aux=null;
	    if (!vacio())
	    {
	        if ((aux=getHijoIzq())!=null)
	            aux.inOrden();
	      
	        System.out.println(this.datoRaiz);
	        
	        if ((aux=getHijoDer())!=null)
	            aux.inOrden();
	    }
	}
	
	/**
	 * Medir profundidad
	 *
	 * @return numero entero con la profundidad
	 */
	public int profundidad()
	{
		if(vacio())
			return 0;
		else
		{
			int i = (hIzq!=null)?hIzq.profundidad():0;
			int d = (hDer!=null)?hDer.profundidad():0;
			return 1+((i>d)?i:d);
		}
	}
	
	public void itemPerAnchura()
	{
		ArrayList<Integer> lista = new ArrayList<Integer>();
		itemPerAnchura(lista, 0);
		for (Integer integer : lista) {
			System.out.println(integer);
		}
	}
	
	private void itemPerAnchura(ArrayList<Integer> lista, int nivel)
	{
		if(size()>0)
		{
			if(lista.isEmpty() || nivel>=lista.size())
				lista.add(1);
			else
				lista.set(nivel, lista.get(nivel)+1);
			
			if(getHijoIzq()!=null)
				getHijoIzq().itemPerAnchura(lista, nivel+1);
			
			if(getHijoDer()!=null)
				getHijoDer().itemPerAnchura(lista, nivel+1);
		}
	}
	
	public void anchura() //SE RECIBE LA RAIZ DEL ARBOL
	{
		Cola<Arbol<T>> cola=new Cola<Arbol<T>>(); //SE INSTANCIA EL OBJETO COLA
		Cola<T> colaAux=new Cola<T>(); //SE INSTANCIA EL OBJETO COLAAUX
		Arbol<T> aux; //DEFINICI�N AUX DE TIPO NODOARBOL
		
		cola.add(this); //SE INSERTA EL NODOARBOL "A" (RAIZ) COMO PRIMER NODO EN LA COLA
		while (cola.size()>0) //MIENTRAS HAYAN ELEMENTOS EN LA COLA...
		{
			aux=cola.get();
			colaAux.add(aux.getRaiz());
			cola.remove();
			
			if (aux.hIzq != null) //SI EL HIJO IZQUIERDO DEL NODO ACTUAL EXISTE
				cola.add(aux.hIzq); //SE INSERTA ESE HIJO COMO ELEMENTO SIGUIENTE EN LA COLA
			
			if (aux.hDer!= null) //SI EL HIJO DERECHO DEL NODO ACTUAL EXISTE
				cola.add(aux.hDer); //SE INSERTA ESE HIJO COMO ELEMENTO SIGUIENTE EN LA COLA
		}
		
		// Se imprime la cola resultante
		while(colaAux.size()>0)
		{
			System.out.println(colaAux.get());
			colaAux.remove();
		}
	}
	
	public void anchura2()
	{
		for (int i = 1; i<=profundidad(); i++)
	    {
			anchura2(i, 1);
			System.out.println();
		}
	}
	
	private void anchura2(int nivel, int nivelactual)
	{
		if(!vacio())
		{
			if(nivel==nivelactual)
				System.out.print(datoRaiz.toString()+" ");
			
			if(hIzq!=null)
				hIzq.anchura2(nivel, nivelactual+1);
			
			if(hDer!=null)
				hDer.anchura2(nivel, nivelactual+1);
		}
	}
	
	public void balancear()
	{
		int m;
		ArrayList<T> valores = new ArrayList<T>();
		Cola<Integer[]> procesos = new Cola<Integer[]>();
		
	    if (!vacio())
	    {
	    	getArrayList(valores);
	    	clear();
	    	
	        procesos.add(new Integer[] {0, valores.size()-1});
			while(procesos.size()>0)
			{
				Integer[] aux = procesos.get();
				procesos.remove();
				
				if(aux[0]!=aux[1])
				{
					m = (aux[0]+aux[1])/2;
	
					this.add(valores.get(m));
					
					if(aux[0]<(m-1))
						procesos.add(new Integer[]{aux[0],m-1});
					else if(aux[0]==(m-1))
						procesos.add(new Integer[]{aux[0],aux[0]});
					
					if(aux[1]>(m+1))
						procesos.add(new Integer[]{m+1,aux[1]});
					else if(aux[1]==(m+1))
						procesos.add(new Integer[]{aux[1],aux[1]});
				}
				else
					this.add(valores.get(aux[0]));
			}
	    }
	}

	private void getArrayList(ArrayList<T> lista)
	{
		Arbol<T> aux=null;
	    if (!vacio())
	    {
	        if ((aux=getHijoIzq())!=null)
	        	aux.getArrayList(lista);
	        lista.add(datoRaiz);
	        if ((aux=getHijoDer())!=null)
	            aux.getArrayList(lista);
	    }
	}
}
