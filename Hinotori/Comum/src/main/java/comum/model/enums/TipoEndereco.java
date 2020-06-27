package comum.model.enums;

/**
 * <p>
 * Enuns de tipos de endereços.
 * </p>
 * 
 * <p>
 * <b>COMERCIAL, RESIDENCIAL, COBRANCA, ENTREGA E OUTROS</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum TipoEndereco {

	COMERCIAL("Comercial"), RESIDENCIAL("Residencial"), COBRANCA("Cobrança"), ENTREGA("Entrega"), OUTROS("Outros");

	private String tipoEndereco;

	TipoEndereco(String tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getDescricao() {
		return tipoEndereco;
	}

	@Override
	public String toString() {
		return this.tipoEndereco;
	}

}
