package Administrador.model.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import model.enums.Situacao;
import model.enums.UsuarioNivel;

public class Usuario extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1829885748257026644L;

	private Long idBairro;
	private String login;
	private String senha;
	private Enum<Situacao> situacao;
	private String observacao;
	private byte[] imagem;
	private Enum<UsuarioNivel> nivel;

	public Long getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Enum<UsuarioNivel> getNivel() {
		return nivel;
	}

	public Usuario() {
		super();
	}

	// Teste
	public Usuario(String login, Enum<UsuarioNivel> nivel) {
		super();
		this.login = login;
		this.nivel = nivel;
	}

	public Usuario(String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, Date dataCadastro, Long idBairro, String login, String senha,
			String observacao, byte[] imagem, Enum<UsuarioNivel> nivel, Enum<Situacao> situacao) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email, dataCadastro);
		this.idBairro = idBairro;
		this.login = login;
		this.senha = senha;
		this.observacao = observacao;
		this.imagem = imagem;
		this.nivel = nivel;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [idBairro=" + idBairro + ", login=" + login + ", senha=" + senha + ", situacao=" + situacao
				+ ", observacao=" + observacao + ", imagem=" + Arrays.toString(imagem) + ", nivel=" + nivel + "]";
	}

}
