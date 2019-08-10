package Administrador.controller.frame;

import java.net.URL;
import java.util.ResourceBundle;

import Administrador.model.entities.PesquisaGenericaDados;
import javafx.collections.ObservableList;
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
	
	@FXML
	public void duploClique() {
		controller.setItemSelecionado(tabela.getSelectionModel().getSelectedItem());
	}
	
	public PesquisaGenericaDados getSelectItem() {
		return tabela.getSelectionModel().getSelectedItem();
	}

	public void carregaGrid(ObservableList<PesquisaGenericaDados> Lista) {
		tabela.setItems(Lista);
	}
	
	public void setControllerPai(PesquisaGenericaController controller) {
		this.controller = controller;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		
		tabela.setRowFactory(tv -> {
			TableRow<PesquisaGenericaDados> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					duploClique();
				
				}	
			});
			return row;
		});
		
	}
}
