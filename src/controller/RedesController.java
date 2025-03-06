package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {

	private String os() {
		return System.getProperty("os.name");
	}

	public String ip() {
		String nome = os();
		StringBuilder builder = new StringBuilder();

		if (nome.contains("Windows")) {
			try {
				Process p = Runtime.getRuntime().exec("ipconfig");
				InputStream stream = p.getInputStream();
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader buffer = new BufferedReader(reader);

				String adaptador = "";
				String linha = buffer.readLine();
				while (linha != null) {
					if (linha.contains("Adaptador"))
						adaptador = linha;

					if (linha.contains("IPv4")) {
						String[] dados = linha.split(" : ");
						String ipv4 = dados[1];

						builder.append("Adaptador: " + adaptador);
						builder.append("\n");
						builder.append("IPv4: " + ipv4);
						builder.append("\n");
					}

					linha = buffer.readLine();
				}

				buffer.close();
				reader.close();
				stream.close();
			} catch (IOException e) {
				String err = e.getMessage();
				System.err.println(err);
			}
		} else if (nome.contains("Linux")) {
			try {
				Process p = Runtime.getRuntime().exec("ifconfig");
				InputStream stream = p.getInputStream();
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader buffer = new BufferedReader(reader);

				String linha = buffer.readLine();
				String adaptador = "";
				while (linha != null) {
					if (linha.contains("flags="))
						adaptador = linha.split(": ")[0];

					if (linha.contains("inet ")) {
						String[] dados = linha.split("inet ");
						String ipv4 = dados[1].split(" ")[0];

						builder.append("Adaptador: " + adaptador);
						builder.append("\n");
						builder.append("IPv4: " + ipv4);
						builder.append("\n");
					}

					linha = buffer.readLine();
				}

				buffer.close();
				reader.close();
				stream.close();
			} catch (IOException e) {
				String err = e.getMessage();
				System.err.println(err);
			}
		} else {
			return "SO não identificado";
		}
		
		return builder.toString();
	}

	public String ping() {
		String nome = os();
		StringBuilder builder = new StringBuilder();

		if (nome.contains("Windows")) {
			try {
				Process p = Runtime.getRuntime().exec("ping -4 -n 10 www.google.com.br");
				InputStream stream = p.getInputStream();
				InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
				BufferedReader buffer = new BufferedReader(reader);

				String linha = buffer.readLine();
				String linhaMedia = null;
				while (linha != null) {
					linha = buffer.readLine();
					if (linha != null)
						linhaMedia = linha;
				}
				String media = linhaMedia.split(", ")[2];
				String ms = media.split(" = ")[1];


				buffer.close();
				reader.close();
				stream.close();
				
				builder.append("A média do ping foi de: " + ms);
			} catch (IOException e) {
				String err = e.getMessage();
				System.err.println(err);
			}
		} else if (nome.contains("Linux")) {
			try {
				Process p = Runtime.getRuntime().exec("ping -4 -c 10 www.google.com.br");
				InputStream stream = p.getInputStream();
				InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
				BufferedReader buffer = new BufferedReader(reader);

				String linha = buffer.readLine();
				while (linha != null) {
					if (linha.contains("avg")) {
						String values = linha.split(" = ")[1];
						String avg = values.split("/")[1];
						builder.append("A média do ping foi de: " + avg + "ms");

					}

					linha = buffer.readLine();
				}

				buffer.close();
				reader.close();
				stream.close();
			} catch (IOException e) {
				String err = e.getMessage();
				System.err.println(err);
			}
		} else {
			return "SO não identificado";
		}
		
		return builder.toString();
	}

}
