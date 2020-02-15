package administrador.model.dao.services;

import java.util.List;

import administrador.model.dao.DaoFactory;
import administrador.model.dao.EmpresaDao;
import administrador.model.entities.Empresa;
import comum.model.exceptions.ExcessaoBd;

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

	public Empresa pesquisar(Long id) throws ExcessaoBd {
		return empresaDao.find(id);
	};

	public List<Empresa> pesquisarTodos() throws ExcessaoBd {
		return empresaDao.findAll();
	};

}
