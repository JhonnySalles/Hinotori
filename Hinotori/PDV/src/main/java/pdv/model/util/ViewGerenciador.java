package pdv.model.util;

import java.io.IOException;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public class ViewGerenciador {

	private static ViewGerenciador instancia;
	private static final HashMap<String, Node> TELA_PRE_CARREGADA = new HashMap<>();

	public static ObservableList<String> stylesheets = FXCollections.observableArrayList("");
	private static String[] PRE_CARREGAMENTO = { "/pdv/view/cadastros/CadCliente.fxml",
			"/pdv/view/cadastros/CadEmpresa.fxml", "/pdv/view/cadastros/CadUsuario.fxml",
			"/pdv/view/cadastros/CadProduto.fxml" };

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
	public static void preCarregamentoTelas() {
		TELA_PRE_CARREGADA.clear();
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				for (int i = 0; i < PRE_CARREGAMENTO.length; i++) {
					if (!PRE_CARREGAMENTO[i].isEmpty()) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource(PRE_CARREGAMENTO[i]));
						Parent novaTela = loader.load();
						TELA_PRE_CARREGADA.put(PRE_CARREGAMENTO[i], novaTela);
					}
				}
				return null;
			}
		};

		Thread thread = new Thread(preCarregamentoTask);
		thread.start();
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
	private static void preCarregamentoTelas(String absoluteName) {
		Task<Void> preCarregamentoTask = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Parent novaTela = loader.load();
				TELA_PRE_CARREGADA.put(absoluteName, novaTela);
				return null;
			}
		};

		Thread thread = new Thread(preCarregamentoTask);
		thread.start();
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
	public static Node getTelaPreCarregada(String absoluteName) {
		if (TELA_PRE_CARREGADA.containsKey(absoluteName)) {
			Node telaPre = TELA_PRE_CARREGADA.get(absoluteName);
			preCarregamentoTelas(absoluteName);
			return telaPre;

		} else
			return null;
	}

	public static Boolean verificaTelaCarregada(String absoluteName) {
		return TELA_PRE_CARREGADA.containsKey(absoluteName);
	}

	public static void carregaCss() {
		stylesheets.addAll(ViewGerenciador.class.getResource("/pdv/resources/css/Paleta_Cores.css").toExternalForm(),
				ViewGerenciador.class.getResource("/pdv/resources/css/White_Cadastros.css").toExternalForm(),
				ViewGerenciador.class.getResource("/pdv/resources/css/White_Dashboard.css").toExternalForm(),
				ViewGerenciador.class.getResource("/pdv/resources/css/White_Dashboard_Botoes.css").toExternalForm(),
				ViewGerenciador.class.getResource("/pdv/resources/css/White_Dashboard_Graficos.css").toExternalForm(),
				ViewGerenciador.class.getResource("/pdv/resources/css/White_Frame.css").toExternalForm());
	}

}
