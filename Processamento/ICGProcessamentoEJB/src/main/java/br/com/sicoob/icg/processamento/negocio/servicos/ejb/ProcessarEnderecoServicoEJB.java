package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.EnderecoDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.EnderecoDTO;
import br.com.sicoob.capes.api.negocio.vo.EnderecoPessoaVO;
import br.com.sicoob.icg.comum.negocio.servicos.EnderecoPessoaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.EnderecoPessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoEnderecoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.negocio.validacao.util.Validar;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(ProcessamentoEnderecoServico.class)
public class ProcessarEnderecoServicoEJB extends ProcessarServicoEJB<EnderecoPessoaImportacao>
		implements ProcessamentoEnderecoServico {

	@EJB
	private EnderecoPessoaImportacaoServico servico;

	private EnderecoDelegate enderecoDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia()
			.criarEnderecoDelegate();

	@Override
	protected EnderecoPessoaImportacao instanciarFiltro() {
		return new EnderecoPessoaImportacao();
	}

	@Override
	protected EnderecoPessoaImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(EnderecoPessoaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {

		Optional<EnderecoPessoaVO> optVO = Optional.empty();
		List<EnderecoPessoaVO> filtros = pesquisarEnderecos(pessoaBasicaVO);
		if (!filtros.isEmpty()) {
			optVO = retornaOptEnderecoPessoaVO(vo, filtros);
		}

		EnderecoDTO dto = montarEnderecoDTO(vo, optVO, pessoaBasicaVO);

		if (Objects.nonNull(dto.getIdEndereco())) {
			getLogger().info(Constantes.MENSAGEM_ALTERACAO_CAPES + vo.getIdEndereco());
			alterarEnderecoDTO(dto);
		} else {
			getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getIdEndereco());
			incluirEnderecoDTO(dto);
		}

	}

	/***
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	private List<EnderecoPessoaVO> pesquisarEnderecos(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		getLogger().info("Lista os endereços da pessoa no CAPES");
		return ConsultasUtil.pesquisarEnderecos(pessoaBasicaVO);
	}

	/**
	 * 
	 * @param vo
	 * @param filtros
	 * @return
	 */
	private Optional<EnderecoPessoaVO> retornaOptEnderecoPessoaVO(EnderecoPessoaImportacao vo,
			List<EnderecoPessoaVO> filtros) {
		getLogger()
				.info("Retorna o endereço primeiro endereço da lista de acordo com o tipo de endereço de atualização");
		Optional<EnderecoPessoaVO> optVO = filtros.stream()
				.filter(f -> f.getCodigoTipoEndereco().toString().equals(vo.getCodigoTipoEndereco())).findFirst();
		if (!optVO.isPresent()) {
			optVO = Optional.empty();
		}

		return optVO;
	}

	/**
	 * 
	 * @param vo
	 * @param optEnderecoPessoaVO
	 * @param pessoaBasicaVO
	 * @return
	 */
	public EnderecoDTO montarEnderecoDTO(EnderecoPessoaImportacao vo, Optional<EnderecoPessoaVO> optEnderecoPessoaVO,
			PessoaBasicaVO pessoaBasicaVO) {
		getLogger().info("Monta o DTO de endereço");
		EnderecoDTO dto = new EnderecoDTO();
		dto.setCodigoTipoEndereco(Validar.converterParaShort(vo.getCodigoTipoEndereco()));
		dto.setCep(vo.getCodigoCep());
		dto.setBairro(vo.getNomeBairro());
		dto.setComplemento(vo.getDescricaoComplemento());
		dto.setDescricao(vo.getDescricaoLogradouro());
		dto.setNumero(vo.getNumero());

		if (optEnderecoPessoaVO.isPresent()) {
			dto.setIdEndereco(optEnderecoPessoaVO.get().getIdEndereco());
		}
		dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
		dto.setIdInstituicao(vo.getIdInstituicao());
		dto.setCodigoLocalidade(Validar.converterParaInteger(vo.getCodigoLocalidade().toString()));
		dto.setCodigoTipoLogradouro(Validar.converterParaInteger(vo.getCoodigoTipoLogradouro().toString()));
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
	public void alterarEnderecoDTO(EnderecoDTO dto) throws BancoobException {
		getLogger().info("Altera os dados de endereço no CAPES da pessoa");
		enderecoDelegate.alterar(dto);
	}

	/**
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void incluirEnderecoDTO(EnderecoDTO dto) throws BancoobException {
		getLogger().info("Cadastra novo endereço no CAPES  da pessoa");
		enderecoDelegate.incluir(dto);
	}

}
