package Administrador.enums;

public enum Situacao {

	ATIVO("Ativo"), INATIVO("Inativo"), EXCLUIDO("Excluído");

	private String situacao;
	private static Situacao resultado;

	Situacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return situacao;
	}

	public static Situacao getEnum(String descricao) {
		switch (descricao) {
		case "ATIVO":
			resultado = ATIVO;
			break;
		case "INATIVO":
			resultado = INATIVO;
			break;
		case "EXCLUIDO":
			resultado = EXCLUIDO;
			break;
		default:
			resultado = null;
		}
		return resultado;
	}

}
