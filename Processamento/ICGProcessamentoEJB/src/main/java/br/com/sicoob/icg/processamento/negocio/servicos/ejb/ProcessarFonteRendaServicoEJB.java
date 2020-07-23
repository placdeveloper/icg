package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.FonteRendaDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.FonteRendaDTO;
import br.com.sicoob.capes.api.negocio.vo.FonteRendaPessoaVO;
import br.com.sicoob.icg.comum.negocio.servicos.FonteRendaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.FonteRendaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.negocio.enums.TipoPessoaEnum;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoFonteRendaServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */

@Stateless
@Local(ProcessamentoFonteRendaServico.class)
public class ProcessarFonteRendaServicoEJB extends ProcessarServicoEJB<FonteRendaImportacao>
		implements ProcessamentoFonteRendaServico {

	@EJB
	private FonteRendaImportacaoServico servico;

	private FonteRendaDelegate fonteRendaDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia()
			.criarFonteRendaDelegate();

	@Override
	protected FonteRendaImportacao instanciarFiltro() {
		return new FonteRendaImportacao();
	}

	@Override
	protected FonteRendaImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(FonteRendaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {

		List<FonteRendaPessoaVO> filtros = pesquisarFonteRendas(pessoaBasicaVO);

		Optional<FonteRendaPessoaVO> optVO = retornaOptFonteRendaPessoaVO(vo, filtros);

		FonteRendaDTO dto = montarFonteRendaDTO(vo, pessoaBasicaVO, optVO);

		if (optVO.isPresent()) {
			getLogger().info(Constantes.MENSAGEM_ALTERACAO_CAPES + vo.getId());
			alterarFonteRendaDTO(dto);
		} else {
			getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getId());
			incluirFonteRendaDTO(dto);
		}

	}

	/***
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	private List<FonteRendaPessoaVO> pesquisarFonteRendas(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		getLogger().info("Lista as fontes de renda da pessoa no CAPES");
		return ConsultasUtil.pesquisarFonteRendas(pessoaBasicaVO);
	}


	/**
	 * 
	 * @param vo
	 * @param filtro
	 * @return
	 */
	private Optional<FonteRendaPessoaVO> retornaOptFonteRendaPessoaVO(FonteRendaImportacao vo,
			List<FonteRendaPessoaVO> filtro) {
		/**
		 * Regra PJ: SE EXISTIR FONTE DE RENDA CADASTRADA REALIZA A ALTERA (SÓ PODE
		 * HAVER UMA FONTE DE RENDA PARA PJ)
		 * 
		 */
		getLogger().info("Retorna a primeira fonte de renda da lista");

		Optional<FonteRendaPessoaVO> optVO = null;
		if (!filtro.isEmpty()
				&& vo.getCodigoTipoPessoa().equals(TipoPessoaEnum.PESSOA_JURIDICA.getCodigo().toString())) {
			optVO = filtro.stream().findFirst();
		} else {
			optVO = filtro.stream()
					.filter(f -> Objects.nonNull(f.getCodigoTipoFonteRenda())
							&& Objects.nonNull(f.getDataValidadeRenda())
							&& f.getCodigoTipoFonteRenda().toString().equals(vo.getCodigoTipoFonteRenda())
							&& f.getDataValidadeRenda().equals((vo.getDataValidadeRenda())))
					.findFirst();

			if (!optVO.isPresent()) {
				optVO = Optional.empty();
			}
		}
		return optVO;
	}

	/**
	 * 
	 * @param vo
	 * @param pessoaBasicaVO
	 * @param optVO
	 * @return
	 */
	public FonteRendaDTO montarFonteRendaDTO(FonteRendaImportacao vo, PessoaBasicaVO pessoaBasicaVO,
			Optional<FonteRendaPessoaVO> optVO) {
		getLogger().info("Monta o DTO de fonte de renda");
		FonteRendaDTO dto = new FonteRendaDTO();

		if (optVO.isPresent()) {
			dto.setIdFonteRenda(optVO.get().getIdFonteRenda());
		}

		dto.setIdInstituicao(vo.getIdInstituicao());
		dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
		dto.setBolRendaFixa(Validar.converterParaBoolen(vo.getRendaFixa().toString()));
		dto.setBolSimplesNacional(Validar.converterParaBoolen(vo.getSimplesNacional().toString()));
		dto.setCodigoTipoFonteRenda(Validar.converterParaShort(vo.getCodigoTipoFonteRenda()));
		dto.setDataValidadeRenda(vo.getDataValidadeRenda());
		dto.setValorReceitaBrutaMensal(vo.getValorReceitaBrutaMensal());
		dto.setIdUnidadeInst(pessoaBasicaVO.getIdUnidadeInst());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setVerificarAutorizacao(pessoaBasicaVO.getVerificarAutorizacao());

		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	public void alterarFonteRendaDTO(FonteRendaDTO dto) throws BancoobException {
		getLogger().info("Altera os dados da fonte de renda da pessoa no CAPES");
		fonteRendaDelegate.alterar(dto);
	}

	/**
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	public void incluirFonteRendaDTO(FonteRendaDTO dto) throws BancoobException {
		getLogger().info("Cadastra nova fonte de renda no CAPES");
		fonteRendaDelegate.incluir(dto);
	}
}
