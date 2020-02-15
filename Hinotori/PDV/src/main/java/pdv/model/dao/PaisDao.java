package pdv.model.dao;

import java.util.List;

import pdv.model.entities.Pais;

public interface PaisDao {
	
	void insert(Pais obj);
	void update(Pais obj);
	void delete(Long id);
	Pais find(Long id);
	List<Pais> findAll();

}
