package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.PesquisaGenerica;
import Administrador.model.entities.PesquisaGenericaDados;

public interface PesquisaGenericaDao {
	List<PesquisaGenericaDados> pesquisar(PesquisaGenerica pesquisa, String sql);
}
