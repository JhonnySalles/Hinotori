package servidor.dao;

import java.util.List;

import servidor.entities.Estado;

public interface EstadoDao {

	void insert(Estado obj);

	void update(Estado obj);

	void delete(Long id);

	Estado find(Long id);

	List<Estado> findAll();

}
