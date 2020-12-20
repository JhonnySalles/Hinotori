package cadastro.controller.componente;

import com.jfoenix.controls.JFXButton;

import comum.model.utils.Utils;
import javafx.fxml.FXML;
import servidor.entities.GrupoSubGrupo;

public class SubGrupoComponenteController {

	@FXML
	private JFXButton root = new JFXButton();

	private Long idGrupo;
	private GrupoSubGrupo subGrupo;

	public JFXButton getRoot() {
		return root;
	}

	public void createSubGrupo(GrupoSubGrupo subGrupo) {
		root.setText(subGrupo.getDescricao());

		if (!subGrupo.getCor().isEmpty()) {
			root.setStyle("-fx-background-color: " + subGrupo.getCor());
			root.setTextFill(Utils.getContrastColor(subGrupo.getCor()));
		}
	}
	
	public Long getIdGrupo() {
		return idGrupo;
	}

	public GrupoSubGrupo getSubGrupo() {
		return subGrupo;
	}

	public SubGrupoComponenteController(GrupoSubGrupo subGrupo) {
		this.idGrupo = null;
		this.subGrupo = subGrupo;
		createSubGrupo(subGrupo);
	}
	
	public SubGrupoComponenteController(Long idGrupo, GrupoSubGrupo subGrupo) {
		this.idGrupo = idGrupo;
		this.subGrupo = subGrupo;
		createSubGrupo(subGrupo);
	}

}
