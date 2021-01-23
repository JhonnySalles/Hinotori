package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import comum.model.enums.Situacao;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GrupoBase extends EntidadeBase implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 560772010212263047L;

	@Column(name = "Descricao", columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "Cor", columnDefinition = "varchar(10)")
	private String cor;

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

	public GrupoBase() {
		this.id = Long.valueOf(0);
		this.descricao = "";
		this.cor = "#000000";
		this.situacao = Situacao.ATIVO;
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;

		setCor(cor);
	}

	@Override
	public String toString() {
		return "GrupoBase [descricao=" + descricao + ", cor=" + cor + ", id=" + id + ", situacao=" + situacao + "]";
	}
}
