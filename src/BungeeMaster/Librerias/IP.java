package BungeeMaster.Librerias;

public class IP implements Comparable<IP>
{
	private final static int MIN_PARTES = 2;
	
	private Integer ip[];
	private int size;
	
	public IP()
	{
		ip = new Integer[4];
		setIp("0.0.0.0");
	}
	
	public IP(String direccion) throws NumberFormatException
	{
		ip = new Integer[4];
		setIp(direccion);
	}
	
	public IP(IP ip)
	{
		copy(ip);
	}
	
	public IP copy()
	{
		return new IP(toString());
	}
	
	public IP copy(IP ip)
	{
		return new IP(ip.toString());
	}
	
	public void setIp(String direccion) throws NumberFormatException
	{
		int i;
		Integer x;
		String arry[] = direccion.split("\\.");
		
		if(arry.length>=MIN_PARTES && arry.length<=4)
		{
			for (i = 0; i < arry.length; i++)
			{
				x = new Integer(Integer.parseInt(arry[i]));
				
				if(x.compareTo(255)>0 || x.compareTo(0)<0)
					throw new NumberFormatException();
				
				ip[i] = x;
			}
			for (int j = i; j < ip.length; j++)
				ip[j] = null;
			size = arry.length;
		}
		else
			throw new NumberFormatException();
	}
	
	public void setIp(String direccion, int del) throws IndexOutOfBoundsException, NumberFormatException
	{
		if(del<0 || del>(4-MIN_PARTES))
			throw new IndexOutOfBoundsException();
		setIp(direccion);
		for (int i = ip.length-1; i <= (ip.length-(1+del)); i--)
			ip[i] = null;
		size-=del;
	}
	
	public IP del(int del) throws IndexOutOfBoundsException, NumberFormatException
	{
		if(del<0 || del>(4-MIN_PARTES))
			throw new IndexOutOfBoundsException();
		
		for (int i = 4-1; i >= (4-del); i--)
			ip[i] = null;
		size = (size<4-del)?size:4-del;
		
		return this;
	}
	
	public void print()
	{
		for (int i = 0; i < size; i++)
			System.out.print("["+ip[i]+"]");
		System.out.println();
	}
	
	public Integer[] getIp()
	{
		return ip;
	}
	
	public Integer getIp(int indice) throws IndexOutOfBoundsException
	{
		if(indice>4 || indice<size-1)
			throw new IndexOutOfBoundsException();
		return ip[indice];
	}

	public int size()
	{
		return size;
	}
	
	@Override
	public String toString()
	{
		String cad="";
		for (int i = 0; i < size; i++)
			cad+=ip[i]+".";
		return cad.substring(0, cad.length()-1);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean ret = false;
		IP aux;
		if(obj instanceof IP)
		{
			aux = (IP) obj;
			if(this==obj)
				ret = true;
			else
				ret = toString().equals(aux.toString());
		}
		else
			ret = false;
		return ret;
	}

	@Override
	public int compareTo(IP o)
	{
		int ret = 0;
		if(!equals(o))
		{
			if(this.size>o.size)
				ret = 1;
			else if(this.size<o.size)
				ret = -1;
			else
			{
				int tm = -1;
				
				do
					tm++;
				while (tm<size && this.ip[tm].compareTo(o.ip[tm])==0);

				if(this.ip[tm].compareTo(o.ip[tm])>0)
					ret = 1;
				else if(this.ip[tm].compareTo(o.ip[tm])<0)
					ret = -1;
				else
					ret = 0;
			}
		}
		return ret;
	}
}