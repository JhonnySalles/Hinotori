package Administrador.controller.pesquisas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Administrador.controller.cadastros.CadUsuarioController;
import Administrador.model.dao.services.UsuarioServices;
import Administrador.model.entities.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.animation.ClienteTranslate;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.notification.Notificacao;

public class PsqUsuarioController implements Initializable {

	@FXML
	private TableView<Usuario> tabela;

	@FXML
	private TableColumn<Usuario, String> nome;

	@FXML
	private TableColumn<Usuario, String> telefone;

	@FXML
	private TableColumn<Usuario, String> celular;

	@FXML
	private TableColumn<Usuario, String> email;

	@FXML
	private TableColumn<Usuario, String> empresa;

	@FXML
	private TableColumn<Usuario, UsuarioNivel> nivel;

	@FXML
	private TableColumn<Usuario, Situacao> situacao;

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
	private JFXButton btnAtualizar;

	private ObservableList<Usuario> obsLUsuario;
	private FXMLLoader loader;
	private UsuarioServices usuarioService;

	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {
		loadView("/Administrador/view/cadastros/CadUsuario.fxml");
		CadUsuarioController cadUsuario = loader.getController();
		cadUsuario.setPsqUsuario(this);
	}

	@FXML
	public void onBtnEditarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEditar.fire();
		}
	}

	@FXML
	public void onBtnEditarClick() {
		if (tabela.getSelectionModel().isEmpty()) {
			Notificacao.Dark("Nenhum item selecionado", "Favor selecionar algum item.", 5.0, Pos.BASELINE_RIGHT);
			return;
		}

		desabilitaBotoes();
		loadView("/Administrador/view/cadastros/CadUsuario.fxml");
		CadUsuarioController cadUsuario = loader.getController();
		cadUsuario.setPsqUsuario(this);
		cadUsuario.carregaUsuario(usuarioService.pesquisar(tabela.getSelectionModel().getSelectedItem().getNome()));
		habilitaBotoes();
	}

	@FXML
	public void onBtnAtualizarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAtualizar.fire();
		}
	}

	@FXML
	public void onBtnAtualizarClick() {
		carregaGrid();
	}

	public void loadView(String tela) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(tela));
			Parent rootCima = loader.load();
			stackPane.getChildren().add(rootCima);

			new ClienteTranslate().abrirPane(stackPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void returnView() {
		new ClienteTranslate().fecharPane(stackPane, t -> {
			stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
			if (stackPane.getChildren().size() <= 1)
				carregaGrid();
		});
	}

	private void desabilitaBotoes() {
		btnNovo.setDisable(true);
		btnEditar.setDisable(true);
		btnAtualizar.setDisable(true);
	}

	private void habilitaBotoes() {
		btnNovo.setDisable(false);
		btnEditar.setDisable(false);
		btnAtualizar.setDisable(false);
	}

	private void carregaGrid() {
		if (usuarioService == null)
			usuarioService = new UsuarioServices();

		List<Usuario> lista = usuarioService.pesquisarTodos();
		obsLUsuario = FXCollections.observableArrayList(lista);
		tabela.setItems(obsLUsuario);
	}

	private void inicializaGrid() {
		nome.setCellValueFactory(new Callback<CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Usuario, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getNome() != null && !p.getValue().getNome().isEmpty()) {
					return new SimpleStringProperty(p.getValue().getNome() + " " + p.getValue().getSobreNome());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		telefone.setCellValueFactory(new Callback<CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Usuario, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getTelefone() != null
						&& !p.getValue().getTelefone().isEmpty()) {
					return new SimpleStringProperty(
							"(" + p.getValue().getDddTelefone() + ")" + p.getValue().getTelefone());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		celular.setCellValueFactory(new Callback<CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Usuario, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getCelular() != null && !p.getValue().getCelular().isEmpty()) {
					return new SimpleStringProperty(
							"(" + p.getValue().getDddCelular() + ")" + p.getValue().getCelular());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		empresa.setCellValueFactory(new Callback<CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Usuario, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getIdEmpresa() != null) {
					return new SimpleStringProperty(p.getValue().getIdEmpresa().toString());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		nivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		tabela.setRowFactory(tv -> {
			TableRow<Usuario> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					onBtnEditarClick();
			});
			return row;
		});

		carregaGrid();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		inicializaGrid();
	}
}
