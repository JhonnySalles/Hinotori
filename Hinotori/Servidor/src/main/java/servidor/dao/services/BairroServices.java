package servidor.dao.services;

import java.util.List;

import servidor.entities.Bairro;

public class BairroServices extends HibernateService {

	final static String SELECT_ALL = "SELECT p FROM " + Bairro.TABELA + " p WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	public Bairro salvar(Bairro produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Bairro.TABELA);
	};

	public Bairro pesquisar(Long id) {
		return em.find(Bairro.class, id);
	};

	public List<Bairro> pesquisarTodos() {
		return em.createQuery(SELECT_ALL, Bairro.class).getResultList();
	};
}
