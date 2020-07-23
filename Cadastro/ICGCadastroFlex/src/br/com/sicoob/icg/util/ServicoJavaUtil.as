package br.com.sicoob.icg.util {
	import mx.core.Application;
	
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	
	public class ServicoJavaUtil {
		
		private static var destinoVO:DestinoVO;
		
		/*
		* Recupera uma referência a um serviço Java.
		*/
		public static function getServico(source:String, mensagemEspera:String = null,
										  tipoEvento:String = null, listener:Function = null): ServicoJava {
			var servico: ServicoJava = new ServicoJava();
			servico.source = source;
			if (mensagemEspera != null) {
				servico.bloquearOperacao = true;
				servico.mensagemEspera = mensagemEspera;
			}
			if((tipoEvento != null) && (listener != null)) {
				servico.addEventListener(tipoEvento, listener);
			}
			return servico;
		}
		
		public static function definirOperacao(operacao: String, servico: ServicoJava) : ServicoJava {
			var operacoes: Object = new Object();
			operacoes[operacao];
			servico.operations = operacoes;      			
			return servico;
		}
		
		public static  function recuperarDestino():DestinoVO {
			var aplicacao:Object = Application.application;
			var destino:DestinoVO = null;
			if (aplicacao.hasOwnProperty("destinoAplicacaoSelecionada") 
				&& aplicacao.destinoAplicacaoSelecionada != null) {
				destino = aplicacao.destinoAplicacaoSelecionada as DestinoVO;
			}
			return destino
		}
	}	
}