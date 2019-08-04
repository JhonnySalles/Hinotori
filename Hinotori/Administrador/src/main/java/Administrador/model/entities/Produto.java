package Administrador.model.entities;

import java.io.Serializable;
import java.util.Date;

import model.enums.Situacao;
import model.enums.TipoProduto;

public class Produto implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -2972348557775718310L;

	private Long id;
	private String descricao;
	private String observacao;
	private String codigoBarras;
	private String unidade;
	private Date dataCadastro;
	private Double pesoBruto;
	private Double pesoLiquido;
	private Enum<TipoProduto> tipo;
	private Enum<Situacao> situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Produto(Long id, String descricao, String observacao, String codigoBarras, String unidade, Date dataCadastro,
			Double pesoBruto, Double pesoLiquido, Enum<TipoProduto> tipo, Enum<Situacao> situacao) {

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

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", descricao=" + descricao + ", observacao=" + observacao + ", codigoBarras="
				+ codigoBarras + ", unidade=" + unidade + ", dataCadastro=" + dataCadastro + ", tipo=" + tipo
				+ ", pesoBruto=" + pesoBruto + ", pesoLiquido=" + pesoLiquido + ", situacao=" + situacao + "]";
	}

}
