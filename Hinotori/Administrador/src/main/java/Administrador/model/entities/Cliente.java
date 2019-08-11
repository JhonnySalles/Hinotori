package Administrador.model.entities;

import java.io.Serializable;
import java.util.Date;

import model.enums.Situacao;
import model.enums.UsuarioNivel;

public class Cliente extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	private Long id;
	private Long idBairro;

	private Date ultimaAlteracao;
	private String observacao;
	private String endereco;
	private String numero;
	private String complemento;
	private String cep;
	private String cpfCnpj;

	private Enum<UsuarioNivel> tipo;
	private Enum<Situacao> situacao;

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

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Enum<UsuarioNivel> getTipo() {
		return tipo;
	}

	public void setTipo(UsuarioNivel usuarioNivel) {
		this.tipo = usuarioNivel;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Cliente() {
		super();
	}

	public Cliente(Long id, Long idBairro, String nome, String sobreNome, String dddTelefone, String telefone,
			String dddCelular, String celular, String email, Date dataCadastro, Date ultimaAlteracao, String numero,
			String complemento, String cep, String cpfCnpj, String observacao, Enum<UsuarioNivel> tipo,
			Enum<Situacao> situacao) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email, dataCadastro);
		this.id = id;
		this.idBairro = idBairro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.observacao = observacao;
		this.tipo = tipo;
		this.situacao = situacao;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.cpfCnpj = cpfCnpj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idBairro == null) ? 0 : idBairro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
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
		return "Cliente [id=" + id + ", idBairro=" + idBairro + ", ultimaAlteracao=" + ultimaAlteracao + ", observacao="
				+ observacao + ", numero=" + numero + ", endereco=" + endereco + ", tipo=" + tipo + ", situacao="
				+ situacao + ", complemento=" + complemento + ", cep=" + cep + ", cpfCnpj=" + cpfCnpj + "]";
	}

}
