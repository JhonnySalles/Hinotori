package Administrador.entities;

import java.util.Date;

import Administrador.enums.Situacao;
import Administrador.enums.TipoProduto;

public class Produto {

	private Integer id;
	private String descricao;
	private String observacao;
	private String codigoBarras;
	private String unidade;
	private Date dataCadastro;
	private Enum<TipoProduto> tipo;
	private Double pesoBruto;
	private Double pesoLiquido;
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

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Enum<TipoProduto> getTipo() {
		return tipo;
	}

	public void setTipo(Enum<TipoProduto> tipo) {
		this.tipo = tipo;
	}

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Double getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Produto() {

	}

	public Produto(Integer id, String descricao, String observacao, String codigoBarras, String unidade,
			Date dataCadastro, Enum<TipoProduto> tipo, Double pesoBruto, Double pesoLiquido, Enum<Situacao> situacao) {

		this.id = id;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.dataCadastro = dataCadastro;
		this.tipo = tipo;
		this.pesoBruto = pesoBruto;
		this.pesoLiquido = pesoLiquido;
		this.situacao = situacao;
	}

}
