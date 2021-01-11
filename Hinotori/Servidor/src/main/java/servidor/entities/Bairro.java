package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import servidor.dao.Entidade;

@Entity
@Table(name = "bairros")
public class Bairro implements Serializable, Entidade {

	final public static String TABELA = Bairro.class.getAnnotation(Table.class).name();
	
	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6407704915654886503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@OneToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCidade", nullable = false, foreignKey = @ForeignKey(name = "FK_BAIRRO_CIDADE"))
	@Cascade(CascadeType.SAVE_UPDATE)
	private Cidade cidade;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Bairro() {
		this.id = Long.valueOf(0);
		this.nome = "";
	}

	public Bairro(Long id, String nome, Cidade cidade) {
		this.id = id;
		this.nome = nome;
		this.cidade = cidade;
	}

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Bairro other = (Bairro) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bairro [id=" + id + ", nome=" + nome + ", cidade=" + cidade + "]";
	}

}
