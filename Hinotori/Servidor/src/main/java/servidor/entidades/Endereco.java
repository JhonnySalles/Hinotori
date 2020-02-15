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


@SuppressWarnings("deprecation")
@Entity
@Table(name="Enderecos")
public class Endereco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="end_codigo",nullable=false,length=11)
	private Long codigo;
	
	@Column(name="end_descricao", nullable=false,length=255)
	private String nome;
	
	@Column(name="end_numero", length=20)
	private String numero;
	
	@Column(name="end_complemento", length=30)
	private String complemento;
	
	@Column(name = "end_cep", length = 9, nullable = false)        
    private String cep;
	
	@Column(name = "end_bairro", length = 70, nullable = false)        
    private String bairro;
	
    @Column(name = "end_referencia", length = 80)        
    private String referencia;
	
    @ManyToOne
    @JoinColumn(name = "cli_codigo", referencedColumnName="cli_codigo",nullable = false)
    @ForeignKey(name = "Enderecos_Clientes")
    private  Clientes cliente;
    
    @ManyToOne
    @JoinColumn(name = "tipoend_codigo", referencedColumnName = "tipoend_codigo", nullable = false)
    @ForeignKey(name = "Enderecos_TipoEnderecos")    
    private TipoEndereco tipoEndereco;
    
    @ManyToOne
    @JoinColumn(name = "cid_codigo", referencedColumnName = "cid_codigo", nullable = false)
    @ForeignKey(name = "Enderecos_Cidades")        
    private Cidades cidade;

    
    
	public Endereco() {
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public Cidades getCidade() {
		return cidade;
	}

	public void setCidade(Cidades cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Endereco other = (Endereco) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
    
    
}
