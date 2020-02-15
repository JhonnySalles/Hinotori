package administrador.model.dao;

import administrador.model.entities.PesquisaGenerica;
import administrador.model.entities.PesquisaGenericaDados;

public interface PesquisaGenericaDao {
	PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa, String sql);
}
