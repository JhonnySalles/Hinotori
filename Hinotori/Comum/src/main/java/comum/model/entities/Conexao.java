package comum.model.entities;

public class Conexao {

	String database;
	String host;
	String porta;
	String base;
	String usuario;
	String senha;
	String caminhoLog;
	Boolean mostraErro;
	String characterEncoding;
	Boolean useUnicode;

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

	public String getCaminhoLog() {
		return caminhoLog;
	}

	public void setCaminhoLog(String caminhoLog) {
		this.caminhoLog = caminhoLog;
	}

	public Boolean getMostraErro() {
		return mostraErro;
	}

	public void setMostraErro(Boolean mostraErro) {
		this.mostraErro = mostraErro;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public Boolean getUseUnicode() {
		return useUnicode;
	}

	public void setUseUnicode(Boolean useUnicode) {
		this.useUnicode = useUnicode;
	}

	public Conexao() {

	}

	public Conexao(String database, String host, String porta, String base, String usuario, String senha,
			String caminhoLog, Boolean mostraErro) {
		this.database = database;
		this.host = host;
		this.porta = porta;
		this.base = base;
		this.usuario = usuario;
		this.senha = senha;
		this.caminhoLog = caminhoLog;
		this.mostraErro = mostraErro;
	}

	@Override
	public String toString() {
		return "Conexao [database=" + database + ", host=" + host + ", porta=" + porta + ", base=" + base + ", usuario="
				+ usuario + ", senha=" + senha + ", caminhoLog=" + caminhoLog + ", mostraErro=" + mostraErro + "]";
	}

}
