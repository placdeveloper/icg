package br.com.sicoob.icg.negocio.enums;

import java.io.Serializable;

/**
 * 
 * @author Pablo.Andrade
 *
 */
public enum SituacaoProcessamentoEnum  implements Serializable {

	A_INICIAR("1", "A iniciar"),
	ARQUIVO_VALIDADO("7", "Arquivo validado"),
	EM_PROCESSAMENTO("2", "Em processamento"),
	ERRO_PROCESSAMENTO("8", "Erro de processamento"),
	ERRO_VALIDACAO("5", "Erro de validação"),
	PROCESSADO("3", "Processado"),
	PROCESSADO_PARCIALMENTE("4", "Processado parcialmente"),
	VALIDADO_PARCIALMENTE("6", "Validado parcialmente");
	
	private String idSituacao;
	
	private String descricao;

	private SituacaoProcessamentoEnum(String idSituacao, String descricao) {
		this.idSituacao = idSituacao;
		this.descricao = descricao.toUpperCase();
	}	
	
	public String getIdSituacao() {
		return idSituacao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + name();
	}	
	
	public static SituacaoProcessamentoEnum getSituacaoPeloID(String id){
        for(SituacaoProcessamentoEnum situacao : SituacaoProcessamentoEnum.values()){
            if(situacao.getIdSituacao().equals(id))
                return situacao;
        }
        return null;            
    }
	
}
