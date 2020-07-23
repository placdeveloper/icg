package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.TelefoneDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.TelefoneDTO;
import br.com.sicoob.capes.api.negocio.vo.TelefonePessoaVO;
import br.com.sicoob.icg.comum.negocio.servicos.TelefoneImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.TelefonePessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoTelefoneServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.util.Constantes;

/**
 * 
 * @author Pablo.Andrade
 *
 */

@Stateless

@Local(ProcessamentoTelefoneServico.class)
public class ProcessarTelefoneServicoEJB extends ProcessarServicoEJB<TelefonePessoaImportacao>
		implements ProcessamentoTelefoneServico {

	@EJB
	private TelefoneImportacaoServico servico;

	private TelefoneDelegate telefoneDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia()
			.criarTelefoneDelegate();

	@Override
	protected TelefonePessoaImportacao instanciarFiltro() {
		return new TelefonePessoaImportacao();
	}

	@Override
	protected TelefoneImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(TelefonePessoaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		List<TelefonePessoaVO> filtros = pesquisarTelefones(pessoaBasicaVO);

		Optional<TelefonePessoaVO> optVO = Optional.empty();
		if (!filtros.isEmpty()) {
			optVO = retornaOptTelefonePessoaVO(vo, filtros);
		}

		TelefoneDTO dto = montarTelefoneDTO(vo, optVO, pessoaBasicaVO);

		if (Objects.nonNull(dto.getIdTelefonePessoa())) {
			getLogger().info(Constantes.MENSAGEM_ALTERACAO_CAPES + vo.getIdTelefone());
			alterarTelefoneDTO(dto);
		} else {
			getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getIdTelefone());
			incluirTelefoneDTO(dto);
		}

	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	private List<TelefonePessoaVO> pesquisarTelefones(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		getLogger().info("Lista os telefones da pessoa no CAPES");
		return ConsultasUtil.pesquisarTelefones(pessoaBasicaVO);
	}

	/**
	 * 
	 * @param vo
	 * @param filtros
	 * @return
	 */
	private Optional<TelefonePessoaVO> retornaOptTelefonePessoaVO(TelefonePessoaImportacao vo,
			List<TelefonePessoaVO> filtros) {
		getLogger().info("Retorna o primeiro telefone da lista de acordo com os parametros");
		Optional<TelefonePessoaVO> optVO = filtros
				.stream().filter(f -> f.getDdd().equals(vo.getNumeroDdd())
						&& f.getTelefone().equals(vo.getNumeroTelefone()) && f.getRamal().equals(vo.getNumeroRamal()))
				.findFirst();
		if (!optVO.isPresent()) {
			optVO = Optional.empty();
		}

		return optVO;
	}

	/**
	 * 
	 * @param vo
	 * @param pessoaBasicaVO
	 * @return
	 */
	public TelefoneDTO montarTelefoneDTO(TelefonePessoaImportacao vo, Optional<TelefonePessoaVO> optVO,
			PessoaBasicaVO pessoaBasicaVO) {
		getLogger().info("Monta o DTO de telefone");
		TelefoneDTO dto = new TelefoneDTO();
		if (optVO.isPresent()) {
			dto.setIdTelefonePessoa(optVO.get().getIdTelefone());
		}

		dto.setCodigoTipoTelefone(Validar.converterParaShort(vo.getCodigoTipoTelefone().toString()));
		dto.setDdd(vo.getNumeroDdd());
		dto.setIdInstituicao(vo.getIdInstituicao());
		dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
		dto.setObservacao(vo.getDescricaoObservacao());
		dto.setRamal(vo.getNumeroRamal());
		dto.setTelefone(vo.getNumeroTelefone());
		dto.setIdUnidadeInst(pessoaBasicaVO.getIdUnidadeInst());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setVerificarAutorizacao(false);
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	public void incluirTelefoneDTO(TelefoneDTO dto) throws BancoobException {
		getLogger().info("Cadastra novo telefone no CAPES");
		telefoneDelegate.incluir(dto);
	}

	/***
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	public void alterarTelefoneDTO(TelefoneDTO dto) throws BancoobException {
		getLogger().info("Altera o telefone no CAPES");
		telefoneDelegate.alterar(dto);
	}

}
