package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Stateless;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.CAPESApiInclusaoFabricaDelegates;
import br.com.sicoob.capes.api.inclusao.negocio.delegates.PessoaDelegate;
import br.com.sicoob.capes.api.inclusao.negocio.dto.PessoaDTO;
import br.com.sicoob.capes.api.inclusao.negocio.dto.PessoaFisicaDTO;
import br.com.sicoob.capes.api.inclusao.negocio.dto.PessoaJuridicaDTO;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.negocio.enums.TipoPessoaEnum;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoCompartilhamentoServico;
import br.com.sicoob.icg.processamento.util.Constantes;

/**
 * 
 * @author Pablo.Andrade
 *
 */
@Stateless
@Local(ProcessamentoCompartilhamentoServico.class)
public class ProcessarCompartilhamentoServicoEJB implements ProcessamentoCompartilhamentoServico {

	private PessoaDelegate pessoaDelegate = CAPESApiInclusaoFabricaDelegates.obterInstancia().criarPessoaDelegate();

	@Override
	public void compartilharPessoa(PessoaBasicaVO vo) throws BancoobException {

		if (vo.getCodigoTipoPessoa().equals(TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString())) {
			PessoaFisicaDTO dto = (PessoaFisicaDTO) gerarPessoaDTO(vo);
			pessoaDelegate.incluir(dto);

		} else {
			PessoaJuridicaDTO dto = (PessoaJuridicaDTO) gerarPessoaDTO(vo);
			pessoaDelegate.incluir(dto);
		}

	}
	
	public PessoaDTO gerarPessoaDTO(PessoaBasicaVO vo) {
		
		PessoaDTO dto = null;
		if (vo.getCodigoTipoPessoa().equals(TipoPessoaEnum.PESSOA_FISICA.getCodigo().toString())) {
			dto = new PessoaFisicaDTO();
		} else {
			dto = new PessoaJuridicaDTO();
		}
		
		dto.setIdPessoa(vo.getIdPessoa());
		dto.setIdInstituicao(vo.getInstituicao());
		dto.setCpfCnpj(vo.getCpfCnpj());
		dto.setDataInclusaoSistema(new Date());
		dto.setIdUnidadeInst(vo.getIdUnidadeInst());
		dto.setIdUsuarioAprovacao(Constantes.ICG_USUARIO_APROVACAO);
		dto.setVerificarAutorizacao(vo.getVerificarAutorizacao());
		dto.setNomeCompleto("CADASTRADO PELO ICG");
		dto.setNomePessoa("CADASTRADO PELO ICG");
		
		return dto;
	}

	@Override
	public void verificarDisponibilidade() {
		// TODO Auto-generated method stub

	}

}
