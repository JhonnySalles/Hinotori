package comum.form;

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

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import animatefx.animation.Pulse;
import comum.model.animation.DoubleTransition;
import comum.model.utils.ViewGerenciador;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * <p>
 * Classe abstrata que irá conter os métodos padrões para a criação de um
 * dashboard e sua reutilização em vários módulos.
 * 
 * Irá conter funções como a criação das abas, abertura de telas sobreposta e
 * dialogos como suas movimentações e animações.
 * 
 * Por padrão a tela já implementa o método initializable do Javafx, fazendo a
 * chamada da função inicializa que servirá para propagar para as classes
 * herdadas a mesma função. Será necessário manter a função no form padrão, pois
 * alguns métodos requer o prévio carregamento.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public abstract class DashboardFormPadrao implements Initializable {

	private final static Logger LOGGER = Logger.getLogger(DashboardFormPadrao.class.getName());

	// Irá mapear as abas abertas.
	final static protected Map<URL, Tab> abasAbertas = new HashMap<>();

	protected final static DropShadow efeitoPainelDetalhe = new DropShadow();

	@FXML
	protected AnchorPane apGlobal;

	@FXML
	protected StackPane rootStackPane;

	@FXML
	protected SplitPane splPane;
	protected DoubleProperty dPropSplPane;
	protected DoubleTransition dTransSplPane;
	protected Timeline tmLineAbrir;

	@FXML
	protected AnchorPane apBotoes;

	@FXML
	protected AnchorPane apBotoesDetalhes;
	protected TranslateTransition tTransApBotoesDetalhes;

	@FXML
	protected VBox vbBotoesDetalhes;

	@FXML
	protected JFXTabPane tbPaneAbas;

	@FXML
	protected JFXHamburger btnBurgerBotao;
	protected HamburgerBackArrowBasicTransition btnBurgerTask;

	/**
	 * <p>
	 * Função necessária para atualizar o conteiner de abas, no qual devido a um bug
	 * não ajusta direito o conteudo novo ao ser adicionado.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void atualizaTabPane() {
		tbPaneAbas.requestLayout();
	}

	// ********************************************************************************//
	// Método para a movimentações dos panels e dos botões.
	// *******************************************************************************//

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
	protected void fecharBotoesDetalhe() {
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
	 * Função para fazer o do pane com a lista de botões, no split pane.
	 * </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * @author Jhonny de Salles Noschang
	 */
	protected synchronized void loadBotoes(URL absoluteName) {
		FXMLLoader loader = new FXMLLoader(absoluteName);
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

	// ********************************************************************************//
	// Método para a abertura das abas
	// *******************************************************************************//

	// Comando synchronized irá fazer com que a tela carregue por primeiro, não
	// esperando outros processamentos que possam ter.
	/*
	 * <p> Função para fazer o carregamento da tela e insere na aba ou mostrar se a
	 * tela já está mapeada. </p>
	 * 
	 * @param absoluteName Endereço em <b>String</b> da tela a ser carregada.
	 * 
	 * @param tela Nome que será apresentada na aba.
	 * 
	 * @param icon Icone opcional, no qual será inserido na aba. Necessário o
	 * caminho após o comando <b><i>getPath</i><b>.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized void loadAbas(URL absoluteName, String tela, String icon) {
		if (tbPaneAbas == null)
			tbPaneAbas = new JFXTabPane();

		if (abasAbertas.containsKey(absoluteName))
			// Caso ja foi mapeada a aba, foco nela.
			tbPaneAbas.getSelectionModel().select(abasAbertas.get(absoluteName));
		else {
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
						FXMLLoader loader = new FXMLLoader(absoluteName);
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

	// ********************************************************************************//
	// Funções de carregamento da tela
	// *******************************************************************************//
	protected synchronized void prepareAnimacaoMenuSlide() {
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

	private DashboardFormPadrao setEfeito() {
		efeitoPainelDetalhe.setWidth(21.0);
		efeitoPainelDetalhe.setWidth(21.0);
		efeitoPainelDetalhe.setHeight(21.0);
		efeitoPainelDetalhe.setRadius(10.0);
		efeitoPainelDetalhe.setRadius(10.0);
		efeitoPainelDetalhe.setOffsetX(2.0);
		efeitoPainelDetalhe.setOffsetY(2.0);
		return this;
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		// Injeta a dependencia deste controlador no gerenciador de view
		ViewGerenciador.setDashboardController(this);

		setEfeito();
		prepareAnimacaoMenuSlide();

		SplitPane.setResizableWithParent(splPane, false);
		dPropSplPane = splPane.getDividers().get(0).positionProperty();
		dTransSplPane = new DoubleTransition(Duration.millis(600), dPropSplPane);

		tTransApBotoesDetalhes = new TranslateTransition(new Duration(350), apBotoesDetalhes);

		inicializa(arg0, arg1);
	}

}
