package Administrador.controller.frame;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Administrador.model.entities.PesquisaGenericaDados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PesquisaGenericaGridController implements Initializable {

	@FXML
	private TableView<PesquisaGenericaDados> tabela;

	@FXML
	private TableColumn<PesquisaGenericaDados, String> id;

	@FXML
	private TableColumn<PesquisaGenericaDados, String> descricao;

	private PesquisaGenericaController controller;
	private ObservableList<PesquisaGenericaDados> lista;

	@FXML
	public void clique() {
		controller.setItemSelecionado(tabela.getSelectionModel().getSelectedItem());
	}

	public PesquisaGenericaDados getSelectItem() {
		return tabela.getSelectionModel().getSelectedItem();
	}

	public void carregaGrid(List<PesquisaGenericaDados> lista) {
		this.lista = FXCollections.observableArrayList(lista);
		tabela.setItems(this.lista);
		
		FilteredList<PesquisaGenericaDados> filteredData = new FilteredList<>(this.lista, p -> true);
        
		controller.txt_Pesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
		    filteredData.setPredicate(person -> {
		        if (newValue == null || newValue.isEmpty()) {
		            return true;
		        }
		        
		        String lowerCaseFilter = newValue.toLowerCase();
		        
		        if (person.getDescricao().toLowerCase().contains(lowerCaseFilter))
		            return true;
		        else 
		        	return false;
		    });
		});
		SortedList<PesquisaGenericaDados> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tabela.comparatorProperty());
		tabela.setItems(sortedData);
	}

	public void setControllerPai(PesquisaGenericaController controller) {
		this.controller = controller;
	}
	
	public void setIdInvisivel(Boolean valor) {
		this.id.setVisible(!valor);
		this.descricao.setMinWidth(385);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		tabela.setRowFactory(tv -> {
			TableRow<PesquisaGenericaDados> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty()))
						clique();
			});
			return row;
		});
	}
}
