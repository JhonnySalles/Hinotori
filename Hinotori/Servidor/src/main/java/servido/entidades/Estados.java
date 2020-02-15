package Servidor.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Entity
@Table(name="Estados") 
public class Estados implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="estado_sigla", nullable=false, unique=true, length=2) 
	private String sigla;
	
	@Column(name="estado_nome", nullable=false, length=50)
	private String nome;

	@Column(name="estado_codibge", nullable=false,length=11)
	private int ibge;	
	
	@ManyToOne
	@JoinColumn(name = "pais_codigo", referencedColumnName = "pais_codigo", nullable = false)
	@ForeignKey(name = "Estados_Paises")
	private Paises pais;
	 
	public Estados() {
	
	}
	
	public Estados(String sigla, String nome, int codibge) {
		this.sigla=sigla;
		this.nome=nome;
		this.ibge=codibge;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public int getIbge() {
		return ibge;
	}

	public void setIbge(int ibge) {
		this.ibge = ibge;
	}

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Estados other = (Estados) obj;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
}
