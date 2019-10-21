package tabuleiro;

public class TabuleiroExcecao extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//Criando o construtor que recebe a mensagem
	public TabuleiroExcecao (String mensagem) {
		super(mensagem);
	}
}
