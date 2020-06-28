package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "produtos_imagens")
public class ProdutoImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	@ManyToOne
	private Produto produto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoImagem(Produto produto) {
		this.produto = produto;
	}

	public ProdutoImagem() {
		super();
	}

	public ProdutoImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public ProdutoImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

	public ProdutoImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho, Produto produto) {
		super(nome, extenssao, imagem, tamanho);
		this.produto = produto;
	}
}
