package Administrador.model.entities;

import java.io.Serializable;
import java.util.Date;

import model.enums.Situacao;

public class Empresa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 5585811158914792352L;

	private Long id;
	private Long idBairro;
	private String nomeFantasia;
	private String razaoSocial;
	private String cnpj;
	private String endereco;
	private String numero;
	private String cep;
	private String complemento;
	private Date dataCadastro;
	private Enum<Situacao> situacao;
	private EmpresaContato contato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(Long idBairro) {
		this.idBairro = idBairro;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public EmpresaContato getContato() {
		return contato;
	}

	public void setContato(EmpresaContato contato) {
		this.contato = contato;
	}

	public Empresa() {

	}

	public Empresa(Long id, Long idBairro, String nomeFantasia, String razaoSocial, String cnpj, String endereco,
			String numero, String cep, String complemento, Date dataCadastro, Enum<Situacao> situacao) {
		this.id = id;
		this.idBairro = idBairro;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
	}

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idBairro == null) ? 0 : idBairro.hashCode());
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
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idBairro == null) {
			if (other.idBairro != null)
				return false;
		} else if (!idBairro.equals(other.idBairro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", idBairro=" + idBairro + ", nomeFantasia=" + nomeFantasia + ", razaoSocial="
				+ razaoSocial + ", cnpj=" + cnpj + ", endereco=" + endereco + ", numero=" + numero + ", cep=" + cep
				+ ", complemento=" + complemento + ", dataCadastro=" + dataCadastro + ", situacao=" + situacao + "]";
	}

}
