package com.comprime;

import java.util.*;
import java.io.*;

public class LempelZivWelsh {
	@SuppressWarnings("resource")
	public static void encode(File arq, String dir) throws IOException {

		// Direct = Diretório do Window OU Linux, user.home pega o atual usuário e seu
		// diretório
		File direct = new File(System.getProperty("user.home") + "/Desktop");

		// Arquivo de saída
		File saida = new File(direct, dir);
		InputStream is = new FileInputStream(arq);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		// String linha vai receber a próxima linha do arquivo parâmetro pelo
		// BufferedReader;
		String linha = br.readLine();

		// Texto vai ser a concatenação das linhas.
		String texto = linha;
		while (linha != null) {
			linha = br.readLine();
			texto = texto + linha;

		}
		br.close();

		// Construindo o dicionário de indices;
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
		// o loop recebera valor null ao final dela e entrará em condição de parada
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

		try {
			saida.createNewFile();
			FileWriter escr = new FileWriter(saida);
			escr.write(resultado.toString());
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo");
			e.printStackTrace();
		}
	}

	public static void decode(File arq, String out) throws IOException { // List<Integer> arquivo
		File direct = new File(System.getProperty("user.home") + "/Desktop");
		File saida = new File(direct, out);
		List<Integer> arquivo = new ArrayList<Integer>();
		List<String> temp = new ArrayList<String>();
		InputStream is = new FileInputStream(arq);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		br.read();
		String texto = "";
		String caracter = "";
		int valor;
		while ((valor = br.read()) != -1) {
			caracter = String.valueOf((char) valor);
			if (caracter == "]") {
				break;
			} else {
				texto = texto + caracter;
			}
		}
		br.close();

		// Como o arquivo bin que vai receber vai ter vários indices, separamos eles por
		// virgulas, como num arquivo CSV
		temp = Arrays.asList(texto.split(","));
		for (String str : temp) {
			StringBuilder sb = new StringBuilder(str);
			if (str.charAt(0) == ' ')
				sb.deleteCharAt(0);
			str = sb.toString();
			arquivo.add(Integer.parseInt(str));
		}

		// Monta o dicionário padrão ASCII
		int tamanho = 128;
		Map<Integer, String> dicionario = new HashMap<Integer, String>();
		for (int i = 0; i < tamanho; i++)
			dicionario.put(i, "" + (char) i);

		String ab = "";

		// Stringbuffer que vai receber todo o texto;
		StringBuffer resultado = new StringBuffer(ab);
		String palavra = "";

		// Loop recebe os indices o arquivo até ficar vazio
		for (int indice : arquivo) {

			// Quando o indice encontrar espaço, ele encerra palavra e coloca ela no
			// dicionário e no resultado
			if (indice == 32) {
				dicionario.put(tamanho++, palavra);
				resultado.append(palavra);
				resultado.append(" ");
				palavra = "";
			} else

			// Quandno não encontra, ele concatena a palavra
			if (dicionario.containsKey(indice))
				palavra = palavra + dicionario.get(indice);

		}
		resultado.append(palavra);

		try {
			saida.createNewFile();
			FileWriter escr = new FileWriter(saida);
			escr.write(resultado.toString());
			escr.close();
		} catch (IOException e) {
			System.out.println("Erro ao criar arquivo");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String in = "", out = "";
		System.out.println("Insira o código");
		String[] comando = new String[5];
		for (int i = 0; i < 5; i++) {
			comando[i] = scan.next();
		}
		scan.close();

		in = comando[2];
		out = comando[4];
		
		/*if (comando[0].equals("encode")) {
			in = comando[2];
			out = comando [4];
		}else if(comando[0].equals("decode")){
			in = comando[2];
			out = comando[4];
		}else 
			System.out.println("Comando inserido inválido");
			*/
		
		File arq = new File(System.getProperty("user.home") + "/Desktop/" + in);
		
		// O nome do arquivo que o 'encode' vái gerar é sai.bin, então tem que servir de
		// entrada para o decode
		File arq2 = new File(System.getProperty("user.home") + "/Desktop/" + in);
		try {
			if(comando[0].equals("encode")) 
				encode(arq, out);
			else if(comando[0].equals("decode")) 
					decode(arq2, out);
				else
					System.out.println("Comando inserido inválido"); 
			
		} catch (IOException e) {
			System.out.println("Arquivo não existente");
			e.printStackTrace();
		}

		// System.out.println(resultado);
		// System.out.println(compressed);
		// String decompressed = decode(compressed);
		// System.out.println(decompressed);
	}
}
