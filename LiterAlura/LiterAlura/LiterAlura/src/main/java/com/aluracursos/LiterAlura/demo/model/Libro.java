package com.aluracursos.LiterAlura.demo.model;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private int numeroDescargas;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    // Getters, setters, constructores
}

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer añoNacimiento;
    private Integer añoFallecimiento;

    // Getters, setters, constructores
}