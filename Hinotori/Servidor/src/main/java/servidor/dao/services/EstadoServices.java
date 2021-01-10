package servidor.dao.services;

import java.util.List;

import servidor.entities.Estado;

public class EstadoServices extends HibernateService {
	
	final static String SELECT_ALL = "SELECT e FROM " + Estado.TABELA + " e WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";
	
	public Estado salvar(Estado produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Estado.TABELA);
	};

	public Estado pesquisar(Long id) {
		return em.find(Estado.class, id);
	};

	public List<Estado> pesquisarTodos() {
		return em.createQuery(SELECT_ALL, Estado.class).getResultList();
	};
}
