package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.EmpresaDao;
import Administrador.model.entities.Empresa;

public class EmpresaServices {

	private EmpresaDao empresaDao = DaoFactory.createEmpresaDao();

	public void salvar(Empresa empresa) {
		if (empresa.getId() == 0)
			empresaDao.update(empresa);
		else
			empresaDao.insert(empresa);
	}

	public void deletar(Long id) {
		empresaDao.delete(id);
	};
	
	public Empresa pesquisar(Long id) {
		return empresaDao.find(id);
	};

	public List<Empresa> pesquisarTodos() {
		return empresaDao.findAll();
	};

}
