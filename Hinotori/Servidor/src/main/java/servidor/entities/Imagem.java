package servidor.entities;

import java.io.Serializable;

import comum.model.enums.Padrao;
import comum.model.enums.TamanhoImagem;

public class Imagem implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 6407704915654886503L;

	private Long idSequencial;
	private String nome;
	private String extenssao;

	private byte[] imagem;

	private Enum<Padrao> padrao;
	private Enum<TamanhoImagem> tamanho;

	public Long getIdSequencial() {
		return idSequencial;
	}

	public void setIdSequencial(Long idSequencial) {
		this.idSequencial = idSequencial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getExtenssao() {
		return extenssao;
	}

	public void setExtenssao(String extenssao) {
		this.extenssao = extenssao;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Enum<Padrao> getPadrao() {
		return padrao;
	}

	public void setPadrao(Enum<Padrao> padrao) {
		this.padrao = padrao;
	}

	public Enum<TamanhoImagem> getTamanho() {
		return tamanho;
	}

	public void setTamanho(Enum<TamanhoImagem> tamanho) {
		this.tamanho = tamanho;
	}

	public Imagem() {
		this.idSequencial = Long.valueOf(0);
	}

	public Imagem(String nome, String extenssao, byte[] imagem, Enum<Padrao> padrao, Enum<TamanhoImagem> tamanho) {
		this.nome = nome;
		this.extenssao = extenssao;
		this.imagem = imagem;
		this.padrao = padrao;
		this.tamanho = tamanho;
	}

	public Imagem(Long idSequencial, String nome, String extenssao, byte[] imagem, Enum<Padrao> padrao,
			Enum<TamanhoImagem> tamanho) {
		this.idSequencial = idSequencial;
		this.nome = nome;
		this.extenssao = extenssao;
		this.imagem = imagem;
		this.padrao = padrao;
		this.tamanho = tamanho;
	}

	@Override
	public String toString() {
		return "Imagem [idSequencial=" + idSequencial + ", nome=" + nome + ", extenssao=" + extenssao + ", padrao="
				+ padrao + ", tamanho=" + tamanho + "]";
	}

}
