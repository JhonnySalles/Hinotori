package comum.cep;

import org.talesolutions.cep.CEP;
import org.talesolutions.cep.CEPNaoEncontradoException;
import org.talesolutions.cep.CEPService;
import org.talesolutions.cep.CEPServiceFactory;
import org.talesolutions.cep.CEPServiceFailureException;

import comum.model.exceptions.ExcessaoCep;
import comum.model.messages.Mensagens;

public class CepCorreios {

	private static CEPService buscaCEP = CEPServiceFactory.getCEPService();
	private static String onlyNumber = "^[0-9]$";

	/**
	 * <p>
	 * Cep a ser consultado, é necessário que seja informado apenas números.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 * @throws ExcessaoCep
	 */
	public static CEP getCep(String cep) throws ExcessaoCep {
		if (cep.matches(onlyNumber))
			throw new NumberFormatException("Cep inválido, apenas números são aceito.");

		try {
			return buscaCEP.obtemPorNumeroCEP(cep);
		} catch (CEPNaoEncontradoException e) {
			e.printStackTrace();
			throw new ExcessaoCep(Mensagens.CEP_INVALIDO);
		} catch (CEPServiceFailureException e) {
			e.printStackTrace();
			throw new ExcessaoCep(Mensagens.CEP_SERVICO_INDISPONIVEL);
		}
	}

}
