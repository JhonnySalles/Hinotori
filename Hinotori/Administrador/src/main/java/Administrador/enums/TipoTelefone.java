package Administrador.enums;

public enum TipoTelefone {

	TELEFONE("Telefone"), CELULAR("Celular"), FAX("Fax");

	private String tipoTelefone;

	TipoTelefone(String tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getDescricao() {
		return tipoTelefone;
	}

}
