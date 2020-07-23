package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.processamento.negocio.enums.OpcaoSimNaoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoSimNao implements Validacao {

	private Boolean validar(String valor) {
		Boolean retorno = true;
		OpcaoSimNaoEnum opcaoSimNaoEnum = OpcaoSimNaoEnum.obterPorCod(valor);

		if (Objects.isNull(opcaoSimNaoEnum)) {
			retorno = false;
		}
		
		return retorno;
	}

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampo, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();
		
		if (!validar(valor)) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		}
		return retorno;
	}

}
