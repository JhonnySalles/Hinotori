package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.Endereco;

/**
 * <p>
 * Classe responssável por fazer a validação da classe cliente.
 * </p>
 * 
 * <p>
 * Ficou definido estar propagando uma excessão para quando o campo a ser
 * validado for inválido, pois pode-se estar tratando com mensagens de aviso ou
 * colorindo a borda do campo correspondente.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public class ValidaEndereco {

	public static boolean validaEndereco(Endereco endereco) throws ExcessaoCadastro {

		if (endereco == null)
			return false;

		validaEndereco(endereco.getEndereco());

		validaCEP(endereco.getCep());

		// validaCidade(endereco.getCidade());

		return true;
	}

	public static void validaEndereco(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_ENDERECO_ENDERECO_VAZIO);
	}

	public static void validaCEP(String cep) throws ExcessaoCadastro {
		if (cep != null && !cep.isEmpty())
			if (!Validadores.validaCep(cep))
				throw new ExcessaoCadastro(Mensagens.CAD_ENDERECO_CEP);
	}

	/*
	 * public static void validaCidade(String cidade) throws ExcessaoCadastro { if
	 * (cidade == null || cidade.isEmpty()) throw new
	 * ExcessaoCadastro(Mensagens.CAD_CLI_CNPJ); }
	 */

}
