package servidor.dao;

import servidor.entities.PesquisaGenerica;
import servidor.entities.PesquisaGenericaDados;

public interface PesquisaGenericaDao {
	PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa, String sql);
}
