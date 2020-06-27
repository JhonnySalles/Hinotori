package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1829885748257026644L;

	@Column(name = "Login", columnDefinition = "char(20)")
	private String login;

	@Column(name = "Senha", columnDefinition = "varchar(250)")
	private String senha;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_imagens", joinColumns = @JoinColumn(name = "idUsuario"), foreignKey = @ForeignKey(name = "FK_USUARIOS_IMAGENS_IDUSUARIO"), inverseJoinColumns = @JoinColumn(name = "idImagem"), inverseForeignKey = @ForeignKey(name = "FK_USUARIOS_IMAGENS_IDIMAGEM"), uniqueConstraints = {
			@UniqueConstraint(name = "usuario_imagem", columnNames = { "idUsuario", "idImagem" }) })
	private Set<Imagem> imagens;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_contatos", joinColumns = @JoinColumn(name = "idUsuario"), foreignKey = @ForeignKey(name = "FK_USUARIOS_CONTATOS_IDUSUARIO"), inverseJoinColumns = @JoinColumn(name = "idContato"), inverseForeignKey = @ForeignKey(name = "FK_USUARIOS_CONTATOS_IDCONTATO"), uniqueConstraints = {
			@UniqueConstraint(name = "usuario_contato", columnNames = { "idUsuario", "idContato" }) })
	private Set<Contato> contatos;

	@Column(name = "Nivel", columnDefinition = "enum('USUARIO','ADMINISTRADOR','TOTAL')")
	private Enum<UsuarioNivel> nivel;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	private Enum<Situacao> situacao;

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

	@Enumerated(EnumType.STRING)
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

	public Set<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<Imagem> imagens) {
		this.imagens = imagens;
	}

	@Enumerated(EnumType.STRING)
	public Enum<UsuarioNivel> getNivel() {
		return nivel;
	}

	public void setNivel(Enum<UsuarioNivel> nivel) {
		this.nivel = nivel;
	}

	public Set<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario() {
		super();
		this.contatos = new HashSet<>();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String login,
			String observacao, Enum<UsuarioNivel> nivel, Enum<Situacao> situacao) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao);
		this.login = login;
		this.nivel = nivel;
		this.observacao = observacao;
		this.situacao = situacao;
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Usuario(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String login,
			String observacao, Enum<UsuarioNivel> nivel, Enum<Situacao> situacao, Set<Imagem> imagens) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao);
		this.login = login;
		this.situacao = situacao;
		this.observacao = observacao;
		this.imagens = imagens;
		this.nivel = nivel;
		this.contatos = new HashSet<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
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
		return "Usuario [login=" + login + ", senha=" + senha + ", observacao=" + observacao + ", imagens=" + imagens
				+ ", contatos=" + contatos + ", nivel=" + nivel + ", situacao=" + situacao + "]";
	}

}
