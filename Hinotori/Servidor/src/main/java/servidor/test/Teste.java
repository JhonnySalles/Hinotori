package servidor.test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoContato;
import comum.model.enums.TipoEndereco;
import comum.model.enums.TipoPessoa;
import comum.model.enums.TipoProduto;
import comum.model.enums.UsuarioNivel;
import servidor.entities.Bairro;
import servidor.entities.Cidade;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Empresa;
import servidor.entities.Endereco;
import servidor.entities.Estado;
import servidor.entities.Ncm;
import servidor.entities.Pais;
import servidor.entities.Produto;
import servidor.entities.Usuario;
import servidor.util.HibernateUtil;

public class Teste {

	public static Pais pais = new Pais((long) 1, "Brasil");
	public static Estado estado = new Estado((long) 1, "São Paulo", "SP", 1125, pais);
	public static Cidade cidade = new Cidade((long) 1, "São Paulo", "41", Situacao.ATIVO, estado);
	public static Bairro bairro = new Bairro((long) 1, "São vicente", cidade);

	public static Contato contato_usuario = new Contato((long) 2, "Fernanda", "32212545", "999882252",
			"fernanda@hotmail.com", "Telefone opcional", Timestamp.from(Instant.now()), TipoContato.RESIDENCIAL,
			Situacao.INATIVO, false);
	public static Usuario usuario = new Usuario((long) 6, "São vicente", Timestamp.from(Instant.now()),
			Timestamp.from(Instant.now()), "VICENTE", "Usuário Administrador", UsuarioNivel.ADMINISTRADOR,
			Situacao.ATIVO);

	public static Contato contato_cliente = new Contato((long) 5, "Jorge", "32255123", "999982562", "jorge@hotmail.com",
			"Contato", Timestamp.from(Instant.now()), TipoContato.RESIDENCIAL, Situacao.INATIVO, false);
	public static Endereco endereco_cliente = new Endereco(bairro, "Rua 15 de setembro", "2254", "77024038", "Casa",
			"Segundo andar", Timestamp.from(Instant.now()), TipoEndereco.ENTREGA, Situacao.ATIVO, true);
	public static Cliente cliente = new Cliente((long) 1, "Luis da Silva", Timestamp.from(Instant.now()),
			Timestamp.from(Instant.now()), "67206653081", "38577711000104", "", TipoPessoa.FISICO, TipoCliente.CLIENTE,
			Situacao.ATIVO);

	public static Contato contato_empresa = new Contato((long) 5, "Whurgiurt", "32255123", "", "whurgiurt@gmail.com",
			"", Timestamp.from(Instant.now()), TipoContato.RESIDENCIAL, Situacao.INATIVO, false);
	public static Endereco endereco_empresa = new Endereco(bairro, "Rua 18 de maio", "5413", "08490360", "", "Matriz",
			Timestamp.from(Instant.now()), TipoEndereco.COMERCIAL, Situacao.ATIVO, true);
	public static Empresa empresa = new Empresa((long) 2, "Empresa de demonstração", "Razão Social", "68996932000170",
			Timestamp.from(Instant.now()), Situacao.ATIVO);

	public static Ncm ncm = new Ncm("03022200",
			"Peixes frescos ou refrigerados, exceto os fil?s de peixes e outra carne de peixes da posi??o 03.04. Peixes chatos (Pleuronectidae, Bothidae, Cynoglossidae, Soleidae, Scophthalmidae e Citharidae), exceto f?gados, ovas e s?men: Solha (Pleuronectes platessa) ");

	public static Produto produto = new Produto("Produto teste 1", "Observação de teste", "00000", "KG",
			"Marca de teste", 5.2, 12.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
			TipoProduto.PRODUZIDO, Situacao.ATIVO);

	public static Produto produto2 = new Produto("Produto teste 2", "Observação de teste 2", "00000", "PG",
			"Marca de teste", 5.0, 14.5, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
			TipoProduto.PRODUTOFINAL, Situacao.INATIVO);

	public void Salvar() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student objects
			System.out.println("Salvando país");
			session.save(pais);

			System.out.println("Salvando estado");
			session.save(estado);

			System.out.println("Salvando cidade");
			session.save(cidade);

			System.out.println("Salvando bairro");
			session.save(bairro);

			transaction.commit();

			transaction = session.beginTransaction();

			System.out.println("Salvando usuario");
			session.save(usuario);

			transaction.commit();

			transaction = session.beginTransaction();

			System.out.println("Salvando cliente");
			session.save(cliente);

			transaction.commit();

			transaction = session.beginTransaction();

			System.out.println("Salvando usuário");
			session.save(usuario);

			transaction.commit();

			transaction = session.beginTransaction();

			System.out.println("Salvando produtos");
			session.save(produto);
			session.save(produto2);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void Carregar() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			System.out.println("Carregando produto");

			String hql = "from Produto where id = 1";
			Query<?> query = session.createQuery(hql);
			query.setMaxResults(1);
			Produto produto = (Produto) query.uniqueResult();
			System.out.println(produto.toString());

			System.out.println("Carregando cliente");
			hql = "from Clientes where NomeSobrenome = :nome";
			query = session.createQuery(hql);
			query.setString("nome", "Jorge");
			List<?> results = query.list();

			for (Object cl : results)
				System.out.println(cl.toString());

			System.out.println("Carregando pais");
			hql = "from Paises where nome = ''Brasil''";
			query = session.createQuery(hql);
			Pais pais = (Pais) query.list().get(0);

			System.out.println(pais.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Iniciar() {
		Salvar();
		Carregar();
	}

	public Teste() {
		usuario.addContatos(contato_usuario);
		cliente.addContatos(contato_cliente);
		cliente.addEnderecos(endereco_cliente);
		empresa.addContatos(contato_empresa);
		empresa.addEnderecos(endereco_empresa);
		produto.setNcm(ncm);
		produto.setNcm(ncm);
	}

}
