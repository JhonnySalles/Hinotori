package servidor.validations;

import comum.model.constraints.Validadores;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import servidor.entities.Cliente;

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
public class ValidaCliente {

	public static boolean validaCliente(Cliente cliente) throws ExcessaoCadastro {

		if (cliente == null)
			return false;

		validaNome(cliente.getNomeSobrenome());

		validaPessoa(cliente.getTipoPessoa(), cliente.getCnpj(), cliente.getCpf());

		return true;
	}

	public static void validaNome(String nome) throws ExcessaoCadastro {
		if (nome == null || nome.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_NOME_VAZIO);
	}

	public static void validaPessoa(TipoPessoa pessoa, String cnpj, String cpf) throws ExcessaoCadastro {
		switch (pessoa) {
		case FISICO:
			validaCPF(cpf);
			break;
		case JURIDICO:
			validaCPF(cnpj);
			break;
		default:
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_TIPO_CLIENTE_VAZIO);
		}
	}

	public static void validaCPF(String cpf) throws ExcessaoCadastro {
		if (cpf == null || cpf.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CPF);

		if (!Validadores.validaCpfCnpj(cpf))
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CPF);
	}

	public static void validaCNPJ(String cnpj) throws ExcessaoCadastro {
		if (cnpj == null || cnpj.isEmpty())
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CPF);

		if (!Validadores.validaCpfCnpj(cnpj))
			throw new ExcessaoCadastro(Mensagens.CAD_CLI_CNPJ);
	}

}
