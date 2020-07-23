package br.com.sicoob.icg.importacaoarquivocadastro
{
	import flash.events.MouseEvent;
	
	import mx.events.FlexEvent;

	import br.com.bancoob.componentes.containers.Janela;
	import br.com.sicoob.icg.vo.ImportacaoArquivoVO;
	
	public class ImportaArquivoVisualizar extends ImportaArquivoVisualizarView { 
		
		public var importaArquivoCadastro: ImportaArquivoCadastro;
		public var importacaoArquivoVO: ImportacaoArquivoVO;
		[Bindable]
		public var janelaPai: Janela;
		
		public function ImportaArquivoVisualizar() {
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, this.init);		
		}
		
		protected override function init(event:FlexEvent): void {
			super.init(event);
			configurarBotao();
		}
		
		
		private function configurarBotao(): void {
			this.btnVoltar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
			this.pegaJanela().barraBotoes.btFechar.addEventListener(MouseEvent.CLICK, voltarJanelaEvento);
		}
		
		private function voltarJanelaEvento(Evento: MouseEvent): void {
			this.fecharJanela();
		}

		public override function fecharJanela(): void {
			pegaJanela().visible = false;
			
			super.fecharJanela();
			visualizarJanelaPai();
		}
		
		private function visualizarJanelaPai(): void {
			this.janelaPai.visible = true;
		}
		
		protected override function preencherCampos(): void {
			this.txtArquivo.text = importacaoArquivoVO.nomeArquivo;
			this.txtSituacao.text = importacaoArquivoVO.situacaoProcessamentoEnum.descricao;
			this.txtAreaDescricaoErro.text = importacaoArquivoVO.descricaoErro;
		}
	}
}