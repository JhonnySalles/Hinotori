package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.dao.DaoFactory;
import servidor.dao.EmpresaDao;
import servidor.entities.Empresa;

public class EmpresaServices {

	private EmpresaDao empresaDao = DaoFactory.createEmpresaDao();

	public void salvar(Empresa empresa) throws ExcessaoBd {
		if (empresa.getId() != null && empresa.getId() != 0)
			empresaDao.update(empresa);
		else
			empresaDao.insert(empresa);
	}

	public void deletar(Long id) throws ExcessaoBd {
		empresaDao.delete(id);
	};

	public Empresa pesquisar(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		return empresaDao.find(id, tamanho);
	};

	public List<Empresa> pesquisarTodos(TamanhoImagem tamanho) throws ExcessaoBd {
		return empresaDao.findAll(tamanho);
	};

}
