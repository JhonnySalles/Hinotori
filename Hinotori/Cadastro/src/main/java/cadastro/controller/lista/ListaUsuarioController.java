package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import cadastro.controller.cadastros.CadUsuarioController;
import comum.form.DashboardFormPadrao;
import comum.form.ListaFormPadrao;
import comum.model.enums.UsuarioNivel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Imagem;
import servidor.entities.Usuario;

public class ListaUsuarioController extends ListaFormPadrao implements Initializable {

	@FXML
	private TableView<Usuario> tbUsuarios;

	@FXML
	private TableColumn<Usuario, String> tbClId;

	@FXML
	private TableColumn<Usuario, List<Imagem>> tbClImagem;

	@FXML
	private TableColumn<Usuario, String> tbClNome;

	@FXML
	private TableColumn<Usuario, String> tbClLogin;

	@FXML
	private TableColumn<Usuario, String> tbClContatoPadrao;

	@FXML
	private TableColumn<Usuario, String> tbClObservacao;

	@FXML
	private TableColumn<Usuario, UsuarioNivel> tbClNivel;

	@FXML
	private TableColumn<Usuario, String> tbClDataCadastro;

	private List<Usuario> usuarios;
	private ObservableList<Usuario> obsUsuarios;
	private FilteredList<Usuario> filteredData;
	private UsuarioServices usuarioService;
	
	@Override
	public void onNovoKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnNovo.fire();
	}

	@Override
	public void onBtnNovoClick() {
		CadUsuarioController ctn = (CadUsuarioController) DashboardFormPadrao
				.loadTela(CadUsuarioController.getFxmlLocate(), spRoot);
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
		return ListaUsuarioController.class.getResource("/cadastro/view/lista/ListaUsuario.fxml");
	}

}
