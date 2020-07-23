package br.com.sicoob.icg.comum.negocio.servicos.ejb;

import br.com.bancoob.negocio.entidades.BancoobEntidade;
import br.com.bancoob.negocio.servicos.ejb.BancoobCrudServicoEJB;
import br.com.sicoob.icg.comum.negocio.servicos.ICGCrudComumServico;

public abstract class ICGCrudComumServicoEJB<T extends BancoobEntidade> extends BancoobCrudServicoEJB<T>
		implements ICGCrudComumServico<T> {

}