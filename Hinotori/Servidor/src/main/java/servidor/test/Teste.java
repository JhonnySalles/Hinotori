package servidor.test;

import java.sql.Timestamp;
import java.time.Instant;

import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;
import servidor.entities.Produto;

public class Teste {
	
	public static Produto produto1 = new Produto("Produto teste 1", "Observação de teste", "00000", "KG",
			"Marca de teste", 1.0, 2.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), 
			TipoProduto.PRODUTOFINAL, Situacao.ATIVO);
	
	public static Produto produto2 = new Produto("Produto teste 2", "Observação de teste", "00000", "KG",
			"Marca de teste", 1.0, 2.0, Timestamp.from(Instant.now()), Timestamp.from(Instant.now()), 
			TipoProduto.PRODUTOFINAL, Situacao.ATIVO);

}
