package comum.model.enums;

/**
 * <p>Enuns de tipos de pessoa (Físico/Jurídico).</p>
 * 
 * <p><b>FISICO, JURIDICO, FISICO_JURIDICO</b></p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum PessoaTipo {
	
	FISICO("Físico"), JURIDICO("Júridico"), AMBOS("Ambos");

	private String pessoaTipo;

	PessoaTipo(String pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}

	public String getDescricao() {
		return pessoaTipo;
	}
	
	@Override      
	public String toString(){
	    return this.pessoaTipo;
	}  
}
