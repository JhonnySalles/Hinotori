package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comum.model.enums.Situacao;
import comum.model.enums.TipoEndereco;
import javafx.beans.property.SimpleBooleanProperty;
import servidor.converter.BooleanPropertyConverter;

@Entity
@Table(name = "Enderecos")
public class Endereco implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -7050982212146652850L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(targetEntity = Bairro.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Bairro bairro;

	@Column(name = "Endereco", columnDefinition = "varchar(150)")
	private String endereco;

	@Column(name = "Numero", columnDefinition = "varchar(10)")
	private String numero;

	@Column(name = "CEP", columnDefinition = "varchar(10)")
	private String cep;

	@Column(name = "Complemento", columnDefinition = "varchar(150)")
	private String complemento;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "DataCadastro", columnDefinition = "datetime")
	private Timestamp dataCadastro;

	@Column(name = "Tipo", columnDefinition = "enum('COMERCIAL','COBRANÇA','ENTREGA','OUTROS')")
	@Enumerated(EnumType.STRING)
	private TipoEndereco tipoEndereco;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

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

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	@Enumerated(EnumType.STRING)
	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public boolean isPadrao() {
		return padrao.get();
	}

	public SimpleBooleanProperty padraoProperty() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao.set(padrao);
	}

	public Endereco() {
		this.id = Long.valueOf(0);
		this.endereco = "";
		this.numero = "";
		this.cep = "";
		this.complemento = "";
		this.observacao = "";
		this.tipoEndereco = TipoEndereco.COBRANCA;
		this.situacao = Situacao.ATIVO;
		this.padrao.set(false);
	}

	public Endereco(Bairro bairro, String endereco, String numero, String cep, String complemento, String observacao,
			Timestamp dataCadastro, TipoEndereco tipoEndereco, Situacao situacao, Boolean padrao) {
		this.id = Long.valueOf(0);
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = dataCadastro;
		this.padrao.set(padrao);
		this.tipoEndereco = tipoEndereco;
		this.situacao = situacao;
	}

	public Endereco(Long id, Bairro bairro, String endereco, String numero, String cep, String complemento,
			String observacao, Timestamp dataCadastro, TipoEndereco tipoEndereco, Situacao situacao, Boolean padrao) {
		this.id = id;
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.dataCadastro = dataCadastro;
		this.padrao.set(padrao);
		this.tipoEndereco = tipoEndereco;
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipoEndereco == null) ? 0 : tipoEndereco.hashCode());
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
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (tipoEndereco == null) {
			if (other.tipoEndereco != null)
				return false;
		} else if (!tipoEndereco.equals(other.tipoEndereco))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", bairro=" + bairro + ", endereco=" + endereco + ", numero=" + numero + ", cep="
				+ cep + ", complemento=" + complemento + ", observacao=" + observacao + ", dataCadastro=" + dataCadastro
				+ ", tipoEndereco=" + tipoEndereco + ", situacao=" + situacao + ", padrao=" + padrao + "]";
	}

}
