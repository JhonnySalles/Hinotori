package servidor.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comum.model.enums.Situacao;
import servidor.dao.Entidade;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable, Entidade {

	public static final String TABELA = "empresas";

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 5585811158914792352L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NomeFantasia")
	private String nomeFantasia;

	@Column(name = "RazaoSocial")
	private String razaoSocial;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "DataCadastro")
	private Timestamp dataCadastro;

	@Column(name = "Situacao", columnDefinition = "enum('ATIVO','INATIVO','EXCLUIDO')")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@OneToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cidade_id", foreignKey = @ForeignKey(name = "FK_EMPRESA_CIDADE"))
	private Cidade cidade;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_enderecos", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_ENDERECO"))
	private Set<Endereco> enderecos;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_contatos", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_CONTATO"))
	private Set<Contato> contatos;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_imagens", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_EMPRESA_IMAGEM"))
	private Set<EmpresaImagem> imagens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public Set<EmpresaImagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<EmpresaImagem> imagens) {
		this.imagens = imagens;
	}

	public void addImagens(EmpresaImagem imagens) {
		this.imagens.add(imagens);
	}

	public Empresa() {
		this.id = Long.valueOf(0);
		this.nomeFantasia = "";
		this.razaoSocial = "";
		this.cnpj = "";
		this.situacao = Situacao.ATIVO;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id) {
		this.id = id;
		this.nomeFantasia = "";
		this.razaoSocial = "";
		this.cnpj = "";
		this.situacao = Situacao.ATIVO;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Situacao situacao) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Situacao situacao, Cidade cidade) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
		this.cidade = cidade;
		this.enderecos = new HashSet<>();
		this.contatos = new HashSet<>();
		this.imagens = new HashSet<>();
	}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Situacao situacao, Cidade cidade, Set<Endereco> enderecos, Set<Contato> contatos,
			Set<EmpresaImagem> imagens) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataCadastro = dataCadastro;
		this.situacao = situacao;
		this.cidade = cidade;
		this.enderecos = enderecos;
		this.contatos = contatos;
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeFantasia == null) ? 0 : nomeFantasia.hashCode());
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
		if (nomeFantasia == null) {
			if (other.nomeFantasia != null)
				return false;
		} else if (!nomeFantasia.equals(other.nomeFantasia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nomeFantasia=" + nomeFantasia + ", razaoSocial=" + razaoSocial + ", cnpj="
				+ cnpj + ", dataCadastro=" + dataCadastro + ", situacao=" + situacao + ", enderecos=" + enderecos
				+ ", contatos=" + contatos + ", imagens=" + imagens + "]";
	}

}
