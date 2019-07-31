package Administrador.entities;

import java.util.Date;

import Administrador.enums.Situacao;
import Administrador.enums.TipoEmpresa;

public class Cliente {
	
	private Integer id;
	private Integer idBairro;
	private String nome;
	private String sobreNome;
	private String dddTelefone;
	private String telefone;
	private String dddCelular;
	private String email;
	private Date dataCadastro;
	private Date ultimaAlteracao;
	private String observacao;
	private Enum<TipoEmpresa> tipo;
	private Enum<Situacao> situacao;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdBairro() {
		return idBairro;
	}
	
	public void setIdBairro(Integer idBairro) {
		this.idBairro = idBairro;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobreNome() {
		return sobreNome;
	}
	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}
	
	public String getDddTelefone() {
		return dddTelefone;
	}
	
	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getDddCelular() {
		return dddCelular;
	}
	
	public void setDddCelular(String dddCelular) {
		this.dddCelular = dddCelular;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Enum<TipoEmpresa> getTipo() {
		return tipo;
	}
	
	public void setTipo(Enum<TipoEmpresa> tipo) {
		this.tipo = tipo;
	}
	
	public Enum<Situacao> getSituacao() {
		return situacao;
	}
	
	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}
	
	public Cliente() {
		
	}
	
	public Cliente(Integer id, Integer idBairro, String nome, String sobreNome, String dddTelefone, String telefone,
			String dddCelular, String email, Date dataCadastro, Date ultimaAlteracao, String observacao, Enum<TipoEmpresa> tipo,
			Enum<Situacao> situacao) {
		super();
		this.id = id;
		this.idBairro = idBairro;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.dddTelefone = dddTelefone;
		this.telefone = telefone;
		this.dddCelular = dddCelular;
		this.email = email;
		this.dataCadastro = dataCadastro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.observacao = observacao;
		this.tipo = tipo;
		this.situacao = situacao;
	}
	
}
