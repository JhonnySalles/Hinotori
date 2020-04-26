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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import comum.model.enums.Situacao;

@Entity
@Table(name = "empresas", schema = "baseteste")
public class Empresa implements Serializable {

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
	private Enum<Situacao> situacao;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "empresas_enderecos", joinColumns = @JoinColumn(name = "idEmpresa"), foreignKey = @ForeignKey(name = "UK_EMPRESAS_ENDERECOS_IDEMPRESA"), inverseJoinColumns = @JoinColumn(name = "idEndereco"), inverseForeignKey = @ForeignKey(name = "UK_EMPRESAS_ENDERECOS_IDENDERECO"), uniqueConstraints = {
			@UniqueConstraint(name = "empresa_endereco", columnNames = { "idEmpresa", "idEndereco" }) })
	private Set<Endereco> enderecos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "empresas_contatos", joinColumns = @JoinColumn(name = "idEmpresa"), foreignKey = @ForeignKey(name = "UK_EMPRESAS_CONTATOS_IDEMPRESA"), inverseJoinColumns = @JoinColumn(name = "idContato"), inverseForeignKey = @ForeignKey(name = "UK_EMPRESAS_CONTATOS_IDCONTATO"), uniqueConstraints = {
			@UniqueConstraint(name = "empresa_contato", columnNames = { "idEmpresa", "idContato" }) })
	private Set<Contato> contatos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "empresas_imagens", joinColumns = @JoinColumn(name = "idEmpresa"), foreignKey = @ForeignKey(name = "UK_EMPRESAS_IMAGENS_IDEMPRESA"), inverseJoinColumns = @JoinColumn(name = "idImagem"), inverseForeignKey = @ForeignKey(name = "UK_EMPRESAS_CONTATOS_IDIMAGEM"), uniqueConstraints = {
			@UniqueConstraint(name = "empresa_imagem", columnNames = { "idEmpresa", "idImagem" }) })
	private Set<Imagem> imagens;

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

	@Enumerated(EnumType.STRING)
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

	public Set<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(Set<Imagem> imagens) {
		this.imagens = imagens;
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

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Timestamp dataCadastro,
			Enum<Situacao> situacao) {
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
