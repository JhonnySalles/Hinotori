package Administrador.model.entities;

public class PesquisaGenericaDados {
	
	private String id;
	private String descricao;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public PesquisaGenericaDados(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

}
