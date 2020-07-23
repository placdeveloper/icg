package br.com.sicoob.icg.negocio.enums;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public enum TipoExtensaoArquivoEnum {

	CSV("csv"),
	JSON("JSON"); 

	private String descricao;

	private TipoExtensaoArquivoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + name();
	}

	public static TipoExtensaoArquivoEnum getTipoAtualizacaoPeloID(String tipo) {
		for (TipoExtensaoArquivoEnum tipoExtensaoArquivoEnum : TipoExtensaoArquivoEnum.values()) {
			if (tipoExtensaoArquivoEnum.getDescricao().equals(tipo))
				return tipoExtensaoArquivoEnum;
		}
		return null;
	}
}
