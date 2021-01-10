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
import servidor.dao.Entidade;

@Entity
@Table(name = "grupos")
public class Grupo extends GrupoBase implements Entidade {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6729440671900319532L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "grupo_subgrupo", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_GRUPO_SUBGRUPO"))
	private Set<SubGrupo> subGrupos;

	public Set<SubGrupo> getSubGrupos() {
		return subGrupos;
	}

	public void setSubGrupos(Set<SubGrupo> subGrupos) {
		this.subGrupos = subGrupos;
	}

	public boolean addSubGrupo(SubGrupo subGrupo) {
		if (!this.subGrupos.contains(subGrupo)) {
			this.subGrupos.add(subGrupo);
			return true;
		} else
			return false;
	}

	public boolean removeSubGrupo(SubGrupo subGrupo) {
		if (this.subGrupos.contains(subGrupo)) {
			this.subGrupos.remove(subGrupo);
			return true;
		} else
			return false;
	}

	public Grupo() {
		super();
		this.subGrupos = new HashSet<>();
	}

	public Grupo(Long id, String descricao, String cor, Situacao situacao) {
		super(id, descricao, cor, situacao);
		this.subGrupos = new HashSet<>();
	}

	public Grupo(Long id, String descricao, String cor, Situacao situacao, Set<SubGrupo> subGrupos,
			Set<GupoBaseImagem> imagens) {
		super(id, descricao, cor, situacao, imagens);
		this.subGrupos = subGrupos;
	}

}
