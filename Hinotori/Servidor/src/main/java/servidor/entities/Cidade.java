package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import comum.model.entities.Entidade;
import comum.model.enums.Situacao;

@Entity
@Table(name = "cidades")
public class Cidade implements Serializable, Entidade {

	final public static String TABELA = Cidade.class.getAnnotation(Table.class).name();
	
	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 8936948944326503399L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@OneToOne(targetEntity = Estado.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEstado", nullable = false, foreignKey = @ForeignKey(name = "FK_CIDADE_ESTADO"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private Estado estado;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	@Column(name = "Ddd", columnDefinition = "varchar(3)")
	private String ddd;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUÍDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Cidade() {
		this.id = Long.valueOf(0);
		this.nome = "";
		this.ddd = "";
		this.situacao = Situacao.ATIVO;
	}
	
	public Cidade(String nome, String ddd, Estado estado) {
		this.id = Long.valueOf(0);
		this.estado = estado;
		this.nome = nome;
		this.ddd = ddd;
		this.situacao = Situacao.ATIVO;
	}

	public Cidade(Long id, String nome, String ddd, Situacao situacao, Estado estado) {
		this.id = id;
		this.estado = estado;
		this.nome = nome;
		this.ddd = ddd;
		this.situacao = situacao;
	}

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome + ", ddd=" + ddd + ", situacao=" + situacao + ", estado=" + estado
				+ "]";
	}

}
