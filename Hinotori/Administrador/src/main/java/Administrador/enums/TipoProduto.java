package Administrador.enums;

public enum TipoProduto {

	PRODUCAO("Produção"), MATERIAPRIMA("Materia prima"), SERVICO("Serviço");

	private String tipoProduto;
	private static TipoProduto resultado;

	TipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getDescricao() {
		return tipoProduto;
	}

	public static TipoProduto getEnum(String descricao) {
		switch (descricao) {
		case "MATERIAPRIMA":
			resultado = MATERIAPRIMA;
			break;
		case "PRODUCAO":
			resultado = PRODUCAO;
			break;
		case "SERVICO":
			resultado = SERVICO;
			break;
		default:
			resultado = null;
		}
		return resultado;
	}
}
