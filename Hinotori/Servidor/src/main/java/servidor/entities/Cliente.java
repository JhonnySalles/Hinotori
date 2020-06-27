package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;

@Entity
@Table(name = "clientes", uniqueConstraints = { @UniqueConstraint(columnNames = { "CPF" }, name = "UK_CLIENTE_CPF"),
		@UniqueConstraint(columnNames = { "CNPJ" }, name = "UK_CLIENTE_CNPJ") })
public class Cliente extends Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	@Column(name = "CPF", unique = true, nullable = true, insertable = true, updatable = true, length = 15, columnDefinition = "varchar(15)")
	private String cpf;

	@Column(name = "CNPJ", unique = true, nullable = true, insertable = true, updatable = true, length = 15, columnDefinition = "varchar(15)")
	private String cnpj;

	@Column(name = "Observacao", columnDefinition = "longtext")
	private String observacao;

	@Column(name = "Tipo", columnDefinition = "enum('FISICO','JURIDICO','AMBOS')")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(name = "Enquadramento", columnDefinition = "enum('CLIENTE','FORNECEDOR','AMBOS')")
	@Enumerated(EnumType.STRING)
	private TipoCliente tipoCliente;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "clientes_contatos", joinColumns = @JoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "FK_CLIENTES_CONTATOS_IDCLIENTE"), inverseJoinColumns = @JoinColumn(name = "contato_id"), inverseForeignKey = @ForeignKey(name = "FK_CLIENTES_CONTATOS_IDCONTATO"), uniqueConstraints = {
			@UniqueConstraint(name = "cliente_contato", columnNames = { "cliente_id", "contato_id" }) })
	@ElementCollection(targetClass = Contato.class)
	@CollectionTable(name = "clientes_contatos", joinColumns = @JoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "FK_CLIENTES_CONTATOS_IDCLIENTE"))
	private Set<Contato> contatos = new HashSet<Contato>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "clientes_enderecos", joinColumns = @JoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "FK_CLIENTES_ENDERECOS_IDCLIENTE"), inverseJoinColumns = @JoinColumn(name = "endereco_id"), inverseForeignKey = @ForeignKey(name = "FK_CLIENTES_ENDERECOS_IDENDERECO"), uniqueConstraints = {
			@UniqueConstraint(name = "cliente_endereco", columnNames = { "cliente_id", "endereco_id" }) })
	@ElementCollection(targetClass = Endereco.class)
	@CollectionTable(name = "clientes_enderecos", joinColumns = @JoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "FK_CLIENTES_ENDERECOS_IDCLIENTE"))
	private Set<Endereco> enderecos = new HashSet<Endereco>();

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

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void addEnderecos(Endereco enderecos) {
		this.enderecos.add(enderecos);
	}

	public Set<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}

	public void addContatos(Contato contatos) {
		this.contatos.add(contatos);
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
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();

	}

	public Cliente(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String cpf,
			String cnpj, String observacao, TipoPessoa tipoPessoa, TipoCliente tipoCliente, Situacao situacao) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.tipoCliente = tipoCliente;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String cpf,
			String cnpj, String observacao, TipoPessoa tipoPessoa, TipoCliente tipoCliente, Situacao situacao,
			Set<Contato> contatos, Set<Endereco> enderecos) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.tipoCliente = tipoCliente;
		this.contatos = contatos;
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", cnpj=" + cnpj + ", observacao=" + observacao + ", tipoPessoa=" + tipoPessoa
				+ ", tipoCliente=" + tipoCliente + ", contatos=" + contatos + ", enderecos=" + enderecos + "]";
	}

}
