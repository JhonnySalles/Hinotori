package servidor.dao;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.entities.Usuario;

public interface UsuarioDao {

	Usuario insert(Usuario obj) throws ExcessaoBd;

	Usuario update(Usuario obj) throws ExcessaoBd;

	Long delete(Long id) throws ExcessaoBd;

	Usuario find(Long id, TamanhoImagem tamanho) throws ExcessaoBd;

	Usuario find(String login) throws ExcessaoBd;

	List<Usuario> findAll(TamanhoImagem tamanho) throws ExcessaoBd;

	Boolean validaLogin(Long id, String login) throws ExcessaoBd;

	List<String> findLogins() throws ExcessaoBd;

}
