package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Programa {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.println("Programinha que lê um arquivo CSV com dados de colaboradores e compara o salario de cada um conforme você digitar\ne também mostra o salário dos colaboradores cujos nomes começam com a letra que você digitar!!");
		System.out.println();
		System.out.println("XXXXXXXXXXXXXXXXXXX");
		System.out.println();
		System.out.println("Digite o caminho do arquivo: ");
		String caminho = sc.nextLine();
		try(BufferedReader br = new BufferedReader(new FileReader(caminho))){
			List<Employee> lista = new ArrayList<>();
			String linha = br.readLine();
			while(linha != null) {
				String[] campos = linha.split(",");
				lista.add(new Employee(campos[0], campos[1], Double.parseDouble(campos[2])));
				linha = br.readLine();
			}
			
			System.out.println("Digite um valor de salário: ");
			double salario = sc.nextDouble();
			sc.nextLine();
			
			//Coletar os e-mails de colaboradores que recebem mais que o salario digitado acima
			List<String> emails = lista.stream()
					.filter(x -> x.getSalario() > salario)
					.map(x -> x.getEmail())
					.sorted()
					.collect(Collectors.toList());
			
			System.out.println("Email dos colaboradores cujo salário é maior que R$" + String.format("%.2f", salario) + ":");
			emails.forEach(System.out::println);
			
			System.out.println();
			//Somar salarios dos colab que começam com o char desejado
			System.out.println("Digite uma letra: ");
			char inicial = sc.next().charAt(0);
			double soma = lista.stream()
					.filter(x -> x.getNome().charAt(0) == inicial)
					.map(x -> x.getSalario())
					.reduce(0.0, (x,y) -> x + y);
			if (soma == 0) {
				System.out.println("Não existem colaboradores que começem com a letra " + inicial + "!");
			}
			else {
				System.out.println("Soma dos salários dos colaboradores cujo nome começa com '" + inicial + "': R$" + String.format("%.2f", soma));
			}
		}catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		System.out.println();
		System.out.println("ISSO É TUDO PESSOAL!");
		sc.close();

	}

}
