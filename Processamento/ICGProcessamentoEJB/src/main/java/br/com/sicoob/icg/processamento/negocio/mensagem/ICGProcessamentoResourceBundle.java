package br.com.sicoob.icg.processamento.negocio.mensagem;

import java.util.ResourceBundle;

import br.com.bancoob.infraestrutura.mensagens.BancoobResourceBundle;
import br.com.bancoob.infraestrutura.mensagens.CorporativoResourceBundle;

public final class ICGProcessamentoResourceBundle extends BancoobResourceBundle {

	public static final String ICG_PROCESSAMENTO_PROPERTIES = "icg.processamento.properties";

	private static ICGProcessamentoResourceBundle bundle = new ICGProcessamentoResourceBundle();

	public static ICGProcessamentoResourceBundle getInstance() {
		return bundle;
	}

	private ICGProcessamentoResourceBundle() {
		this(ICG_PROCESSAMENTO_PROPERTIES);
	}

	protected ICGProcessamentoResourceBundle(String arquivoProperties) {
		this(arquivoProperties, CorporativoResourceBundle.getInstance());
	}

	protected ICGProcessamentoResourceBundle(String arquivoProperties, ResourceBundle resourcePai) {
		super(arquivoProperties, resourcePai);
	}
}
