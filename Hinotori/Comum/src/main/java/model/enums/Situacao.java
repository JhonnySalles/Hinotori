package model.enums;

public enum Situacao {

	ATIVO("Ativo"), INATIVO("Inativo");

	private String situacao;

	Situacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return situacao;
	}
	
	// Necessário para que a escrita do combo seja Ativo e não ATIVO (nome do enum)
	@Override      
	public String toString(){
	    return this.situacao;
	}  

}
