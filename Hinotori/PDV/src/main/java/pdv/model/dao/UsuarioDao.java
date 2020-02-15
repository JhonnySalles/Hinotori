package pdv.model.dao;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import pdv.model.entities.Usuario;

public interface UsuarioDao {

	void insert(Usuario obj) throws ExcessaoBd;

	void update(Usuario obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Usuario find(Long id, TamanhoImagem tamanho) throws ExcessaoBd;

	List<Usuario> findAll(TamanhoImagem tamanho) throws ExcessaoBd;

	Boolean validaLogin(String login) throws ExcessaoBd;

}
