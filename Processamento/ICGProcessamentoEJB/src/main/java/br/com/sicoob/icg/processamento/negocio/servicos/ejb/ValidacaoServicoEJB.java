package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.nio.file.Path;

import javax.ejb.EJB;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.servicos.ejb.BancoobServicoEJB;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoExtensaoArquivoEnum;
import br.com.sicoob.icg.processamento.negocio.entidades.vo.ValidacaoVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ValidacaoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ArquivoUtil;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;

public abstract class ValidacaoServicoEJB extends BancoobServicoEJB implements ValidacaoServico {

	@EJB
	private ImportacaoArquivoServicoLocal importaArquivoServico;

	@Override
	public void iniciaValidacaoArquivo(ImportaArquivo arquivo) throws BancoobException {
		ValidacaoVO validacaoVO = validar(arquivo);
		atualizaSituacaoProcessamentoArquivo(arquivo, validacaoVO);
	}

	private ValidacaoVO validar(ImportaArquivo importaArquivo) {
		ValidacaoVO validacaoVO = null;
		final Path diretorioArquivo = ArquivoUtil.recuperaDiretorioArquivo(importaArquivo);
		String extensao = Validar.retornaExtensaoArquivo(importaArquivo.getNomeArquivo());

		if (extensao.equals(TipoExtensaoArquivoEnum.CSV.getDescricao())) {
			validacaoVO = validarCSV(importaArquivo, diretorioArquivo);
		} else {
			validacaoVO = validarJson(importaArquivo, diretorioArquivo);
		}

		return validacaoVO;
	}

	protected abstract ValidacaoVO validarCSV(ImportaArquivo importaArquivo, Path diretorioArquivo);

	protected abstract ValidacaoVO validarJson(ImportaArquivo importaArquivo, Path diretorioArquivo);

	protected void atualizaSituacaoProcessamentoArquivo(ImportaArquivo importaArquivo, ValidacaoVO validacaoVO)
			throws BancoobException {
		getLogger().info("atualiza situacao da validacao do arquivo");
		if (Validar.possuiErroValidacao(validacaoVO.getErroValidacao()) && validacaoVO.getHouveInclusao()) {
			importaArquivo
					.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.VALIDADO_PARCIALMENTE.getIdSituacao());
		} else {
			if (Validar.possuiErroValidacao(validacaoVO.getErroValidacao()) && !validacaoVO.getHouveInclusao()) {
				importaArquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_VALIDACAO.getIdSituacao());
			} else {
				importaArquivo
						.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.ARQUIVO_VALIDADO.getIdSituacao());
			}
		}

		importaArquivo.setDescricaoErro(validacaoVO.getErroValidacao().toString());
		importaArquivoServico.alterar(importaArquivo);

	}
}
