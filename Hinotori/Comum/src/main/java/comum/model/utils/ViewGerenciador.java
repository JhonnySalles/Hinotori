package comum.model.utils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * <p>
 * Classe responssável por executar as funções de carregamento das telas,
 * retornará a tela quando já previamente carregada ou então irá executar como
 * tarefas o carregamento da tela solicitada. Também irá ser responsável por
 * manter uma cópia em memória das telas previamente carregada ou que foram
 * carregada por ele para melhor desempenho do sistema como um todo.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class ViewGerenciador {

	private static ViewGerenciador instancia;
	private static final HashMap<URL, Node> TELA_PRE_CARREGADA = new HashMap<>();

	public static ObservableList<String> stylesheets = FXCollections.observableArrayList("");
	private static URL[] CAMINHO_TELAS_PRE_CARREGAMENTO = {};

	public static ViewGerenciador getInstance() {
		if (instancia == null) {
			instancia = new ViewGerenciador();
		}
		return instancia;
	}

	/**
	 * <p>
	 * Função para fazer o pré carregamento das telas através de uma task, no qual é
	 * chamada assim que o dashboard inicia.
	 * </p>
	 * <p>
	 * Utiliza o <b>Array <i>telasPreCarregamento</i></b> para obter o nome dos
	 * <b>fxml</b> que devem ser carregadas.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private static void preCarregamentoTelas() {
		TELA_PRE_CARREGADA.clear();
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				for (int i = 0; i < CAMINHO_TELAS_PRE_CARREGAMENTO.length; i++) {
					if (!CAMINHO_TELAS_PRE_CARREGAMENTO[i].getPath().isEmpty()) {
						FXMLLoader loader = new FXMLLoader(CAMINHO_TELAS_PRE_CARREGAMENTO[i]);
						Parent novaTela = loader.load();
						TELA_PRE_CARREGADA.put(CAMINHO_TELAS_PRE_CARREGAMENTO[i], novaTela);
					}
				}
				return null;
			}
		};

		Thread preCarregamento = new Thread(preCarregamentoTask);
		preCarregamento.start();
	}

	/**
	 * <p>
	 * Função para fazer o pré carregamento de outra tela já carregada com task,
	 * para que ao acessar novamente após fechar a tela, não carregue todos os dados
	 * na tela.</b>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @author Jhonny de Salles Noschang
	 */
	private static void carregamentoTela(URL absoluteName) {
		Task<Void> carregamentoTelaTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				FXMLLoader loader = new FXMLLoader(absoluteName);
				Parent novaTela = loader.load();
				TELA_PRE_CARREGADA.put(absoluteName, novaTela);
				return null;
			}
		};

		Thread tela = new Thread(carregamentoTelaTask);
		tela.start();
	}

	/**
	 * <p>
	 * Função verificar se a tela já foi pré carregada pela função
	 * <b><i>preCarregamentoTelas</i></b>.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @return Retorna o <b>Parent</b> da tela já carregada préviamente.
	 * @author Jhonny de Salles Noschang
	 */
	public static Node getTelaPreCarregada(URL absoluteName) {
		if (TELA_PRE_CARREGADA.containsKey(absoluteName)) {
			Node telaPre = TELA_PRE_CARREGADA.get(absoluteName);
			carregamentoTela(absoluteName);
			return telaPre;

		} else
			return null;
	}

	public static Boolean verificaTelaCarregada(URL absoluteName) {
		return TELA_PRE_CARREGADA.containsKey(absoluteName);
	}

	/**
	 * <p>
	 * Função para adicionar quais telas serão carregada automaticamente.
	 * </p>
	 * 
	 * @param listaTelas Endereços em uma <b>Lista de URL</b> para carregamento
	 *                   automático.
	 * @author Jhonny de Salles Noschang
	 */
	public static void setCaminhoTelasPreCarregamento(URL[] listaTelas) {
		CAMINHO_TELAS_PRE_CARREGAMENTO = listaTelas;
		preCarregamentoTelas();
	}

	public static URL[] getCaminhoTelasPreCarregamento() {
		return CAMINHO_TELAS_PRE_CARREGAMENTO;
	}

	public static void carregaCss(String[] listaCss) {
		stylesheets.addAll(listaCss);
	}

}
