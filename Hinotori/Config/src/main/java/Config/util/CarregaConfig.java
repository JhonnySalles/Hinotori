package config.util;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Conexao;
import config.gui.TelaConfiguracaoController;

public class CarregaConfig {

	public static Conexao dadosConexao = ProcessaConfig.getDadosConexao();

	public void processaConfig(TelaConfiguracaoController cntr) {
		cntr.setConfig(dadosConexao);
	}

}
