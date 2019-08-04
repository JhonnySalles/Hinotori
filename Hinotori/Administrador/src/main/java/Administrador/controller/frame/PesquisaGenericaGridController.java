package Administrador.controller.frame;

import java.net.URL;
import java.util.ResourceBundle;

import Administrador.model.entities.PesquisaGenericaDados;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PesquisaGenericaGridController implements Initializable {

	@FXML
	TableView<PesquisaGenericaDados> tabela;

	@FXML
	TableColumn<PesquisaGenericaDados, String> id;

	@FXML
	TableColumn<PesquisaGenericaDados, String> descricao;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
	}
	
	public void carregaGrid(ObservableList<PesquisaGenericaDados> Lista) {
		tabela.setItems(Lista);
	}

}
