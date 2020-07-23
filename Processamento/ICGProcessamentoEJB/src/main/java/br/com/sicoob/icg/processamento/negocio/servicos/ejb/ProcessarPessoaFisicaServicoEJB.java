package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.PessoaDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.PessoaFisicaDTO;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.icg.comum.negocio.servicos.PessoaFisicaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.PessoaFisicaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoPessoaServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.util.Constantes;

/**
 * 
 * @author Pablo.Andrade
 *
 */

@Stateless
@Local(ProcessamentoPessoaServico.class)
public class ProcessarPessoaFisicaServicoEJB extends ProcessarServicoEJB<PessoaFisicaImportacao>
		implements ProcessamentoPessoaServico {

	@EJB
	private PessoaFisicaImportacaoServico servico;

	private PessoaDelegate pessoaDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia().criarPessoaDelegate();

	@Override
	protected PessoaFisicaImportacao instanciarFiltro() {
		return new PessoaFisicaImportacao();
	}

	@Override
	protected PessoaFisicaImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(PessoaFisicaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		getLogger().info(Constantes.MENSAGEM_ALTERACAO_CAPES + vo.getId());
		PessoaFisicaDTO dto = montarPessoaFisicaDTO(vo, pessoaBasicaVO);
		pessoaDelegate.alterar(dto);
	}

	@Override
	protected void executarPessoaInexistente(PessoaFisicaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getId());
		PessoaFisicaDTO dto = montarPessoaFisicaDTO(vo, pessoaBasicaVO);
		pessoaDelegate.incluir(dto);
	}

	/**
	 * 
	 * @param vo
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	private PessoaFisicaDTO montarPessoaFisicaDTO(PessoaFisicaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		getLogger().info("Monta PessoaFisicaDTO");

		PessoaFisicaDTO dto = new PessoaFisicaDTO();
		if (pessoaBasicaVO != null) {
			dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
			dto.setIdUnidadeInst(pessoaBasicaVO.getIdUnidadeInst());
			dto.setVerificarAutorizacao(pessoaBasicaVO.getVerificarAutorizacao());
		} else {
			dto.setIdUnidadeInst(Constantes.ICG_UNIDADE_INST);
			dto.setVerificarAutorizacao(false);
		}
		dto.setIdInstituicao(vo.getIdInstituicao());
		dto.setDataNascimento(vo.getDataNascimento());
		dto.setMenorEmancipado(Validar.converterParaBoolen(vo.getMenorEmancipado().toString()));
		dto.setNomePai(vo.getNomepai());
		dto.setNomeMae(vo.getNomeMae());
		dto.setCodigoTipoDocumento(!vo.getCodigoTipoDocumentoIdentificacao().isEmpty() ? Validar.converterParaShort(vo.getCodigoTipoDocumentoIdentificacao()) : null);
		dto.setNumeroDocumento(vo.getNumeroDocumentoIdentificacao());
		dto.setOrgaoExpedidorDocumento(vo.getDescricaoOrgaoExpedidorDocumentoIdentificacao());
		dto.setUfOrgaoExpedidorDocumento(vo.getSiglaUfOrgaoExpedidorDocumentoIdentificacao());
		dto.setDataEmissaoDocumento(vo.getDataEmissaoDocumentoIdentificacao());
		dto.setTipoSexo(Validar.converterParaCharacter(vo.getCodigpTipoSexo()));
		dto.setCodigoOcupacaoProfissional(Validar.converterParaInteger(vo.getCodigoOcupacaoProfissional().toString()));
		dto.setCodigoEstadoCivil(Validar.converterParaShort(vo.getCodigoEstadoCivil()));
		dto.setCodigoRegimeCasamento(Validar.converterParaShort(vo.getCodigoRegimeCasamento()));
		dto.setQuantidadeDependentes(Validar.converterParaShort(vo.getQuantidadeDependente().toString()));
		dto.setUniaoEstavel(Validar.converterParaBoolen(vo.getUniaoEestavel().toString()));
		dto.setCodigoGrauInstrucao(Validar.converterParaShort(vo.getCodigoGrauInstrucao()));
		dto.setDescNaturalidade(vo.getDescricaoNaturalidade());
		dto.setIdNaturalidade(Validar.converterParaInteger(vo.getCodigoNaturalidade().toString()));
		dto.setCodigoVinculoEmpregaticio(Validar.converterParaShort(vo.getCodigoVinculoEmpregaticio()));
		dto.setCodigoNacionalidade(Validar.converterParaShort(Constantes.CODIGO_NACIONALIDADE));
		dto.setCpfCnpj(vo.getNumeroCpfCnpj());
		dto.setNomePessoa(vo.getNomePessoa());
		dto.setNomeCompleto(vo.getNomeCompleto());
		dto.setDataInclusaoSistema(new Date());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setCodigoAtividadeEconomica(Validar.converterParaShort(vo.getCodigoAtividadeEconomica()));
		if (vo.getNumeroCpfConjuge() != null) {
			PessoaVO pessoaConjuge = ConsultasUtil.recuperaInstituicaoConjuge(vo.getNumeroCpfConjuge(),
					vo.getIdInstituicao());
			if (pessoaConjuge != null) {
				dto.setIdPessoaConjuge(pessoaConjuge.getIdPessoa());
				dto.setIdInstituicaoConjuge(pessoaConjuge.getIdInstituicao());
			}
		}

		return dto;
	}
}
