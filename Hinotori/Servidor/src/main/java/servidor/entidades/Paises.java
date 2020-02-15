package servidor.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Paises") 
public class Paises implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pais_codigo",nullable=false, length=11)
	private int pais_codigo;
	@Column(name="pais_nome", nullable=false,length=50)
	private String nome;
	@Column(name="pais_codbacen", length=11)
	private int bacen;

	public Paises() {
	}

	public Paises(int id) {
		this.pais_codigo=id;
	}

	public Paises(int id,String nome, int bacen) {
		this.pais_codigo= id;
		this.nome = nome;
		this.bacen = bacen;
	}

	
	public int getId() {
		return pais_codigo;
	}

	public void setId(int id) {
		this.pais_codigo = id;
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
		result = prime * result + pais_codigo;
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
		if (pais_codigo != other.pais_codigo)
			return false;
		return true;
	}
}
