package administrador.model.dao;

import java.util.List;

import administrador.model.entities.Cidade;

public interface CidadeDao {

	void insert(Cidade obj);
	void update(Cidade obj);
	void delete(Long id);
	Cidade find(Long id);
	List<Cidade> findAll();
	
}
