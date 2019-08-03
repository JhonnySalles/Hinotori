package Administrador.enums;

public enum TipoEmpresa {

	FISICO("Físico"), JURIDICO("Júridico"), FISICO_JURIDICO("Físico/Júridico");

	private String tipoEmpresa;
	private static TipoEmpresa resultado;

	TipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getDescricao() {
		return tipoEmpresa;
	}

	public static TipoEmpresa getEnum(String descricao) {
		switch (descricao) {
		case "FISICA JURIDICA":
			resultado = FISICO_JURIDICO;
			break;
		case "FISICO":
			resultado = FISICO;
			break;
		case "JURIDICO":
			resultado = JURIDICO;
			break;
		default:
			resultado = null;
		}
		return resultado;
	}

}
