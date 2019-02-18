package servidor.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Estados") 
public class Estados implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="estado_codigo",sequenceName="estado_codigo",allocationSize = 1) 
	@GeneratedValue(generator="estado_codigo", strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Column(name="estado_nome", nullable=false)
	private String nome;

	@Column(name="estado_codibge", nullable=false)
	private int ibge;

	
	
	
	public Estados() {
	
	}
	
	public Estados(int id, String nome, int codibge) {
		this.id=id;
		this.nome=nome;
		this.ibge=codibge;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Estados other = (Estados) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
