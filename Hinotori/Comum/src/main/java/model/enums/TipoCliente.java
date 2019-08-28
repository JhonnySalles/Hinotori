package model.enums;

public enum TipoCliente {
	FISICO("Físico"), JURIDICO("Júridico"), FISICO_JURIDICO("Físico/Júridico");

	private String tipoCliente;

	TipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getDescricao() {
		return tipoCliente;
	}
	
	@Override      
	public String toString(){
	    return this.tipoCliente;
	}  
}
