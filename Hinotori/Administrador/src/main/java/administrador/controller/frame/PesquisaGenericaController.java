package administrador.controller.frame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import servidor.dao.services.PesquisaGenericaServices;
import servidor.entities.PesquisaGenerica;
import servidor.entities.PesquisaGenericaDados;

/**
 * <p>
 * Classe controladora de frame genérico, utilizado para pesquisas padrões,
 * conforme for escrevendo irá filtrando a pesquisa e mostrando em um popup o
 * que foi encontrado.
 * </p>
 * <p>
 * O formulario deverá ser incluido no fxml que deseja e será necessário
 * inicializar a pesquisa com a função <i><b>setPesquisa</i></b>.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class PesquisaGenericaController implements Initializable {

	@FXML
	private AnchorPane frameBackground;

	@FXML
	public JFXTextField txtFraPesquisa;

	@FXML
	private JFXButton btnFraLimpar;

	@FXML
	private JFXButton btnFraPesquisar;

	private PesquisaGenerica pesquisa;
	private PesquisaGenericaServices pesquisaService;
	private PesquisaGenericaDados resultado;
	private PesquisaGenericaGridController controllerGrid;
	private PopOver pop;
	private String titulo;

	private static List<KeyCode> IGNORE_KEY_CODES = new ArrayList<>();
	static {
		Collections.addAll(IGNORE_KEY_CODES,
				new KeyCode[] { KeyCode.TAB, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.ALT });
	}

	@FXML
	private void onTxtKeyPress(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (IGNORE_KEY_CODES.contains(e.getCode()))
			return;

		if (e.getCode().toString().equals("ENTER") && !pop.isShowing()) {
			Robot robot = new Robot();
			robot.keyPress(KeyCode.TAB);
		} else {
			if (!e.getCode().toString().equals("ENTER") && !e.getCode().toString().equals("TAB")) {
				if (!txtFraPesquisa.getText().isEmpty() && controllerGrid.getTabela().getItems().size() > 0) {
					if (!pop.isShowing())
						mostraGrid();
				} else if (pop.isShowing())
					pop.hide();
			}
		}
	}

	@FXML
	private void onBtnLimparAction() {
		controllerGrid.limpaItemSelecionado();
		txtFraPesquisa.setText("");
		pop.hide();
	}

	@FXML
	private void onBtnPesquisarAction() {
		pop.show(txtFraPesquisa);
	}

	@FXML
	public void exitApplication(ActionEvent event) {
		if (pop.isShowing())
			pop.hide();
	}

	public JFXButton getBtnLimpar() {
		return btnFraLimpar;
	}

	public JFXButton getBtnPesquisar() {
		return btnFraPesquisar;
	}

	/**
	 * <p>
	 * Função responsável por estar armazenando a pesquisa do frame e também irá
	 * fazer a primeira pesquisa.
	 * </p>
	 * <p>
	 * Nem todos os campos são obrigatórios, podendo receber vazio, os campos
	 * opcionais são: joins, where e groupOrder.
	 * </p>
	 * <p>
	 * Os campos select e tabela são obrigatórios.
	 * </p>
	 * <p>
	 * A função irá fazer a montagem dos campos e retornar a pesquisa na <b>classe
	 * <i>PesquisaGenerica</b></i>.
	 * </p>
	 * 
	 * @param campoID      Será utilizado para estar pesquisando o objeto
	 *                     selecionado e o retornando, caso não encontrado irá ser
	 *                     definido a primeira coluna. selecionado e o retornando,
	 *                     caso não encontrado irá ser definido a primeira coluna.
	 * @param campoFiltrar Campo utilizado para filtrar a pesquisa conforme digitar,
	 *                     se não encontrado irá filtar a primeira coluna.
	 * @param select       Campo será utilizada para a pesquisa padrão
	 * @param tabela       Tabela principal que será feita a consulta.
	 * @param joins        <i>Opcional<i>: Campo opcional para ligação em mais de
	 *                     uma tabela.
	 * @param where        <i>Opcional<i>: Campo where para que filtre a pesquisa
	 *                     caso necessário.
	 * @param groupOrder   <i>Opcional<i>: Campo para utilização do Group By e Order
	 *                     By.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public PesquisaGenericaController setPesquisa(String campoID, String campoFiltrar, String select, String tabela,
			String joins, String where, String groupOrder) {
		txtFraPesquisa.setText("");

		if (pop.isShowing())
			pop.hide();

		pesquisa = new PesquisaGenerica(campoID, campoFiltrar, select, tabela, joins, where, groupOrder);
		resultado = pesquisaService.pesquisar(pesquisa);
		if (resultado != null)
			controllerGrid.carregaGrid(resultado, pesquisa);
		return this;
	}

	public void locateId(String id) {
		limpaCampos();
		if (resultado == null)
			return;

		for (int i = 0; i < controllerGrid.getTabela().getItems().size(); i++) {
			if (controllerGrid.getTabela().getItems().get(i).get(controllerGrid.getIndexId()).toString()
					.equalsIgnoreCase(id)) {
				controllerGrid.getTabela().getSelectionModel().select(i);
				txtFraPesquisa
				.setText(controllerGrid.getItemSelecionado().get(controllerGrid.getIndexFiltrar()).toString());
				break;
			}	
		}
	}

	public void locateFiltrar(String texto) {
		limpaCampos();

		if (resultado == null)
			return;
		
		txtFraPesquisa.setText(texto);
		for (int i = 0; i < controllerGrid.getTabela().getItems().size(); i++) {
			if (resultado.getData().get(i).get(controllerGrid.getIndexFiltrar()).toString().equalsIgnoreCase(texto)) {
				controllerGrid.getTabela().getSelectionModel().select(i);
				txtFraPesquisa
						.setText(controllerGrid.getItemSelecionado().get(controllerGrid.getIndexFiltrar()).toString());
				break;
			}
		}
	}

	/**
	 * <p>
	 * Recarrega os dados do frame.
	 * </p>
	 */
	public void refreshPesquisa() {
		limpaCampos();
	}

	/**
	 * <p>
	 * Inserir um novo item na grid frame.
	 * </p>
	 * 
	 */
	public void duploClique() {
		if (controllerGrid.getItemSelecionado() != null)
			txtFraPesquisa
					.setText(controllerGrid.getItemSelecionado().get(controllerGrid.getIndexFiltrar()).toString());
		pop.hide();
	}

	/**
	 * <p>
	 * Função para alterar a descrição do txtPesquisa.
	 * </p>
	 */
	public void setDescricao(String descricao) {
		controllerGrid.limpaItemSelecionado();
		txtFraPesquisa.setText(descricao);
	}

	public String getDescricao() {
		return txtFraPesquisa.getText();
	}

	/**
	 * <p>
	 * Obtem o id do item selecionado.
	 * </p>
	 * 
	 * @return Retorna o id do item selecionado, caso não tenha nenhum selecionado
	 *         retorna vazio.
	 */

	public String getId() {
		if (controllerGrid.getItemSelecionado() != null) {
			return controllerGrid.getItemSelecionado().get(controllerGrid.getIndexId()).toString();
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Função para ocultar a coluna id, caso não encontrado a coluna id, por padrão
	 * irá ocultar a coluna 0.
	 * </p>
	 */
	public void setColunaIdVisivel(Boolean visivel) {
		controllerGrid.setColunaIdVisivel(visivel);
	}

	/**
	 * <p>
	 * Faz a limpesa dos campos e recarrega a pesquisa.
	 * </p>
	 * 
	 */
	public PesquisaGenericaController limpaCampos() {
		resultado = pesquisaService.pesquisar(pesquisa);
		btnFraLimpar.fire();
		txtFraPesquisa.setText("");
		return this;
	}

	public String getTitulo() {
		return titulo;
	}

	public PesquisaGenericaController setTitulo(String titulo) {
		this.titulo = titulo;
		pop.setTitle(titulo);
		this.txtFraPesquisa.setPromptText(titulo);
		return this;
	}

	/**
	 * <p>
	 * Função a ser executada quando o cliente efetuar um duplo clique na grid.
	 * {@code PesquisaGenericaGridController}.
	 * </p>
	 *
	 * @defaultValue null
	 */
	public final void setOnDuploClique(EventHandler<ActionEvent> value) {
		controllerGrid.setOnDuploClique(value);
	}

	public final EventHandler<ActionEvent> getOnDuploClique() {
		return controllerGrid.getOnDuploClique();
	}

	/**
	 * <p>
	 * Função a ser executada quando sair do txt.
	 * {@code PesquisaGenericaController}.
	 * </p>
	 *
	 * @defaultValue null
	 */
	private ObjectProperty<EventHandler<ActionEvent>> onTxtPesquisaExit;
	private static final EventHandler<ActionEvent> DEFAULT_ON_TXT_PESQUISA_EXIT = null;

	public final void setOnTxtPesquisaExit(EventHandler<ActionEvent> value) {
		if ((onTxtPesquisaExit != null) || (value != null /* DEFAULT_ON_FINISHED */)) {
			onDuploCliqueProperty().set(value);
		}
	}

	public final EventHandler<ActionEvent> getOnTxtPesquisaExit() {
		return (onTxtPesquisaExit == null) ? DEFAULT_ON_TXT_PESQUISA_EXIT : onTxtPesquisaExit.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onDuploCliqueProperty() {
		if (onTxtPesquisaExit == null) {
			onTxtPesquisaExit = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onTxtPesquisaExit",
					DEFAULT_ON_TXT_PESQUISA_EXIT);
		}
		return onTxtPesquisaExit;
	}

	/**
	 * <p>
	 * Função para chamar o popup contendo a grid.
	 * </p>
	 *
	 * @defaultValue null
	 */
	public PesquisaGenericaController mostraGrid() {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = txtFraPesquisa.getScene();
		Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
		Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
		Point2D nodeCoord = txtFraPesquisa.localToScene(0.0, 0.0);
		// Explicacao em:
		// https://blog.crisp.se/2012/08/29/perlundholm/window-scene-and-node-coordinates-in-javafx
		// double cordenadaX = Math.round(windowCoord.getX() + sceneCoord.getX() +
		// nodeCoord.getX());
		double cordenadaY = Math.round(windowCoord.getY() + sceneCoord.getY() + nodeCoord.getY());

		if (cordenadaY < screenBounds.getHeight() / 2)
			pop.arrowLocationProperty().set(ArrowLocation.TOP_CENTER);
		else
			pop.arrowLocationProperty().set(ArrowLocation.BOTTOM_CENTER);

		pop.show(txtFraPesquisa);
		return this;
	}

	private PesquisaGenericaController iniciaGrid() {
		URL url = getClass().getResource("/pdv/view/frame/PesquisaGenericaGrid.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(3));// 10px padding todos os lados
		try {
			vbox.getChildren().add(loader.load());

			controllerGrid = loader.getController();
			controllerGrid.setControllerPai(this);
			pop.setContentNode(vbox);
			pop.setCornerRadius(5);
			pop.setHideOnEscape(true);
			pop.setAutoFix(true);
			pop.setAutoHide(true);

			return this;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	private void onTxtPesquisaExit() {
		if (pop.isShowing())
			pop.hide();

		final EventHandler<ActionEvent> handler = getOnTxtPesquisaExit();
		if (handler != null) {
			handler.handle(new ActionEvent(this, null));
		}
	}

	private void setExitTxt() {
		txtFraPesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				onTxtPesquisaExit();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resultado = null;
		pesquisaService = new PesquisaGenericaServices();

		/* Popup de descricao dos botoes */
		Tooltip toltLimpar = new Tooltip("Limpar");
		Tooltip toltPesquisar = new Tooltip("Pesquisar");
		titulo = "Pesquisa";
		btnFraLimpar.setTooltip(toltLimpar);
		btnFraPesquisar.setTooltip(toltPesquisar);
		pop = new PopOver();

		iniciaGrid();
		setExitTxt();
	}

}
