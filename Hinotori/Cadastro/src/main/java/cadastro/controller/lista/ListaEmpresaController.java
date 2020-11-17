package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadEmpresaController;
import comum.form.DashboardFormPadrao;
import comum.form.ListaFormPadrao;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import servidor.dao.services.EmpresaServices;
import servidor.entities.Empresa;
import servidor.entities.Imagem;

public class ListaEmpresaController extends ListaFormPadrao implements Initializable {

	@FXML
	private TableView<Empresa> tbEmpresas;

	@FXML
	private TableColumn<Empresa, String> tbClId;

	@FXML
	private TableColumn<Empresa, List<Imagem>> tbClLogo;

	@FXML
	private TableColumn<Empresa, String> tbClRazaoSocial;

	@FXML
	private TableColumn<Empresa, String> tbClNomeFantasia;

	@FXML
	private TableColumn<Empresa, String> tbClCnpj;

	@FXML
	private TableColumn<Empresa, String> tbClDataCadastro;

	@FXML
	private TableColumn<Empresa, String> tbClContatoPadrao;

	@FXML
	private TableColumn<Empresa, String> tbClEnderecoPadrao;

	private List<Empresa> empresas;
	private ObservableList<Empresa> obsEmpresas;
	private FilteredList<Empresa> filteredData;
	private EmpresaServices empresaService;

	@Override
	public void onNovoKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnNovo.fire();
	}

	@Override
	public void onBtnNovoClick() {
		CadEmpresaController ctn = (CadEmpresaController) DashboardFormPadrao
				.loadTela(CadEmpresaController.getFxmlLocate(), spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onExcluirKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnExcluir.fire();
	}

	@Override
	public void onBtnExcluirClick() {

	}

	@Override
	public void onEditarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnEditar.fire();
	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onAtualizarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnAtualizar.fire();
	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}
	
	public void atualizar() {

	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		inicializaHeranca();
	}

	public static URL getFxmlLocate() {
		return ListaEmpresaController.class.getResource("/cadastro/view/lista/ListaEmpresa.fxml");
	}

}
