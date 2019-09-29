package com.comprime;

import java.util.*;

public class LempelZivWelsh {
	public static List<Integer> encode(String texto) {
		// Construindo o dicion�rio de indices.

		int tamanho = 128;
		Map<String, Integer> dicionario = new HashMap<String, Integer>();
		// Criar uma Lista de Vetores HashMap com os indices do dicion�rio, inicialmente
		// vai ser limpo, a cada etapa do i
		// vai copiando os indices do HashMap nesse dicion�rio, que s�o os ASCII;

		for (int i = 0; i < tamanho; i++) {
			// indice i que vai receber o caractere ASCII.

			dicionario.put("" + (char) i, i);
		}

		String palavra = "";
		// Lista de Vetores
		List<Integer> resultado = new ArrayList<Integer>();
		List<Integer> vetTemp = new ArrayList<Integer>();
		// Converte o texto em vetor de caracteres, palavra = caracteres entre espa�os
		// vazios
		// o loop recebera valor null ao final dela e entrar� em condi��o de parada;

		for (char proxCaractere : texto.toCharArray()) {
			// Pega o prox caractere
			String ab = "" + proxCaractere;
			// Procura o espa�o, se n�o houver, ele concatena o caractere � palavra;
			if (!(proxCaractere == ' ')) {
				// Procura se ja existe a palavra no dicion�rio, caso exista, ele pega o indice
				// e adiciona
				// no vetor tempor�rio
				if (dicionario.containsKey(ab))
					vetTemp.add(dicionario.get(ab));

				// Concatena toda a palavra com o proximo caractere dela, se n�o for espa�o.
				palavra = palavra + ab;
			} else {
				// Se houver espa�o, finaliza palavra, e ve se ela existe no dicion�rio, se
				// n�o ele cria um novo indice
				if (dicionario.containsKey(palavra)) {
					resultado.add(dicionario.get(palavra));
					resultado.add(dicionario.get(" "));
				} else {
					for (int j = 0; j < vetTemp.size(); j++)
						resultado.add(vetTemp.get(j));
					resultado.add(dicionario.get(" "));
					dicionario.put(palavra, tamanho++);
				}

				vetTemp.clear();
				palavra = "";
			}
		}

		if (!palavra.equals(""))
			resultado.add(dicionario.get(palavra));
		return resultado;
	}

	public static String decode(List<Integer> arquivo) {
		// Monta o dicion�rio padr�o ASCII
		int tamanho = 128;
		Map<Integer, String> dicionario = new HashMap<Integer, String>();
		for (int i = 0; i < tamanho; i++)
			dicionario.put(i, "" + (char) i);

		// Cria vari�vel que vai recever o indice do primeiro cacacter do arquivo
		// comprimido
		String ab = "";
		StringBuffer resultado = new StringBuffer(ab);
		String palavra = "";
		// Loop recebe os indices o arquivo at� ficar vazio
		for (int indice : arquivo) {
			if(indice == 32) {
				dicionario.put(tamanho++, palavra); 
				resultado.append(palavra);
				resultado.append(" ");
				palavra = "";
			}
			else
				if (dicionario.containsKey(indice))
					palavra = palavra + dicionario.get(indice);

		}
		resultado.append(palavra);
		return resultado.toString();
	}

	public static void main(String[] args) {
		List<Integer> compressed = encode("This is as Simple as it is");
		System.out.println(compressed);
		String decompressed = decode(compressed);
		System.out.println(decompressed);
	}
}
