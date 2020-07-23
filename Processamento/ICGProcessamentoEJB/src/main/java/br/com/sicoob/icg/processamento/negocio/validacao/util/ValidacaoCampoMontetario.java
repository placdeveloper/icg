package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoMontetario implements Validacao {

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampoPermitido, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();

		if (Objects.isNull(valor) || valor.isEmpty()) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		} else {

			if (!Validar.isBigDecimal(valor)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN008,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}
		}
		return retorno;
	}
}
