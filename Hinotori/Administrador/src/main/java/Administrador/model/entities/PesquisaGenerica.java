package Administrador.model.entities;

public class PesquisaGenerica {

	String campoId;
	String campoDescricao;
	String select;
	String tabela;
	String joins;
	String where;
	String groupOrder;

	public String getCampoID() {
		return campoId;
	}

	public String getCampoDescricao() {
		return campoDescricao;
	}

	public String getSelect() {
		return select;
	}

	public String getTabela() {
		return tabela;
	}

	public String getJoins() {
		return joins;
	}

	public String getWhere() {
		return where;
	}

	public String getGroupOrder() {
		return groupOrder;
	}

	public PesquisaGenerica(String campoId, String campoDescricao, String select, String tabela, String joins,
			String where, String groupOrder) {
		this.campoId = campoId;
		this.campoDescricao = campoDescricao;
		this.select = select;
		this.tabela = tabela;
		this.joins = joins;
		this.where = where;
		this.groupOrder = groupOrder;
	}

}
