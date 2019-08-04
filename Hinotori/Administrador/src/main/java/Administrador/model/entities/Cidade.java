package Administrador.model.entities;

import java.io.Serializable;

import model.enums.Situacao;

public class Cidade implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 8936948944326503399L;

	private Long id;
	private Long idEstado;
	private String nome;
	private String ddd;
	private Enum<Situacao> situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Cidade() {

	}

	public Cidade(Long id, Long idEstado, String nome, String ddd, Enum<Situacao> situacao) {
		this.id = id;
		this.idEstado = idEstado;
		this.nome = nome;
		this.ddd = ddd;
		this.situacao = situacao;
	}

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", idEstado=" + idEstado + ", nome=" + nome + ", ddd=" + ddd + ", situacao="
				+ situacao + "]";
	}

}
