package servidor.entidades;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Clientes_juridico")
public class Clientes_Juridico extends Clientes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="clijur_cnpj", nullable=false, length=11) 
	private int cnpj;
	
	@Column(name="clijur_ie", length=25)
	private String ie;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "clijur_dtconstituicao", nullable = false)
	private Calendar dtconstituicao;
	
	@Column(name="clijur_im", length=25)
	private String im;
	
	@Column(name="clijur_simples", length=1)
	private boolean simples;
	
	@Column(name="clijur_icms", length=1)
	private boolean icms;
	
	@Column(name="clijur_enviaim", length=1)
	private boolean enviaim;
		
	public Clientes_Juridico() {
	
	}

	public int getCnpj() {
		return cnpj;
	}

	public void setCnpj(int cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public Calendar getDtconstituicao() {
		return dtconstituicao;
	}

	public void setDtconstituicao(Calendar dtconstituicao) {
		this.dtconstituicao = dtconstituicao;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public boolean isSimples() {
		return simples;
	}

	public void setSimples(boolean simples) {
		this.simples = simples;
	}

	public boolean isIcms() {
		return icms;
	}

	public void setIcms(boolean icms) {
		this.icms = icms;
	}

	public boolean isEnviaim() {
		return enviaim;
	}

	public void setEnviaim(boolean enviaim) {
		this.enviaim = enviaim;
	}
	
	

}
