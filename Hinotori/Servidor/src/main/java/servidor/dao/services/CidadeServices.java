package servidor.dao.services;

import java.util.List;

import servidor.entities.Cidade;

public class CidadeServices extends HibernateService {

	final static String SELECT_ALL = "SELECT c FROM " + Cidade.TABELA + " c WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	public Cidade salvar(Cidade produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Cidade.TABELA);
	};

	public Cidade pesquisar(Long id) {
		return em.find(Cidade.class, id);
	};

	public List<Cidade> pesquisarTodos() {
		return em.createQuery(SELECT_ALL, Cidade.class).getResultList();
	};
}
