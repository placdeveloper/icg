package br.com.sicoob.icg.processamento.negocio.servicos.util;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.bancoob.excecao.BancoobException;
import br.com.sicoob.capes.api.negocio.delegates.CAPESApiFabricaDelegates;
import br.com.sicoob.capes.api.negocio.delegates.DominioDelegate;
import br.com.sicoob.capes.api.negocio.delegates.EmailPessoaDelegate;
import br.com.sicoob.capes.api.negocio.delegates.EnderecoPessoaDelegate;
import br.com.sicoob.capes.api.negocio.delegates.FonteRendaPessoaDelegate;
import br.com.sicoob.capes.api.negocio.delegates.InstituicaoResponsavelDelegate;
import br.com.sicoob.capes.api.negocio.delegates.PessoaDelegate;
import br.com.sicoob.capes.api.negocio.delegates.TelefonePessoaDelegate;
import br.com.sicoob.capes.api.negocio.vo.DominioVO;
import br.com.sicoob.capes.api.negocio.vo.EmailPessoaVO;
import br.com.sicoob.capes.api.negocio.vo.EnderecoPessoaVO;
import br.com.sicoob.capes.api.negocio.vo.FonteRendaPessoaVO;
import br.com.sicoob.capes.api.negocio.vo.InstituicaoResponsavelVO;
import br.com.sicoob.capes.api.negocio.vo.PessoaVO;
import br.com.sicoob.capes.api.negocio.vo.TelefonePessoaVO;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.negocio.entidades.vo.PessoaBasicaVO;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public class ConsultasUtil {

	private ConsultasUtil() {
	}

	private static InstituicaoResponsavelDelegate responsavelDelegate = CAPESApiFabricaDelegates.getInstance()
			.criarInstituicaoResponsavelDelegate();

	private static PessoaDelegate pessoaDelegate = CAPESApiFabricaDelegates.getInstance().criarPessoaDelegate();

	private static EnderecoPessoaDelegate enderecoPessoaDelegate = CAPESApiFabricaDelegates.getInstance()
			.criarEnderecoPessoaDelegate();

	private static EmailPessoaDelegate emailPessoaDelegate = CAPESApiFabricaDelegates.getInstance()
			.criarEmailPessoaDelegate();

	private static FonteRendaPessoaDelegate fonteRendaPessoaDelegate = CAPESApiFabricaDelegates.getInstance()
			.criarFonteRendaPessoaDelegate();

	private static TelefonePessoaDelegate telefonePessoaDelegate = CAPESApiFabricaDelegates.getInstance()
			.criarTelefonePessoaDelegate();

	private static DominioDelegate dominioDelegate = CAPESApiFabricaDelegates.getInstance().criarDominioDelegate();

	/**
	 * 
	 * @param cpfCnpj
	 * @param idInstituicao
	 * @return
	 * @throws BancoobException
	 */
	public static PessoaBasicaVO pesquisarCPFCNPJ(String cpfCnpj, Integer idInstituicao) throws BancoobException {
		PessoaVO pessoaVO = pessoaDelegate.obterPorCpfCnpjInstituicao(cpfCnpj, idInstituicao);
		PessoaBasicaVO pessoaBasicaVO = new PessoaBasicaVO();
		if (Objects.nonNull(pessoaVO)) {
			pessoaBasicaVO.setIdPessoa(pessoaVO.getIdPessoa());
			pessoaBasicaVO.setInstituicao(pessoaVO.getIdInstituicao());
		}
		return pessoaBasicaVO;
	}

	/**
	 * 
	 * @param cpfCnpj
	 * @return
	 * @throws BancoobException
	 */
	public static Boolean pessoaCadastradaNoCAPES(String cpfCnpj) throws BancoobException {
		List<PessoaVO> pessoaVOs = pessoaDelegate.obterPorCpfCnpj(cpfCnpj);
		if (pessoaVOs.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param valor
	 * @param tipoDominioEnum
	 * @return
	 * @throws BancoobException
	 */
	public static Boolean ehCodigoDominioValido(String valor, TipoDominioEnum tipoDominioEnum) throws BancoobException {
		List<DominioVO> dominioVO = obterDominio(tipoDominioEnum);
		return dominioVO.stream().anyMatch(c -> valor.equalsIgnoreCase(c.getCodigo().toString()));
	}

	/**
	 * 
	 * @param tipoDominioEnum
	 * @return
	 * @throws BancoobException
	 */
	private static List<DominioVO> obterDominio(TipoDominioEnum tipoDominioEnum) throws BancoobException {
		return dominioDelegate.obterPorTipoDominio(tipoDominioEnum);
	}

	/**
	 * 
	 * @param cpfCnpj
	 * @return
	 * @throws BancoobException
	 */
	public static InstituicaoResponsavelVO obterInstituicaoResponsavel(String cpfCnpj) throws BancoobException {
		return responsavelDelegate.obterPorCpfCnpj(cpfCnpj);
	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	public static List<EmailPessoaVO> pesquisarEmail(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		return emailPessoaDelegate.obterPorPessoaInstituicao(pessoaBasicaVO.getIdPessoa(),
				pessoaBasicaVO.getInstituicao());
	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	public static List<EnderecoPessoaVO> pesquisarEnderecos(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		return enderecoPessoaDelegate.obterPorPessoaInstituicao(pessoaBasicaVO.getIdPessoa(),
				pessoaBasicaVO.getInstituicao());
	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	public static List<FonteRendaPessoaVO> pesquisarFonteRendas(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		return fonteRendaPessoaDelegate.obterPorPessoaInstituicao(pessoaBasicaVO.getIdPessoa(),
				pessoaBasicaVO.getInstituicao());
	}

	/**
	 * 
	 * @param pessoaBasicaVO
	 * @return
	 * @throws BancoobException
	 */
	public static List<TelefonePessoaVO> pesquisarTelefones(PessoaBasicaVO pessoaBasicaVO) throws BancoobException {
		return telefonePessoaDelegate.obterPorPessoaInstituicao(pessoaBasicaVO.getIdPessoa(),
				pessoaBasicaVO.getInstituicao());
	}

	public static PessoaVO recuperaInstituicaoConjuge(String cpf, Integer instituicao) throws BancoobException {
		List<PessoaVO> p = pesquisarCPFConjuge(cpf);
		Optional<PessoaVO> optPessoaVO = p.stream().filter(f -> f.getIdInstituicao().equals(instituicao)).findFirst();
		if (optPessoaVO.isPresent()) {
			return optPessoaVO.get();
		}
		return null;
	}

	private static List<PessoaVO> pesquisarCPFConjuge(String cpf) throws BancoobException {
		return pessoaDelegate.obterPorCpfCnpj(cpf);
	}

}
