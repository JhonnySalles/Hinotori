package administrador.model.entities;

import java.io.Serializable;

public class Tamanho implements Serializable {
	
	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = -5050978997046905877L;

	private Long id;
	private String sigla;
	private String descricao;
	private Integer qtdPedacos;
	private Integer qtdSabores;
	//private Enum<Situacao> situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQtdPedacos() {
		return qtdPedacos;
	}

	public void setQtdPedacos(Integer qtdPedacos) {
		this.qtdPedacos = qtdPedacos;
	}

	public Integer getQtdSabores() {
		return qtdSabores;
	}

	public void setQtdSabores(Integer qtdSabores) {
		this.qtdSabores = qtdSabores;
	}

	/*public Enum<Situacao> getSituacao() {
		return situacao;
	}

	public void setSituacao(Enum<Situacao> situacao) {
		this.situacao = situacao;
	}*/

	public Tamanho() {

	}

	public Tamanho(Long id, String sigla, String descricao, Integer qtdPedacos, Integer qtdSabores
			//,	Enum<Situacao> situacao
			) {
		this.id = id;
		this.sigla = sigla;
		this.descricao = descricao;
		this.qtdPedacos = qtdPedacos;
		this.qtdSabores = qtdSabores;
		//this.situacao = situacao;
	}

	// Utilizado para que possamos comparar os objetos por conteúdo e não
	// por referência de ponteiro.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Tamanho other = (Tamanho) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tamanho [id=" + id + ", sigla=" + sigla + ", descricao=" + descricao + ", qtdPedacos=" + qtdPedacos
				+ ", qtdSabores=" + qtdSabores + ", situacao=" //+ situacao 
				+ "]";
	}

}
