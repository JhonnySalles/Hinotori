package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import comum.model.enums.Situacao;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GrupoBase implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 3236226184046318646L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "Descricao", columnDefinition = "varchar(250)")
	private String descricao;

	@Column(name = "Cor", columnDefinition = "varchar(10)")
	private String cor;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUÍDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

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
	}

	public GrupoBase(Long id, String descricao, String cor, Situacao situacao) {
		this.id = id;
		this.descricao = descricao;
		this.situacao = situacao;
		
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
				+ "]";
	}

}
