package persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.talesolutions.cep.CEP;

import comum.cep.CepCorreios;
import comum.model.enums.Enquadramento;
import comum.model.enums.Situacao;
import comum.model.enums.TipoPessoa;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoCep;
import junit.framework.TestCase;
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

@FixMethodOrder(MethodSorters.JVM)
public class PersistenciaTest extends TestCase {

	private Pais pais;
	private Estado estado;

	@Test
	public void testFindPais() {
		PaisService service = new PaisService();
		pais = service.pesquisar(Long.valueOf(1));

		assertTrue(pais != null && pais.getId() > 0);
		fail("Erro ao pesquisar o país.");
	}

	@Test
	public void testFindEstado() {
		EstadoService service = new EstadoService();
		estado = service.pesquisar(Long.valueOf(1));

		assertTrue(estado != null && estado.getId() > 0);
		fail("Erro ao pesquisar o estado.");
	}

	@Test
	public void testSaveCidade() {
		EstadoService serviceEstado = new EstadoService();
		estado = serviceEstado.pesquisar(Long.valueOf(1));
		
		CidadeDao service = new CidadeDao();
		Cidade cidade = new Cidade(Long.valueOf(0), "Cascavel", "45", Situacao.ATIVO, estado);
		cidade = service.salvarAtomico(cidade).getLastEntity();

		assertTrue(cidade != null && cidade.getId() > 0);
		fail("Erro ao salvar a cidade.");
	}

	@Test
	public void testFindCidade() {
		CidadeService service = new CidadeService();
		Cidade cidade = service.pesquisar("cascavel");

		assertTrue(cidade != null && cidade.getId() > 0);
		fail("Erro ao pesquisar a cidade.");
	}

	@Test
	public void testSaveUsuario() {
		Usuario usuario = new Usuario(Long.valueOf(0), "Maria das Dores", Timestamp.valueOf(LocalDateTime.now()),
				Timestamp.valueOf(LocalDateTime.now()), "MARIA", "Observação de teste", UsuarioNivel.ADMINISTRADOR,
				Situacao.ATIVO);

		UsuarioService service = new UsuarioService();
		usuario = service.salvar(usuario);

		assertTrue(usuario.getId() > 0);
		fail("Erro ao salvar o usuário.");
	}

	@Test
	public void testFindUsuario() {
		UsuarioService service = new UsuarioService();
		Usuario usuario = service.pesquisar(Long.valueOf(1));

		assertTrue(usuario != null && usuario.getId() > 0);
		fail("Erro ao pesquisar o usuário.");
	}

	@Test
	public void testSaveEmpresa() {
		CEP cep;
		Endereco endereco;
		try {
			cep = CepCorreios.getCep("85801000");
			endereco = EnderecoDTO.toEndereco(cep);
		} catch (ExcessaoCep e) {
			e.printStackTrace();
			return;
		}
		
		Empresa empresa = new Empresa(Long.valueOf(0), "Empresa de teste", "Empresa de demonstração", "27341631000120",
				Timestamp.valueOf(LocalDateTime.now()), Situacao.ATIVO, endereco.getBairro());
		
		empresa.addEnderecos(endereco);

		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		empresa = service.salvar(empresa);

		assertTrue(empresa.getId() > 0);
		fail("Erro ao salvar a empresa.");
	}

	@Test
	public void testFindEmpresa() {
		GenericService<Empresa> service = new GenericService<Empresa>(Empresa.class);
		Empresa empresa = service.pesquisar(Long.valueOf(1));

		assertTrue(empresa != null && empresa.getId() > 0);
		fail("Erro ao pesquisar a empresa.");
	}

	@Test
	public void testSaveCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		
		Cliente cliente = new Cliente("Hefer Ortas", "", "26353989091", "", "Teste de cadastro.",
				TipoPessoa.FISICO, Enquadramento.CLIENTE);
		
		cliente = service.salvar(cliente);

		assertTrue(cliente != null && cliente.getId() > 0);
		fail("Erro ao salvar o cliente.");
	}
	
	@Test
	public void testFindCliente() {
		GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);
		Cliente cliente = service.pesquisar(Long.valueOf(1));

		assertTrue(cliente != null && cliente.getId() > 0);
		fail("Erro ao pesquisar o cliente.");
	}

	@Test
	public void testProduto() {
		assertTrue(true);
		fail("Erro ao salvar o usuário.");
	}

}
