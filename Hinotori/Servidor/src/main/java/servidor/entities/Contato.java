package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import comum.model.enums.Situacao;
import comum.model.enums.TipoContato;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
public class Contato implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1562578455627883930L;

	private Long idSequencial;

	@Column(name = "Nome")
	private String nome;

	@Column(name = "Telefone")
	private String telefone;

	@Column(name = "Celular")
	private String celular;

	@Column(name = "Email")
	private String email;

	@Column(name = "Observacao")
	private String observacao;

	@Column(name = "Tipo")
	private Enum<TipoContato> tipoContato;

	@Column(name = "Situacao")
	private Enum<Situacao> situacao;

	@Column(name = "Padrao")
	private SimpleBooleanProperty padrao = new SimpleBooleanProperty(false);

	public Long getIdSequencial() {
		return idSequencial;
	}

	public void setIdSequencial(Long idSequencial) {
		this.idSequencial = idSequencial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<TipoContato> getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(Enum<TipoContato> tipo) {
		this.tipoContato = tipo;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public boolean isPadrao() {
		return padrao.get();
	}

	public SimpleBooleanProperty padraoProperty() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao.set(padrao);
	}

	public Contato() {
		this.idSequencial = Long.valueOf(0);
		this.nome = "";
		this.telefone = "";
		this.celular = "";
		this.email = "";
		this.observacao = "";
		this.tipoContato = TipoContato.RESIDENCIAL;
		this.situacao = Situacao.ATIVO;
		this.padrao.set(false);
	}

	public Contato(Long idSequencial, String nome, String telefone, String celular, String email, String observacao,
			Enum<TipoContato> tipoContato, Enum<Situacao> situacao, Boolean padrao) {
		this.idSequencial = idSequencial;
		this.nome = nome;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.observacao = observacao;
		this.tipoContato = tipoContato;
		this.situacao = situacao;
		this.padrao.set(padrao);
	}

	@Override
	public String toString() {
		return "Contato [idSequencial=" + idSequencial + ", nome=" + nome + ", telefone=" + telefone + ", celular="
				+ celular + ", email=" + email + ", observacao=" + observacao + ", tipoContato=" + tipoContato
				+ ", situacao=" + situacao + ", padrao=" + padrao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		Contato other = (Contato) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

}
