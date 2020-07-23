package br.com.sicoob.icg.processamento.negocio.validacao.util;

import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public class ValidacaoCampoNumerico implements Validacao {

	/**
	 * 
	 * @param valor
	 * @return
	 */
	private Boolean ehNumerico(String valor) {
		try {
			Long.parseLong(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampoPermitido, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();

		if (obrigatorio && ((valor == null) || StringUtil.isBlank(valor))) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		} else {
			if ((valor != null) && (StringUtil.isNotBlank(valor))) {
				if (!ehNumerico(valor)) {
					retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN002,
							ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
							MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
				}

				if (Validar.validaTamanhoCampo(valor.length(), tamanhoCampoPermitido)) {
					retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN003,
							ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
							MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
				}
			}

		}
		return retorno;
	}

}
