package restaurante.gui;

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

import comum.model.animation.DoubleTransition;
import javafx.beans.property.DoubleProperty;
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

public class DashboardController implements Initializable  {

	private Map<String, Tab> abasAbertas = new HashMap<>(); // Irá mapear as abas abertas.
	
	@FXML
	JFXHamburger btn_BurgerBotoes;
	
	@FXML
	SplitPane splitPane;
	Boolean btnPaneAberto = false;
	
	@FXML
	AnchorPane apBotoes;
	
	@FXML
	AnchorPane apBotoesDetalhes;
	
	@FXML
	VBox vbBotoesDetalhes;
	
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
	JFXButton btn_Cadastros;
	
	@FXML
	JFXButton btn_Lancamentos;
	
	@FXML
	JFXButton btn_Pedidos;
	
	@FXML
	JFXButton btn_Configuracao;
	
	@FXML
	private void onBtnHanburgerAction() {
		if (btnPaneAberto) {
			apBotoesMovEsquerda();
			btnPaneAberto = false;
		} else {
			apBotoesMovDireita();
			btnPaneAberto = true;
		}
	}
	
	@FXML
	private void onBtnCadastrosAction() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu/Cadastros.fxml"));
		try {
			VBox vbCadastros = loader.load();
			vbBotoesDetalhes.getChildren().clear();
			vbBotoesDetalhes.getChildren().add(vbCadastros);
			vbBotoesDetalhes.setFillWidth(true);
			vbBotoesDetalhes.alignmentProperty().set(Pos.TOP_LEFT);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBtnConfiguracaoAction() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu/Configuracao.fxml"));
		try {
			VBox vbCadastros = loader.load();
			vbBotoesDetalhes.getChildren().clear();
			vbBotoesDetalhes.getChildren().add(vbCadastros);
			vbBotoesDetalhes.setFillWidth(true);
			vbBotoesDetalhes.alignmentProperty().set(Pos.TOP_LEFT);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtnCadClienteAction() {
		loadView("cadastros/CadCliente.fxml", "Cadastro Cliente", "../resources/images/icon/icoMenuCliente.png");
	}
	
	@FXML
	public void onBtnCadEmpresaAction() {
		loadView("cadastros/CadEmpresa.fxml", "Cadastro Empresa", "../resources/images/icon/icoMenuEmpresa.png");
	}
	
	@FXML
	public void onBtnCadUsuarioAction() {
		loadView("cadastros/CadUsuario.fxml", "Cadastro Usuario", "../resources/images/icon/icoMenuUsuario.png");
	}
	
	@FXML
	public void onBtnConfigImpressoraAction() {
		loadView("configuracao/ConfigImpressora.fxml", "Config. Impressora", "../resources/images/icon/icoMenuImpressoras.png");
	}
	

	private void apBotoesMovDireita() {
		apBotoesDetalhes.setMaxWidth(150);
		DoubleProperty dprop = splitPane.getDividers().get(1).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(600), dprop);
		dt.setToValue(1); dt.play();	
		SplitPane.setResizableWithParent(splitPane, Boolean.FALSE);
	}
	
	private void apBotoesMovEsquerda() {
		DoubleProperty dprop = splitPane.getDividers().get(1).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(600), dprop);
		
		dt.setToValue(0); dt.play();
		SplitPane.setResizableWithParent(splitPane, Boolean.FALSE);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		HamburgerBackArrowBasicTransition burgerBtnTask = new HamburgerBackArrowBasicTransition(btn_BurgerBotoes);
		burgerBtnTask.setRate(-1);
        btn_BurgerBotoes.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
        	burgerBtnTask.setRate(burgerBtnTask.getRate() * -1);
        	burgerBtnTask.play();
        });
        
        /* Popup de descricao dos botoes */
        Tooltip toltCadastro = new Tooltip("Cadastros");
        btn_Cadastros.setTooltip(toltCadastro);
	}
	
	private static ImageView buildImage(InputStream inputStream) { // Redimenciona a imagem para a aba
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
	
	// Comando synchronized irá fazer com que a tela carregue por primeiro, não
	// esperando outros processamentos que possam ter.
	private synchronized void loadView(String absoluteName, String tela, String icon) { // Irá ser passado o nome da tela para que o
																			// mesmo carregue
		try {
			if (painelAbas == null) {
				painelAbas = new TabPane();
			}

			if (abasAbertas.containsKey(absoluteName)) {
				painelAbas.getSelectionModel().select(abasAbertas.get(absoluteName)); // Caso ja foi mapeada a aba, foco
																						// nela.
			} else {
				Tab aba = new Tab(tela);

				if (icon != "") { // Irá chamar a função para redimencionar e colocar a imagem.
					File initialFile = new File(getClass().getResource("").getPath()+icon); // Coloca o caminho do arquivo com o caminho da imagem.
					aba.setGraphic(buildImage(new FileInputStream(initialFile)));
				}
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				AnchorPane newAnchorPane = loader.load();
				aba.setContent(newAnchorPane);
				painelAbas.getTabs().add(aba);
				abasAbertas.put(absoluteName, aba); // Irá mapear a aba que abriu para futuramente localizar e focar
													// nela.
				painelAbas.getSelectionModel().select(painelAbas.getTabs().size() - 1); // Foco na ultima tabela
																						// adicionada.
				aba.setOnClosed(e -> abasAbertas.remove(absoluteName)); // Remove do mapeamento se fechou.
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
