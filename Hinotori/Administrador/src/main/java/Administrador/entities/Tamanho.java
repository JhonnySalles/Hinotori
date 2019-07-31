package Administrador.entities;

import Administrador.enums.Situacao;

public class Tamanho {

	private Integer id;
	private String sigla;
	private String descricao;
	private Integer qtdPedacos;
	private Integer qtdSabores;
	private Enum<Situacao> situacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQtdPedacos() {
		return qtdPedacos;
	}

	public void setQtdPedacos(Integer qtdPedacos) {
		this.qtdPedacos = qtdPedacos;
	}

	public Integer getQtdSabores() {
		return qtdSabores;
	}

	public void setQtdSabores(Integer qtdSabores) {
		this.qtdSabores = qtdSabores;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Tamanho() {

	}

	public Tamanho(Integer id, String sigla, String descricao, Integer qtdPedacos, Integer qtdSabores,
			Enum<Situacao> situacao) {
		this.id = id;
		this.sigla = sigla;
		this.descricao = descricao;
		this.qtdPedacos = qtdPedacos;
		this.qtdSabores = qtdSabores;
		this.situacao = situacao;
	}

}
