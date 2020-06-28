package servidor.validations;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import comum.model.encode.DecodeHash;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoLogin;
import comum.model.messages.Mensagens;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Usuario;

/**
 * <p>
 * Classe responssável por fazer a validação da classe usuária.
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
public class ValidaLogin {

	private static UsuarioServices usuarioService;
	private static Usuario localizado;

	public static Usuario validaLogin(Usuario usuario) throws ExcessaoLogin, ExcessaoBd {
		localizado = null;
		if (usuario.getLogin().isEmpty())
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_LOGIN);

		if (usuario.getSenha().isEmpty())
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA_VAZIA);

		validaSenha(usuario);

		return localizado;
	}

	public static boolean validaSenha(Usuario usuario) throws ExcessaoBd, ExcessaoLogin {

		if (usuarioService == null)
			usuarioService = new UsuarioServices();

		Usuario localizado = usuarioService.pesquisar(usuario.getLogin());

		if (localizado == null)
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_LOGIN_NAO_ENCONTRADO);

		try {
			usuario.setSenha(DecodeHash.DecodePassword(usuario.getSenha()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA);
		}

		if (!usuario.getSenha().equalsIgnoreCase(localizado.getSenha()))
			throw new ExcessaoLogin(Mensagens.LOGIN_ERRO_USER_SENHA);

		return true;
	}

}
