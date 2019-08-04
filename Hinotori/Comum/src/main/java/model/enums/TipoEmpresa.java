package model.enums;

public enum TipoEmpresa {

	FISICO("Físico"), JURIDICO("Júridico"), FISICO_JURIDICO("Físico/Júridico");

	private String tipoEmpresa;

	TipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getDescricao() {
		return tipoEmpresa;
	}

}
