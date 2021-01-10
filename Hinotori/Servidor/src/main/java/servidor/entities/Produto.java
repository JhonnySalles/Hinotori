package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;
import servidor.dao.Entidade;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable, Entidade {

	public static final String TABELA = "produtos";
	
	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -2972348557775718310L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private Long idGrupo;

	@Column(name = "Descricao", nullable = false, insertable = true, updatable = true, length = 250, columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "CodigoBarras", length = 35, columnDefinition = "varchar(35)")
	private String codigoBarras;

	@Column(name = "Marca", length = 250, columnDefinition = "varchar(250)")
	private String marca;

	@Column(name = "Unidade", length = 4, columnDefinition = "varchar(4)")
	private String unidade;

	@Column(name = "Peso", precision = 4)
	private Double peso;

	@Column(name = "Volume", precision = 4)
	private Double volume;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	private Timestamp dataCadastro;

	@Column(name = "DataUltimaAlteracao", columnDefinition = "datetime")
	private Timestamp dataUltimaAlteracao;

	@Column(name = "TipoProduto", columnDefinition = "enum('PRODUZIDO','MATERIAPRIMA','SERVICO','PRODUTOFINAL')", length = 20)
	@Enumerated(EnumType.STRING)
	private TipoProduto tipoProduto;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')", length = 10)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@OneToOne(targetEntity = Ncm.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ncm", nullable = true, foreignKey = @ForeignKey(name = "FK_PRODUTO_NCM"))
	private Ncm ncm;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"), inverseJoinColumns = @JoinColumn(name = "imagem_id"), inverseForeignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDIMAGEM"), uniqueConstraints = {
			@UniqueConstraint(name = "produto_imagem", columnNames = { "produto_id", "imagem_id" }) })
	@ElementCollection(targetClass = Endereco.class)
	@CollectionTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"))
	private Set<Imagem> imagens;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Timestamp getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Timestamp dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
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

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	public Set<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<Imagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(Imagem imagens) {
		this.imagens.add(imagens);
	}

	public Produto() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.observacao = "";
		this.codigoBarras = "";
		this.unidade = "";
		this.marca = "";
		this.peso = 0.0;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.dataUltimaAlteracao = Timestamp.valueOf(LocalDateTime.now());
		this.tipoProduto = TipoProduto.PRODUTOFINAL;
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id) {
		this.id = id;
		this.descricao = "";
		this.observacao = "";
		this.codigoBarras = "";
		this.unidade = "";
		this.marca = "";
		this.peso = 0.0;
		this.dataCadastro = Timestamp.valueOf(LocalDateTime.now());
		this.dataUltimaAlteracao = Timestamp.valueOf(LocalDateTime.now());
		this.tipoProduto = TipoProduto.PRODUTOFINAL;
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public Produto(String descricao, String observacao, String codigoBarras, String unidade, String marca, Double peso,
			Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, TipoProduto tipoProduto,
			Situacao situacao) {
		this.id = Long.valueOf(0);
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.situacao = situacao;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id, Long idGrupo, String descricao, String observacao, String codigoBarras, String unidade,
			String marca, Double peso, Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			TipoProduto tipoProduto, Situacao situacao) {
		this.id = id;
		this.idGrupo = idGrupo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.situacao = situacao;
		this.imagens = new HashSet<>();
	}

	public Produto(Long id, Long idGrupo, String descricao, String observacao, String codigoBarras, String unidade,
			String marca, Double peso, Double volume, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			TipoProduto tipoProduto, Situacao situacao, Ncm ncm, Set<Imagem> imagens) {
		this.id = id;
		this.idGrupo = idGrupo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.volume = volume;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.tipoProduto = tipoProduto;
		this.situacao = situacao;
		this.ncm = ncm;
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
				+ ", tipoProduto=" + tipoProduto + ", peso=" + peso + ", situacao=" + situacao + "]";
	}

}
