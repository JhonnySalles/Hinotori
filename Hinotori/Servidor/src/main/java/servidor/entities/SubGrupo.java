package servidor.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import comum.model.enums.Situacao;

@Entity
@Table(name = "sub_grupos")
public class SubGrupo extends GrupoBase {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -4164176524362313995L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdSubGrupo", nullable = true, foreignKey = @ForeignKey(name = "FK_SUBGRUPO_IMAGEM"))
	private Set<GupoSubGrupoImagem> imagens;

	public Set<GupoSubGrupoImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<GupoSubGrupoImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(GupoSubGrupoImagem imagem) {
		this.imagens.add(imagem);
	}

	public SubGrupo() {
		super();
		this.imagens = new HashSet<>();
	}

	public SubGrupo(Long id, String descricao, String cor, Situacao situacao, Set<GupoSubGrupoImagem> imagens) {
		super(id, descricao, cor, situacao);
		this.imagens = imagens;
	}

	public SubGrupo(Long id, String descricao, String cor, Situacao situacao) {
		super(id, descricao, cor, situacao);
		this.imagens = new HashSet<>();
	}

}
