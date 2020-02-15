package pdv.controller.frame;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import servidor.entities.PesquisaGenerica;
import servidor.entities.PesquisaGenericaDados;

public class PesquisaGenericaGridController implements Initializable {

	@FXML
	private TableView<List<Object>> tabela;

	private PesquisaGenericaController psqGenericaController;
	private ObservableList<List<Object>> lista = FXCollections.observableArrayList();
	private int indexFiltrar = 0;
	private int indexId = 0;
	private Boolean colunaIdInvisivel = false;

	@FXML
	private void onTableKeyPress(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			duploClique();
		}
	}

	/**
	 * <p>
	 * Index do campo que será utilizado para filtar, caso não encontrado será
	 * definido a primeira coluna.
	 * </p>
	 * 
	 * <p>
	 * Ela será obtida automaticamente através do nome da coluna informada no
	 * setPesquisa.
	 * </p>
	 * 
	 */
	public int getIndexFiltrar() {
		return indexFiltrar;
	}

	/**
	 * <p>
	 * Index do campo que será utilizado para o id, caso não encontrado será
	 * definido a primeira coluna.
	 * </p>
	 * 
	 * <p>
	 * Ela será obtida automaticamente através do nome da coluna informada no
	 * setPesquisa.
	 * </p>
	 * 
	 */
	public int getIndexId() {
		return indexId;
	}

	/**
	 * <p>
	 * Função para ocultar a coluna id, caso não encontrado a coluna id, por padrão irá ocultar a coluna 0.
	 * </p>
	 */
	public void setColunaIdVisivel(Boolean visivel) {
		tabela.getColumns().get(indexId).setVisible(visivel);
		colunaIdInvisivel = !visivel;
	}

	/**
	 * <p>
	 * Função carregar os valores e também vincular um listener para fazer leitura
	 * do que digita e filtra a grid.
	 * </p>
	 */
	public void carregaGrid(PesquisaGenericaDados dados, PesquisaGenerica pesquisa) {
		this.lista = FXCollections.observableArrayList(dados.getData());
		
		tabela.getColumns().clear();
		for (int i = 0; i < dados.getNumColumns(); i++) {
			TableColumn<List<Object>, Object> column = new TableColumn<>(dados.getColumnName(i));
			int columnIndex = i;
			column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
			tabela.getColumns().add(column);

			if (dados.getColumnName(i).equalsIgnoreCase(pesquisa.getCampoID()))
				indexId = i;

			if (dados.getColumnName(i).equalsIgnoreCase(pesquisa.getCampoFiltrar()))
				indexFiltrar = i;
		}
		tabela.setItems(lista);
		
		if (colunaIdInvisivel)
			tabela.getColumns().get(indexId).setVisible(false);
		
		FilteredList<List<Object>> filteredData = new FilteredList<>(this.lista, p -> true);

		psqGenericaController.txtFraPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();
				if (person.get(indexFiltrar).toString().toLowerCase().contains(lowerCaseFilter))
					return true;
				else
					return false;
			});
		});
		SortedList<List<Object>> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tabela.comparatorProperty());
		tabela.setItems(sortedData);
	}

	/**
	 * <p>
	 * Obtem a referência para o controlador de pesquisa, pois é utilizado para
	 * fazer algumas chamadas e procedimento como para carregar as informações no
	 * <i>txtPesquisa</i>.
	 * </p>
	 */
	public void setControllerPai(PesquisaGenericaController psqGenericaController) {
		this.psqGenericaController = psqGenericaController;
	}

	public void duploClique() {
		psqGenericaController.duploClique();
		final EventHandler<ActionEvent> handler = getOnDuploClique();
		if (handler != null) {
			handler.handle(new ActionEvent(this, null));
		}
	}

	/**
	 * <p>
	 * Função para retornar o item selecionado na grid.
	 * </p>
	 * 
	 * @return Retorna a classe <b>PesquisaGenericaDados</b> que contem os atributos
	 *         id e descrição.
	 * 
	 */
	public List<Object> getItemSelecionado() {
		return tabela.getSelectionModel().getSelectedItem();
	}

	/**
	 * <p>
	 * Função para retornar realizar a limpeza do item selecionado.
	 * </p>
	 * 
	 */
	public void limpaItemSelecionado() {
		tabela.getSelectionModel().clearSelection();
	}
	
	/**
	 * <p>
	 * Obtem a referênicia para a tabela de pesquisa padrão.
	 * </p>
	 * 
	 * @return Retorna a tabela da grid.
	 * 
	 */
	public TableView<List<Object>> getTabela() {
		return tabela;
	}

	/**
	 * <p>
	 * Obtem a referênicia para a lista de objeto incorporado a tabela.
	 * </p>
	 * 
	 * @return Retorna a tabela da grid.
	 * 
	 */
	public ObservableList<List<Object>> getObstLista() {
		return lista;
	}

	/**
	 * Função a ser executada quando ocorrer um duplo clique na grid.
	 * {@code PesquisaGenericaGridController}.
	 *
	 * @defaultValue null
	 */
	private ObjectProperty<EventHandler<ActionEvent>> onDuploClique;
	private static final EventHandler<ActionEvent> DEFAULT_ON_DUPLO_CLIQUE = null;

	public final void setOnDuploClique(EventHandler<ActionEvent> value) {
		if ((onDuploClique != null) || (value != null /* DEFAULT_ON_DUPLO_CLIQUE */)) {
			onDuploCliqueProperty().set(value);
		}
	}

	public final EventHandler<ActionEvent> getOnDuploClique() {
		return (onDuploClique == null) ? DEFAULT_ON_DUPLO_CLIQUE : onDuploClique.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onDuploCliqueProperty() {
		if (onDuploClique == null) {
			onDuploClique = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onDuploClique",
					DEFAULT_ON_DUPLO_CLIQUE);
		}
		return onDuploClique;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tabela.setRowFactory(tv -> {
			TableRow<List<Object>> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty()))
				 duploClique();
			});
			return row;
		});
	}
}
