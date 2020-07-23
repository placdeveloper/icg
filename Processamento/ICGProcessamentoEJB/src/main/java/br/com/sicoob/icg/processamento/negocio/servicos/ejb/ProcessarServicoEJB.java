package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.negocio.dto.ConsultaDto;
import br.com.bancoob.negocio.servicos.ejb.BancoobServicoEJB;
import br.com.bancoob.persistencia.types.DateTimeDB;
import br.com.bancoob.util.MensagemUtil;
import br.com.sicoob.capes.api.inclusao.negocio.excecao.CAPESApiInclusaoNegocioException;
import br.com.sicoob.capes.api.inclusao.negocio.excecao.CAPESApiInclusaoRuntimeException;
import br.com.sicoob.capes.api.negocio.vo.InstituicaoResponsavelVO;
import br.com.sicoob.icg.comum.negocio.servicos.ICGCrudComumServico;
import br.com.sicoob.icg.comum.negocio.servicos.interfaces.ImportacaoArquivoServicoLocal;
import br.com.sicoob.icg.negocio.entidades.ComumImportacao;
import br.com.sicoob.icg.negocio.entidades.ImportaArquivo;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;
import br.com.sicoob.icg.negocio.enums.SituacaoProcessamentoEnum;
import br.com.sicoob.icg.negocio.enums.TipoAtualizacaoEnum;
import br.com.sicoob.icg.processamento.negocio.mensagem.ICGProcessamentoResourceBundle;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoCompartilhamentoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.ProcessamentoServico;
import br.com.sicoob.icg.processamento.negocio.servicos.util.ConsultasUtil;
import br.com.sicoob.icg.processamento.util.Constantes;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public abstract class ProcessarServicoEJB<E extends ComumImportacao<? extends Serializable>> extends BancoobServicoEJB
		implements ProcessamentoServico {

	@Inject
	@Default
	private ImportacaoArquivoServicoLocal importaArquivoServico;

	private Set<String> listaIdImportacaoErroProcessamento;
	private Set<String> listaErrosProcessamento;
	

	@EJB
	private ProcessamentoCompartilhamentoServico processamentoCompartilhamentoServico;

	/**
	 * 
	 * @return
	 */
	protected abstract E instanciarFiltro();

	/**
	 * 
	 * @return
	 */
	protected abstract ICGCrudComumServico<E> getServico();

	/**
	 * 
	 * @param vo
	 * @param pessoaBasicaVO
	 * @throws BancoobException
	 */
	protected abstract void executarPessoaExistente(E vo, PessoaBasicaVO pessoaBasicaVO) throws BancoobException;

	/**
	 * 
	 * @param vo
	 * @throws BancoobException
	 */
	protected void executarPessoaInexistente(E vo, PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		getLogger().info("executa pessoa inexistente");
		vo.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
		vo.setDescErroProcessamento((MensagemUtil.getString(Constantes.MENSAGEM_MN004,
				ICGProcessamentoResourceBundle.getInstance(), vo.getNumeroCpfCnpj())));
		populaListaIdImportacaoErroProcessamento(vo);
	}

	@Override
	public final void iniciaAtualizacao(ImportaArquivo arquivo) throws BancoobException {

//		arquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.EM_PROCESSAMENTO.getIdSituacao());
//		importaArquivoServico.alterar(arquivo);

		getLogger().info("Inicia processo de atualização");
		listaIdImportacaoErroProcessamento = new HashSet<>();
		listaErrosProcessamento = new HashSet<>();
		List<E> vos = listarObjetosParaAtualizar(arquivo);
		for (E vo : vos) {
			try {

				/**
				 * PODE ATUALIZAR TODAS ENTIDADES, MAS APENAS TELEFONE E E-MAIL NÃO ENTRAM NO
				 * FLUXO DE AUTORIZAÇÃO
				 * 
				 * QUANDO TENTA ATUALIZAR OS DADOS DE ENTIDADES DIFERENTE DE PF E PJ, EXIBE
				 * EXCEPTION: PESSOA NÃO CADASTRADA NO CAPES
				 * 
				 * 
				 * CADASTRO DE PESSOA: CONSULTA PARA VERIFICAR SE O CPF ESTÁ CADASTRADO NO CAPES
				 * ==============NOVO CADASTRO================ EXECUTA PESSOA INEXISTENTE
				 * 
				 * ========ALTERAÇÃO DE CADASTRO=========== CONSULTA INSTITUIÇÃO RESPONSÁVEL
				 * 
				 * =======SE FOR A MESMA INSTITUIÇÃO RESPONSÁVEL===== EXECUTA PESSOA EXISTENTE
				 * 
				 * =======SE NÃO FOR A MESMA INSTITUIÇÃO RESPONSÁVEL========= EXECUTA PESSOA
				 * INEXISTENTE EXECUTA PESSOA EXISTENTE
				 * 
				 */

				PessoaBasicaVO pessoaBasicaVO = new PessoaBasicaVO();
				vo.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.PROCESSADO.getIdSituacao());

				getLogger().info("Pesquisa se a pessoa está cadastrada no CAPES");
				if (ConsultasUtil.pessoaCadastradaNoCAPES(vo.getNumeroCpfCnpj())) {

					/**
					 * PESQUISA A INSTITUIÇÃO RESPONSÁVEL PELA PESSOA. SE FOR A MESMA INSTITUIÇÃO,
					 * REALIZA A ALTERAÇÃO SEM SOLICITAR APROVAÇÃO SE NÃO FOR A MESMA INSTITUIÇÃO,
					 * REALIZA O COMPARTILHAMENTO, DEPOIS ALTERA OS DADOS SOLICITANDO APROVAÇÃO SE
					 * NÃO EXISTIR INSTITUIÇÃO RESPONSÁVEL, INCLUI
					 * 
					 */

					getLogger().info("Consulta a instituição responsável");
					InstituicaoResponsavelVO instituicaoResponsavelVO = null;
					instituicaoResponsavelVO = ConsultasUtil.obterInstituicaoResponsavel(vo.getNumeroCpfCnpj());

					pessoaBasicaVO.setIdPessoa(instituicaoResponsavelVO.getIdPessoa());
					pessoaBasicaVO.setIdUnidadeInst(instituicaoResponsavelVO.getIdInstituicao());
					pessoaBasicaVO.setCpfCnpj(vo.getNumeroCpfCnpj());
					if (instituicaoResponsavelVO.getIdInstituicao().equals(vo.getIdInstituicao())) {
						/**
						 * Mesma instituição, altera sem verificar autorização
						 */
						getLogger().info("Mesma instituição, altera sem verificar autorização");

						pessoaBasicaVO.setInstituicao(vo.getIdInstituicao());
						pessoaBasicaVO.setVerificarAutorizacao(false);
						executarPessoaExistente(vo, pessoaBasicaVO);

					} else {

						getLogger().info("Instituição " + arquivo.getIdInstituicao()
								+ " não é responsável. Ativa fluxo de autorização");
						pessoaBasicaVO.setVerificarAutorizacao(true);
						pessoaBasicaVO.setCodigoTipoPessoa(vo.getCodigoTipoPessoa());

						getLogger().info("Pesquisa instituição compartilhada");
						PessoaBasicaVO pbVO = ConsultasUtil.pesquisarCPFCNPJ(vo.getNumeroCpfCnpj(),
								vo.getIdInstituicao());

						if (Objects.nonNull(pbVO.getIdPessoa())) {
							getLogger().info("Instituição compartilhada - Altera os dados da pessoa");
							vo.setIdInstituicao(vo.getIdInstituicao());
							pessoaBasicaVO.setInstituicao(vo.getIdInstituicao());
							executarPessoaExistente(vo, pessoaBasicaVO);
						} else {
							/***
							 * realiza o compartilhamento
							 * 
							 * TIPO DE ENTIDADE INDEFINDO *
							 */
							getLogger().info("Realiza o compartilhamento");
							pessoaBasicaVO.setInstituicao(vo.getIdInstituicao());
							processamentoCompartilhamentoServico.compartilharPessoa(pessoaBasicaVO);

							getLogger().info("Realiza a altera dos dados");
							executarPessoaExistente(vo, pessoaBasicaVO);

						}
					}
				} else {
					/**
					 * Cadastra apenas PF e PJ no CAPES
					 */
					if (arquivo.getCodigoTipoArquivo().equals(TipoAtualizacaoEnum.PESSOAFISICA.getIdTipoMigracao())
							|| arquivo.getCodigoTipoArquivo()
									.equals(TipoAtualizacaoEnum.PESSOAJURIDICA.getIdTipoMigracao())) {
						pessoaBasicaVO.setVerificarAutorizacao(false);
						pessoaBasicaVO.setIdUnidadeInst(arquivo.getIdInstituicao());
						executarPessoaInexistente(vo, pessoaBasicaVO);
					} else {
						vo.setDescErroProcessamento(Constantes.MENSAGEM_PESSOA_NAO_CADASTRADA_CAPES);
						vo.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
						populaListaIdImportacaoErroProcessamento(vo);
					}
				}

				atualizaSituacaoProcessamento(vo);
			} catch (CAPESApiInclusaoNegocioException | CAPESApiInclusaoRuntimeException e) {
				getLogger().erro(e, Constantes.MENSAGEM_ERRO_CAPES + e.getMessage());
				vo.setDescErroProcessamento(Constantes.MENSAGEM_ERRO_CAPES + e.getMessage());
				vo.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
				populaListaIdImportacaoErroProcessamento(vo);
				atualizaSituacaoProcessamento(vo);
			} catch (BancoobException e) {
				final String mensagemBase = "Erro ao processar " + vo.getClass().getSimpleName() + ": " + vo.getId();
				getLogger().erro(e, mensagemBase);
				vo.setDescErroProcessamento(mensagemBase + "ERRO: " + e.getMessage());
				vo.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
				populaListaIdImportacaoErroProcessamento(vo);
				atualizaSituacaoProcessamento(vo);
			}
		}

		finalizarAtualizacao(arquivo, vos.size());
	}

	/**
	 * 
	 * @param arquivo
	 * @return
	 * @throws BancoobException
	 */
	private List<E> listarObjetosParaAtualizar(ImportaArquivo arquivo) throws BancoobException {
		getLogger().info("Lista objetos importados para atualizar no CAPES");
		ConsultaDto<E> criterios = new ConsultaDto<>();
		E filtro = instanciarFiltro();
		filtro.setImportaArquivo(arquivo);
		filtro.setCodSituacaoProcessamento(SituacaoProcessamentoEnum.A_INICIAR.getIdSituacao());
		criterios.setFiltro(filtro);
		return getServico().listar(criterios);
	}

	/**
	 * 
	 * @param objeto
	 */
	private void populaListaIdImportacaoErroProcessamento(E objeto) {
		listaIdImportacaoErroProcessamento.add(objeto.getImportaArquivo().getIdImportaArquivo().toString());
		listaErrosProcessamento.add(objeto.getId().toString());
	}

	/**
	 * 
	 * @param objeto
	 * @throws BancoobException
	 */
	private void atualizaSituacaoProcessamento(E objeto) throws BancoobException {
		getLogger().info("Atualiza a situação do processamento do objeto");
		objeto.setDataHoraProcessamento(new DateTimeDB());
		getServico().alterar(objeto);
	}

	/**
	 * 
	 * @param arquivo
	 * @param registrosParaAtualizar
	 * @throws BancoobException
	 */
	private void finalizarAtualizacao(ImportaArquivo arquivo, int registrosParaAtualizar) throws BancoobException {

		if (listaIdImportacaoErroProcessamento.isEmpty()) {
			if (arquivo.getCodigoSituacaoProcessamento().equals(SituacaoProcessamentoEnum.ARQUIVO_VALIDADO.getIdSituacao())) {
				arquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.PROCESSADO.getIdSituacao());
			} else {
				arquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.PROCESSADO_PARCIALMENTE.getIdSituacao());
			}
		} else {
			if ((listaErrosProcessamento.size() == registrosParaAtualizar) && arquivo.getCodigoSituacaoProcessamento().equals(SituacaoProcessamentoEnum.ARQUIVO_VALIDADO.getIdSituacao())) {
				arquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.ERRO_PROCESSAMENTO.getIdSituacao());
			} else {
				arquivo.setCodigoSituacaoProcessamento(SituacaoProcessamentoEnum.PROCESSADO_PARCIALMENTE.getIdSituacao());

			}
		}
		importaArquivoServico.alterar(arquivo);
	}

}