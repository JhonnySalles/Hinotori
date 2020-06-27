package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
	private Enum<TipoPessoa> tipoPessoa;

	@Column(name = "Enquadramento", columnDefinition = "enum('CLIENTE','FORNECEDOR','AMBOS')")
	private Enum<TipoCliente> tipoCliente;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	private Enum<Situacao> situacao;

	/*@ElementCollection(targetClass = Contato.class)
	@CollectionTable(name = "clientes_contatos", joinColumns = @JoinColumn(name = "idCliente"), foreignKey = @ForeignKey(name = "FK_CLIENTES_CONTATOS_IDCLIENTE"))*/
	private Set<Contato> contatos = new HashSet<Contato>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "clientes_enderecos", joinColumns = @JoinColumn(name = "idCliente"), foreignKey = @ForeignKey(name = "FK_CLIENTES_ENDERECOS_IDCLIENTE"), inverseJoinColumns = @JoinColumn(name = "idEndereco"), inverseForeignKey = @ForeignKey(name = "FK_CLIENTES_ENDERECOS_IDENDERECO"), uniqueConstraints = {
			@UniqueConstraint(name = "cliente_endereco", columnNames = { "idCliente", "idEndereco" }) })
	private Set<Endereco> enderecos;

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

	public Set<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
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
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();

	}

	public Cliente(Long id, String nomeSobrenome, Timestamp dataCadastro, Timestamp dataUltimaAlteracao, String cpf,
			String cnpj, String observacao, Enum<TipoPessoa> tipoPessoa, Enum<TipoCliente> tipoCliente,
			Enum<Situacao> situacao) {
		super(id, nomeSobrenome, dataCadastro, dataUltimaAlteracao);
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.observacao = observacao;
		this.tipoPessoa = tipoPessoa;
		this.tipoCliente = tipoCliente;
		this.situacao = situacao;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", cnpj=" + cnpj + ", observacao=" + observacao + ", tipoPessoa=" + tipoPessoa
				+ ", tipoCliente=" + tipoCliente + ", situacao=" + situacao + ", contatos=" + contatos + ", enderecos="
				+ enderecos + "]";
	}

}
