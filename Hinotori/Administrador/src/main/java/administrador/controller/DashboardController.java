package administrador.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import administrador.App;
import administrador.model.util.ViewGerenciador;
import animatefx.animation.Pulse;
import comum.model.animation.DoubleTransition;
import comum.model.animation.TelaAnimation;
import comum.model.entities.Conexao;
import comum.model.mysql.ConexaoMysql;
import comum.model.notification.Alertas;
import comum.model.notification.Notificacoes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DashboardController implements Initializable {

	private final static Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

	private static Conexao conexao;
	private Map<String, Tab> abasAbertas = new HashMap<>(); // Irá mapear as abas abertas.

	private final static DropShadow efeitoPainelDetalhe = new DropShadow();

	@FXML
	private AnchorPane apGlobal;

	@FXML
	private StackPane rootStackPane;

	@FXML
	private SplitPane splPane;
	private DoubleProperty dPropSplPane;
	private DoubleTransition dTransSplPane;
	private Timeline tmLineAbrir;

	@FXML
	private AnchorPane apBotoes;

	@FXML
	private AnchorPane apBotoesDetalhes;
	private TranslateTransition tTransApBotoesDetalhes;

	@FXML
	private VBox vbBotoesDetalhes;

	@FXML
	private JFXTabPane tbPaneAbas;

	@FXML
	private Tab tbDashBoardGraficos;

	@FXML
	private AnchorPane apDashBoardGraficos;

	@FXML
	private JFXScrollPane scPaneDashGraficos;

	@FXML
	private HBox hBoxTopo;

	@FXML
	private JFXHamburger btnBurgerBotao;
	private HamburgerBackArrowBasicTransition btnBurgerTask;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnCadastros;

	@FXML
	private JFXButton btnLancamentos;

	@FXML
	private JFXButton btnPesquisas;

	@FXML
	private JFXButton btnConfiguracoes;

	@FXML
	private JFXButton btnEmpresa;

	@FXML
	private JFXButton btnUsuario;

	@FXML
	private JFXButton btnBd;

	@FXML
	private Tooltip tootBd;

	@FXML
	private ImageView imgBd;

	@FXML
	private void onBtnCadastrosAction() {
		loadView("/pdv/view/cadastros/Cadastros.fxml");
	}

	@FXML
	private void onBtnLancamentosAction() {

	}

	@FXML
	private void onBtnPesquisasAction() {
		loadView("/pdv/view/pesquisas/Pesquisas.fxml");
	}

	@FXML
	private void onBtnConfiguracaoAction() {

	}

	@FXML
	private void onMouseEnterApBotoes() {
		tmLineAbrir.play();
	}

	@FXML
	private void onMouseExitApBotoes() {
		tmLineAbrir.stop();
	}

	@FXML
	private void closeBtnDetalhes() {
		fecharBotoesDetalhe();
	}

	public void atualizaTabPane() {
		tbPaneAbas.requestLayout();
	}

	/**
	 * <p>
	 * Função fazer a abertura do bloco de botões mostrando a sua descrição.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void apBotoesMovDireita() {
		// Necessário desativar a animação, pois causa but na tela ao mover a linha de
		// divisória.
		tbPaneAbas.setDisableAnimation(true);
		dTransSplPane.stop();
		apBotoes.setMaxWidth(150);
		dPropSplPane = splPane.getDividers().get(0).positionProperty();
		dTransSplPane = new DoubleTransition(Duration.millis(1400), dPropSplPane);
		dTransSplPane.setToValue(1);
		dTransSplPane.setOnFinished(e -> apBotoes.setMinWidth(150));
		dTransSplPane.play();
	}

	/**
	 * <p>
	 * Função fazer o fechamento do bloco de botões escondendo a sua descrição.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void apBotoesMovEsquerda() {
		dTransSplPane.stop();
		apBotoes.setMinWidth(50);
		dPropSplPane = splPane.getDividers().get(0).positionProperty();
		dTransSplPane = new DoubleTransition(Duration.millis(600), dPropSplPane);
		dTransSplPane.setToValue(0);
		dTransSplPane.setOnFinished(e -> {
			apBotoes.setMaxWidth(50);
			tbPaneAbas.setDisableAnimation(false);
		});
		dTransSplPane.play();
	}

	/**
	 * <p>
	 * Função para abrir o split pane que possui botões de detalhes (botões
	 * internos).
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void abrirBotoesDetalhe() {
		Timeline tm = new Timeline(new KeyFrame(Duration.millis(100), ae -> {
			tTransApBotoesDetalhes.stop();
			btnBurgerTask.stop();
			tTransApBotoesDetalhes.setToX(0);
			btnBurgerTask.setRate(1);
			tTransApBotoesDetalhes.setOnFinished(efeito -> apBotoesDetalhes.setEffect(efeitoPainelDetalhe));
			btnBurgerTask.setOnFinished(alternar -> btnBurgerTask.setRate(1));
			tTransApBotoesDetalhes.play();
			btnBurgerTask.play();
		}));
		tm.play();
	}

	/**
	 * <p>
	 * Função para fechar o split pane que possui botões de detalhes (botões
	 * internos).
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void fecharBotoesDetalhe() {
		if (apBotoesDetalhes.getTranslateX() == 0) {
			/*
			 * Necessário utilizar para não ficar bugado burguer botão ao fechar e mostrando
			 * a flecha
			 */
			Timeline tm = new Timeline(new KeyFrame(Duration.millis(100), ae -> {
				tTransApBotoesDetalhes.stop();
				btnBurgerTask.stop();
				tTransApBotoesDetalhes.setToX(-(apBotoesDetalhes.getWidth() + 5));
				btnBurgerTask.setRate(-1);
				apBotoesDetalhes.setEffect(null);
				btnBurgerTask.setOnFinished(alternar -> btnBurgerTask.setRate(-1));
				tTransApBotoesDetalhes.play();
				btnBurgerTask.play();
			}));
			tm.play();
		}
		tmLineAbrir.stop();
		apBotoesMovEsquerda();
	}

	/**
	 * <p>
	 * Função para fazer o do pane do botões de detalhe (botões internos), no split
	 * pane.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @author Jhonny de Salles Noschang
	 */
	private synchronized void loadView(String absoluteName) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		try {
			VBox vbPainelBotoes = loader.load();
			vbBotoesDetalhes.getChildren().clear();
			vbBotoesDetalhes.getChildren().add(vbPainelBotoes);
			vbBotoesDetalhes.setFillWidth(true);
			vbBotoesDetalhes.alignmentProperty().set(Pos.TOP_LEFT);
			if (apBotoesDetalhes.getTranslateX() != 0)
				abrirBotoesDetalhe();
			else
				new Pulse(apBotoesDetalhes).play();

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar a tela: " + absoluteName + "}", e);
		}
	}

	// Redimenciona a imagem para o tab pane
	private static ImageView buildImage(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		Image img = new Image(inputStream);
		ImageView imageView = new ImageView();

		imageView.setFitHeight(16);
		imageView.setFitWidth(16);
		imageView.setImage(img);
		return imageView;
	}

	// Comando synchronized irá fazer com que a tela carregue por primeiro, não
	// esperando outros processamentos que possam ter.
	/**
	 * <p>
	 * Função para fazer o carregamento da tela e insere na aba ou mostrar se a tela
	 * já está mapeada.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param tela         Nome que será apresentada na aba.
	 * @param icon         Icone opcional, no qual será inserido na aba. Necessário
	 *                     o caminho após o comando <b><i>getPath</i><b>.
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized void loadView(String absoluteName, String tela, String icon) {
		if (tbPaneAbas == null) {
			tbPaneAbas = new JFXTabPane();
		}

		if (abasAbertas.containsKey(absoluteName)) {
			// Caso ja foi mapeada a aba, foco nela.
			tbPaneAbas.getSelectionModel().select(abasAbertas.get(absoluteName));
		} else {

			// Caso a tela já foi previamente carregada, carrega ela.
			if (ViewGerenciador.verificaTelaCarregada(absoluteName)) {
				Tab aba = new Tab(tela);

				if (icon != "") { // Irá chamar a função para redimencionar e colocar a imagem.
					// Coloca o caminho do arquivo com o caminho da imagem.
					File initialFile = new File(getClass().getResource("").getPath() + icon);
					try {
						aba.setGraphic(buildImage(new FileInputStream(initialFile)));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						LOGGER.log(Level.SEVERE, "{Erro ao carregar a imagem da aba: " + initialFile + "}", e1);
					}
				}

				aba.setContent(ViewGerenciador.getTelaPreCarregada(absoluteName));
				tbPaneAbas.getTabs().add(aba);

				// Irá mapear a aba que abriu para futuramente localizar e focar nela.
				abasAbertas.put(absoluteName, aba);

				// Foco na ultima tabela adicionada.
				tbPaneAbas.getSelectionModel().select(tbPaneAbas.getTabs().size() - 1);

				// Remove do mapeamento se fechou.
				aba.setOnClosed(removMap -> abasAbertas.remove(absoluteName));
			} else {

				Task<Node> loadTask = new Task<Node>() {
					@Override
					public Node call() throws IOException, InterruptedException {
						FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
						return loader.load();
					}
				};

				loadTask.setOnSucceeded(e -> {
					Tab aba = new Tab(tela);

					if (icon != "") { // Irá chamar a função para redimencionar e colocar a imagem.
						// Coloca o caminho do arquivo com o caminho da imagem.
						File initialFile = new File(getClass().getResource("").getPath() + icon);
						try {
							aba.setGraphic(buildImage(new FileInputStream(initialFile)));
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
							LOGGER.log(Level.SEVERE, "{Erro ao carregar a imagem da aba: " + initialFile + "}", e1);
						}
					}

					aba.setContent(loadTask.getValue());
					tbPaneAbas.getTabs().add(aba);

					// Irá mapear a aba que abriu para futuramente localizar e focar nela.
					abasAbertas.put(absoluteName, aba);

					// Foco na ultima tabela adicionada.
					tbPaneAbas.getSelectionModel().select(tbPaneAbas.getTabs().size() - 1);

					// Remove do mapeamento se fechou.
					aba.setOnClosed(removMap -> abasAbertas.remove(absoluteName));
				});

				loadTask.setOnFailed(errorTask -> loadTask.getException().printStackTrace());

				Thread thread = new Thread(loadTask);
				thread.start();

			}
		}
		fecharBotoesDetalhe();
	}

	/**
	 * <p>
	 * Função estatica apenas para fazer o carregamento de um panel em cima de
	 * outro, com a animação de movimentação dos panels para a esquerda e fechamento
	 * para a direita.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @param spRoot       <b>StackPane</b> da tela que irá ser adicionado a nova
	 *                     tela acima.
	 * @return Retorna um objeto no formato do controlador da tela aberta, caso
	 *         apresente erro irá retornar null.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Object loadView(String absoluteName, StackPane spRoot) {
		FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource(absoluteName));
		try {
			Node rootCima = loader.load();
			spRoot.getChildren().add(rootCima);
			new TelaAnimation().abrirPane(spRoot);
			// Necessário por um bug na tela ao carregar ela.
			App.getMainController().atualizaTabPane();
			return loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar a tela: " + absoluteName + "}", e);
		}
		return null;
	}

	/**
	 * <p>
	 * Chama o método de verificar conexão animando o icone do dashboard, também
	 * obtem os dados da conexão.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void verificaConexao() {
		conexao = ConexaoMysql.testaConexaoMySQL(imgBd, tootBd);
	}

	public Conexao getConexao() {
		return conexao;
	}

	private synchronized void prepareSlideMenuAnimation() {
		/* Função para abrir automaticamente depois de 2 segundos */
		tmLineAbrir = new Timeline(new KeyFrame(Duration.millis(2000), ae -> apBotoesMovDireita()));

		btnBurgerTask = new HamburgerBackArrowBasicTransition(btnBurgerBotao);
		btnBurgerTask.setRate(-1);
		// Evento para abrir e fechar via programacao.
		btnBurgerBotao.addEventHandler(ActionEvent.ACTION, e -> {
			if (apBotoesDetalhes.getTranslateX() != 0) {
				abrirBotoesDetalhe();
			} else {
				fecharBotoesDetalhe();
			}
		});

		// Evento para o botao do mouse.
		btnBurgerBotao.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			btnBurgerBotao.fireEvent(new ActionEvent());
		});
	}

	// Irá inicializar a parte do dashboard grafico
	private void inicializaGraficos() {

		JFXButton btnAtualiza = new JFXButton("");
		SVGGlyph glpIcone = new SVGGlyph(0, "FULLSCREEN",
				"M356.577,20.47C244.17-27.11,114.517,10.696,45.89,109.82L23.99,93.4C14.28,86.06,0,92.97,0,105.4v150    c0,11.04,11.57,18.37,21.71,13.42l120-60c9.91-4.97,11.22-18.71,2.28-25.42l-25.6-19.21c48.82-74.16,140.467-87.34,203.137-60.84    c134.21,56.85,134.01,248.701-0.02,305.421c-85.863,36.352-183.214-4.356-218.557-87.82c-9.67-22.91-36.04-33.62-58.95-23.93    c-22.95,9.67-33.62,36.12-23.94,58.94c54.26,128.62,205.407,191.23,336.488,135.69C563.958,404.081,563.667,107.95,356.577,20.47z",
				Color.WHITE);
		glpIcone.setSize(30, 30);
		btnAtualiza.setGraphic(glpIcone);
		btnAtualiza.setRipplerFill(Color.WHITE);
		scPaneDashGraficos.getTopBar().getChildren().add(btnAtualiza);
		StackPane.setAlignment(btnAtualiza, Pos.CENTER_LEFT);
		StackPane.setMargin(btnAtualiza, new Insets(20, 0, 0, 10));

		try {
			FXMLLoader loaderMeio = new FXMLLoader(
					getClass().getResource("/pdv/view/metricas/DashBoardGraficosTituloMeio.fxml"));
			StackPane spMeio = loaderMeio.load();
			scPaneDashGraficos.getMidBar().getChildren().add(spMeio);
			StackPane.setMargin(spMeio, new Insets(0, 0, 0, 80));
			StackPane.setAlignment(spMeio, Pos.CENTER_LEFT);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}

		try {
			FXMLLoader loaderBase = new FXMLLoader(
					getClass().getResource("/pdv/view/metricas/DashBoardGraficosTituloBase.fxml"));
			StackPane spBase = loaderBase.load();
			scPaneDashGraficos.getBottomBar().getChildren().add(spBase);
			StackPane.setMargin(spBase, new Insets(0, 0, 0, 80));
			StackPane.setAlignment(spBase, Pos.CENTER_LEFT);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}

		try {
			FXMLLoader loaderConteudo = new FXMLLoader(
					getClass().getResource("/pdv/view/metricas/DashBoardGraficos.fxml"));
			StackPane spConteudo = loaderConteudo.load();
			spConteudo.setPadding(new Insets(24));
			scPaneDashGraficos.setContent(spConteudo);
			StackPane.setAlignment(spConteudo, Pos.TOP_CENTER);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar os gráficos do dashboard}", e);
		}
	}

	private DashboardController setEfeito() {
		efeitoPainelDetalhe.setWidth(21.0);
		efeitoPainelDetalhe.setWidth(21.0);
		efeitoPainelDetalhe.setHeight(21.0);
		efeitoPainelDetalhe.setRadius(10.0);
		efeitoPainelDetalhe.setRadius(10.0);
		efeitoPainelDetalhe.setOffsetX(2.0);
		efeitoPainelDetalhe.setOffsetY(2.0);
		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		setEfeito().verificaConexao();
		prepareSlideMenuAnimation();
		inicializaGraficos();

		SplitPane.setResizableWithParent(splPane, false);
		dPropSplPane = splPane.getDividers().get(0).positionProperty();
		dTransSplPane = new DoubleTransition(Duration.millis(600), dPropSplPane);

		tTransApBotoesDetalhes = new TranslateTransition(new Duration(350), apBotoesDetalhes);

		/* Popup de descricao dos botoes */
		btnCadastros.setTooltip(new Tooltip("Cadastros"));
		btnLancamentos.setTooltip(new Tooltip("Lançamentos"));
		btnPesquisas.setTooltip(new Tooltip("Pesquisas"));
		btnConfiguracoes.setTooltip(new Tooltip("Configurações"));

		/* Setando as variáveis para o alerta padrão. */
		Alertas.setNodeBlur(rootStackPane);
		Alertas.setNodeBlur(splPane);
		Notificacoes.setRootStackPane(apGlobal);

		rootStackPane.setCache(true);
		rootStackPane.setCacheHint(CacheHint.SPEED);
		apGlobal.setCache(true);
		apGlobal.setCacheHint(CacheHint.SPEED);
	}

}
