package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import comum.cep.BuscarCep;
import comum.model.entities.Cep;
import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCep;
import servidor.configuration.ManagerFactory;
import servidor.dao.CidadeDao;
import servidor.dao.services.CidadeService;
import servidor.dao.services.EstadoService;
import servidor.dao.services.GenericService;
import servidor.dao.services.PaisService;
import servidor.dao.services.UsuarioService;
import servidor.dto.EnderecoDTO;
import servidor.entities.Cidade;
import servidor.entities.Cliente;
import servidor.entities.Empresa;
import servidor.entities.Endereco;
import servidor.entities.Estado;
import servidor.entities.Pais;
import servidor.entities.Usuario;

@TestMethodOrder(OrderAnnotation.class)
public class PersistenciaTest {

	private Pais pais;
	private Estado estado;

	@BeforeAll
	public static void iniciaBd() {
		try {
			ManagerFactory.iniciaBD();
			assertTrue(true);
		} catch (ExcessaoBd e) {
			e.printStackTrace();
			fail("Erro ao iniciar o banco.");
		}
	}

	@Test
	@Order(1)
	public void testFindPais() {
		PaisService service = new PaisService();
		pais = service.pesquisar(Long.valueOf(1));

		assertTrue(pais != null && pais.getId() > 0);
	}

	@Test
	@Order(2)
	public void testFindEstado() {
		EstadoService service = new EstadoService();
		estado = service.pesquisar(Long.valueOf(1));

		assertTrue(estado != null && estado.getId() > 0);
	}

	@Test
	@Order(3)
	public void testSaveCidade() {
		EstadoService serviceEstado = new EstadoService();
		estado = serviceEstado.pesquisar(Long.valueOf(1));

		CidadeDao service = new CidadeDao();
		Cidade cidade = new Cidade(Long.valueOf(0), "Cascavel", "45", Situacao.ATIVO, estado);
		cidade = service.salvarAtomico(cidade).getLastEntity();

		assertTrue(cidade != null && cidade.getId() > 0);
	}

	@Test
	@Order(4)
	public void testFindCidade() {
		CidadeService service = new CidadeService();
		Cidade cidade = service.pesquisar("cascavel");

		assertTrue(cidade != null && cidade.getId() > 0);
	}

	@Test
	@Order(5)
	public void testSaveUsuario() {
		Usuario usuario = new Usuario(Long.valueOf(0), "Maria das Dores", Timestamp.valueOf(LocalDateTime.now()),
				Timestamp.valueOf(LocalDateTime.now()), "MARIA", "Observação de teste", UsuarioNivel.ADMINISTRADOR,
				Situacao.ATIVO);

		UsuarioService service = new UsuarioService();
		usuario = service.salvar(usuario);

		assertTrue(usuario.getId() > 0);
	}

	@Test
	@Order(6)
	public void testFindUsuario() {
		UsuarioService service = new UsuarioService();
		Usuario usuario = service.pesquisar(Long.valueOf(1));

		assertTrue(usuario != null && usuario.getId() > 0);
	}

	@Test
	@Order(7)
	public void testSaveEmpresa() {
		Cep cep;
		Endereco endereco;
		try {
			cep = BuscarCep.getCep("85801000");
			endereco = EnderecoDTO.toEndereco(cep);
		} catch (ExcessaoCep e) {
			e.printStackTrace();
			fail("Erro ao consultar o cep.");
			return;
		}

		Empresa empresa = new Empresa(Long.valueOf(0), "Empresa de teste", "Empresa de demonstração", "27341631000120",
				Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO, endereco.getBairro());

		empresa.addEnderecos(endereco);

		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		empresa = service.salvar(empresa);

		assertTrue(empresa.getId() > 0);
	}

	@Test
	@Order(8)
	public void testFindEmpresa() {
		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		Empresa empresa = service.pesquisar(Long.valueOf(1));

		assertTrue(empresa != null && empresa.getId() > 0);
	}

	@Test
	@Order(9)
	public void testSaveCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

		Cliente cliente = new Cliente("Hefer Ortas", "", "26353989091", "", "Teste de cadastro.", TipoPessoa.FISICO,
				Enquadramento.CLIENTE);

		cliente = service.salvar(cliente);

		assertTrue(cliente != null && cliente.getId() > 0);
	}

	@Test
	@Order(9)
	public void testFindCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		Cliente cliente = service.pesquisar(Long.valueOf(1));

		assertTrue(cliente != null && cliente.getId() > 0);
	}

	@Test
	@Order(10)
	public void testProduto() {
		assertTrue(true);
	}

}
