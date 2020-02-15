package Utilitarios.gui.encode;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Utilitarios.model.encode.DecodeHash;
import Utilitarios.model.encode.Encryption;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TelaCodificacaoController implements Initializable {

	final static double imgIcoWidth = 24;
	final static double imgIcoHeight = 24;

	final static String imgDecript = "/Utilitarios/resources/images/encode/icoDescriptografa_48.png";
	final static String imgDecriptErro = "/Utilitarios/resources/images/encode/icoDescriptografaErro_48.png";
	final static String imgDecriptAtivo = "/Utilitarios/resources/images/encode/icoDescriptografaAtivo_48.png";

	final static String imgEncript = "/Utilitarios/resources/images/encode/icoCriptografa_48.png";
	final static String imgEncriptErro = "/Utilitarios/resources/images/encode/icoCriptografaErro_48.png";
	final static String imgEncriptAtivo = "/Utilitarios/resources/images/encode/icoCriptografaAtivo_48.png";

	final static double imgAvisoWidth = 24;
	final static double imgAvisoHeight = 24;

	final static String imgAviso = "/Utilitarios/resources/images/icoAviso_48.png";
	final static String imgErro = "/Utilitarios/resources/images/icoErro_48.png";

	@FXML
	Button btnCriptografia;

	@FXML
	Button btnDescriptografar;

	@FXML
	ChoiceBox<String> chBoxTipoCodificacao;

	@FXML
	TextField txtPalavraDescripto;

	@FXML
	TextField txtPalavraCripto;

	@FXML
	Pane pnImgAviso;

	@FXML
	Label lblAviso;

	@FXML
	ImageView imgViewDescripto;

	@FXML
	ImageView imgViewCripto;

	@FXML
	public void onBtnDescriptoClick() {
		limpaCampos();
		if (validaCamposDescripto()) {
			txtPalavraDescripto.setText(processa(txtPalavraCripto.getText(), "D"));

			if (!txtPalavraDescripto.getText().isEmpty()) {
				loadImageIcone(imgViewDescripto, imgDecriptAtivo);
				txtPalavraDescripto.setStyle("-fx-border-color: green;");
			} else {
				loadImageIcone(imgViewCripto, imgEncriptErro);
			}
		}
	}

	@FXML
	public void onBtnCriptoClick() {
		limpaCampos();
		if (validaCamposCripto()) {
			txtPalavraCripto.setText(processa(txtPalavraDescripto.getText(), "E"));

			if (!txtPalavraCripto.getText().isEmpty()) {
				loadImageIcone(imgViewCripto, imgEncriptAtivo);
				txtPalavraCripto.setStyle("-fx-border-color: green;");
			} else {
				loadImageIcone(imgViewDescripto, imgEncriptErro);
			}
		}
	}

	public String processa(String texto, String tipo) {
		if (tipo == "E") {
			switch (chBoxTipoCodificacao.getSelectionModel().getSelectedIndex()) {
			case 0:

				try {
					texto = Encryption.codifica(texto);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro, chave inválida. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro, não suportado a codificação. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro NoSuchAlgorithmException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (NoSuchPaddingException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro NoSuchPaddingException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (IllegalBlockSizeException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro IllegalBlockSizeException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (BadPaddingException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Erro BadPaddingException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
					loadAviso(imgErro, "Parâmetro do algoritmo inválido. \n Verifique a palavra criptografada.");
					texto = "";
				}

				break;
			case 1:

				try {
					texto = DecodeHash.DecodePassword(texto);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Chave inválida. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Chave não suportada para criptografia. \n Verifique a palavra criptografada.");
					texto = "";
				}

				break;
			default:
				texto = "";
			}
		}

		if (tipo == "D") {
			switch (chBoxTipoCodificacao.getSelectionModel().getSelectedIndex()) {
			case 0:

				try {
					texto = Encryption.decodifica(texto);
				} catch (InvalidKeyException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Chave inválida. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro NoSuchAlgorithmException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro NoSuchPaddingException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro IllegalBlockSizeException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (BadPaddingException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro BadPaddingException. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (InvalidAlgorithmParameterException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro, parâmetro do almoritmo inválido. \n Verifique a palavra criptografada.");
					texto = "";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					loadAviso(imgErro, "Erro, criptografia não suportada. \n Verifique a palavra criptografada.");
					texto = "";
				}

				break;
			default:
				texto = "";
			}
		}

		return texto;
	}

	public boolean validaCamposDescripto() {
		if (txtPalavraCripto.getText().isEmpty()) {
			loadAviso(imgAviso, "Necessário informar uma palavra para descriptografar.");
			loadImageIcone(imgViewCripto, imgEncriptErro);
			txtPalavraCripto.setStyle("-fx-border-color: red;");
			return false;
		}

		loadImageIcone(imgViewCripto, imgEncriptAtivo);
		return true;
	}

	public boolean validaCamposCripto() {
		if (txtPalavraDescripto.getText().isEmpty()) {
			loadAviso(imgAviso, "Necessário informar uma palavra para criptografar.");
			loadImageIcone(imgViewDescripto, imgDecriptErro);
			txtPalavraDescripto.setStyle("-fx-border-color: red;");
			return false;
		}

		loadImageIcone(imgViewDescripto, imgDecriptAtivo);
		return true;
	}

	public void limpaCampos() {
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);

		txtPalavraCripto.setStyle("");
		txtPalavraDescripto.setStyle("");
	}

	public void loadImageIcone(@SuppressWarnings("exports") ImageView imgView, String img) {
		imgView.setImage(new Image(getClass().getResourceAsStream(img)));
		imgView.setFitHeight(imgIcoHeight);
		imgView.setFitWidth(imgIcoWidth);
	}

	public void loadAviso(String img, String texto) {
		ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream(img)));
		imgView.setFitWidth(imgAvisoWidth);
		imgView.setFitHeight(imgAvisoHeight);

		pnImgAviso.getChildren().clear();
		pnImgAviso.getChildren().add(imgView);
		pnImgAviso.setVisible(true);

		lblAviso.setText(texto);
		lblAviso.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		chBoxTipoCodificacao.getItems().add("Cripto\\Descriptografia");
		chBoxTipoCodificacao.getItems().add("Criptografia por Hash");
		chBoxTipoCodificacao.getSelectionModel().select(0);

		// Irá desabilitar para a criptografia hash, pois a mesma não pode ser
		// descriptografada.
		chBoxTipoCodificacao.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
				if ((Integer) newNumber == 1) {
					btnDescriptografar.setDisable(true);
				} else {
					btnDescriptografar.setDisable(false);
				}

				limpaCampos();
				txtPalavraDescripto.setText("");
				txtPalavraCripto.setText("");
			}
		});

		txtPalavraDescripto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					txtPalavraDescripto.setStyle("");
				}
			}
		});

		txtPalavraCripto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					txtPalavraCripto.setStyle("");
				}
			}
		});
	}
}
