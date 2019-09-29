package com.comprime;

import java.util.*;

public class LempelZivWelsh {
	public static List<Integer> encode(String texto) {
		// Construindo o dicionário de indices.

		int tamanho = 128;
		Map<String, Integer> dicionario = new HashMap<String, Integer>();
		// Criar uma Lista de Vetores HashMap com os indices do dicionário, inicialmente
		// vai ser limpo, a cada etapa do i
		// vai copiando os indices do HashMap nesse dicionário, que são os ASCII;

		for (int i = 0; i < tamanho; i++) {
			// indice i que vai receber o caractere ASCII.

			dicionario.put("" + (char) i, i);
		}

		String palavra = "";
		// Lista de Vetores
		List<Integer> resultado = new ArrayList<Integer>();
		List<Integer> vetTemp = new ArrayList<Integer>();
		// Converte o texto em vetor de caracteres, palavra = caracteres entre espaços
		// vazios
		// o loop recebera valor null ao final dela e entrará em condição de parada;

		for (char proxCaractere : texto.toCharArray()) {
			// Pega o prox caractere
			String ab = "" + proxCaractere;
			// Procura o espaço, se não houver, ele concatena o caractere à palavra;
			if (!(proxCaractere == ' ')) {
				// Procura se ja existe a palavra no dicionário, caso exista, ele pega o indice
				// e adiciona
				// no vetor temporário
				if (dicionario.containsKey(ab))
					vetTemp.add(dicionario.get(ab));

				// Concatena toda a palavra com o proximo caractere dela, se não for espaço.
				palavra = palavra + ab;
			} else {
				// Se houver espaço, finaliza palavra, e ve se ela existe no dicionário, se
				// não ele cria um novo indice
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
		// Monta o dicionário padrão ASCII
		int tamanho = 128;
		Map<Integer, String> dicionario = new HashMap<Integer, String>();
		for (int i = 0; i < tamanho; i++)
			dicionario.put(i, "" + (char) i);

		// Cria variável que vai recever o indice do primeiro cacacter do arquivo
		// comprimido
		String ab = "";
		StringBuffer resultado = new StringBuffer(ab);
		String palavra = "";
		// Loop recebe os indices o arquivo até ficar vazio
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
