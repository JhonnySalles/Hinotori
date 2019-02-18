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
@Table(name="Paises") 
public class Paises implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="pais_codigo",sequenceName="pais_codigo",allocationSize = 1) 
	@GeneratedValue(generator="pais_codigo", strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(name="pais_nome")
	private String nome;
	@Column(name="pais_codbacen")
	private int bacen;
	
	
	
	public Paises() {
	}

	public Paises(int id) {
		this.id=id;
	}

	public Paises(int id,String nome, int bacen) {
		this.id = id;
		this.nome = nome;
		this.bacen = bacen;
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
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getBacen() {
		return bacen;
	}
	public void setBacen(int bacen) {
		this.bacen = bacen;
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
		Paises other = (Paises) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
