package servidor.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;

public class Produto implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -2972348557775718310L;

	private Long id;
	private Long idNcm;
	private Long idGrupo;
	private String descricao;
	private String observacao;
	private String codigoBarras;
	private String marca;
	private String unidade;
	private Double peso;
	private Double volume;
	private Double qtdeVolume;
	private Date dataCadastro;

	private Enum<TipoProduto> tipo;
	private Enum<Situacao> situacao;

	private List<Imagem> imagens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNcm() {
		return idNcm;
	}

	public void setIdNcm(Long idNcm) {
		this.idNcm = idNcm;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Enum<TipoProduto> getTipoProduto() {
		return tipo;
	}

	public void setTipoProduto(Enum<TipoProduto> tipo) {
		this.tipo = tipo;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getQtdeVolume() {
		return qtdeVolume;
	}

	public void setQtdeVolume(Double qtdeVolume) {
		this.qtdeVolume = qtdeVolume;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public Produto() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.observacao = "";
		this.codigoBarras = "";
		this.unidade = "";
		this.marca = "";
		this.peso = 0.0;
		this.tipo = TipoProduto.PRODUTOFINAL;
		this.situacao = Situacao.ATIVO;
	}

	public Produto(Long id, String descricao, String observacao, String codigoBarras, String unidade, String marca,
			Double peso, Double volume, Date dataCadastro, Enum<TipoProduto> tipo, Enum<Situacao> situacao) {
		this.id = id;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.qtdeVolume = 0.0;
		this.dataCadastro = dataCadastro;
		this.tipo = tipo;
		this.situacao = situacao;
	}

	public Produto(Long id, Long idNcm, Long idGrupo, String descricao, String observacao, String codigoBarras,
			String unidade, String marca, Double peso, Double volume, Date dataCadastro, Enum<TipoProduto> tipo,
			Enum<Situacao> situacao) {
		this.id = id;
		this.idNcm = idNcm;
		this.idGrupo = idGrupo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.qtdeVolume = 0.0;
		this.dataCadastro = dataCadastro;
		this.tipo = tipo;
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
				+ codigoBarras + ", unidade=" + unidade + ", marca=" + marca + ", dataCadastro=" + dataCadastro
				+ ", tipo=" + tipo + ", peso=" + peso + ", situacao=" + situacao + "]";
	}

}
