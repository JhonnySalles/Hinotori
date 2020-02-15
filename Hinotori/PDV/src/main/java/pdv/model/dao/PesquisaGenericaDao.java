package pdv.model.dao;

import pdv.model.entities.PesquisaGenerica;
import pdv.model.entities.PesquisaGenericaDados;

public interface PesquisaGenericaDao {
	PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa, String sql);
}
