package Administrador.entities;

import Administrador.enums.Situacao;

public class Sabor {

	private Integer id;
	private String descricao;
	private String observacao;
	private Enum<Situacao> situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Sabor() {

	}

	public Sabor(Integer id, String descricao, String observacao, Enum<Situacao> situacao) {

		this.id = id;
		this.descricao = descricao;
		this.observacao = observacao;
		this.situacao = situacao;
	}

}
