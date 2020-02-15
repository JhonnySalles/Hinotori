package pdv.model.dao;

import java.util.List;

import pdv.model.entities.Estado;

public interface EstadoDao {

	void insert(Estado obj);
	void update(Estado obj);
	void delete(Long id);
	Estado find(Long id);
	List<Estado> findAll();
	
}
