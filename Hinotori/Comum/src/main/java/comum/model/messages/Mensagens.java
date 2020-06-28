package comum.model.messages;

/**
 * <p>
 * Classe responssável por conter em apenas um ponto as menssagens apresentada
 * em alertas em todo o sistema.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class Mensagens {

	public final static String LOGIN_ADMINISTRADOR_PADRAO = "HINOTORI";
	
	public final static String BD_ERRO_INSERT = "Erro ao inserir o novo registro.";
	public final static String BD_ERRO_UPDATE = "Erro ao atualizar o novo registro.";
	public final static String BD_ERRO_DELETE = "Erro ao apagar o novo registro.";
	public final static String BD_ERRO_SELECT = "Erro ao pesquisar o registro.";
	public final static String BD_ERRO_SELECT_ALL = "Erro ao carregar os registros.";
	public final static String BD_ERRO_SALVAR_IMAGEM = "Erro ao salvar a imagem.";
	public final static String BD_ERRO_CARREGAR_IMAGEM = "Erro ao carregar a imagens.";
	public final static String BD_ERRO_APAGAR_IMAGEM = "Erro ao apagar a imagem.";
	public final static String BD_ERRO_CARREGAR_ENDERECO = "Erro ao carregar o endereço.";
	public final static String BD_ERRO_SALVAR_ENDERECO = "Erro ao salvar o endereço.";
	public final static String BD_ERRO_CARREGAR_CONTATO = "Erro ao carregar o contato.";
	public final static String BD_ERRO_SALVAR_CONTATO = "Erro ao salvar o contato.";
	public final static String BD_ERRO_USR_VALIDAR_LOGIN = "Erro tentar validar o login.";

	public final static String LOGIN_ERRO_USER_LOGIN = "Necessário selecionar um usuário.";
	public final static String LOGIN_ERRO_USER_LOGIN_NAO_ENCONTRADO = "Login informado não encontrado.";
	public final static String LOGIN_ERRO_USER_SENHA = "Senha incorreta, favor informa-la novamente.";
	public final static String LOGIN_ERRO_USER_SENHA_VAZIA = "Necessário informar uma senha.";

	public final static String CAD_USR_NOME_VAZIO = "Nome não pode ser vazio.";
	public final static String CAD_USR_LOGIN_VAZIO = "Loguin não pode ser vazio.";
	public final static String CAD_USR_LOGIN_UTILLIZADO = "Loguin já cadastrado por outro usuário.";
	public final static String CAD_USR_SENHA_VAZIA = "Senha não pode ser vazia.";

}
