package br.com.sicoob.icg.processamento.persistencia;

import java.util.Properties;

import br.com.bancoob.infraestrutura.conexao.CorporativoDataSource;

public class ICGProcessamentoDatasource extends CorporativoDataSource {

	public ICGProcessamentoDatasource(String nomeJndi, Properties propriedades) {
		super(nomeJndi, propriedades);
	}

}
