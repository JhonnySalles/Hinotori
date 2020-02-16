package servidor.dao;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.entities.Empresa;

public interface EmpresaDao {

	void insert(Empresa obj) throws ExcessaoBd;

	void update(Empresa obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Empresa find(Long id, TamanhoImagem tamanho) throws ExcessaoBd;

	List<Empresa> findAll(TamanhoImagem tamanho) throws ExcessaoBd;

}
