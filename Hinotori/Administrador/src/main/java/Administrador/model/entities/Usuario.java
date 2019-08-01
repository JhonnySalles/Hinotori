package Administrador.model.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import Administrador.enums.Situacao;
import Administrador.enums.UsuarioNivel;

public class Usuario extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 349012166580737616L;

	private Long id;
	private Long idBairro;
	private Long idEmpresa;
	private String login;
	private String senha;
	private Enum<Situacao> situacao;
	private String observacao;
	private byte[] imagem;
	private Enum<UsuarioNivel> nivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public void setNivel(Enum<UsuarioNivel> nivel) {
		this.nivel = nivel;
	}

	public Usuario() {
		super();
	}

	public Usuario(String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, Date dataCadastro, Long id, Long idBairro, Long idEmpresa, String login,
			String senha, Enum<Situacao> situacao, String observacao, byte[] imagem, Enum<UsuarioNivel> nivel) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email, dataCadastro);
		this.id = id;
		this.idBairro = idBairro;
		this.idEmpresa = idEmpresa;
		this.login = login;
		this.senha = senha;
		this.situacao = situacao;
		this.observacao = observacao;
		this.imagem = imagem;
		this.nivel = nivel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", idBairro=" + idBairro + ", idEmpresa=" + idEmpresa + ", login=" + login
				+ ", senha=" + senha + ", situacao=" + situacao + ", observacao=" + observacao + ", imagem="
				+ Arrays.toString(imagem) + ", nivel=" + nivel + "]";
	}

}
