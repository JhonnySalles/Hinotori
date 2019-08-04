package Administrador.model.entities;

import java.io.Serializable;

import model.enums.TipoTelefone;

public class EmpresaContato implements Serializable {

	private static final long serialVersionUID = -6231049731142074406L;
	private Integer id;
	private String dddTelefone;
	private String telefone;
	private String observacao;
	private Enum<TipoTelefone> tipo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Enum<TipoTelefone> getTipo() {
		return tipo;
	}

	public void setTipo(Enum<TipoTelefone> tipo) {
		this.tipo = tipo;
	}

	public EmpresaContato(Integer id, String dddTelefone, String telefone, String observacao, Enum<TipoTelefone> tipo) {
		this.id = id;
		this.dddTelefone = dddTelefone;
		this.telefone = telefone;
		this.observacao = observacao;
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpresaContato other = (EmpresaContato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmpresaContato [id=" + id + ", dddTelefone=" + dddTelefone + ", telefone=" + telefone + ", observacao="
				+ observacao + ", tipo=" + tipo + "]";
	}

}
