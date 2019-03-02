package Servidor.entidades;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="Clientes_Fisica")
public class Clientes_Fisica extends Clientes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="clifis_cpf", nullable=false, length=11) 
	private int cpf;
	
	@Column(name="clifis_rg", length=15)
	private String rg;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "clifis_dtNascimento", nullable = false)
	private Calendar dtNascimento;
	
	@Column(name="clifis_sexo", nullable=false,length=1)
	private char sexo;
	
	@Column(name="clifis_naturalidade", nullable=false,length=100)
	private String naturalidade;
	
	@Column(name="clifis_EstadoCivil", nullable=false,length=1)
	private char estadoCivil;	
	
	@Column(name="clifis_pai",length=45)
	private String pai;
	
	@Column(name="clifis_mae",length=45)
	private String mae;
	
	@Column(name="clifis_produtorRural",nullable=false,length=1)
	private boolean cadProdutor;

	
	
	public Clientes_Fisica() {
	
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Calendar getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Calendar dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public char getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(char estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public boolean isCadProdutor() {
		return cadProdutor;
	}

	public void setCadProdutor(boolean cadProdutor) {
		this.cadProdutor = cadProdutor;
	}
	
	
}
