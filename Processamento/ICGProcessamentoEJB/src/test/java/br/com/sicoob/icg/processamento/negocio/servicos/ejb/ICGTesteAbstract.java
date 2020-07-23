package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.security.Principal;

import org.jboss.security.SecurityAssociation;

import br.com.bancoob.infraestrutura.seguranca.UsuarioBancoob;

/**
 * A Classe CAPESTesteAbstract.
 */
public abstract class ICGTesteAbstract {

	/**
	 * O m�todo Sets the principal contexto.
	 */
	protected void setPrincipalContexto() {
		SecurityAssociation.setPrincipal(getUsuarioBancoob());
	}

	/**
	 * Recupera o valor de usuarioBancoob.
	 *
	 * @return o valor de usuarioBancoob
	 */
	private Principal getUsuarioBancoob() {
		UsuarioBancoob u = new UsuarioBancoob();
		u.setLogin("pablo.andrade");
		u.setCooperativa("3353");
		u.setDominio("bancoob_df");
		u.setIdInstituicao("910");
		u.setIdUnidadeInstituicao("2");
		u.setPac("02");
		return u;
	}
}