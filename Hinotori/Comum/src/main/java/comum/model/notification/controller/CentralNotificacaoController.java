package comum.model.notification.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import comum.model.notification.Notificacoes;
import comum.model.notification.Notificacoes.Menssagem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class CentralNotificacaoController implements Initializable {

	@FXML
	private AnchorPane apBackground;

	@FXML
	private ListView<Menssagem> lvLista;

	@FXML
	private JFXButton btnOrdenarHora;

	@FXML
	private JFXButton btnOrdenarTipo;

	@FXML
	private JFXButton btnLimpar;

	private ObservableList<Menssagem> observableList = FXCollections.observableArrayList();

	@FXML
	public void onBtnOrdenaHoraClick() {

	}

	@FXML
	public void onBtnOrdenaTipoClick() {

	}

	@FXML
	public void onBtnLimpaClick() {
		Notificacoes.limpaNotificacoes();
	}

	public void atualiza() {
		observableList.clear();
		observableList.setAll(Notificacoes.getMenssagens());
		lvLista.setItems(observableList);
	}

	public void remove(Menssagem msg) {
		observableList.remove(msg);
	}

	public void add(Menssagem msg) {
		observableList.add(msg);
	}

	// Cria a forma de exibição das menssagens
	private void configureListView() {
		lvLista.setCellFactory(new Callback<ListView<Menssagem>, ListCell<Menssagem>>() {
			@Override
			public ListCell<Menssagem> call(ListView<Menssagem> listView) {
				return new NotificacaoDetalheCell();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configureListView();
		atualiza();
	}

	public static URL getFxmlLocate() {
		return CentralNotificacaoController.class.getResource("/comum/model/notification/view/CentralNotificacao.fxml");
	}

}
