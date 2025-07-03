package com.fernandoruiz87.literalura.principal;

import com.fernandoruiz87.literalura.dto.AutorDTO;
import com.fernandoruiz87.literalura.dto.LibroDTO;
import com.fernandoruiz87.literalura.model.Autor;
import com.fernandoruiz87.literalura.model.Libro;
import com.fernandoruiz87.literalura.repository.AutorRepository;
import com.fernandoruiz87.literalura.repository.LibroRepository;
import com.fernandoruiz87.literalura.service.APIservice;
import com.fernandoruiz87.literalura.service.Conversor;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner sc = new Scanner(System.in);
    private final APIservice api = new APIservice();
    private final LibroRepository libroRepository;
    private final String URL_BASE = "https://gutendex.com/books/";
    private final AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.libroRepository = repository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        boolean flag = true;
        while (flag) {
            System.out.println("--------------");
            System.out.println("¡Bienvenido a literalura!");
            System.out.println("Para continuar elija una opción: ");
            System.out.println("""
                    \t 1 - Buscar libro por titulo
                    \t 2 - Mostrar libros registrados
                    \t 3 - Mostrar autores registrados
                    \t 4 - Mostrar autores vivos en un año determinado
                    \t 5 - Mostrar libros por idioma
                    \t 0 - Salir
                    """);
            System.out.print("Opcion: ");
            var opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibro();
                    break;
                case "2":
                    mostrarLibrosRegistrados();
                    break;
                case "3":
                    mostrarAutoresRegistrados();
                    break;
                case "4":
                    mostrarAutoresVivosEnAño();
                    break;
                case "5":
                    mostrarLibrosPorIdioma();
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }

    }

    private void buscarLibro() {
        System.out.print("Escribe el titulo del libro: ");
        var titulo = sc.nextLine();

        if (titulo.isBlank()) {
            System.out.println("El titulo no puede estar vacio");
            return;
        }

        var json = api.obtenerDatos(URL_BASE + "?search=" + titulo);
        Optional<LibroDTO> datos = Conversor.obtenerDatos(json, "results", LibroDTO.class);

        if (datos.isEmpty()) {
            System.out.println("No se encontraron resultados para el titulo: " + titulo);
            return;
        }

        System.out.println("Libro encontrado, guardando en la base de datos...");
        LibroDTO libroDTO = datos.get();
        Libro libro = new Libro(libroDTO);

        List<Autor> autores = libroDTO.autores().stream().map(this::buscarOCrearAutor).toList();

        libro.setAutores(autores);

        libroRepository.save(libro);
        System.out.println("Libro guardado exitosamente.......\n" + libro);
    }

    private void mostrarAutoresRegistrados() {
        System.out.println("Autores registrados:");
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void mostrarLibrosRegistrados() {
        System.out.println("Libros registrados:");
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> System.out.println(libro.toString()));
        }
    }

    private void mostrarAutoresVivosEnAño() {
        System.out.print("Ingrese el año: ");
        var year = sc.nextLine();

        if (year.isBlank()) {
            System.out.println("El año no puede estar vacío.");
            return;
        }

        try {
            int anioInt = Integer.parseInt(year);
            List<Autor> autoresVivos = autorRepository.buscarAutoresVivosEnAnio(anioInt)
                    .stream()
                    .filter(autor -> autor.getFechaFallecimiento() == null ||
                            autor.getFechaFallecimiento() > anioInt).toList();

            if (autoresVivos.isEmpty()) {
                System.out.println("No hay autores vivos en el año " + year);
            } else {
                autoresVivos.forEach(a -> System.out.println(a.toString()));
            }
        } catch (NumberFormatException e) {
            System.out.println("El año debe ser un número válido.");
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                Idiomas disponibles:
                \t es - Español
                \t en - Inglés
                """);
        System.out.print("Ingrese el idioma: ");
        var idioma = sc.nextLine();

        if (idioma.isBlank()) {
            System.out.println("El idioma no puede estar vacío.");
            return;
        }

        List<Libro> librosPorIdioma = libroRepository.buscarLibrosPorIdiomaIgnoreCase(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma: " + idioma);
        } else {
            librosPorIdioma.forEach(libro -> System.out.println(libro.toString()));
        }
    }

    private Autor buscarOCrearAutor(AutorDTO autorDTO) {
        return autorRepository.findByNombreAndFechaNacimientoAndFechaFallecimiento(autorDTO.nombre(), autorDTO.fechaNacimiento(), autorDTO.fechaFallecimiento()).orElseGet(() -> {
            Autor nuevo = new Autor();
            nuevo.setNombre(autorDTO.nombre());
            nuevo.setFechaNacimiento(autorDTO.fechaNacimiento());
            nuevo.setFechaFallecimiento(autorDTO.fechaFallecimiento());
            return autorRepository.save(nuevo);
        });
    }
}
