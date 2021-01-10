package persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;
import junit.framework.TestCase;
import servidor.dao.services.GenericServices;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Cidade;
import servidor.entities.Empresa;
import servidor.entities.Estado;
import servidor.entities.Pais;
import servidor.entities.Usuario;

public class PersistenciaTest extends TestCase {

	private Pais pais;
	private Estado estado;

	public void testFindPais() {
		GenericServices<Pais> service = new GenericServices<Pais>(Pais.class);
		pais = service.pesquisar(Long.valueOf(1));

		assertTrue(pais != null && pais.getId() > 0);
		fail("Erro ao pesquisar o país.");
	}

	public void testFindEstado() {
		GenericServices<Estado> service = new GenericServices<Estado>(Estado.class);
		estado = service.pesquisar(Long.valueOf(1));

		assertTrue(estado != null && estado.getId() > 0);
		fail("Erro ao pesquisar o estado.");
	}

	public void testSaveUsuario() {
		Usuario usuario = new Usuario(Long.valueOf(0), "Maria das Dores", Timestamp.valueOf(LocalDateTime.now()),
				Timestamp.valueOf(LocalDateTime.now()), "MARIA", "Observação de teste", UsuarioNivel.ADMINISTRADOR,
				Situacao.ATIVO);
		
		UsuarioServices service = new UsuarioServices();
		usuario = service.salvar(usuario);

		assertTrue(usuario.getId() > 0);
		fail("Erro ao salvar o usuário.");
	}

	public void testFindUsuario() {
		UsuarioServices service = new UsuarioServices();
		Usuario usuario = service.pesquisar(Long.valueOf(1));

		assertTrue(usuario != null && usuario.getId() > 0);
		fail("Erro ao pesquisar o usuário.");
	}

	public void testSaveEmpresa() {
		Cidade cidade = new Cidade(Long.valueOf(1), "Cascavel", "45", Situacao.ATIVO, estado);
		Empresa empresa = new Empresa(Long.valueOf(0), "Empresa de teste", "Empresa de demonstração", "27341631000120",
				Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO, cidade);

		GenericServices<Empresa> service = new GenericServices<Empresa>(Empresa.class);
		empresa = service.salvar(empresa);

		assertTrue(empresa.getId() > 0);
		fail("Erro ao salvar a empresa.");
	}
	
	public void testFindEmpresa() {
		GenericServices<Empresa> service = new GenericServices<Empresa>(Empresa.class);
		Empresa empresa = service.pesquisar(Long.valueOf(1));

		assertTrue(empresa != null && empresa.getId() > 0);
		fail("Erro ao pesquisar o usuário.");
	}
	
	public void testCliente() {
		assertTrue(true);
		fail("Erro ao salvar o usuário.");
	}

	public void testProduto() {
		assertTrue(true);
		fail("Erro ao salvar o usuário.");
	}

}
