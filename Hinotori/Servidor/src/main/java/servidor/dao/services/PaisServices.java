package servidor.dao.services;

import java.util.List;

import servidor.entities.Pais;

public class PaisServices extends HibernateService {

	final static String SELECT_ALL = "SELECT p FROM " + Pais.TABELA + " p WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	public Pais salvar(Pais produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Pais.TABELA);
	};

	public Pais pesquisar(Long id) {
		return em.find(Pais.class, id);
	};

	public List<Pais> pesquisarTodos() {
		return em.createQuery(SELECT_ALL, Pais.class).getResultList();
	};
}
