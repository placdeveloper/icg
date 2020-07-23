package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoEmail implements Validacao {

	private Boolean ehEmailValido(String email) {
		boolean ehValido = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				ehValido = true;
			}
		}
		return ehValido;
	}

	@Override
	public StringBuilder retornaValidacao(String valor, String linhaArquivo, String campo,
			Integer tamanhoCampoPermitido, Boolean obrigatorio) {
		StringBuilder retorno = new StringBuilder();
		
		if (!ehEmailValido(valor)) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		}

		if (Validar.validaTamanhoCampo(valor.length(), tamanhoCampoPermitido)) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN003,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		}

		return retorno;
	}
}
