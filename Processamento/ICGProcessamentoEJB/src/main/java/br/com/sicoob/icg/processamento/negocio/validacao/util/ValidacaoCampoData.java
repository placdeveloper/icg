package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoData implements Validacao {

	public Boolean ehDataValida(String data) {

		try {
			if (data.length() != Constantes.TAMANHO_CAMPO_DATA) {
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(data);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampoPermitido, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();

		if (obrigatorio && ((valor == null) || StringUtil.isBlank(valor))) {
			if (obrigatorio) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}
		} else {
			if ((valor != null) && (StringUtil.isNotBlank(valor))) {
				if (!ehDataValida(valor)) {
					retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN005,
							ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
							MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
				}
			}
		}
		return retorno;
	}
}
