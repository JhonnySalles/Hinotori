package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "grupo_subgrupo_imagens")
public class GupoBaseImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	public GupoBaseImagem() {
		super();
	}

	public GupoBaseImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public GupoBaseImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

}
