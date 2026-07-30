package proyecto.com.proyectobasesdedatos.modelos;

import java.util.List;

import proyecto.com.proyectobasesdedatos.servicios.*;

public class Cine {
    private static Cine instancia;

    // Listas de datos
    private List<Pais> paises;
    private List<Ciudad> ciudades;
    private List<Municipio> municipios;
    private List<Sector> sectores;
    private List<Idioma> idiomas;
    private List<Genero> generos;
    private List<Sucursal> sucursales;
    private List<PuestoTrabajo> puestosTrabajo;
    private List<Persona> personas;
    private List<Cliente> clientes;
    private List<Actor> actores;
    private List<Director> directores;
    private List<Empleado> empleados;
    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Asiento> asientos;
    private List<Funcion> funciones;
    private List<Venta> ventas;
    private List<Boleto> boletos;

    private Cine() {
        cargarDatos();
    }

    public static synchronized Cine getInstance() {
        if (instancia == null) {
            instancia = new Cine();
        }
        return instancia;
    }

    private void cargarDatos() {
        System.out.println("===== INICIANDO CARGA DE DATOS =====");

        // Cargar datos en orden de dependencias
        ServicioPaises servicioPaises = new ServicioPaises();
        servicioPaises.cargar();
        paises = servicioPaises.obtenerTodos();

        ServicioCiudades servicioCiudades = new ServicioCiudades();
        servicioCiudades.cargar();
        ciudades = servicioCiudades.obtenerTodos();

        ServicioMunicipios servicioMunicipios = new ServicioMunicipios();
        servicioMunicipios.cargar();
        municipios = servicioMunicipios.obtenerTodos();

        ServicioSectores servicioSectores = new ServicioSectores();
        servicioSectores.cargar();
        sectores = servicioSectores.obtenerTodos();

        ServicioIdiomas servicioIdiomas = new ServicioIdiomas();
        servicioIdiomas.cargar();
        idiomas = servicioIdiomas.obtenerTodos();

        ServicioGeneros servicioGeneros = new ServicioGeneros();
        servicioGeneros.cargar();
        generos = servicioGeneros.obtenerTodos();

        ServicioPuestosTrabajo servicioPuestos = new ServicioPuestosTrabajo();
        servicioPuestos.cargar();
        puestosTrabajo = servicioPuestos.obtenerTodos();

        ServicioSucursales servicioSucursales = new ServicioSucursales();
        servicioSucursales.cargar();
        sucursales = servicioSucursales.obtenerTodos();

        ServicioPersonas servicioPersonas = new ServicioPersonas();
        servicioPersonas.cargar();
        personas = servicioPersonas.obtenerTodos();

        ServicioClientes servicioClientes = new ServicioClientes();
        servicioClientes.cargar();
        clientes = servicioClientes.obtenerTodos();

        ServicioActores servicioActores = new ServicioActores();
        servicioActores.cargar();
        actores = servicioActores.obtenerTodos();

        ServicioDirectores servicioDirectores = new ServicioDirectores();
        servicioDirectores.cargar();
        directores = servicioDirectores.obtenerTodos();

        ServicioEmpleados servicioEmpleados = new ServicioEmpleados();
        servicioEmpleados.cargar();
        empleados = servicioEmpleados.obtenerTodos();

        ServicioPeliculas servicioPeliculas = new ServicioPeliculas();
        servicioPeliculas.cargar();
        peliculas = servicioPeliculas.obtenerTodos();

        ServicioSalas servicioSalas = new ServicioSalas();
        servicioSalas.cargar();
        salas = servicioSalas.obtenerTodos();

        ServicioAsientos servicioAsientos = new ServicioAsientos();
        servicioAsientos.cargar();
        asientos = servicioAsientos.obtenerTodos();

        // Cargar relaciones
        ServicioGenerosPeliculas servicioGenerosPeliculas = new ServicioGenerosPeliculas();
        servicioGenerosPeliculas.cargar();

        ServicioActoresPeliculas servicioActoresPeliculas = new ServicioActoresPeliculas();
        servicioActoresPeliculas.cargar();

        ServicioSubtitulosPeliculas servicioSubtitulos = new ServicioSubtitulosPeliculas();
        servicioSubtitulos.cargar();

        ServicioFunciones servicioFunciones = new ServicioFunciones();
        servicioFunciones.cargar();
        funciones = servicioFunciones.obtenerTodos();

        ServicioVentas servicioVentas = new ServicioVentas();
        servicioVentas.cargar();
        ventas = servicioVentas.obtenerTodos();

        ServicioBoletos servicioBoletos = new ServicioBoletos();
        servicioBoletos.cargar();
        boletos = servicioBoletos.obtenerTodos();
    }

    // Getters para todas las listas
    public List<Pais> getPaises() { return paises; }
    public List<Ciudad> getCiudades() { return ciudades; }
    public List<Municipio> getMunicipios() { return municipios; }
    public List<Sector> getSectores() { return sectores; }
    public List<Idioma> getIdiomas() { return idiomas; }
    public List<Genero> getGeneros() { return generos; }
    public List<Sucursal> getSucursales() { return sucursales; }
    public List<PuestoTrabajo> getPuestosTrabajo() { return puestosTrabajo; }
    public List<Persona> getPersonas() { return personas; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Actor> getActores() { return actores; }
    public List<Director> getDirectores() { return directores; }
    public List<Empleado> getEmpleados() { return empleados; }
    public List<Pelicula> getPeliculas() { return peliculas; }
    public List<Sala> getSalas() { return salas; }
    public List<Asiento> getAsientos() { return asientos; }
    public List<Funcion> getFunciones() { return funciones; }
    public List<Venta> getVentas() { return ventas; }
    public List<Boleto> getBoletos() { return boletos; }

    // Métodos de utilidad para búsquedas específicas
    public Pais buscarPaisPorCodigo(int codigo) {
        return paises.stream().filter(p -> p.getCodigo() == codigo).findFirst().orElse(null);
    }

    public Ciudad buscarCiudadPorCodigo(int codigo) {
        return ciudades.stream().filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
    }

    public Pelicula buscarPeliculaPorCodigo(int codigo) {
        return peliculas.stream().filter(p -> p.getCodigo() == codigo).findFirst().orElse(null);
    }

    public Cliente buscarClientePorCodigo(int codigo) {
        return clientes.stream().filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
    }

    public List<Pelicula> buscarPeliculasPorGenero(int codigoGenero) {
        return peliculas.stream()
                .filter(p -> p.getGeneros() != null &&
                        p.getGeneros().stream().anyMatch(g -> g.getCodigo() == codigoGenero))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Pelicula> buscarPeliculasPorActor(int codigoActor) {
        return peliculas.stream()
                .filter(p -> p.getActores() != null &&
                        p.getActores().stream().anyMatch(a -> a.getCodigo() == codigoActor))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Funcion> buscarFuncionesPorPelicula(int codigoPelicula) {
        return funciones.stream()
                .filter(f -> f.getPelicula().getCodigo() == codigoPelicula)
                .collect(java.util.stream.Collectors.toList());
    }
}