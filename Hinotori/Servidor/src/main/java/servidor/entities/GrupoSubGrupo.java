package servidor.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;
import comum.model.enums.TipoGrupo;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GrupoSubGrupo implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6407704915654886503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "Descricao", columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "cor", columnDefinition = "varchar(10)")
	private String cor;

	private Set<GrupoSubGrupo> subGrupos;

	@Column(name = "Tipo", columnDefinition = "enum('GRUPO','SUBGRUPO')")
	@Enumerated(EnumType.STRING)
	private TipoGrupo tipo;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUÍDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"), inverseJoinColumns = @JoinColumn(name = "imagem_id"), inverseForeignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDIMAGEM"), uniqueConstraints = {
			@UniqueConstraint(name = "produto_imagem", columnNames = { "produto_id", "imagem_id" }) })
	@ElementCollection(targetClass = Endereco.class)
	@CollectionTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"))
	private Set<GupoSubGrupoImagem> imagens;

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

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		if (cor.isEmpty())
			this.cor = "#000000";
		else
			this.cor = cor;
	}

	public Set<GrupoSubGrupo> getSubGrupos() {
		return subGrupos;
	}

	public void setSubGrupos(Set<GrupoSubGrupo> subGrupos) {
		if (subGrupos.stream().filter(item -> item.getTipo().equals(TipoGrupo.GRUPO)).findFirst().isPresent())
			throw new IllegalArgumentException("Não é possível inserir um grupo dentro de outro grupo.");

		this.subGrupos = subGrupos;
	}

	public boolean addSubGrupo(GrupoSubGrupo subGrupo) throws IllegalArgumentException {
		if (subGrupo.tipo.equals(TipoGrupo.GRUPO))
			throw new IllegalArgumentException("Não é possível inserir um grupo dentro de outro grupo.");
		if (!this.subGrupos.contains(subGrupo)) {
			this.subGrupos.add(subGrupo);
			return true;
		}

		return false;
	}

	public boolean removeSubGrupo(GrupoSubGrupo subGrupo) throws IllegalArgumentException {
		if (subGrupo.tipo.equals(TipoGrupo.GRUPO))
			throw new IllegalArgumentException("Não é possível remover um grupo dentro de outro grupo.");

		if (this.subGrupos.contains(subGrupo)) {
			this.subGrupos.remove(subGrupo);
			return true;
		}

		return false;
	}

	public Set<GupoSubGrupoImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<GupoSubGrupoImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(GupoSubGrupoImagem imagem) {
		this.imagens.add(imagem);
	}

	public TipoGrupo getTipo() {
		return tipo;
	}

	public void setTipo(TipoGrupo tipo) {
		this.tipo = tipo;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public GrupoSubGrupo() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.cor = "#000000";
		this.tipo = TipoGrupo.GRUPO;
		this.situacao = Situacao.ATIVO;
		this.subGrupos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public GrupoSubGrupo(Long id, String descricao, String cor, TipoGrupo tipo, Situacao situacao) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.situacao = situacao;

		this.subGrupos = new HashSet<>();
		this.imagens = new HashSet<>();

		setCor(cor);
	}

	public GrupoSubGrupo(Long id, String descricao, String cor, Set<GrupoSubGrupo> subGrupos, TipoGrupo tipo,
			Situacao situacao, Set<GupoSubGrupoImagem> imagens) {
		this.id = id;
		this.descricao = descricao;
		this.subGrupos = subGrupos;
		this.tipo = tipo;
		this.situacao = situacao;
		this.imagens = imagens;

		setCor(cor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		GrupoSubGrupo other = (GrupoSubGrupo) obj;
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
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrupoSubGrupo [id=" + id + ", descricao=" + descricao + ", cor=" + cor + ", subGrupos=" + subGrupos
				+ ", tipo=" + tipo + ", situacao=" + situacao + "]";
	}

}
