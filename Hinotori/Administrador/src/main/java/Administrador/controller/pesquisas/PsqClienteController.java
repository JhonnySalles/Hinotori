package Administrador.controller.pesquisas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.dao.services.ClienteServices;
import Administrador.model.entities.Cliente;
import Administrador.model.entities.PesquisaGenericaDados;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.TipoCliente;
import model.mask.Mascaras;
import model.notification.Notificacao;
import model.utils.Utils;

public class PsqClienteController implements Initializable {

	@FXML
	private TableView<Cliente> tabela;

	@FXML
	private TableColumn<Cliente, String> nome;

	@FXML
	private TableColumn<Cliente, String> telefone;
	
	@FXML
	private TableColumn<Cliente, String> celular;
	
	@FXML
	private TableColumn<Cliente, String> email;
	
	@FXML
	private TableColumn<Cliente, TipoCliente> tipo;
	
	@FXML
	private TableColumn<Cliente, Date> dataCadastro;
	
	@FXML
	private TableColumn<Cliente, Situacao> situacao;
	

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private StackPane stackPane;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXButton btnSair;

	private List<Cliente> clientes;
	private FXMLLoader loader;
	private ClienteServices clienteService;

	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {

	}

	@FXML
	public void onBtnEditarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEditar.fire();
		}
	}

	@FXML
	public void onBtnEditarClick() {

	}

	@FXML
	public void onBtnSairEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnSair.fire();
		}
	}

	@FXML
	public void onBtnSairClick() {

	}

	@FXML
	public void onBtnEnderecosClick() {
		loadSecondView("/Administrador/view/cadastros/CadClienteEndereco.fxml");
		//CadClienteEnderecoController endereco = loader.getController();
		//endereco.setCadCliente(this);
	}

	public void loadSecondView(String tela) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(tela));
			Parent root = loader.load();
			Scene scene = rootPane.getScene();
			root.translateXProperty().set(scene.getWidth());
			stackPane.getChildren().add(root);

			Timeline timeline = new Timeline();
			KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
			timeline.getKeyFrames().add(kf);
			timeline.play();

			desabilitaBotoes();
		} catch (IOException e) {
			e.printStackTrace();
			habilitaBotoes();
		}
	}

	public void loadPrimaryView() {
		Scene scene = rootPane.getScene();
		Node node = stackPane.getChildren().get(stackPane.getChildren().size() - 1);
		node.translateXProperty().set(0);
		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(node.translateXProperty(), scene.getWidth(), Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(t -> {
			stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
		});
		timeline.play();
		habilitaBotoes();
	}

	private void desabilitaBotoes() {
		btnNovo.setDisable(true);
		btnEditar.setDisable(true);
		btnSair.setDisable(true);
	}

	private void habilitaBotoes() {
		btnNovo.setDisable(false);
		btnEditar.setDisable(false);
		btnSair.setDisable(false);
	}

	private void inicializaGrid() {
		nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		telefone.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		celular.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		email.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tipo.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		dataCadastro.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		situacao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		tabela.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					onBtnEditarClick();
			});
			return row;
		});
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		inicializaGrid();
	}
}
