package br.com.shiyoken.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.animation.DoubleTransition;

public class DashboardController implements Initializable  {

	private Map<String, Tab> abasAbertas = new HashMap<>(); // Irá mapear as abas abertas.
	private Map<URL, Stage> telas = new HashMap<>();
	
	@FXML
	JFXHamburger btnHamburger;
	
	@FXML
	SplitPane slipPane;
	Boolean btnPaneAberto = false;
	
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
	private Menu menuAjuda;
	
	@FXML
	JFXButton btnCadastros;
	
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/menu/Cadastros.fxml"));
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
		loadView("../gui/cadastros/CadCliente.fxml", "Cadastro Cliente" ,"../resources/images/icon/icoMenuCliente.png");
	}
	
	@FXML
	public void onBtnCadEmpresaAction() {
		loadView("../gui/cadastros/CadEmpresa.fxml", "Cadastro Empresa" ,"../resources/images/icon/icoMenuEmpresa.png");
	}
	
	@FXML
	public void onBtnCadUsuarioAction() {
		loadView("../gui/cadastros/CadUsuario.fxml", "Cadastro Usuario" ,"../resources/images/icon/icoMenuUsuario.png");
	}
	

	private void apBotoesMovDireita() {
		DoubleProperty dprop = slipPane.getDividers().get(1).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(800), dprop);
		dt.setToValue(1); dt.play();		
	}
	
	private void apBotoesMovEsquerda() {
		DoubleProperty dprop = slipPane.getDividers().get(1).positionProperty();
		DoubleTransition dt = new DoubleTransition(Duration.millis(800), dprop);
		dt.setToValue(0); dt.play();		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		HamburgerBackArrowBasicTransition burgerTask = new HamburgerBackArrowBasicTransition(btnHamburger);
        burgerTask.setRate(-1);
		btnHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
        });
	}
	
	private static ImageView buildImage(InputStream inputStream) { // Redimenciona a imagem para a aba
		Image i = new Image(inputStream);
		ImageView imageView = new ImageView();
		// You can set width and height
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
				// aba.setContent(new Button("Um botão de conteúdo"));
				// aba.setContent(new Rectangle(150, 50));
				if (icon != "") { // Irá chamar a função para redimencionar e colocar a imagem.
					aba.setGraphic(buildImage(getClass().getResourceAsStream(icon)));
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
			//Alerts.ShowAlert("Erro", "Não foi possivel carregar a tela.", e.getMessage(), AlertType.ERROR);
		}
	}
}
