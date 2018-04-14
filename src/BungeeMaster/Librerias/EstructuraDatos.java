package BungeeMaster.Librerias;

public abstract interface EstructuraDatos<T>
{
	public void clear();
	public void add(T d);
	public void remove();
	public T get();
	public int size();
	public void clone(EstructuraDatos<T> d);
}
