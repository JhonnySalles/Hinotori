package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "clientes_imagens")
public class ClienteImagem extends Imagem {

	private static final long serialVersionUID = 5107953301444621796L;

	@ManyToOne
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ClienteImagem() {
		super();
	}

	public ClienteImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public ClienteImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

	public ClienteImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho, Usuario usuario) {
		super(nome, extenssao, imagem, tamanho);
		this.usuario = usuario;
	}

}
