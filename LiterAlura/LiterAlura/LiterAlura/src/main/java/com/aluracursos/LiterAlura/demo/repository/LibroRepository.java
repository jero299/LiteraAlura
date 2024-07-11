package com.aluracursos.LiterAlura.demo.repository;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Libro buscarLibroPorTitulo(String titulo) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.get("results");
        if (results.size() > 0) {
            JsonNode book = results.get(0);
            return mapearLibro(book);
        }
        return null;
    }

    private Libro mapearLibro(JsonNode bookNode) {
        Libro libro = new Libro();
        libro.setTitulo(bookNode.get("title").asText());
        libro.setIdioma(bookNode.get("languages").get(0).asText());
        libro.setNumeroDescargas(bookNode.get("download_count").asInt());

        JsonNode authorNode = bookNode.get("authors").get(0);
        Autor autor = new Autor();
        autor.setNombre(authorNode.get("name").asText());
        autor.setAnioNacimiento(authorNode.get("birth_year").isNull() ? null : authorNode.get("birth_year").asInt());
        autor.setAnioFallecimiento(authorNode.get("death_year").isNull() ? null : authorNode.get("death_year").asInt());
        libro.setAutor(autor);

        return libro;
    }
}