package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoPessoaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidacaoCampoTipoPessoa implements Validacao {

	public static Boolean ehTipoPessoaValido(String valor) {
		return ((valor != null) && (TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString().equals(valor)
				|| TipoPessoaEnum.PESSOA_JURIDICA.getCodigo().toString().equals(valor)));
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
			if (!ehTipoPessoaValido(valor)) {
				retorno.append(MensagemUtil.getString(Constantes.MENSAGEM_MN013,
						ICGProcessamentoResourceBundle.getInstance(), linhaArquivo,
						MensagemUtil.getString(campo, ICGProcessamentoResourceBundle.getInstance()), valor));
			}

		}
		return retorno;
	}
}
