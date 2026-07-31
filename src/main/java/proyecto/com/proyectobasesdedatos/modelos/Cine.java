package proyecto.com.proyectobasesdedatos.modelos;

import java.util.List;

import proyecto.com.proyectobasesdedatos.servicios.*;

public class Cine {
    private static Cine instancia;

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
        System.out.println("=== INICIANDO CARGA DE DATOS ===");

        // 1. Datos básicos (sin dependencias)
        System.out.println("Cargando países...");
        paises = ServicioPaises.getInstance().obtenerTodos();

        System.out.println("Cargando ciudades...");
        ciudades = ServicioCiudades.getInstance().obtenerTodos();

        System.out.println("Cargando idiomas...");
        idiomas = ServicioIdiomas.getInstance().obtenerTodos();

        System.out.println("Cargando géneros...");
        generos = ServicioGeneros.getInstance().obtenerTodos();

        System.out.println("Cargando puestos de trabajo...");
        puestosTrabajo = ServicioPuestosTrabajo.getInstance().obtenerTodos();

        // 2. Ubicaciones (dependen de datos básicos)
        System.out.println("Cargando municipios...");
        municipios = ServicioMunicipios.getInstance().obtenerTodos();

        System.out.println("Cargando sectores...");
        sectores = ServicioSectores.getInstance().obtenerTodos();

        // 3. Sucursales (dependen de sectores)
        System.out.println("Cargando sucursales...");
        sucursales = ServicioSucursales.getInstance().obtenerTodos();

        // 4. Personas y derivados
        System.out.println("Cargando personas...");
        personas = ServicioPersonas.getInstance().obtenerTodos();

        System.out.println("Cargando clientes...");
        clientes = ServicioClientes.getInstance().obtenerTodos();

        System.out.println("Cargando actores...");
        actores = ServicioActores.getInstance().obtenerTodos();

        System.out.println("Cargando directores...");
        directores = ServicioDirectores.getInstance().obtenerTodos();

        System.out.println("Cargando empleados...");
        empleados = ServicioEmpleados.getInstance().obtenerTodos();

        // 5. Películas (dependen de directores e idiomas)
        System.out.println("Cargando películas...");
        peliculas = ServicioPeliculas.getInstance().obtenerTodos();

        // 6. Relaciones de películas
        System.out.println("Cargando géneros de películas...");
        ServicioGenerosPeliculas.getInstance().cargar();

        System.out.println("Cargando actores de películas...");
        ServicioActoresPeliculas.getInstance().cargar();

        System.out.println("Cargando subtítulos de películas...");
        ServicioSubtitulosPeliculas.getInstance().cargar();

        // 7. Salas (dependen de sucursales)
        System.out.println("Cargando salas...");
        salas = ServicioSalas.getInstance().obtenerTodos();

        // 8. Asientos (dependen de salas)
        System.out.println("Cargando asientos...");
        asientos = ServicioAsientos.getInstance().obtenerTodos();

        // 9. Funciones (dependen de películas y salas)
        System.out.println("Cargando funciones...");
        funciones = ServicioFunciones.getInstance().obtenerTodos();

        // 10. Ventas (dependen de clientes, empleados y sucursales)
        System.out.println("Cargando ventas...");
        ventas = ServicioVentas.getInstance().obtenerTodos();

        // 11. Boletos (dependen de ventas, funciones y asientos)
        System.out.println("Cargando boletos...");
        boletos = ServicioBoletos.getInstance().obtenerTodos();

        System.out.println("=== CARGA DE DATOS COMPLETADA ===");
        System.out.println("Total de registros cargados:");
        System.out.println("  Paises: " + (paises != null ? paises.size() : 0));
        System.out.println("  Ciudades: " + (ciudades != null ? ciudades.size() : 0));
        System.out.println("  Municipios: " + (municipios != null ? municipios.size() : 0));
        System.out.println("  Sectores: " + (sectores != null ? sectores.size() : 0));
        System.out.println("  Idiomas: " + (idiomas != null ? idiomas.size() : 0));
        System.out.println("  Generos: " + (generos != null ? generos.size() : 0));
        System.out.println("  Sucursales: " + (sucursales != null ? sucursales.size() : 0));
        System.out.println("  PuestosTrabajo: " + (puestosTrabajo != null ? puestosTrabajo.size() : 0));
        System.out.println("  Personas: " + (personas != null ? personas.size() : 0));
        System.out.println("  Clientes: " + (clientes != null ? clientes.size() : 0));
        System.out.println("  Actores: " + (actores != null ? actores.size() : 0));
        System.out.println("  Directores: " + (directores != null ? directores.size() : 0));
        System.out.println("  Empleados: " + (empleados != null ? empleados.size() : 0));
        System.out.println("  Peliculas: " + (peliculas != null ? peliculas.size() : 0));
        System.out.println("  Salas: " + (salas != null ? salas.size() : 0));
        System.out.println("  Asientos: " + (asientos != null ? asientos.size() : 0));
        System.out.println("  Funciones: " + (funciones != null ? funciones.size() : 0));
        System.out.println("  Ventas: " + (ventas != null ? ventas.size() : 0));
        System.out.println("  Boletos: " + (boletos != null ? boletos.size() : 0));
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
}