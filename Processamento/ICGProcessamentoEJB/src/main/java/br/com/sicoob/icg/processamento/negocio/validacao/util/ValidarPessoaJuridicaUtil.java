package br.com.sicoob.icg.processamento.negocio.validacao.util;

import java.util.Objects;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.util.MensagemUtil;
import br.com.bancoob.util.StringUtil;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaJuridicaVO;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.enums.ConstanteComumEnum;
import br.com.sicoob.icg.processamento.negocio.enums.ConstantePessoaJuridicaEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

public class ValidarPessoaJuridicaUtil {

	private ValidarPessoaJuridicaUtil() {
	}

	public static ValidacaoVO validar(PessoaJuridicaVO vo) throws BancoobException {

		ValidacaoVO validacaoVO = new ValidacaoVO();
		validacaoVO.setRegistroValido(true);
		validacaoVO.getErroValidacao().append("");

		ValidacaoCampoNumerico validacaoCampoNumerico = new ValidacaoCampoNumerico();
		ValidacaoTamanhoCampo validacaoTamanhoCampo = new ValidacaoTamanhoCampo();
		ValidacaoCampoData validacaoCampoData = new ValidacaoCampoData();
		ValidacaoCampoCPFCNPJ validacaoCampoCPFCNPJ = new ValidacaoCampoCPFCNPJ();

		validacaoVO.getErroValidacao()
				.append(validacaoCampoCPFCNPJ.retornaValidacao(vo.getCpfCnpj(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_CPF_CNPJ, ConstanteComumEnum.TAMANHO_CPF.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomePessoa(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_PESSOA_JURIDICA,
						ConstantePessoaJuridicaEnum.TAMANHO_RAZAO_SOCIAL.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNomeCompleto(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_NOME_COMPLETO_PESSOA_JURIDICA,
						ConstantePessoaJuridicaEnum.TAMANHO_RAZAO_SOCIAL_COMPLETO.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoNumerico.retornaValidacao(vo.getCodigoEsferaAdministrativa(),
						vo.getLinhaArquivo(), Constantes.MENSAGEM_TEXTO_ESFERA_ADMINISTRATIVA,
						ConstantePessoaJuridicaEnum.TAMANHO_CODESFERAADMINISTRATIVA.getCodigo(), true));

		if (Objects.nonNull(vo.getCodigoAtividadeEconomica()) && StringUtil.isNotBlank(vo.getCodigoAtividadeEconomica())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoAtividadeEconomica().toString(),
					TipoDominioEnum.ATIVIDADE_ECONOMICA)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
								ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
								MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_ATIVIDADE_ECONOMICA,
										ICGProcessamentoResourceBundle.getInstance()),
								vo.getCodigoAtividadeEconomica()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_ATIVIDADE_ECONOMICA,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoTamanhoCampo.retornaValidacao(vo.getNumeroRegistroJuntaComercial(),
						vo.getLinhaArquivo(), Constantes.MENSAGEM_TEXTO_NUMERO_REGISTRO_ORGAO_COMPETENTE,
						ConstantePessoaJuridicaEnum.TAMANHO_NUMREGISTROJUNTACOMERCIAL.getCodigo(), false));

		if (Objects.nonNull(vo.getCodigoTipoFormaConstituicao()) && StringUtil.isNotBlank(vo.getCodigoTipoFormaConstituicao())) {
			if (!ConsultasUtil.ehCodigoDominioValido(vo.getCodigoTipoFormaConstituicao().toString(),
					TipoDominioEnum.TIPO_FORMA_CONSTITUICAO)) {
				validacaoVO.getErroValidacao()
						.append(MensagemUtil.getString(Constantes.MENSAGEM_MN007,
								ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
								MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_FORMA_CONSTITUICAO,
										ICGProcessamentoResourceBundle.getInstance()),
								vo.getCodigoTipoFormaConstituicao()));
			}
		} else {
			validacaoVO.getErroValidacao()
					.append(MensagemUtil.getString(Constantes.MENSAGEM_MN009,
							ICGProcessamentoResourceBundle.getInstance(), vo.getLinhaArquivo(),
							MensagemUtil.getString(Constantes.MENSAGEM_TEXTO_CODIGO_TIPO_FORMA_CONSTITUICAO,
									ICGProcessamentoResourceBundle.getInstance()),
							""));
		}

		validacaoVO.getErroValidacao()
				.append(validacaoCampoData.retornaValidacao(vo.getDataConstituicao(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DATA_CONSTITUICAO,
						ConstantePessoaJuridicaEnum.TAMANHO_DATA.getCodigo(), true));

		validacaoVO.getErroValidacao()
				.append(validacaoCampoData.retornaValidacao(vo.getDataRegistroJuntaComercial(), vo.getLinhaArquivo(),
						Constantes.MENSAGEM_TEXTO_DATA_REGISTRO, ConstantePessoaJuridicaEnum.TAMANHO_DATA.getCodigo(),
						false));

		if (StringUtil.isNotBlank(validacaoVO.getErroValidacao().toString())) {
			validacaoVO.setRegistroValido(false);
		}

		return validacaoVO;
	}
}