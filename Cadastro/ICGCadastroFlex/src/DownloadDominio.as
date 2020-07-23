package
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.registerClassAlias;
	
	import mx.events.FlexEvent;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.dto.RequisicaoReqDTO;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.sisbr.portal.spool.ISpoolTemporarios;
	import br.com.bancoob.sisbr.portal.spool.SpoolTemporariosFactory;
	import br.com.bancoob.sisbr.portal.spool.eventos.SpoolTemporariosEvento;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.app.Application;
	import br.com.sicoob.icg.downloadDominio.DownloadDominioView;
	import br.com.sicoob.icg.enums.TipoDominioDownloadEnum;
	import br.com.sicoob.icg.util.ServicoJavaUtil;
	import br.com.sicoob.icg.vo.DownloadDominioVO;
	import br.com.sicoob.icg.vo.ImportacaoArquivoVO;
	
	public class DownloadDominio extends DownloadDominioView { 
		
		[Bindable]
		public var importaArquivoCadastro: ImportaArquivoCadastro;
		public var importacaoArquivoVO: ImportacaoArquivoVO;
		
		protected var servicoConsulta : ServicoJava;
		protected var servicoDownload : ServicoJava;
		
		private static const CLASSE_SERVICO: String =  "br.com.sicoob.icg.cadastro.fachada.DownalodDominioFachada";
		private static const OPERACAO_CONSULTA: String = "retornaDominios";
		private static const OPERACAO_DOWNLOAD_DOMINIO: String = "downloadDominio";
		private static const DESTINO_ICG:String = "servicosJavaICG";
		private var _listaTipoDominio:Object = new Object();
		private var _destino:DestinoVO;
		
		public function DownloadDominio() {
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, this.init);		
		}
		
		protected override function init(event:FlexEvent): void {
			super.init(event);
			registerClassAlias("br.com.sicoob.icg.negocio.enums.TipoDominioDownloadEnum", TipoDominioDownloadEnum);
			registerClassAlias("br.com.sicoob.icg.negocio.entidades.vo.DownloadDominioVO", DownloadDominioVO);
			this.servicoConsulta = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Realizando pesquisa de tipos de domínio...", ResultEvent.RESULT, retornaListaDominio);
			this.servicoDownload = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Realizando Download...");
			servicoDownload.addEventListener(ResultEvent.RESULT, retorno_gerar);
			servicoDownload.addEventListener(FaultEvent.FAULT, retorno_gerar_erro);
			
			this.btDownload.addEventListener(MouseEvent.CLICK, ralizarDownloadDominio);
			
			this.btnFechar.addEventListener(MouseEvent.CLICK, fechar);
			PortalModel.portal.obterDefinicoesDestino(DESTINO_ICG, configurarDestino);
			
		}

		private function configurarDestino(destino:DestinoVO):void{
			this._destino = destino;
			servicoConsulta.configurarDestino(destino);
			servicoDownload.configurarDestino(destino);
			trace("Destinos recuperados");
			
			consultar();
		}

		
		private function ralizarDownloadDominio(evt:Event = null): void {
			gerar();
		}		
		
		protected function retornoDownalod(event:ResultEvent):void {
			Alerta.show("Download ralizado com sucesso!", "Sucesso", Alerta.ALERTA_INFORMACAO);
		}

		private function gerar():void {
			
			if (this.comboListaDominio.selectedItem == null){
				Alerta.show("Selecione um tipo de domínio da lista!", "Erro", Alerta.ALERTA_ERRO);
			} else {
				MostraCursor.setBusyCursor("Obtendo o arquivo para visualização...", this, MostraCursor.CURSOR_PESQUISAR);
				
				var dto:RequisicaoReqDTO = new RequisicaoReqDTO();
				preencherParamentroReqDTO(dto);
				servicoDownload.downloadDominio(dto);
			}
		}

		private function retornaListaDominio(event : ResultEvent = null):void {
			this.comboListaDominio.dataProvider = event.result.dados.listaTipoDominio;
			this._listaTipoDominio.list = event.result.dados.listaTipoDominio;
		}
		
		
		private function fechar(Evento: MouseEvent): void {
			this.fecharJanela();
		}

		public function consultar():void {
			if (servicoConsulta != null) {
				MostraCursor.setBusyCursor("PESQUISANDO...", Application.application, MostraCursor.CURSOR_PESQUISAR);
			}
			
			var dto:RequisicaoReqDTO = new RequisicaoReqDTO();
			servicoConsulta.getOperation(OPERACAO_CONSULTA).send();
		}

		public function preencherParamentroReqDTO(dto: RequisicaoReqDTO): void {	
			var vo: TipoDominioDownloadEnum = TipoDominioDownloadEnum.obterPorDescricao(comboListaDominio.text);
			dto.dados.tipoDominioDownload = vo;
			dto.dados.nomeRelatorio = this.comboListaDominio.text;
		}

		public override function fecharJanela(): void {
			pegaJanela().visible = false;
			
			super.fecharJanela();
		}
		
		private function retorno_gerar(evento:ResultEvent):void {
			var arquivo:DownloadDominioVO = evento.result.dados.arquivo;
			
			if(arquivo != null) {
				try	{
					var spool:ISpoolTemporarios = SpoolTemporariosFactory.getSpool("arquivosTemporariosICG");
					spool.gravarItem(arquivo.bytes, arquivo.nome, true);
					spool.addEventListener(SpoolTemporariosEvento.ARQUIVO_GRAVADO, arquivo_gravado);
				} catch(error:Error){
					Alerta.show("Erro ao gerar o arquivo de exportação: " + error.message, "Erro", Alerta.ALERTA_ERRO);
				}
			}
			
			MostraCursor.removeBusyCursor();
		}
		
		private function retorno_gerar_erro(evento:FaultEvent):void {
			MostraCursor.removeBusyCursor();
		}
				
		private function arquivo_gravado(evento:SpoolTemporariosEvento): void {
			Alerta.show("Arquivo de exportação gerado com sucesso no diretório: " + evento.diretorio, "Sucesso", Alerta.ALERTA_INFORMACAO);
		}

	}
}