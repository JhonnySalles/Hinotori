package model.enums;

public enum TipoCliente {
	FISICO("Físico"), JURIDICO("Júridico");

	private String tipoCliente;

	TipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getDescricao() {
		return tipoCliente;
	}
}
