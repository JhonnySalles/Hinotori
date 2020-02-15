package servidor.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Empresas")
public class Empresas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_codigo", nullable=false, length=11)
	private int id;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuariosempresas",
    			joinColumns = 
    				@JoinColumn(name = "emp_codigo", referencedColumnName = "emp_codigo", nullable = false),
    			foreignKey = 
            		@ForeignKey(name="Empresas_Usuarios"),
            	inverseForeignKey = 
            		@ForeignKey(name="Usuarios_Empresas"),
            	inverseJoinColumns = 
            		@JoinColumn(name= "usr_login", referencedColumnName= "usr_login", columnDefinition="varchar(20)",nullable=false),	
            	uniqueConstraints = {
            		@UniqueConstraint(name="usuariosempresas",columnNames={"usr_login","emp_codigo"})
            		}
    		 )
	private List<Usuarios> emprega = new ArrayList<>();
		
	@Column(name="emp_razaosocial", nullable=false, length=255)
	private String razaoSocial;
	
	@Column(name="emp_fantasia")
	private String fantasia;
	
	@Column(name="emp_cnpj", nullable=false, length=14)
	private int cnpj;
	
	@Column(name= "dtConstituicao")
	@Temporal(TemporalType.DATE)
	private Date dtConstituicao;
	
	@Column(name= "dtCadastro")
	@Temporal(TemporalType.DATE)
	private Date dtCadastro;
			
	@Column(name="emp_ie", length=25)
	private String ie;
	
	@Column(name="emp_im", length=25)
	private String im;
	
//	@SuppressWarnings("deprecation")
	@ManyToOne
	@JoinColumn(name = "Cid_codigo", referencedColumnName = "Cid_codigo", nullable = false)
//	ForeignKey(name = "Empresas_Cidades")
	private Cidades cidade;
	
	@Column(name="emp_endereco", nullable=false,length=255)
	private String endereco;
	
	@Column(name="emp_numero", length=20)
	private String numero;
	
	@Column(name="emp_complemento", length=30)
	private String complemento;
	
	@Column(name = "emp_cep", length = 9, nullable = false)        
    private String cep;
	
	@Column(name = "emp_bairro", length = 70, nullable = false)        
    private String bairro;
	
    @Column(name = "emp_referencia", length = 80)        
    private String referencia;
	
    @Column(name = "emp_situacao", length = 1)        
    private String situacao;
    
    @Column(name="emp_paramentro", length =255)
    private String parametro;
	
    @Column(name= "emp_ddd", length = 2)
    private int ddd;
    
    @Column(name= "emp_fone", length = 8)
    private int fone;
    
    @Column(name = "emp_dddcelular", length = 2)
    private int dddcelular;
    
    @Column(name= "emp_celular", length = 9)
    private int celular;
    
    
	
	public Empresas() {
	
	}

	public Cidades getCidade() {
		return cidade;
	}

	public void setCidade(Cidades cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Usuarios> setEmprega() {
		return emprega;
	}

	public void setEmprega(List<Usuarios> emprega) {
		this.emprega = emprega;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public int getCnpj() {
		return cnpj;
	}

	public void setCnpj(int cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDtConstituicao() {
		return dtConstituicao;
	}

	public void setDtConstituicao(Date dtConstituicao) {
		this.dtConstituicao = dtConstituicao;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public int getDdd() {
		return ddd;
	}

	public void setDdd(int ddd) {
		this.ddd = ddd;
	}

	public int getFone() {
		return fone;
	}

	public void setFone(int fone) {
		this.fone = fone;
	}

	public int getDddcelular() {
		return dddcelular;
	}

	public void setDddcelular(int dddcelular) {
		this.dddcelular = dddcelular;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) {
		this.celular = celular;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cnpj;
		result = prime * result + id;
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
		Empresas other = (Empresas) obj;
		if (cnpj != other.cnpj)
			return false;
		if (id != other.id)
			return false;
		return true;
	} 
	
	
}
