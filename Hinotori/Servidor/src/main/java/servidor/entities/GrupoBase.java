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

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GrupoBase implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 3236226184046318646L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "Descricao", columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "cor", columnDefinition = "varchar(10)")
	private String cor;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUÍDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"), inverseJoinColumns = @JoinColumn(name = "imagem_id"), inverseForeignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDIMAGEM"), uniqueConstraints = {
			@UniqueConstraint(name = "produto_imagem", columnNames = { "produto_id", "imagem_id" }) })
	@ElementCollection(targetClass = Endereco.class)
	@CollectionTable(name = "produtos_imagens", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "FK_PRODUTOS_IMAGENS_IDPRODUTO"))
	private Set<GupoBaseImagem> imagens;

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

	public Set<GupoBaseImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<GupoBaseImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(GupoBaseImagem imagem) {
		this.imagens.add(imagem);
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public GrupoBase() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.cor = "#000000";
		this.situacao = Situacao.ATIVO;
		this.imagens = new HashSet<>();
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		this.imagens = new HashSet<>();

		setCor(cor);
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao, Set<GupoBaseImagem> imagens) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		this.imagens = imagens;

		setCor(cor);
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
		GrupoBase other = (GrupoBase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrupoBase [id=" + id + ", descricao=" + descricao + ", cor=" + cor + ", situacao=" + situacao
				+ ", imagens=" + imagens + "]";
	}

}