package Config.util;

import Config.gui.TelaConfiguracaoController;
import model.config.ProcessaConfig;
import model.entitis.Conexao;

public class CarregaConfig {

	public static Conexao dadosConexao = ProcessaConfig.getDadosConexao();

	public void processaConfig(TelaConfiguracaoController cntr) {
		cntr.setConfig(dadosConexao);
	}

}
