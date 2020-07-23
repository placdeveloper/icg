package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.TelefoneVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteTelefoneEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarTelefoneUtil {

	private ValidarTelefoneUtil() {
	}

	public static ValidacaoVO validar(TelefoneVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);
		validacaoVO.getErroValidacao().append("");

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoTamanhoCampo validacaoTamanhoCampo = new ValidacaoTamanhoCampo();
		ValidacaoCampoCPFCNPJ validacaoCampoCPFCNPJ = new ValidacaoCampoCPFCNPJ();

		validacaoVO.getErroValidacao()
				.append(validacaoCampoCPFCNPJ.retornaValidacao(vo.getCpfCnpj(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CPF_CNPJ, ConstanteComumEnum.TAMANHO_CNPJ.getCodigo(), true));

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

		if (Objects.nonNull(vo.getCodigoTipoTelefone()) && StringUtil.isNotBlank(vo.getCodigoTipoTelefone())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoTelefone().toString(),
					TipoDominioEnum.TIPO_TELEFONE)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil
								.getString(Constantes.MENSAGEM_MN007, ICGProcessamentoResourceBundle.getInstance(),
										vo.getLinhaArquivo(),
										MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_TELEFONE,
												ICGProcessamentoResourceBundle.getInstance()),
										vo.getCodigoTipoTelefone()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_TELEFONE,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao().append(validacaoCampoNumerico.retornaValidacao(vo.getDdd(), vo.getLinhaArquivo(),
				Constantes.MENSAGEM_TEXTO_DDD_TELEFONE, ConstanteTelefoneEnum.TAMANHO_NUMDDD.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getTelefone(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NUMERO_TELEFONE,
						ConstanteTelefoneEnum.TAMANHO_NUMTELEFONE.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getRamal(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_RAMAL_TELEFONE, ConstanteTelefoneEnum.TAMANHO_NUMRAMAL.getCodigo(),
						false));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getDescObservacao(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_OBSERVACAO_TELEFONE,
						ConstanteTelefoneEnum.TAMANHO_DESCOBSERVACAO.getCodigo(), false));

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;
	}

}