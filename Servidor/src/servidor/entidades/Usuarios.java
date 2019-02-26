package servidor.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="Usuarios") 
public class Usuarios implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="usr_login" , nullable=false, length=20, unique=true)
	private String login;
	
	@Column(name="usr_nome", length=150)
	private String nome;
    
	@Column(name="usr_senha")
	private String senha;
	
	@Column(name="usr_email")
	private String email;
   
	@Column(name="usr_dtCadastro")
	@Temporal(TemporalType.DATE)
	private Date DtCadastro; 
	
	@Column(name="usr_status")
    private int ativo = 1;
    
	@Column(name="usr_permissao")
    private int permissao = 0;
    
	@Column(name="usr_supervisor")
	private int supervisor;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuariosempresas",    		
    		joinColumns = 
    			@JoinColumn(name= "usr_login", referencedColumnName= "usr_login",columnDefinition="varchar(20)",nullable=false),    			
            inverseJoinColumns = 
            	@JoinColumn(name = "emp_codigo", referencedColumnName = "emp_codigo", nullable = false), 
        	foreignKey = 
            	@ForeignKey(name="Usuarios_Empresas"),
            inverseForeignKey = 
            	@ForeignKey(name="Empresas_Usuarios"),
            uniqueConstraints = {
            	@UniqueConstraint(name="usuariosempresas", columnNames={"usr_login","emp_codigo"})}
    		
    		)           
	
	private List<Empresas> trabalha = new ArrayList<>();
	
	
	public Usuarios() {
    }

    public Usuarios(String login) {
        this.login = login;
    }
    
    
    
	public List<Empresas> getTrabalha() {
		return trabalha;
	}

	public void setTrabalha(List<Empresas> trabalha) {
		this.trabalha = trabalha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtCadastro() {
		return DtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		DtCadastro = dtCadastro;
	}

	public boolean isAtivo() {
        return ativo == 1;
    }
    
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

    public boolean isAdministrador() {
        return permissao == 1;
    }
    
	public void setPermissao(int permissao) {
		this.permissao = permissao;
	}

	public int getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Usuarios other = (Usuarios) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	

}
