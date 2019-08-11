package Administrador.model.entities;

import java.io.Serializable;

import model.enums.Situacao;

public class ClienteEndereco implements Serializable {

	private static final long serialVersionUID = -6097222127900777434L;
	private Integer id;
	private Integer idBairro;
	private String endereco;
	private String numero;
	private String cep;
	private String complemento;
	private String observacao;
	private Enum<Situacao> situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(Integer idBairro) {
		this.idBairro = idBairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public ClienteEndereco(Integer id, Integer idBairro, String endereco, String numero, String cep, String complemento,
			String observacao, Enum<Situacao> situacao) {
		this.id = id;
		this.idBairro = idBairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idBairro == null) ? 0 : idBairro.hashCode());
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
		ClienteEndereco other = (ClienteEndereco) obj;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idBairro == null) {
			if (other.idBairro != null)
				return false;
		} else if (!idBairro.equals(other.idBairro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClienteEndereco [id=" + id + ", idBairro=" + idBairro + ", endereco=" + endereco + ", numero=" + numero
				+ ", cep=" + cep + ", complemento=" + complemento + ", observacao=" + observacao + ", situacao="
				+ situacao + "]";
	}

}
