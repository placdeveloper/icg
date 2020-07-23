package br.com.sicoob.icg.negocio.enums;

/***
 * 
 * @author Pablo.Andrade
 *
 */
public enum TipoAtualizacaoEnum {

	EMAIL("6", "Email"),
	ENDERECO("2", "Endereço"),
	FONTERENDA("4", "Fonte Renda"), 
	PESSOAFISICA("1", "Pessoa Física"), 
	PESSOAJURIDICA("5", "Pessoa Jurídica"), 
	TELEFONE("3", "Telefone"); 

	private String idTipoMigracao;

	private String descricao;

	private TipoAtualizacaoEnum(String idTipoMigracao, String descricao) {
		this.idTipoMigracao = idTipoMigracao;
		this.descricao = descricao;
	}

	public String getIdTipoMigracao() {
		return idTipoMigracao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + name();
	}

	public static TipoAtualizacaoEnum getTipoAtualizacaoPeloID(String id) {
		for (TipoAtualizacaoEnum atualizacaoEnum : TipoAtualizacaoEnum.values()) {
			if (atualizacaoEnum.getIdTipoMigracao().equals(id))
				return atualizacaoEnum;
		}
		return null;
	}
}
