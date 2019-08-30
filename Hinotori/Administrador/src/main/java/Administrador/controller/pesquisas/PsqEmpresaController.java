package Administrador.controller.pesquisas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Administrador.controller.cadastros.CadEmpresaController;
import Administrador.model.dao.services.EmpresaServices;
import Administrador.model.entities.Empresa;
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
import model.mask.ConverterMascaras;
import model.notification.Notificacao;

public class PsqEmpresaController implements Initializable {

	@FXML
	private TableView<Empresa> tabela;

	@FXML
	private TableColumn<Empresa, String> nomeFantasia;

	@FXML
	private TableColumn<Empresa, String> razaoSocial;

	@FXML
	private TableColumn<Empresa, String> cnpj;

	@FXML
	private TableColumn<Empresa, String> bairro;

	@FXML
	private TableColumn<Empresa, String> endereco;

	@FXML
	private TableColumn<Empresa, String> telefone;

	@FXML
	private TableColumn<Empresa, Situacao> situacao;

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

	private ObservableList<Empresa> obsLEmpresa;
	private FXMLLoader loader;
	private EmpresaServices empresaService;

	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {
		loadView("/Administrador/view/cadastros/CadEmpresa.fxml");
		CadEmpresaController cadEmpresa = loader.getController();
		cadEmpresa.setPsqEmpresa(this);
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
		loadView("/Administrador/view/cadastros/CadEmpresa.fxml");
		CadEmpresaController cadEmpresa = loader.getController();
		cadEmpresa.setPsqEmpresa(this);
		cadEmpresa.carregaEmpresa(empresaService.pesquisar(tabela.getSelectionModel().getSelectedItem().getId()));
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
		if (empresaService == null)
			setEmpresaServices(new EmpresaServices());

		List<Empresa> lista = empresaService.pesquisarTodos();
		obsLEmpresa = FXCollections.observableArrayList(lista);
		tabela.setItems(obsLEmpresa);
	}

	private void inicializaGrid() {
		nomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
		razaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));

		cnpj.setCellValueFactory(new Callback<CellDataFeatures<Empresa, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Empresa, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getCnpj() != null && !p.getValue().getCnpj().isBlank()) {
					return new SimpleStringProperty(ConverterMascaras.formataCNPJ(p.getValue().getCnpj()));
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		bairro.setCellValueFactory(new Callback<CellDataFeatures<Empresa, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Empresa, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && !p.getValue().getBairro().getNome().isEmpty()) {
					return new SimpleStringProperty(p.getValue().getBairro().getNome());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		endereco.setCellValueFactory(new Callback<CellDataFeatures<Empresa, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Empresa, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getEndereco() != null
						&& (!p.getValue().getEndereco().isEmpty() || !p.getValue().getNumero().isEmpty())) {
					return new SimpleStringProperty(p.getValue().getEndereco()
							+ (!p.getValue().getEndereco().isEmpty() && !p.getValue().getNumero().isEmpty() ? ", "
									: " ")
							+ p.getValue().getNumero());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		telefone.setCellValueFactory(new Callback<CellDataFeatures<Empresa, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Empresa, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getContato() != null
						&& !p.getValue().getContato().getTelefone().isEmpty()) {
					return new SimpleStringProperty("(" + p.getValue().getContato().getDddTelefone() + ")"
							+ p.getValue().getContato().getTelefone());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		tabela.setRowFactory(tv -> {
			TableRow<Empresa> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					onBtnEditarClick();
			});
			return row;
		});

		carregaGrid();
	}

	private void setEmpresaServices(EmpresaServices empresaService) {
		this.empresaService = empresaService;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setEmpresaServices(new EmpresaServices());
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		inicializaGrid();
	}
}
