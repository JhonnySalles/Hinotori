package Administrador.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import Administrador.enums.Situacao;
import Administrador.enums.TipoEmpresa;

public class Cliente extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	private Long id;
	private Long idBairro;
	private Date ultimaAlteracao;
	private String observacao;
	private List<ClienteContato> contatos;
	private List<ClienteEndereco> endereco;
	private Enum<TipoEmpresa> tipo;
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

	public List<ClienteContato> getContatos() {
		return contatos;
	}

	public void setContatos(List<ClienteContato> contatos) {
		this.contatos = contatos;
	}

	public List<ClienteEndereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<ClienteEndereco> endereco) {
		this.endereco = endereco;
	}

	public Enum<TipoEmpresa> getTipo() {
		return tipo;
	}

	public void setTipo(Enum<TipoEmpresa> tipo) {
		this.tipo = tipo;
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

	public Cliente(String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, Date dataCadastro, Long id, Long idBairro, Date ultimaAlteracao,
			String observacao, List<ClienteContato> contatos, List<ClienteEndereco> endereco, Enum<TipoEmpresa> tipo,
			Enum<Situacao> situacao) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email, dataCadastro);
		this.id = id;
		this.idBairro = idBairro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.observacao = observacao;
		this.contatos = contatos;
		this.endereco = endereco;
		this.tipo = tipo;
		this.situacao = situacao;
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
				+ observacao + ", contatos=" + contatos + ", endereco=" + endereco + ", tipo=" + tipo + ", situacao="
				+ situacao + "]";
	}

}
