package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.EmailVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteEmailEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarEmailUtil {

	private ValidarEmailUtil() {
	}

	public static ValidacaoVO validar(EmailVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);
		validacaoVO.getErroValidacao().append("");

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoCampoTipoPessoa validacaoCampoTipoPessoa = new ValidacaoCampoTipoPessoa();
		ValidacaoCampoEmail validacaoCampoEmail = new ValidacaoCampoEmail();

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCpfCnpj(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CPF_CNPJ, ConstanteComumEnum.TAMANHO_CPF_CNPJ.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoTipoPessoa()) && StringUtil.isNotBlank(vo.getCodigoTipoPessoa())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoPessoa().toString(),
					TipoDominioEnum.TIPO_PESSOA)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_PESSOA,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoPessoa()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_PESSOA,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		if (Objects.nonNull(vo.getCodigoTipoEmail()) && StringUtil.isNotBlank(vo.getCodigoTipoEmail())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoEmail().toString(), TipoDominioEnum.TIPO_EMAIL)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_EMAIL,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoEmail()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_EMAIL,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao().append(validacaoCampoEmail.retornaValidacao(vo.getEmail(), vo.getLinhaArquivo(),
				Constantes.MENSAGEM_TEXTO_EMAIL, ConstanteEmailEnum.TAMANHO_DESCEMAIL.getCodigo(), true));

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;
	}

}