package Administrador.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Administrador.model.entities.Usuario;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.config.ProcessaConfig;
import model.entitis.Conexao;
import model.enums.UsuarioNivel;

public class DashboardController implements Initializable {

	private Map<String, Tab> abasAbertas = new HashMap<>(); // Irá mapear as abas abertas.

	@FXML
	private JFXHamburger btnBurgerBotoes;

	@FXML
	private SplitPane splitPane;

	@FXML
	private AnchorPane apBotoes;

	@FXML
	private AnchorPane apBotoesDetalhes;

	@FXML
	private VBox vbBotoesDetalhes;

	@FXML
	private TabPane painelAbas;

	@FXML
	private MenuItem mnItmCadCliente;

	@FXML
	private MenuItem mnItmCadEmpresa;

	@FXML
	private MenuItem mnItmCadUsuario;

	@FXML
	private MenuItem mnItmConfigImpressora;

	@FXML
	private Menu menuAjuda;

	@FXML
	private JFXButton btnCadastros;

	@FXML
	private JFXButton btnConfiguracao;

	private static Usuario user;
	private static Conexao conexao;

	public DashboardController() {
		Usuario user = new Usuario("Teste", UsuarioNivel.ADMINISTRADOR);
		DashboardController.user = user;
		DashboardController.conexao = ProcessaConfig.getDadosConexao();
	}

	public static Usuario getUser() {
		return user;
	}

	public static Conexao getConexao() {
		return conexao;
	}

	/* Teste */
	/*
	 * @FXML public void onBtnTesteAction() throws IOException { URL url =
	 * getClass().getResource("/Administrador/view/cadastros/CadEmpresa.fxml");
	 * 
	 * FXMLLoader loader = new FXMLLoader(); loader.setLocation(url); AnchorPane
	 * newAnchorPane = loader.load();
	 * 
	 * Scene mainScene = new Scene(newAnchorPane); // Carrega a scena
	 * mainScene.setFill(Color.BLACK);
	 * 
	 * Stage stage = new Stage(); stage.setScene(mainScene); // Seta a cena
	 * principal stage.setTitle("Tela de teste");
	 * stage.initStyle(StageStyle.DECORATED);
	 * stage.initModality(Modality.WINDOW_MODAL); stage.setResizable(true);
	 * stage.setMinWidth(870); stage.setMinHeight(500); stage.show(); // Mostra a
	 * tela. }
	 */
	/* Fim Teste */

	@FXML
	private void onBtnCadastrosAction() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Administrador/view/menu/Cadastros.fxml"));
		try {
			VBox vbCadastros = loader.load();
			vbBotoesDetalhes.getChildren().clear();
			vbBotoesDetalhes.getChildren().add(vbCadastros);
			vbBotoesDetalhes.setFillWidth(true);
			vbBotoesDetalhes.alignmentProperty().set(Pos.TOP_LEFT);
			abrirPaneBotoesDetalhe();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onBtnConfiguracaoAction() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Administrador/view/menu/Configuracao.fxml"));
		try {
			VBox vbCadastros = loader.load();
			vbBotoesDetalhes.getChildren().clear();
			vbBotoesDetalhes.getChildren().add(vbCadastros);
			vbBotoesDetalhes.setFillWidth(true);
			vbBotoesDetalhes.alignmentProperty().set(Pos.TOP_LEFT);
			abrirPaneBotoesDetalhe();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtnCadClienteAction() {
		loadView("/Administrador/view/cadastros/CadCliente.fxml", "Cadastro Cliente",
				"../resources/geral/images/btn/bntUsuario_48.png");
	}

	@FXML
	public void onBtnCadEmpresaAction() {
		loadView("/Administrador/view/cadastros/CadEmpresa.fxml", "Cadastro Empresa",
				"../resources/images/icon/icoMenuEmpresa.png");
	}

	@FXML
	public void onBtnCadUsuarioAction() {
		loadView("/Administrador/view/cadastros/CadUsuario.fxml", "Cadastro Usuario",
				"../resources/images/icon/icoMenuUsuario.png");
	}

	@FXML
	public void onBtnConfigImpressoraAction() {
		loadView("/Administrador/view/configuracao/ConfigImpressora.fxml", "Config. Impressora",
				"../resources/images/icon/icoMenuImpressoras.png");
	}

	private void abrirPaneBotoesDetalhe() {
		if (apBotoesDetalhes.getTranslateX() != 0) {
			btnBurgerBotoes.fireEvent(new ActionEvent());
		}
	}

	private void fecharPaneBotoesDetalhe() {
		if (apBotoesDetalhes.getTranslateX() == 0) {
			btnBurgerBotoes.fireEvent(new ActionEvent());
		}
	}

	// Redimenciona a imagem para a aba
	private static ImageView buildImage(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		Image i = new Image(inputStream);
		ImageView imageView = new ImageView();

		imageView.setFitHeight(16);
		imageView.setFitWidth(16);
		imageView.setImage(i);
		return imageView;
	}

	private void prepareSlideMenuAnimation() {
		TranslateTransition openSlidePane = new TranslateTransition(new Duration(350), apBotoesDetalhes);
		TranslateTransition closeSlidePane = new TranslateTransition(new Duration(350), apBotoesDetalhes);
		HamburgerBackArrowBasicTransition btnBurgerTask = new HamburgerBackArrowBasicTransition(btnBurgerBotoes);
		btnBurgerTask.setRate(-1);
		
		// Evento para abrir e fechar via programacao.
		btnBurgerBotoes.addEventHandler(ActionEvent.ACTION, e -> {
			btnBurgerTask.setRate(btnBurgerTask.getRate() * -1);
			btnBurgerTask.play();
			if (apBotoesDetalhes.getTranslateX() != 0) {
				openSlidePane.setToX(0);
				openSlidePane.play();
				openSlidePane.setOnFinished(new EventHandler<ActionEvent>() { // validar se o botão está correto.
					@Override
					public void handle(ActionEvent event) {
						if (btnBurgerTask.getRate() != 1) {
							btnBurgerTask.setRate(1);
							btnBurgerTask.play();
						}
					}
				});
			} else {
				closeSlidePane.setToX(-(apBotoesDetalhes.getWidth()));
				closeSlidePane.play();
				closeSlidePane.setOnFinished(new EventHandler<ActionEvent>() { // validar se o botão está correto.
					@Override
					public void handle(ActionEvent event) {
						if (btnBurgerTask.getRate() == 1) {
							btnBurgerTask.setRate(-1);
							btnBurgerTask.play();
						}
					}
				});
			}
		});

		// Evento para o botao do mouse.
		btnBurgerBotoes.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			btnBurgerBotoes.fireEvent(new ActionEvent());
		});
	}

	// Comando synchronized irá fazer com que a tela carregue por primeiro, não
	// esperando outros processamentos que possam ter.
	private synchronized void loadView(String absoluteName, String tela, String icon) {
		try {
			if (painelAbas == null) {
				painelAbas = new TabPane();
			}

			if (abasAbertas.containsKey(absoluteName)) {
				// Caso ja foi mapeada a aba, foco nela.
				painelAbas.getSelectionModel().select(abasAbertas.get(absoluteName));
			} else {
				Tab aba = new Tab(tela);

				if (icon != "") { // Irá chamar a função para redimencionar e colocar a imagem.
					// Coloca o caminho do arquivo com o caminho da imagem.
					File initialFile = new File(getClass().getResource("").getPath() + icon);
					aba.setGraphic(buildImage(new FileInputStream(initialFile)));
				}
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				AnchorPane newAnchorPane = loader.load();
				aba.setContent(newAnchorPane);
				painelAbas.getTabs().add(aba);

				// Irá mapear a aba que abriu para futuramente localizar e focar nela.
				abasAbertas.put(absoluteName, aba);

				// Foco na ultima tabela adicionada.
				painelAbas.getSelectionModel().select(painelAbas.getTabs().size() - 1);

				// Remove do mapeamento se fechou.
				aba.setOnClosed(e -> abasAbertas.remove(absoluteName));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		fecharPaneBotoesDetalhe();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/* Popup de descricao dos botoes */
		Tooltip toltCadastro = new Tooltip("Cadastros");
		btnCadastros.setTooltip(toltCadastro);
		prepareSlideMenuAnimation();
	}
}
