package br.com.sicoob.icg.comum.persistencia;

import java.util.Properties;

import br.com.bancoob.infraestrutura.conexao.CorporativoDataSource;

public class ICGComumDatasource extends CorporativoDataSource {

	public ICGComumDatasource(String nomeJndi, Properties propriedades) {
		super(nomeJndi, propriedades);
	}

}
