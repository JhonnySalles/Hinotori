package entitis;

public class Conexao {

	String database;
	String host;
	String porta;
	String base;
	String usuario;
	String senha;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public Conexao() {

	}

	public Conexao(String database, String host, String porta, String base, String usuario, String senha) {
		this.database = database;
		this.host = host;
		this.porta = porta;
		this.base = base;
		this.usuario = usuario;
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Conexao [database=" + database  + ", host=" + host + ", porta=" + porta + ", base=" + base + ", usuario="
				+ usuario + "]";
	}
}
