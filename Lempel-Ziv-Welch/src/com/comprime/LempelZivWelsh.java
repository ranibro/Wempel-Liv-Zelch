package com.comprime;
import java.util.*;

public class LempelZivWelsh {
	public static List<Integer> comprimir(String texto) {
        // Construindo o dicionário de indices.
        
		int tamanho = 128;
        Map<String,Integer> dicionario = new HashMap<String,Integer>();
        // Criar uma Lista de Vetores HashMap com os indices do dicionário, inicialmente vai ser limpo, a cada etapa do i
        // vai copiando os indices do HashMap nesse dicionário, que são os ASCII;
        
        for (int i = 0; i < tamanho; i++) {
        // indice i que vai receber o caractere ASCII.
        
        	dicionario.put("" + (char)i, i);
		}
        
        String palavra = "";
        //Lista de Vetores
        List<Integer> resultado = new ArrayList<Integer>();
        List<Integer> vetTemp = new ArrayList<Integer>();
        //Converte o texto em vetor de caracteres, palavra = caracteres entre espaços vazios
        //o loop recebera valor null ao final dela e entrará em condição de parada;
        
        for (char proxCaractere : texto.toCharArray()) {
        //Pega o prox caractere e concatena com os caracteres que formam a palavra
            String ab = ""+proxCaractere;
        //Procura o espaço, se não houver, ele concatena o caractere à palavra;
            if (!(proxCaractere == ' ')) {												//(dicionario.containsKey(ab))
            	if(dicionario.containsKey(ab))
            		vetTemp.add(dicionario.get(ab));
            	
            	palavra = palavra+ab;
            }
            else {            	
        //Se não houver espaço, finaliza palavra, e ve se ela existe no dicionário, se não ele cria um novo indice
            	if(dicionario.containsKey(palavra)) {
            		//for(int j=1 ; j < vetTemp.size(); j++)
            		resultado.add(dicionario.get(palavra));  resultado.add(dicionario.get(" "));
            	}
            	else {
            		for(int j=0 ; j < vetTemp.size(); j++)
                		resultado.add(vetTemp.get(j)); resultado.add(dicionario.get(" "));
            		dicionario.put(palavra, tamanho++);
            	}
                
            		vetTemp.clear();
                palavra = "";															//palavra = "" + proxCaractere;
            }
        }
 
        // Output the code for w.
        if (!palavra.equals(""))
            resultado.add(dicionario.get(palavra));
        return resultado;
    }
	public static String decompress(List<Integer> compressed) {
        // Build the dictionary.
        int dictSize = 127;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 127; i++)
            dictionary.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        for (int k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
 
            result.append(entry);
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }
        return result.toString();
    }
 
    public static void main(String[] args) {
        List<Integer> compressed = comprimir("This is as Simple as it is");
        System.out.println(compressed);
        String decompressed = decompress(compressed);
        System.out.println(decompressed);
    }
}
