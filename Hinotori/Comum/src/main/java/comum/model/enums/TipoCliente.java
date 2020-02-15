package comum.model.enums;

/**
 * <p>Enuns de tipos de clientes ou fornecedores.</p>
 * 
 * <p><b>CLIENTE, FORNECEDOR, AMBOS</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoCliente {
	
	CLIENTE("Cliente"), FORNECEDOR("Fornecedor"), AMBOS("Ambos");

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
