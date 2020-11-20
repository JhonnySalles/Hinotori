package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadUsuarioController;
import comum.form.ListaFormPadrao;
import comum.model.enums.UsuarioNivel;
import comum.model.utils.ViewGerenciador;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Imagem;
import servidor.entities.Usuario;

public class ListaUsuarioController extends ListaFormPadrao {

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

	@Override
	public void onBtnNovoClick() {
		CadUsuarioController ctn = (CadUsuarioController) ViewGerenciador.loadTela(CadUsuarioController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onBtnExcluirClick() {

	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}

	public void atualizar() {

	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return ListaUsuarioController.class.getResource("/cadastro/view/lista/ListaUsuario.fxml");
	}

}
