package Servidor.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import Servidor.entidades.Estados;

@SuppressWarnings("deprecation")
@Entity
@Table(name="Cidades")
public class Cidades  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cidade_codigo", nullable=false, length=11)
	private Long id; 
	
	@Column(name="cid_nome", nullable=false,length=150)
	private String nome;
	
    @ManyToOne
    @JoinColumn(name = "estado_sigla", referencedColumnName = "estado_sigla", nullable = false)
    @ForeignKey(name = "Cidades_Estados")
    private Estados estado;
    
    @Column(name="cidade_ddd", nullable=false,length=2, columnDefinition="int")
    private int cid_ddd;
    
    @Column(name="cidade_codibge",length=11)
    private int cod_ibge;
    
    public Cidades() {
    }	
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public int getCid_ddd() {
		return cid_ddd;
	}

	public void setCid_ddd(int cid_ddd) {
		this.cid_ddd = cid_ddd;
	}

	public int getCod_ibge() {
		return cod_ibge;
	}

	public void setCod_ibge(int cod_ibge) {
		this.cod_ibge = cod_ibge;
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
		Cidades other = (Cidades) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
    
    
}
