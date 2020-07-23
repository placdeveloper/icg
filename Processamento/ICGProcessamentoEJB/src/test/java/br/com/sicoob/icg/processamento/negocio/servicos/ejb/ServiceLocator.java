package br.com.sicoob.icg.processamento.negocio.servicos.ejb;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * A Classe ServiceLocator.
 */
public final class ServiceLocator {

	/** O atributo instance. */
	private static ServiceLocator instance;
	
	/** O atributo initialContext. */
	private InitialContext initialContext;
	
	/** O atributo cache. */
	private Map<String, Object> cache;

	/**
	 * Recupera a unica instancia de ServiceLocator.
	 *
	 * @return uma instancia de ServiceLocator
	 * @throws NamingException lan�a a exce��o NamingException
	 */
	public synchronized static ServiceLocator getInstance() throws NamingException {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	/**
	 * Construtor default para lookup local
	 * 
	 * @throws NamingException
	 */
	private ServiceLocator() throws NamingException {
		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		map.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
		map.put("java.naming.provider.url", "jnp://localhost:9060");
		initialContext = new InitialContext(map);
		cache = Collections.synchronizedMap(new HashMap<String, Object>());
		System.out.println("### cache: " + cache);
	}

	/**
	 * Faz o lookup do EJB Remoto
	 * 
	 * @param ejbInterface
	 * @return
	 * @throws RuntimeException
	 */
	public Object getObjetoRemoto(String ejbInterface) throws RuntimeException {
		System.out.println("### EJB: " + ejbInterface);
		System.out.println("### cache: " + cache);
		Object result = cache.get(ejbInterface);
		if (result == null) {
			try {
				result = initialContext.lookup(ejbInterface + "Remote");
				cache.put(ejbInterface, result);
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

}