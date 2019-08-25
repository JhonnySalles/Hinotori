package model.enums;

public enum TipoProduto {

	PRODUCAO("Produção"), MATERIAPRIMA("Materia prima"), SERVICO("Serviço");

	private String tipoProduto;

	TipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getDescricao() {
		return tipoProduto;
	}
	
	@Override      
	public String toString(){
	    return this.tipoProduto;
	} 
}
