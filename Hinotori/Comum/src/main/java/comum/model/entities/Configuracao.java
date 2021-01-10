package comum.model.entities;

import comum.model.enums.DataBase;
import comum.model.enums.Tema;
import comum.model.enums.TipoLancamento;

public class Configuracao {

	DataBase server_database;
	String server_host;
	String server_porta;
	String server_base;
	String server_usuario;
	String server_senha;

	Boolean unicode_usar;
	String unicode_encode;

	String log_caminho;
	Boolean log_mostrar;

	Boolean hibernate_mostrar_sql;
	Boolean hibernate_create_database;

	TipoLancamento sistema_tipo;
	Tema sistema_tema;

	public DataBase getServer_database() {
		return server_database;
	}

	public void setServer_database(DataBase server_database) {
		this.server_database = server_database;
	}

	public String getServer_host() {
		return server_host;
	}

	public void setServer_host(String server_host) {
		this.server_host = server_host;
	}

	public String getServer_porta() {
		return server_porta;
	}

	public void setServer_porta(String server_porta) {
		this.server_porta = server_porta;
	}

	public String getServer_base() {
		return server_base;
	}

	public void setServer_base(String server_base) {
		this.server_base = server_base;
	}

	public String getServer_usuario() {
		return server_usuario;
	}

	public void setServer_usuario(String server_usuario) {
		this.server_usuario = server_usuario;
	}

	public String getServer_senha() {
		return server_senha;
	}

	public void setServer_senha(String server_senha) {
		this.server_senha = server_senha;
	}

	public Boolean getUnicode_usar() {
		return unicode_usar;
	}

	public void setUnicode_usar(Boolean unicode_usar) {
		this.unicode_usar = unicode_usar;
	}

	public String getUnicode_encode() {
		return unicode_encode;
	}

	public void setUnicode_encode(String unicode_encode) {
		this.unicode_encode = unicode_encode;
	}

	public String getLog_caminho() {
		return log_caminho;
	}

	public void setLog_caminho(String log_caminho) {
		this.log_caminho = log_caminho;
	}

	public Boolean getLog_mostrar() {
		return log_mostrar;
	}

	public void setLog_mostrar(Boolean log_mostrar) {
		this.log_mostrar = log_mostrar;
	}

	public Boolean getHibernate_mostrar_sql() {
		return hibernate_mostrar_sql;
	}

	public void setHibernate_mostrar_sql(Boolean hibernate_mostrar_sql) {
		this.hibernate_mostrar_sql = hibernate_mostrar_sql;
	}

	public TipoLancamento getSistema_tipo() {
		return sistema_tipo;
	}

	public void setSistema_tipo(TipoLancamento sistema_tipo) {
		this.sistema_tipo = sistema_tipo;
	}

	public Tema getSistema_tema() {
		return sistema_tema;
	}

	public void setSistema_tema(Tema sistema_tema) {
		this.sistema_tema = sistema_tema;
	}

	public Boolean getHibernate_create_database() {
		return hibernate_create_database;
	}

	public void setHibernate_create_database(Boolean hibernate_create_database) {
		this.hibernate_create_database = hibernate_create_database;
	}

	public Configuracao() {

	}

	public Configuracao(DataBase server_database, String server_host, String server_porta, String server_base,
			String server_usuario, String server_senha, Boolean unicode_usar, String unicode_encode, String log_caminho,
			Boolean log_mostrar, Boolean hibernate_mostrar_sql, Boolean hibernate_create_database,
			TipoLancamento sistema_tipo, Tema sistema_tema) {
		this.server_database = server_database;
		this.server_host = server_host;
		this.server_porta = server_porta;
		this.server_base = server_base;
		this.server_usuario = server_usuario;
		this.server_senha = server_senha;
		this.unicode_usar = unicode_usar;
		this.unicode_encode = unicode_encode;
		this.log_caminho = log_caminho;
		this.log_mostrar = log_mostrar;
		this.hibernate_mostrar_sql = hibernate_mostrar_sql;
		this.hibernate_create_database = hibernate_create_database;
		this.sistema_tipo = sistema_tipo;
		this.sistema_tema = sistema_tema;
	}

	@Override
	public String toString() {
		return "Configuracao [server_database=" + server_database + ", server_host=" + server_host + ", server_porta="
				+ server_porta + ", server_base=" + server_base + ", server_usuario=" + server_usuario
				+ ", server_senha=" + server_senha + ", unicode_usar=" + unicode_usar + ", unicode_encode="
				+ unicode_encode + ", log_caminho=" + log_caminho + ", log_mostrar=" + log_mostrar
				+ ", hibernate_mostrar_sql=" + hibernate_mostrar_sql + ", hibernate_create_database="
				+ hibernate_create_database + ", sistema_tipo=" + sistema_tipo + ", sistema_tema=" + sistema_tema + "]";
	}

}
