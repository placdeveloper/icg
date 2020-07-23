package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.EmailDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.EmailDTO;
import br.com.sicoob.capes.api.negocio.vo.EmailPessoaVO;
import br.com.sicoob.capes.comum.negocio.enums.TipoEmailEnum;
import br.com.sicoob.icg.comum.negocio.servicos.EmailPessoaImportacaoServico;
import br.com.sicoob.icg.negocio.entidades.EmailPessoaImportacao;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoEmailServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(ProcessamentoEmailServico.class)
public class ProcessarEmailServicoEJB extends ProcessarServicoEJB<EmailPessoaImportacao>
		implements ProcessamentoEmailServico {

	@EJB
	private EmailPessoaImportacaoServico servico;

	@Override
	protected EmailPessoaImportacao instanciarFiltro() {
		return new EmailPessoaImportacao();
	}

	@Override
	protected EmailPessoaImportacaoServico getServico() {
		return servico;
	}

	@Override
	protected void executarPessoaExistente(EmailPessoaImportacao vo, PessoaBasicaVO pessoaBasicaVO)
			throws BancoobException {
		List<EmailPessoaVO> filtros = pesquisarEmail(pessoaBasicaVO);

		Optional<EmailPessoaVO> optVO = retornaOptEmailPessoaVO(vo, filtros);

		if (!optVO.isPresent()) {
			EmailDTO dto = montarEmailDTO(vo, pessoaBasicaVO);
			getLogger().info(Constantes.MENSAGEM_INCLUINDO_CAPES + vo.getIdEmail());
			incluirEmailDTO(dto);
		}
	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	private List<EmailPessoaVO> pesquisarEmail(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		getLogger().info("Lista os emails da pessoa no CAPES");
		return ConsultasUtil.pesquisarEmail(pessoaBasicaVO);
	}

	/**
	 * 
	 * @param dto
	 * @throws BancoobException
	 */
	private void incluirEmailDTO(EmailDTO dto) throws BancoobException {
		getLogger().info("Cadastra novo email no CAPES");
		EmailDelegate emailDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia().criarEmailDelegate();
		emailDelegate.incluir(dto);
	}

	/**
	 * 
	 * @param emailVO
	 * @param filtros
	 * @return
	 */
	private Optional<EmailPessoaVO> retornaOptEmailPessoaVO(EmailPessoaImportacao emailVO,
			List<EmailPessoaVO> filtros) {
		getLogger().info("Retorna o primeiro email da lista de acordo com o tipo de email");
		Optional<EmailPessoaVO> optVO = filtros.stream()
				.filter(f -> f.getCodigoTipoEmail().toString().equals(emailVO.getCodigoTipoEmail())
						&& f.getDescricaoEmail().equals(emailVO.getDescricaoEmail()))
				.findFirst();
		if (!optVO.isPresent()) {
			optVO = Optional.empty();
		}

		return optVO;
	}

	/**
	 * 
	 * @param emailVO
	 * @param pessoaBasicaVO
	 * @return
	 */
	private EmailDTO montarEmailDTO(EmailPessoaImportacao emailVO, PessoaBasicaVO pessoaBasicaVO) {
		getLogger().info("Monta o DTO de email");
		EmailDTO dto = new EmailDTO();
		dto.setCodigoTipoEmail(TipoEmailEnum.OUTROS.getCodigo());
		dto.setDescricao(emailVO.getDescricaoEmail());
		dto.setIdInstituicao(emailVO.getIdInstituicao());
		dto.setIdPessoa(pessoaBasicaVO.getIdPessoa());
		dto.setIdUnidadeInst(pessoaBasicaVO.getIdUnidadeInst());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setVerificarAutorizacao(false);
		
		return dto;
	}

}
