package servidor.dto;

import org.talesolutions.cep.CEP;

import servidor.dao.BairroDao;
import servidor.dao.CidadeDao;
import servidor.dao.EstadoDao;
import servidor.dao.PaisDao;
import servidor.entities.Bairro;
import servidor.entities.Cidade;
import servidor.entities.Endereco;
import servidor.entities.Estado;
import servidor.entities.Pais;

public class EnderecoDTO {

	private static PaisDao servicePais = new PaisDao();
	private static EstadoDao serviceEstado = new EstadoDao();
	private static CidadeDao serviceCidade = new CidadeDao();
	private static BairroDao serviceBairro = new BairroDao();
	
	public static Endereco toEndereco(CEP cep) {
		Bairro bairro = serviceBairro.pesquisar(cep.getBairro().trim());
		
		if (bairro == null) {
			Cidade cidade = serviceCidade.pesquisar(cep.getLocalidade().trim());
			
			if (cidade == null) {
				Estado estado = serviceEstado.pesquisar(cep.getUf().trim());
				
				if (estado == null) {
					Pais pais = servicePais.brasil();
					
					estado = new Estado(cep.getUf(), cep.getUf().trim(), pais);
					serviceEstado.salvarAtomico(estado);			
				}
				
				cidade = new Cidade(cep.getLocalidade().trim(), "", estado);
				cidade.setNome(cep.getLocalidade().trim());
				serviceCidade.salvarAtomico(cidade);
			}
			
			bairro = new Bairro(0L, cep.getBairro().trim(), cidade);
			serviceBairro.salvarAtomico(bairro);
		}

		return new Endereco(bairro, cep.getLogradouro().trim(), cep.getNumero().trim());
	}

}
