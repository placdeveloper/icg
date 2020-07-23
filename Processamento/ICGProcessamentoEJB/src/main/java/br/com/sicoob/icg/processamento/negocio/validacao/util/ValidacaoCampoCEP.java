package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteEnderecoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoCEP implements Validacao {

	public Boolean ehNumerico(String valor) {
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
		
		if (Objects.isNull(valor) || valor.isEmpty()) {
			retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
					ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
					MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
		} else {
			if (!ehNumerico(valor)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN002,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}

			if (!ConstanteEnderecoEnum.TAMANHO_CODCEP.getCodigo().equals(tamanhoCampoPermitido)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN003,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}

		}
		return retorno;
	}
}
