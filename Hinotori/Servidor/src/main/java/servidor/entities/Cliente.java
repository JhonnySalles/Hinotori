package servidor.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import servidor.dao.Entidade;

@Entity
@Table(name = "clientes", uniqueConstraints = { @UniqueConstraint(columnNames = { "CPF" }, name = "UK_CLIENTE_CPF"),
		@UniqueConstraint(columnNames = { "CNPJ" }, name = "UK_CLIENTE_CNPJ") })
public class Cliente extends Pessoa implements Entidade {

	public static final String TABELA = "clientes";

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6989181117327049412L;

	@Column(name = "RazaoSocial", nullable = true)
	private String razaoSocial;

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
	private Enquadramento enquadramento;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CLIENTE_CONTATO"))
	private Set<Contato> contatos = new HashSet<Contato>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCliente", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ENDERECO"))
	private Set<Endereco> enderecos = new HashSet<Endereco>();

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

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

	public Enquadramento getEnquadramento() {
		return enquadramento;
	}

	public void setEnquadramento(Enquadramento enquadramento) {
		this.enquadramento = enquadramento;
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
		this.razaoSocial = "";
		this.cpf = "";
		this.cnpj = "";
		this.observacao = "";
		this.tipoPessoa = TipoPessoa.FISICO;
		this.enquadramento = Enquadramento.CLIENTE;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(Long id) {
		super(id);
		this.razaoSocial = "";
		this.cpf = "";
		this.cnpj = "";
		this.observacao = "";
		this.tipoPessoa = TipoPessoa.FISICO;
		this.enquadramento = Enquadramento.CLIENTE;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}
	
	public Cliente(String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			TipoPessoa tipoPessoa, Enquadramento enquadramento) {
		super(0L, nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO);
		this.razaoSocial = razaoSocial;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(Long id, String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			TipoPessoa tipoPessoa, Enquadramento enquadramento, Situacao situacao) {
		super(id, nomeSobrenome, Timestamp.valueOf(LocalDateTime.now()), situacao);
		this.razaoSocial = razaoSocial;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	public Cliente(Long id, String nomeSobrenome, String razaoSocial, String cpf, String cnpj, String observacao,
			Timestamp dataCadastro, Timestamp dataUltimaAlteracao, TipoPessoa tipoPessoa, Enquadramento enquadramento,
			Situacao situacao, Set<Contato> contatos, Set<Endereco> enderecos) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao, situacao);
		this.razaoSocial = razaoSocial;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.enquadramento = enquadramento;
		this.contatos = contatos;
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Cliente [razaoSocial=" + razaoSocial + ", cpf=" + cpf + ", cnpj=" + cnpj + ", observacao=" + observacao
				+ ", tipoPessoa=" + tipoPessoa + ", enquadramento=" + enquadramento + ", contatos=" + contatos
				+ ", enderecos=" + enderecos + "]";
	}

}
