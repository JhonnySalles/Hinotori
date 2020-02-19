package servidor.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import comum.model.enums.Situacao;
import comum.model.enums.TipoEndereco;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
public class Endereco implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -7050982212146652850L;
	private Long idSequencial;
	private Bairro bairro;

	@Column(name = "Endereco")
	private String endereco;

	@Column(name = "Numero")
	private String numero;

	@Column(name = "CEP")
	private String cep;

	@Column(name = "Complemento")
	private String complemento;

	@Column(name = "Observacao")
	private String observacao;

	@Column(name = "Tipo")
	private Enum<TipoEndereco> tipoEndereco;

	@Column(name = "Situacao")
	private Enum<Situacao> situacao;

	@Column(name = "Padrao")
	private SimpleBooleanProperty padrao = new SimpleBooleanProperty(false);

	public Long getIdSequencial() {
		return idSequencial;
	}

	public void setIdSequencial(Long idSequencial) {
		this.idSequencial = idSequencial;
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

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Enum<TipoEndereco> getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(Enum<TipoEndereco> tipoEndereco) {
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
		this.idSequencial = Long.valueOf(0);
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
			Enum<TipoEndereco> tipoEndereco, Enum<Situacao> situacao, Boolean padrao) {
		this.idSequencial = Long.valueOf(0);
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
		this.padrao.set(padrao);
		this.tipoEndereco = tipoEndereco;
		this.situacao = situacao;
	}

	public Endereco(Long idSequencial, Bairro bairro, String endereco, String numero, String cep, String complemento,
			String observacao, Enum<TipoEndereco> tipoEndereco, Enum<Situacao> situacao, Boolean padrao) {
		this.idSequencial = idSequencial;
		this.bairro = bairro;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.observacao = observacao;
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
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [idSequencial=" + idSequencial + ", bairro=" + bairro + ", endereco=" + endereco + ", numero="
				+ numero + ", cep=" + cep + ", complemento=" + complemento + ", observacao=" + observacao
				+ ", tipoEndereco=" + tipoEndereco + ", situacao=" + situacao + ", padrao=" + padrao + "]";
	}

}
