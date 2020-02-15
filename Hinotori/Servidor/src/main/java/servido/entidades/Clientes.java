package Servidor.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clientes")
public class Clientes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cli_codigo", nullable=false, length=11)
	private Long id;
	
	@Column(name="cli_nome", nullable=false, length=60)
	private String name;
	
	@Column(name="cli_sobrenome", length=60)
	private String sobrenome;
		
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, 
            fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();
	
	@Column(name="cli_email", length=50)
	private String email;
	
	@Column(name="cli_foneddd", length=2)
	private int foneddd;
	
	@Column(name="cli_fone", length=8)
	private int fone;
	
	@Column(name="cli_celddd", length=2)
	private int cellddd;
	
	@Column(name="cli_celular", length=9)
	private int celular;
	
	@Column(name="cli_ramal", length=9)
	private int ramal;
	
	@Column(name="cli_cxPostal", length=8)
	private int cxpostal;
	
	@Column(name="cli_obs", columnDefinition="Text")
	private String obs;	
	
	
	@Column(name="cli_tipo", nullable=false,length=1, columnDefinition="char")
	private boolean tipo;
	
	@Column(name="cli_consumidorfinal", nullable=false, length=1, columnDefinition="tinyint")
	private boolean consumidorfinal;
	
	@Column(name="cli_LimitCredito",columnDefinition="Double(9,2)")
	private Double limiteCredito;
	
	@Temporal(TemporalType.DATE)
	@Column(name="cli_dtcadastro", nullable=false)
	private Calendar dtCadastro;
	
	@Column(name="cli_situacao", nullable=false, length=2)
	private char situacao;

	public Clientes() {
		
	}
	
	 public void adicionarEndereco(Endereco obj){
        obj.setCliente(this);
        this.enderecos.add(obj);
    }
    
    public void removerEndereco(int index){
        this.enderecos.remove(index);
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFoneddd() {
		return foneddd;
	}

	public void setFoneddd(int foneddd) {
		this.foneddd = foneddd;
	}

	public int getFone() {
		return fone;
	}

	public void setFone(int fone) {
		this.fone = fone;
	}

	public int getCellddd() {
		return cellddd;
	}

	public void setCellddd(int cellddd) {
		this.cellddd = cellddd;
	}

	public int getCelular() {
		return celular;
	}

	public void setCelular(int celular) {
		this.celular = celular;
	}

	public int getRamal() {
		return ramal;
	}

	public void setRamal(int ramal) {
		this.ramal = ramal;
	}

	public int getCxpostal() {
		return cxpostal;
	}

	public void setCxpostal(int cxpostal) {
		this.cxpostal = cxpostal;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public boolean isConsumidorfinal() {
		return consumidorfinal;
	}

	public void setConsumidorfinal(boolean consumidorfinal) {
		this.consumidorfinal = consumidorfinal;
	}

	public Double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public Calendar getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Calendar dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public char getSituacao() {
		return situacao;
	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Clientes other = (Clientes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
