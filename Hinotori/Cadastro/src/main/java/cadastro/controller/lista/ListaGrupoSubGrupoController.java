package cadastro.controller.lista;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.componente.GrupoComponenteController;
import cadastro.controller.componente.SubGrupoComponenteController;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.enums.TipoGrupo;
import comum.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import servidor.entities.GrupoSubGrupo;

public class ListaGrupoSubGrupoController extends ListaFormPadrao {

	private Map<Long, GrupoSubGrupo> listSubGrupo = new HashMap<>();

	@FXML
	private JFXTextField txtPesquisaGrupo;

	@FXML
	private JFXTextField txtPesquisaSubGrupo;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private JFXTextField txtFiltrarGrupo;

	@FXML
	private JFXTextField txtFiltrarSubGrupo;

	@FXML
	private JFXMasonryPane mspGrupos;

	@FXML
	private HBox hbRemover;
	
	@FXML
	private Label lblRemover;

	@FXML
	private FlowPane flpSubGrupos;

	private ObservableList<GrupoComponenteController> grupo = FXCollections.observableArrayList();

	private ObservableList<SubGrupoComponenteController> subGrupo = FXCollections.observableArrayList();

	@Override
	protected void onBtnNovoClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBtnExcluirClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBtnEditarClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onBtnAtualizarClick() {
		// TODO Auto-generated method stub

	}
	
	public JFXMasonryPane getComponenteGrupo() {
		return mspGrupos;
	}
	
	public void setTextoRemover(String texto, Boolean remover) {
		if (texto.isEmpty()) {
			lblRemover.setText("Excluir");
			hbRemover.setStyle("-fx-background-radius: 5; -fx-background-color: #ef6950;");
		} else
			lblRemover.setText(texto);
		
		if (!texto.isEmpty()) {
			if (remover)
				hbRemover.setStyle("-fx-background-radius: 5; -fx-background-color: #ff0000;");
			else
				hbRemover.setStyle("-fx-background-radius: 5; -fx-background-color: #0066ff;");
		}
	}

	/**
	 * <p>
	 * Função será utilizada para criar e adicionar um grupo no pane de grupos.
	 * </p>
	 * 
	 * <p>
	 * Irá criar uma função onDragDropped, no qual será utilizado para realizar a
	 * inserção so subGrupo. O valor aplicado no evento terá as tag idSubGrupo, no
	 * qual pode-se identificar qual subGrupo a ser adicionado.
	 * </p>
	 * 
	 * @param grupo Um <b>GrupoSubGrupo</b> a ser adicionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void addGrupo(GrupoSubGrupo grupo) {
		GrupoComponenteController grup = new GrupoComponenteController(this, grupo);
		this.grupo.add(grup);

		grup.getRoot().setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != grup.getRoot() && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

				event.consume();
			}
		});

		grup.getRoot().setOnDragDropped((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if (db.hasString() && db.getString().contains("idSubGrupo:")) {
				Long subGrupo = Utils.getId("idSubGrupo:", db.getString(), "");

				grup.addSubGrupo(grupo.getId(), listSubGrupo.get(subGrupo));
				event.setDropCompleted(true);
			} else
				event.setDropCompleted(false);
			event.consume();
		});

	}

	/**
	 * <p>
	 * Função será utilizada para adicionar um subGrupo no panel dos subGrupos,
	 * também irá criar o componente do subGrupo e criar uma função onDrag no
	 * componente para poder arrastar para dentro do grupo.
	 * </p>
	 * 
	 * @param subGrupo Um <b>GrupoSubGrupo</b> a ser adicionado.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void listSubGrupo(GrupoSubGrupo subGrupo) {
		listSubGrupo.put(subGrupo.getId(), subGrupo);

		SubGrupoComponenteController sub = new SubGrupoComponenteController(subGrupo);
		sub.getRoot().setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				sub.getRoot().startFullDrag();
				Dragboard db = sub.getRoot().startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("idSubGrupo:" + sub.getSubGrupo().getId());
				db.setContent(content);
				
				setTextoRemover("Arraste até algum grupo para adicionar.", false);
			}
		});

		sub.getRoot().setOnMouseDragged((MouseEvent event) -> {
			event.setDragDetect(true);
		});
		
		sub.getRoot().setOnDragDone(e -> setTextoRemover("", false));

		this.subGrupo.add(sub);
	}

	/**
	 * <p>
	 * Função será utilizada para listar os subGrupos no panel dos subGrupos.
	 * </p>
	 * 
	 * @param subGrupos Uma lista <b>Set</b> de subGrupos.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public void listSubGrupo(Set<GrupoSubGrupo> subGrupos) {
		for (GrupoSubGrupo sb : subGrupos)
			listSubGrupo(sb);
	}

	/**
	 * <p>
	 * Função para encontrar e remover o subGrupo do grupo.
	 * </p>
	 * 
	 * @param subGrupo Um id <b>Long</b> do grupo que contém o subGrupo a ser
	 *                 removido.
	 * @param subGrupo Um id <b>Long</b> do subGrupo a ser removido.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void removeSubGrupo(Long grupo, Long subGrupo) {
		Optional<GrupoComponenteController> obj = this.grupo.stream().filter(sb -> sb.getGrupo().getId().equals(grupo))
				.findFirst();
		
		if (obj.isPresent())
			obj.get().removeSubGrupo(subGrupo);
	}

	private void inicializaTeste() {

		GrupoSubGrupo teste1 = new GrupoSubGrupo(Long.valueOf(4), "Grupo 4 - Preto claro", "#333333", TipoGrupo.GRUPO,
				Situacao.ATIVO);
		teste1.addSubGrupo(
				new GrupoSubGrupo(Long.valueOf(5), "Sub Grupo 1", "#FFFFFF", TipoGrupo.SUBGRUPO, Situacao.ATIVO));
		teste1.addSubGrupo(
				new GrupoSubGrupo(Long.valueOf(6), "Sub Grupo 2", "#BBBBBB", TipoGrupo.SUBGRUPO, Situacao.INATIVO));
		teste1.addSubGrupo(
				new GrupoSubGrupo(Long.valueOf(7), "Sub Grupo 3", "#CCCCCC", TipoGrupo.SUBGRUPO, Situacao.ATIVO));

		addGrupo(teste1);

		addGrupo(new GrupoSubGrupo(Long.valueOf(1), "Grupo 1 - Preto", "#000000", TipoGrupo.GRUPO, Situacao.ATIVO));
		addGrupo(new GrupoSubGrupo(Long.valueOf(2), "Grupo 2 - Branco", "#ffffff", TipoGrupo.GRUPO, Situacao.INATIVO));
		addGrupo(new GrupoSubGrupo(Long.valueOf(3), "Grupo 3 - Verde", "#009933", TipoGrupo.GRUPO, Situacao.EXCLUIDO));

		teste1 = new GrupoSubGrupo();
		teste1.setId(Long.valueOf(8));
		teste1.setDescricao("Grupo 7 - Verde claro");
		teste1.setCor("#66ff66");
		addGrupo(teste1);

		teste1 = new GrupoSubGrupo();
		teste1.setId(Long.valueOf(9));
		teste1.setDescricao("Grupo 6 - Azul claro");
		teste1.setCor("#99ccff");
		addGrupo(teste1);

		teste1 = new GrupoSubGrupo();
		teste1.setId(Long.valueOf(10));
		teste1.setDescricao("Grupo 5 - Vermelho claro");
		teste1.setCor("#ff9966");
		teste1.addSubGrupo(new GrupoSubGrupo(Long.valueOf(13), "Sub Grupo 5 - Vermelho", "#cc0000", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));
		teste1.addSubGrupo(new GrupoSubGrupo(Long.valueOf(11), "Sub Grupo 6 - Rosa", "#cc0099", TipoGrupo.SUBGRUPO,
				Situacao.INATIVO));
		teste1.addSubGrupo(new GrupoSubGrupo(Long.valueOf(12), "Sub Grupo 7 - Azul", "#0000ff", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));
		addGrupo(teste1);

		listSubGrupo(new GrupoSubGrupo(Long.valueOf(5), "Sub Grupo 1 - Roxo", "#660066", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));
		listSubGrupo(new GrupoSubGrupo(Long.valueOf(6), "Sub Grupo 2 - Amarelo", "#ffff00", TipoGrupo.SUBGRUPO,
				Situacao.INATIVO));
		listSubGrupo(new GrupoSubGrupo(Long.valueOf(7), "Sub Grupo 3 - Marrom", "#663300", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));
		listSubGrupo(new GrupoSubGrupo(Long.valueOf(13), "Sub Grupo 5 - Azul marinho", "#006666", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));
		listSubGrupo(new GrupoSubGrupo(Long.valueOf(11), "Sub Grupo 6 - Verde escuro", "#003300", TipoGrupo.SUBGRUPO,
				Situacao.INATIVO));
		listSubGrupo(new GrupoSubGrupo(Long.valueOf(12), "Sub Grupo 7 - Cinza", "#666666", TipoGrupo.SUBGRUPO,
				Situacao.ATIVO));

	}

	/**
	 * <p>
	 * Função principal que faz a criação dos listeners dos <b>ObservableList</b> no
	 * qual fará o trabalho de criar os componentes visuais e adicionar dentro do
	 * pane de grupo ou subGrupo.
	 * </p>
	 * 
	 * <p>
	 * Irá criar uma função no campo remover para que pode-se identificar e remover
	 * o subGrupo do gruop. O valor aplicado no evento deverá ter a tag idGrupo e
	 * idSubGrupo, no qual pode-se identificar qual subGrupo a ser removido.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	private void createListener() {
		grupo.addListener(new ListChangeListener<GrupoComponenteController>() {
			@Override
			public void onChanged(Change<? extends GrupoComponenteController> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); ++i) {
							// permutate
						}
					} else if (c.wasUpdated()) {
						// update item
					} else {
						for (GrupoComponenteController remitem : c.getRemoved()) {
							mspGrupos.getChildren().remove(remitem.getRoot());
						}
						for (GrupoComponenteController additem : c.getAddedSubList()) {
							mspGrupos.getChildren().add(additem.getRoot());
						}
						
						mspGrupos.requestLayout();
					}
				}

			}
		});

		subGrupo.addListener(new ListChangeListener<SubGrupoComponenteController>() {
			@Override
			public void onChanged(Change<? extends SubGrupoComponenteController> c) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); ++i) {
							// permutate
						}
					} else if (c.wasUpdated()) {
						// update item
					} else {
						for (SubGrupoComponenteController remitem : c.getRemoved()) {
							flpSubGrupos.getChildren().remove(remitem.getRoot());
						}
						for (SubGrupoComponenteController additem : c.getAddedSubList()) {
							flpSubGrupos.getChildren().add(additem.getRoot());
						}
						
						flpSubGrupos.requestLayout();
					}
				}

			}
		});

		hbRemover.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != hbRemover && event.getDragboard().hasString())
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

				event.consume();
			}
		});

		hbRemover.setOnDragDropped((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if (db.hasString() && db.getString().contains("idGrupo:") && db.getString().contains("idSubGrupo:")) {
				Long grupo = Utils.getId("idGrupo:", db.getString(), "-");
				Long subGrupo = Utils.getId("idSubGrupo:", db.getString(), "");
				removeSubGrupo(grupo, subGrupo);

				event.setDropCompleted(true);
			} else
				event.setDropCompleted(false);
			event.consume();
		});

	}

	@Override
	protected void inicializa(URL arg0, ResourceBundle arg1) {
		createListener();
		inicializaTeste();

	}

	public static URL getFxmlLocate() {
		return ListaGrupoSubGrupoController.class.getResource("/cadastro/view/lista/ListaGrupoSubGrupo.fxml");
	}

}
