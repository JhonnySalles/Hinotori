package Administrador.entities;

import java.util.Date;

import Administrador.enums.Situacao;
import Administrador.enums.UsuarioNivel;

public class Usuario {

	private Integer id;
	private Integer idEmpresa;
	private String login;
	private String nome;
	private String senha;
	private Enum<Situacao> situacao;
	private Date dataCadastro;
	private String observacao;
	// private Image imagem;
	private Enum<UsuarioNivel> nivel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
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

	public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<UsuarioNivel> getNivel() {
		return nivel;
	}

	public void setNivel(Enum<UsuarioNivel> nivel) {
		this.nivel = nivel;
	}

	public Usuario() {

	}

	public Usuario(Integer id, Integer idEmpresa, String login, String nome, String senha, Enum<Situacao> situacao,
			Date dataCadastro, String observacao, Enum<UsuarioNivel> nivel) {
		this.id = id;
		this.idEmpresa = idEmpresa;
		this.login = login;
		this.nome = nome;
		this.senha = senha;
		this.situacao = situacao;
		this.dataCadastro = dataCadastro;
		this.observacao = observacao;
		this.nivel = nivel;
	}

}
