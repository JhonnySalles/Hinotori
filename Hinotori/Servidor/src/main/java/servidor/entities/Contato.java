package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import comum.model.enums.Situacao;
import comum.model.enums.TipoContato;
import javafx.beans.property.SimpleBooleanProperty;
import servidor.converter.BooleanPropertyConverter;

@Entity
@Table(name = "Contatos")
public class Contato implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -1562578455627883930L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "Nome", columnDefinition = "varchar(150)")
	private String nome;

	@Column(name = "Telefone", columnDefinition = "varchar(15)")
	private String telefone;

	@Column(name = "Celular", columnDefinition = "varchar(15)")
	private String celular;

	@Column(name = "Email", columnDefinition = "varchar(250)")
	private String email;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "Tipo", columnDefinition = "enum('RESIDENCIAL','COMERCIAL')")
	@Enumerated(EnumType.STRING)
	private TipoContato tipoContato;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@Column(name = "DataCadastro")
	private Timestamp dataCadastro;

	@Convert(converter = BooleanPropertyConverter.class)
	@Column(name = "Padrao", columnDefinition = "tinyint(1)")
	private SimpleBooleanProperty padrao = new SimpleBooleanProperty(false);

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Timestamp getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipo) {
		this.tipoContato = tipo;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public final boolean getPadrao() {
		return padrao.get();
	}

	public final void setPadrao(boolean padrao) {
		this.padrao.set(padrao);
	}

	public boolean isPadrao() {
		return padrao.get();
	}

	public SimpleBooleanProperty padraoProperty() {
		return padrao;
	}

	public Contato() {
		this.id = Long.valueOf(0);
		this.nome = "";
		this.telefone = "";
		this.celular = "";
		this.email = "";
		this.observacao = "";
		this.tipoContato = TipoContato.RESIDENCIAL;
		this.situacao = Situacao.ATIVO;
		this.padrao.set(false);
	}

	public Contato(Long id, String nome, String telefone, String celular, String email, String observacao,
			Timestamp dataCadastro, TipoContato tipoContato, Situacao situacao, Boolean padrao) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.observacao = observacao;
		this.dataCadastro = dataCadastro;
		this.tipoContato = tipoContato;
		this.situacao = situacao;
		this.padrao.set(padrao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		Contato other = (Contato) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", celular=" + celular + ", email="
				+ email + ", observacao=" + observacao + ", tipoContato=" + tipoContato + ", situacao=" + situacao
				+ ", dataCadastro=" + dataCadastro + ", padrao=" + padrao + "]";
	}

}
