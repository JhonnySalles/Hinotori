package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import servidor.entities.Empresa;

public class EmpresaServices extends HibernateService {
	
	final static String SELECT_ALL = "SELECT p FROM " + Empresa.TABELA + " p WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	public Empresa salvar(Empresa produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Empresa.TABELA);
	};

	public Empresa pesquisar(Long id) {
		return em.find(Empresa.class, id);
	};

	public List<Empresa> pesquisarTodos(TamanhoImagem tamanho) {
		return em.createQuery(SELECT_ALL, Empresa.class).getResultList();
	};

}
