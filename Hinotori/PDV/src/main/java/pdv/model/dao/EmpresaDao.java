package pdv.model.dao;

import java.util.List;

import comum.model.exceptions.ExcessaoBd;
import pdv.model.entities.Empresa;

public interface EmpresaDao {

	void insert(Empresa obj) throws ExcessaoBd;

	void update(Empresa obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Empresa find(Long id) throws ExcessaoBd;

	List<Empresa> findAll() throws ExcessaoBd;

}
