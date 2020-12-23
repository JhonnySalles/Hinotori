package cadastro.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import comum.form.CadastroDialogPadrao;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoGrupo;
import comum.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import servidor.entities.Grupo;
import servidor.entities.GrupoBase;
import servidor.entities.SubGrupo;

public class DialogCadGrupoSubGrupoController extends CadastroDialogPadrao {

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXColorPicker cpCorFundo;

	@FXML
	private JFXComboBox<TipoGrupo> cbTipo;

	@FXML
	private JFXCheckBox cbAtivo;

	@FXML
	private ImageView imgGrupo;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	private GrupoBase grupoSubGrupo;

	private Boolean recarregar;
	private Boolean edicao;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos())
			salvar(grupoSubGrupo);
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
		edicao = false;
	}

	@Override
	protected <T> void salvar(T entidade) {
		limpaCampos();
		edicao = false;
		recarregar = true;
	}

	@Override
	public <T> void carregar(T entidade) {
		edicao = entidade != null;
		recarregar = false;

		if (entidade == null)
			limpaCampos();
		else
			atualizaTela((GrupoBase) entidade);
	}

	@Override
	protected boolean validaCampos() {
		// try {
		return true; // ValidaEndereco.validaEndereco(endereco);
		// } catch (ExcessaoCadastro e) {
		// e.printStackTrace();
		// }

		// Notificacoes.notificacao(AlertType.INFORMATION, "Aviso",
		// Mensagens.CADASTRO_SALVAR);

		// return false;
	}

	@Override
	public DialogCadGrupoSubGrupoController atualizaEntidade() {
		if (!edicao) {
			if (cbTipo.getSelectionModel().getSelectedItem().equals(TipoGrupo.SUBGRUPO))
				grupoSubGrupo = new SubGrupo();
			else
				grupoSubGrupo = new Grupo();
		}

		grupoSubGrupo.setDescricao(txtDescricao.getText());
		grupoSubGrupo.setCor(Utils.colorToHexString(cpCorFundo.getValue()));
		grupoSubGrupo.setSituacao(cbAtivo.selectedProperty().get() ? Situacao.ATIVO : Situacao.INATIVO);
		// grupoSubGrupo.setImagens(imagens);

		return this;
	}

	@FXML
	public void onBtnProcurarImagemClick() {

	}

	@FXML
	public void onBtnExcluirImagemClick() {

	}
	
	public Boolean getRecarregar() {
		return recarregar;
	}

	public GrupoBase getGrupoSubGrupo() {
		return this.grupoSubGrupo;
	}

	public void limpaCampos() {
		grupoSubGrupo = new GrupoBase();
		txtDescricao.setText("");
		cpCorFundo.setValue(Color.WHITE);
		cbAtivo.setSelected(true);
		cbTipo.getSelectionModel().selectFirst();
		cbTipo.setDisable(false);
	}

	public DialogCadGrupoSubGrupoController atualizaTela(GrupoBase grupoSubGrupo) {
		limpaCampos();

		if (grupoSubGrupo instanceof Grupo)
			cbTipo.getSelectionModel().select(TipoGrupo.GRUPO);
		else if (grupoSubGrupo instanceof SubGrupo)
			cbTipo.getSelectionModel().select(TipoGrupo.SUBGRUPO);

		this.grupoSubGrupo = grupoSubGrupo;

		if (!grupoSubGrupo.getDescricao().isEmpty())
			txtDescricao.setText(grupoSubGrupo.getDescricao());

		if (!grupoSubGrupo.getCor().isEmpty())
			cpCorFundo.setValue(Color.web(grupoSubGrupo.getCor()));

		cbAtivo.setSelected(grupoSubGrupo.getSituacao().equals(Situacao.ATIVO));

		if (edicao)
			cbTipo.setDisable(true);

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Validadores.setTextFieldNotEmpty(txtDescricao);

		TecladoUtils.onEnterConfigureTab(txtDescricao);
		TecladoUtils.onEnterConfigureTab(cbTipo);

		cbTipo.getItems().addAll(TipoGrupo.values());
		cbTipo.getSelectionModel().selectFirst();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/DialogCadGrupoSubGrupo.fxml");
	}

}
