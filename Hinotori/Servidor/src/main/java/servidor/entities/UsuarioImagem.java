package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "usuarios_imagens")
public class UsuarioImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	public UsuarioImagem() {
		super();
	}

	public UsuarioImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public UsuarioImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

	public static UsuarioImagem toUsuarioImagem(Imagem imagem) {
		return new UsuarioImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}

}
