package {
	import mx.events.FlexEvent;
	
	import br.com.sicoob.icg.TesteView;
	
	public class Teste extends TesteView {
		
		public function Teste() {
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, inicializar);
		}
		
		private function inicializar(event:FlexEvent): void {
			
		}
	}
}