package br.com.sicoob.icg.importacaoarquivocadastro
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.rpc.events.ResultEvent;
	
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.bancoob.dto.RequisicaoReqDTO;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.app.Application;
	import br.com.sicoob.icg.util.ServicoJavaUtil;
	import br.com.sicoob.icg.vo.ImportacaoArquivoVO;
	
	public class VisualizarErroProcessamento extends VisualizarErroProcessamentoView { 
		
		[Bindable]
		public var janelaPai: Janela;
		public var importaArquivoCadastro: ImportaArquivoCadastro;
		public var importacaoArquivoVO: ImportacaoArquivoVO;
		
		protected var servicoConsulta : ServicoJava;
		
		private static const CLASSE_SERVICO: String =  "br.com.sicoob.icg.cadastro.fachada.ImportaArquivoFachada";
		private static const OPERACAO_CONSULTA: String = "retornoListaErrosProcessamento";
		private static const DESTINO_ICG:String = "servicosJavaICG";
		private var _destino:DestinoVO;
		
		private var _listaErrosProcessamento:ArrayCollection = new ArrayCollection();
		
		public function VisualizarErroProcessamento() {
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, this.init);		
		}
		
		protected override function init(event:FlexEvent): void {
			super.init(event);
		
			this.servicoConsulta = ServicoJavaUtil.getServico(CLASSE_SERVICO, "Realizando pesquisa erros processamento...", ResultEvent.RESULT, retornaListaErrosProcessamento);
			
			this.btnVoltar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
			PortalModel.portal.obterDefinicoesDestino(DESTINO_ICG, configurarDestino);
			
			configurarBotao();
		}

		private function configurarDestino(destino:DestinoVO):void{
			this._destino = destino;
			servicoConsulta.configurarDestino(destino);
			trace("Destinos recuperados");
			
			consultar();
		}

		private function configurarBotao(): void {
			this.pegaJanela().barraBotoes.btFechar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
		}
				
		private function retornaListaErrosProcessamento(event : ResultEvent = null):void {
			this.listaErrosProcessamento.list = event.result.dados.listaErrosProcessamento;
			if(listaErrosProcessamento != null &&  listaErrosProcessamento.length > 0){
				this.gridListaErrosProcessamento.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaErrosProcessamento, gridListaErrosProcessamento.tamanhoPagina));
			}
		}
		
		public function get listaErrosProcessamento():ArrayCollection {
			return this._listaErrosProcessamento;
		}

		
		private function voltarJanelaEvento(Evento: MouseEvent): void {
			this.fecharJanela();
		}

		public function consultar():void {
			if (servicoConsulta != null) {
				MostraCursor.setBusyCursor("PESQUISANDO...", Application.application, MostraCursor.CURSOR_PESQUISAR);
			}
			
			var dto:RequisicaoReqDTO = new RequisicaoReqDTO();
			preencherRequisicaoReqDTO(dto);
			servicoConsulta.getOperation(OPERACAO_CONSULTA).send(dto);
		}

		public function preencherRequisicaoReqDTO(dto: RequisicaoReqDTO): void {		
			var vo: ImportacaoArquivoVO = new ImportacaoArquivoVO();
			
			vo.idImportaArquivo = importacaoArquivoVO.idImportaArquivo;
			vo.codigoTipoArquivo = importacaoArquivoVO.tipoAtualizacaoEnum.codigo;
						
			dto.dados.importacaoArquivoVO = vo;
		}
		
		protected override function preencherCampos(): void {
			this.txtArquivo.text = importacaoArquivoVO.nomeArquivo;
			this.txtSituacao.text = importacaoArquivoVO.situacaoProcessamentoEnum.descricao;
		}
		
		public override function fecharJanela(): void {
			pegaJanela().visible = false;
			
			super.fecharJanela();
			visualizarJanelaPai();
		}
		
		private function visualizarJanelaPai(): void {
			this.janelaPai.visible = true;
		}
		
	}
}