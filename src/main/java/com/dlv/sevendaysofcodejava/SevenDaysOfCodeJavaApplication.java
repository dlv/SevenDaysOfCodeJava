package com.dlv.sevendaysofcodejava;

import com.dlv.sevendaysofcodejava.main.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SevenDaysOfCodeJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SevenDaysOfCodeJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		File file = new File("top250.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(file);

		// 2. Navegar no caminho: data -> chartTitles -> edges
		JsonNode edges = root.path("data").path("chartTitles").path("edges");

		List<String> idList = new ArrayList<>();

		// 3. Iterar sobre os nós e extrair o "id" de dentro de cada "node"
		for (JsonNode edge : edges) {
			String id = edge.path("node").path("id").asText();
			if (!id.isEmpty()) {
				idList.add(id);
			}
		}

		Main main = new Main();
		main.buscarPorId(idList);
	}
}
