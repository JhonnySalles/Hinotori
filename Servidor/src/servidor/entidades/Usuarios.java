package servidor.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Usuarios") 
public class Usuarios implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USR_login" , nullable=false, length=20, unique=true)
	private String login="Admin";
	
	@Column(name="USR_Nome", length=150)
	private String nome;
    
	@Column(name="USR_Senha")
	private String senha;
	
	@Column(name="USR_email")
	private String email;
    
	@Column(name="Emp_Codigo")
	private int emp_codigo;
    
	@Column(name="USR_DtCadastro")
	@Temporal(TemporalType.DATE)
	private Date DtCadastro; 
	
	@Column(name="USR_Status")
    private int ativo = 1;
    
	@Column(name="USR_Permissao")
    private int permissao = 0;
    
	@Column(name="USR_Supervisor")
	private int supervisor;
	
    public Usuarios() {
    }

    public Usuarios(String login) {
        this.login = login;
    }

    public Usuarios(String login, String nome,String senha,    		
    		Date DtCadastro, int emp_codigo,
    		int ativo, int permissao, int supervisor) {
        this.login = login;
        this.nome = nome;        
        this.senha= senha;
        this.DtCadastro = DtCadastro;
        this.emp_codigo = emp_codigo;
        this.setAtivo(ativo);
        this.setPermissao(permissao);
        this.supervisor=supervisor;
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

	public int getEmp_Codigo() {
		return emp_codigo;
	}

	public void setEmp_Codigo(int emp_codigo) {
		this.emp_codigo = emp_codigo;
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
