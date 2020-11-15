package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

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
public class Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 7073086540992937921L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "NomeSobrenome", nullable = true, insertable = true, updatable = true, length = 250)
	private String nomeSobrenome;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	private Timestamp dataCadastro;

	@Column(name = "DataUltimaAlteracao", columnDefinition = "datetime")
	private Timestamp dataUltimaAlteracao;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeSobrenome() {
		return nomeSobrenome;
	}

	public void setNomeSobrenome(String nomeSobrenome) {
		this.nomeSobrenome = nomeSobrenome;
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

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Pessoa() {
		this.id = Long.valueOf(0);
		this.nomeSobrenome = "";
		this.dataCadastro = Timestamp.from(Instant.now());
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
		this.situacao = Situacao.ATIVO;
	}

	public Pessoa(Long id, String nomeSobrenome, Timestamp dataCadastro, Situacao situacao) {
		this.id = id;
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = Timestamp.from(Instant.now());
		this.situacao = situacao;
	}

	public Pessoa(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao,
			Situacao situacao) {
		this.id = id;
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.situacao = situacao;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nomeSobrenome=" + nomeSobrenome + ", dataCadastro=" + dataCadastro
				+ ", dataUltimaAlteracao=" + dataUltimaAlteracao + "]";
	}

}
