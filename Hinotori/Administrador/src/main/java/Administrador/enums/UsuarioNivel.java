package Administrador.enums;

public enum UsuarioNivel {

	USUARIO("Usuário"), ADMINISTRADOR("Administrador");

	private String nivel;

	UsuarioNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDescricao() {
		return nivel;
	}
}
