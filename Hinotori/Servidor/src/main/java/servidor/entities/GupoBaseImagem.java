package servidor.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import comum.model.enums.TamanhoImagem;

@Entity
@Table(name = "grupo_subgrupo_imagens")
public class GupoBaseImagem extends Imagem {

	private static final long serialVersionUID = 2820959226817240910L;

	private GrupoBase grupoSubGrupo;

	public GrupoBase getGrupoSubGrupo() {
		return grupoSubGrupo;
	}

	public void setGrupoSubGrupo(GrupoBase grupoSubGrupo) {
		this.grupoSubGrupo = grupoSubGrupo;
	}

	public GupoBaseImagem(GrupoBase grupoSubGrupo) {
		this.grupoSubGrupo = grupoSubGrupo;
	}

	public GupoBaseImagem() {
		super();
	}

	public GupoBaseImagem(Long id, String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(id, nome, extenssao, imagem, tamanho);
	}

	public GupoBaseImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho) {
		super(nome, extenssao, imagem, tamanho);
	}

	public GupoBaseImagem(String nome, String extenssao, byte[] imagem, Enum<TamanhoImagem> tamanho,
			GrupoBase grupoSubGrupo) {
		super(nome, extenssao, imagem, tamanho);
		this.grupoSubGrupo = grupoSubGrupo;
	}
}
