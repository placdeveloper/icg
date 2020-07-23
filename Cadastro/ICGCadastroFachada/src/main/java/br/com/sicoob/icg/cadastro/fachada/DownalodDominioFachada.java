package br.com.sicoob.icg.cadastro.fachada;

import java.util.List;
import java.util.Map;

import br.com.bancoob.excecao.BancoobException;
import br.com.bancoob.sisbrweb.dto.RequisicaoReqDTO;
import br.com.bancoob.sisbrweb.dto.RetornoDTO;
import br.com.bancoob.sisbrweb.fachada.BancoobFachada;
import br.com.bancoob.sisbrweb.seguranca.RemoteService;
import br.com.sicoob.capes.api.negocio.delegates.CAPESApiFabricaDelegates;
import br.com.sicoob.capes.api.negocio.delegates.DominioDelegate;
import br.com.sicoob.capes.api.negocio.vo.DominioVO;
import br.com.sicoob.capes.comum.negocio.enums.TipoDominioEnum;
import br.com.sicoob.icg.comum.negocio.delegates.DownloadDominioDelegate;
import br.com.sicoob.icg.comum.negocio.delegates.ICGCadastroFabricaDelegates;
import br.com.sicoob.icg.negocio.entidades.vo.DownloadDominioVO;
import br.com.sicoob.icg.negocio.enums.TipoDominioDownloadEnum;

@RemoteService
public class DownalodDominioFachada extends BancoobFachada {


	public RetornoDTO downloadDominio(RequisicaoReqDTO dto) throws BancoobException {
		RetornoDTO retorno = new RetornoDTO();
		TipoDominioDownloadEnum tipoDominioDownloadEnum = obterTipoDominio(dto);
		TipoDominioEnum tipoDominioEnum = obterTipoDominio(tipoDominioDownloadEnum.name());

		DominioDelegate delegate = CAPESApiFabricaDelegates.getInstance().criarDominioDelegate();
		List<DominioVO> listaDominio = delegate.obterPorTipoDominio(tipoDominioEnum);

		DownloadDominioDelegate downloadDominioDelegate = ICGCadastroFabricaDelegates.getInstance().criarDownloadDominioDelegate();
		
		DownloadDominioVO arquivo = downloadDominioDelegate.obterArquivoDominio(listaDominio, tipoDominioDownloadEnum.name());
		 
		retorno.getDados().put("arquivo", arquivo);

		return retorno;
	}

	private TipoDominioEnum obterTipoDominio(String valor) {
		TipoDominioEnum[] tipos = TipoDominioEnum.values();
		for (TipoDominioEnum tipoDominioEnum : tipos) {
			if (tipoDominioEnum.name().equals(valor)) {
				return tipoDominioEnum;
			}
		}
		return null;
	}

	protected TipoDominioDownloadEnum obterTipoDominio(RequisicaoReqDTO dto) {
		TipoDominioDownloadEnum tipoDominioDownloadEnum = (TipoDominioDownloadEnum) dto.getDados()
				.get("tipoDominioDownload");
		return TipoDominioDownloadEnum.getTipoDominio(tipoDominioDownloadEnum.getDescricao());
	}

	public RetornoDTO retornaDominios() {
		RetornoDTO retorno = new RetornoDTO();
		Map<String, Object> dados = retorno.getDados();
		dados.put("listaTipoDominio", listaTipoDominio());

		return retorno;
	}

	private Object[] listaTipoDominio() {
		return TipoDominioDownloadEnum.values();
	}

}
