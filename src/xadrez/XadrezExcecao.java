package xadrez;

import tabuleiro.TabuleiroExcecao;

public class XadrezExcecao extends TabuleiroExcecao {
	private static final long serialVersionUID = 1L;

	//Criando o construtor que recebe a mensagem
		public XadrezExcecao (String mensagem) {
			super(mensagem);
		}
}
