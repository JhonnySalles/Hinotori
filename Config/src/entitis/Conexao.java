package entitis;

import java.net.URL;

public class Conexao {
	
	String ip;
	String porta;
	String usuario;
	String senha;
	URL url;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
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
	
	public Conexao(String ip, String porta, String usuario, String senha) {
		this.ip = ip;
		this.porta = porta;
		this.usuario = usuario;
		this.senha = senha;
	}
}
