package model.enums;

public enum UsuarioNivel {

	USUARIO("Usuário"), ADMINISTRADOR("Administrador"), TOTAL("");

	private String nivel;

	UsuarioNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDescricao() {
		return nivel;
	}
}
