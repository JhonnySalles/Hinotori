package Administrador.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.enums.Situacao;
import model.enums.TipoCliente;

public class Cliente extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	private Long id;

	private Date ultimaAlteracao;
	private String cpfCnpj;
	private String observacao;

	private Enum<TipoCliente> tipo;
	private Enum<Situacao> situacao;

	private List<ClienteEndereco> enderecos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<TipoCliente> getTipo() {
		return tipo;
	}

	public void setTipo(Enum<TipoCliente> tipo) {
		this.tipo = tipo;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public List<ClienteEndereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<ClienteEndereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Cliente() {
		super();
		enderecos = new ArrayList<>();
	}

	public Cliente(Long id, String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, String cpfCnpj, String observacao, Enum<TipoCliente> tipo,
			Enum<Situacao> situacao) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email);
		this.id = id;
		this.cpfCnpj = cpfCnpj;
		this.observacao = observacao;
		this.tipo = tipo;
		this.situacao = situacao;
	}

	public Cliente(Long id, String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, String cpfCnpj, String observacao, Enum<TipoCliente> tipo,
			Enum<Situacao> situacao, List<ClienteEndereco> enderecos) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email);
		this.id = id;
		this.cpfCnpj = cpfCnpj;
		this.observacao = observacao;
		this.tipo = tipo;
		this.situacao = situacao;
		this.enderecos = enderecos;
	}

	public Cliente(Long id, String nome, String sobreNome, String dddTelefone, String telefone, String dddCelular,
			String celular, String email, Date dataCadastro, Date ultimaAlteracao, String cpfCnpj, String observacao,
			Enum<TipoCliente> tipo, Enum<Situacao> situacao, List<ClienteEndereco> enderecos) {
		super(nome, sobreNome, dddTelefone, telefone, dddCelular, celular, email, dataCadastro);
		this.id = id;
		this.ultimaAlteracao = ultimaAlteracao;
		this.cpfCnpj = cpfCnpj;
		this.observacao = observacao;
		this.tipo = tipo;
		this.situacao = situacao;
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		return true;
	}

}
