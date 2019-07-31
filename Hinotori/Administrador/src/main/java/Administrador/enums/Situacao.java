package Administrador.enums;

public enum Situacao {

	ATIVO("Ativo"),
	INATIVO("Inativo"),
	EXCLUIDO("Excluído");
	
	private String situacao;
	
	Situacao(String situacao){
		this.situacao = situacao;
	}

	public String getDescricao() {
		return situacao;
	}
	
}
