package Administrador.model.entities;

import java.io.Serializable;

import model.enums.Situacao;

public class ClienteContato implements Serializable {

	private static final long serialVersionUID = -4516072563091016608L;
	private Integer id;
	private String nome;
	private String dddTelefone;
	private String telefone;
	private String dddCelular;
	private String celular;
	private String observacao;
	private Enum<Situacao> situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public ClienteContato(Integer id, String nome, String dddTelefone, String telefone, String dddCelular,
			String celular, String observacao, Enum<Situacao> situacao) {

		this.id = id;
		this.nome = nome;
		this.dddTelefone = dddTelefone;
		this.telefone = telefone;
		this.dddCelular = dddCelular;
		this.celular = celular;
		this.observacao = observacao;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		ClienteContato other = (ClienteContato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteContato [id=" + id + ", nome=" + nome + ", dddTelefone=" + dddTelefone + ", telefone=" + telefone
				+ ", dddCelular=" + dddCelular + ", celular=" + celular + ", observacao=" + observacao + ", situacao="
				+ situacao + "]";
	}

}
