package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.PessoaDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.PessoaJuridicaDTO;
import br.com.sicoob.icg.comum.negocio.servicos.PessoaJuridicaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.PessoaJuridicaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoPessoaJuridicaServico;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */

@Stateless

@Local(ProcessamentoPessoaJuridicaServico.class)
public class ProcessarPessoaJuridicaServicoEJB extends ProcessarServicoEJB<PessoaJuridicaImportacao>
		implements ProcessamentoPessoaJuridicaServico {

	@EJB
	private PessoaJuridicaImportacaoServico servico;

	private PessoaDelegate pessoaDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia().criarPessoaDelegate();

	@Override
	protected PessoaJuridicaImportacao instanciarFiltro() {
		return new PessoaJuridicaImportacao();
	}

	@Override
	protected PessoaJuridicaImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(PessoaJuridicaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		getLogger().info(Constantes.MENSAGEM_ALTERACAO_CAPES + vo.getId());
		PessoaJuridicaDTO dto = montarPessoaJuridicaDTO(vo, pessoaBasicaVO);
		pessoaDelegate.alterar(dto);
	}

	@Override
	protected void executarPessoaInexistente(PessoaJuridicaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getId());
		PessoaJuridicaDTO dto = montarPessoaJuridicaDTO(vo, pessoaBasicaVO);
		pessoaDelegate.incluir(dto);
	}

	/**
	 * 
	 * @param vo
	 * @param pessoaBasicaVO
	 * @return
	 */
	private PessoaJuridicaDTO montarPessoaJuridicaDTO(PessoaJuridicaImportacao vo, PessoaBasicaVO pessoaBasicaVO) {
		getLogger().info("Monta PessoaJuridicaDTO");

		PessoaJuridicaDTO dto = new PessoaJuridicaDTO();
		if (pessoaBasicaVO != null) {
			dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
			dto.setIdUnidadeInst(pessoaBasicaVO.getIdUnidadeInst());
			dto.setVerificarAutorizacao(pessoaBasicaVO.getVerificarAutorizacao());
		} else {
			dto.setIdUnidadeInst(Constantes.ICG_UNIDADE_INST);
			dto.setVerificarAutorizacao(false);
		}
		dto.setCpfCnpj(vo.getNumeroCpfCnpj());
		dto.setNomePessoa(vo.getNomeRazaoSocial());
		dto.setNomeCompleto(vo.getNomeRazaoSocialCompleto());
		dto.setCodigoAtividadeEconomica(Validar.converterParaShort(vo.getCodigoAtividadeEconomica()));
		dto.setCodigoEsferaAdministrativa(Validar.converterParaShort(vo.getCodigoEsferaAdministrativa()));
		dto.setDataConstituicao(vo.getDataConstituicao());
		dto.setDataRegistroJuntaComercial(vo.getDataRegistroJuntaComercial());
		dto.setNumeroRegistroJuntaComercial(vo.getNumeroRegistroJuntaComercial());
		dto.setIdInstituicao(vo.getIdInstituicao());
		dto.setDataInclusaoSistema(new Date());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setCodigoFormaConstituicao(Validar.converterParaShort(vo.getCodigoAtividadeEconomica()));

		return dto;
	}

}
