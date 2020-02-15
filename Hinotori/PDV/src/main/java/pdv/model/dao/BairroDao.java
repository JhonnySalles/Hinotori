package pdv.model.dao;

import java.util.List;

import pdv.model.entities.Bairro;

public interface BairroDao {

	void insert(Bairro obj);
	void update(Bairro obj);
	void delete(Long id);
	Bairro find(Long id);
	List<Bairro> findAll();
	
}
