package BungeeMaster.Listeners.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BBDD
{
    private String host;
    private int puerto;
    private String nombre;
    private String usuario;
    private String pass;
    private String prefix;
    private boolean conectado;

    private Connection conex;

    public BBDD(String host, int puerto, String nombre, String usuario, String pass, String prefix) throws ClassNotFoundException
    {
    	Class.forName("com.mysql.jdbc.Driver");
    	
    	this.host = host;
    	this.puerto = puerto;
    	this.nombre = nombre;
    	this.usuario = usuario;
    	this.pass = pass;
    	this.prefix = prefix;
    	this.conectado = false;
    }
    
    public boolean conectar() throws SQLException
    {
    	if(!conectado)
    	{
    		conex = DriverManager.getConnection ("jdbc:mysql://"+host+":"+puerto+"/"+nombre+"?autoReconect=true", usuario, pass);
    		conectado = true;
    	}
    	return conectado;
    }
    
    public void desconectar()
    {
    	if(conectado)
    	{
    		try
    		{
				conex.close();
			}
			catch (SQLException e) {}
			conectado = false;
    	}
    }
    
    public void set(String host, int puerto, String nombre, String usuario, String pass, String prefix)
    {
    	this.host = host;
    	this.puerto = puerto;
    	this.nombre = nombre;
    	this.usuario = usuario;
    	this.pass = pass;
    	this.prefix = prefix;
    	desconectar();
    }
    
    public void crearTablas()
    {
    	
    }

	public String getHost() {
		return host;
	}

	public int getPuerto() {
		return puerto;
	}

	public String getNombre() {
		return nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getPass() {
		return pass;
	}

	public String getPrefix() {
		return prefix;
	}

	public boolean isConectado() {
		return conectado;
	}

	public Connection getConex() {
		return conex;
	}
}
