package com.aluracursos.LiterAlura.demo;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- LiterAlura ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar autores");
            System.out.println("4. Listar autores vivos en un año");
            System.out.println("5. Mostrar cantidad de libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    buscarLibro(scanner);
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos(scanner);
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void buscarLibro(Scanner scanner) throws Exception {
        System.out.print("Ingresa el título del libro: ");
        String titulo = scanner.nextLine();
        Libro libro = gutendexService.buscarLibroPorTitulo(titulo);
        if (libro != null) {
            libroRepository.save(libro);
            System.out.println("Libro encontrado y guardado: " + libro);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibros() {
        libroRepository.findAll().forEach(System.out::println);
    }

    private void listarAutores() {
        autorRepository.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivos(Scanner scanner) {
        System.out.print("Ingresa el año: ");
        int anio = scanner.nextInt();
        autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio)
                .forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        Map<String, Long> librosPorIdioma = libroRepository.findAll().stream()
                .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));
        librosPorIdioma.forEach((idioma, cantidad) ->
                System.out.println(idioma + ": " + cantidad + " libro(s)"));
    }
}