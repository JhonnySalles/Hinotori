package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Tamanho;

public interface TamanhoDao {

	void insert(Tamanho obj);
	void update(Tamanho obj);
	void delete(Integer id);
	Tamanho find(Integer id);
	List<Tamanho> findAll();
	
}
