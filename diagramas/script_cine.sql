CREATE DATABASE cine;
USE cine;

CREATE TABLE Ciudades (
  codigo INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  codigo_postal INT
);

CREATE TABLE Idiomas(
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40)
);

CREATE TABLE Generos (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

INSERT INTO generos (nombre) VALUES
     ('Acción'),
     ('Aventura'),
     ('Animación'),
     ('Comedia'),
     ('Ciencia Ficción'),
     ('Documental'),
     ('Drama'),
     ('Fantasía'),
     ('Musical'),
     ('Misterio'),
     ('Romance'),
     ('Suspenso'),
     ('Terror'),
     ('Western'),
     ('Bélico'),
     ('Deportes'),
     ('Crimen'),
     ('Histórico'),
     ('Familiar'),
     ('Artes Marciales');

CREATE TABLE Puestos_Trabajo(
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    salario_base FLOAT NOT NULL CHECK (salario_base > 0)
);

CREATE TABLE Clientes (
  codigo INT AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(50) NOT NULL,
  apellidos VARCHAR(50) NOT NULL,
  telefono VARCHAR(15),
  fecha_nacimiento DATE,
  cantidad_entradas INT DEFAULT 0,
  sexo CHAR(1) CHECK(sexo = 'M' or sexo = 'F') NOT NULL,
  correo VARCHAR(50) UNIQUE NOT NULL,
  ciudad_residencia INT NOT NULL,
  FOREIGN KEY (ciudad_residencia) REFERENCES Ciudades(codigo)
);

CREATE TABLE Peliculas (
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   codigo_director INT NOT NULL,
   FOREIGN KEY (codigo_director) REFERENCES Directores(codigo)
   duracion_minutos INT,
   clasificacion VARCHAR(10),
    idioma_pelicula INT NOT NULL,
   portada BLOB
);

CREATE TABLE Sucursales (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(15),
    correo VARCHAR(50) UNIQUE NOT NULL,
    codigo_ciudad INT NOT NULL,
    FOREIGN KEY (codigo_ciudad) REFERENCES Ciudades(codigo)
);

CREATE TABLE Generos_Peliculas (
   codigo_pelicula INT NOT NULL,
   codigo_generos INT NOT NULL,
   PRIMARY KEY (codigo_pelicula, codigo_generos),
   FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo) ON DELETE CASCADE,
   FOREIGN KEY (codigo_generos) REFERENCES Generos(codigo) ON DELETE CASCADE
);

CREATE TABLE Salas (
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(50) NOT NULL,
   capacidad INT NOT NULL,
   codigo_sucursal INT NOT NULL,
   FOREIGN KEY (codigo_sucursal) REFERENCES Sucursales(codigo)
);

CREATE TABLE Empleados (
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   nombres VARCHAR(50) NOT NULL,
   apellidos VARCHAR(50) NOT NULL,
   sexo CHAR(1) CHECK(sexo = 'M' or sexo = 'F') NOT NULL,
   telefono VARCHAR(15),
   correo VARCHAR(50) UNIQUE NOT NULL,
   puesto VARCHAR(20) NOT NULL,
   codigo_sucursal INT NOT NULL,
   FOREIGN KEY (codigo_sucursal) REFERENCES Sucursales(codigo)
);

CREATE TABLE Asientos (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    fila VARCHAR(5) NOT NULL,
    codigo_sala INT NOT NULL,
    FOREIGN KEY (codigo_sala) REFERENCES Salas(codigo)
);

CREATE TABLE Funciones (
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   fecha DATE NOT NULL,
   hora_inicio TIME NOT NULL,
   hora_fin TIME NOT NULL,
   precio_entrada DECIMAL(10,2) NOT NULL,
   codigo_pelicula INT NOT NULL,
   codigo_sala INT NOT NULL,
   FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo),
   FOREIGN KEY (codigo_sala) REFERENCES Salas(codigo)
);

CREATE TABLE Ventas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    precio_total DECIMAL(10,2) NOT NULL,
    codigo_cliente INT NOT NULL,
    codigo_empleado INT NOT NULL,
    codigo_sucursal INT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES Clientes(codigo),
    FOREIGN KEY (codigo_empleado) REFERENCES Empleados(codigo),
    FOREIGN KEY (codigo_sucursal) REFERENCES Sucursales(codigo)
);

CREATE TABLE Boletos (
 codigo INT AUTO_INCREMENT PRIMARY KEY,
 precio_aplicado DECIMAL(10,2) NOT NULL,
 codigo_venta INT NOT NULL,
 codigo_funcion INT NOT NULL,
 codigo_asiento INT NOT NULL,
 FOREIGN KEY (codigo_venta) REFERENCES Ventas(codigo) ON DELETE CASCADE,
 FOREIGN KEY (codigo_funcion) REFERENCES Funciones(codigo),
 FOREIGN KEY (codigo_asiento) REFERENCES Asientos(codigo)
);

CREATE TABLE Actores(
   codigo INT AUTO_INCREMENT PRIMARY KEY,
   nombres VARCHAR(50) NOT NULL,
   apellidos VARCHAR(50) NOT NULL,
   fecha_nacimiento DATE,
   sexo CHAR(1) CHECK(sexo = 'M' or sexo = 'F') NOT NULL
);

CREATE TABLE Directores(
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    sexo CHAR(1) CHECK(sexo = 'M' or sexo = 'F') NOT NULL
);

CREATE TABLE Actores_Peliculas (
   codigo_pelicula INT NOT NULL,
   codigo_actor INT NOT NULL,
   PRIMARY KEY (codigo_pelicula, codigo_actor),
   FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo),
   FOREIGN KEY (codigo_actor) REFERENCES Actores(codigo)
);