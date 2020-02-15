package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;

public class Cliente extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	private String cpf;
	private String cnpj;
	private String observacao;

	private Enum<TipoPessoa> tipoPessoa;
	private Enum<TipoCliente> tipoCliente;
	private Enum<Situacao> situacao;

	private List<Endereco> enderecos;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<TipoPessoa> getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Enum<TipoPessoa> tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Enum<TipoCliente> getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(Enum<TipoCliente> tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Cliente() {
		super();
		this.cpf = "";
		this.cnpj = "";
		this.observacao = "";
		this.tipoPessoa = TipoPessoa.FISICO;
		this.tipoCliente = TipoCliente.CLIENTE;
		this.situacao = Situacao.ATIVO;
		this.enderecos = new ArrayList<>();

	}

	public Cliente(Long id, String nomeSobrenome, Timestamp dataCadastro, String cpf, String cnpj, String observacao,
			Enum<TipoPessoa> tipoPessoa, Enum<TipoCliente> tipoCliente, Enum<Situacao> situacao) {
		super(id, nomeSobrenome, dataCadastro);
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.tipoCliente = tipoCliente;
		this.situacao = situacao;
		this.enderecos = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", cnpj=" + cnpj + ", observacao=" + observacao + ", tipoPessoa=" + tipoPessoa
				+ ", tipoCliente=" + tipoCliente + ", situacao=" + situacao + "]";
	}

}
