package xadrez;

public class XadrezExcecao extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//Criando o construtor que recebe a mensagem
		public XadrezExcecao (String mensagem) {
			super(mensagem);
		}
}
