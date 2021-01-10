package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "produtos_imagens")
public class ProdutoImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	public ProdutoImagem() {
		super();
	}

	public ProdutoImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public ProdutoImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

	public static ProdutoImagem toProdutoImagem(Imagem imagem) {
		return new ProdutoImagem(imagem.getId(), imagem.getNome(), imagem.getExtenssao(), imagem.getImagem(),
				imagem.getTamanho());
	}

}
