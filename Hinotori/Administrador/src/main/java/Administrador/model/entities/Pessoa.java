package administrador.model.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pessoa implements Serializable {

	// Utilizado para poder ser transformado em sequencia de bytes
	// e poder então trafegar os dados em rede ou salvar em arquivo.
	private static final long serialVersionUID = 7073086540992937921L;
	private Long id;
	private String nomeSobrenome;
	private Timestamp dataCadastro;

	private List<Contato> contatos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeSobrenome() {
		return nomeSobrenome;
	}

	public void setNomeSobrenome(String nomeSobrenome) {
		this.nomeSobrenome = nomeSobrenome;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public Pessoa() {
		this.id = Long.valueOf(0);
		this.nomeSobrenome = "";
		this.contatos = new ArrayList<>();
	}

	public Pessoa(Long id, String nomeSobrenome, Timestamp dataCadastro) {
		this.id = id;
		this.nomeSobrenome = nomeSobrenome;
		this.dataCadastro = dataCadastro;
		this.contatos = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nomeSobrenome=" + nomeSobrenome + ", dataCadastro=" + dataCadastro + "]";
	}

}
