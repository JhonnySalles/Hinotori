package administrador.model.dao;

import java.util.List;

import administrador.model.entities.Sabor;

public interface SaborDao {
	
	void insert(Sabor obj);
	void update(Sabor obj);
	void delete(Long id);
	Sabor find(Long id);
	List<Sabor> findAll();

}
