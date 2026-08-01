CREATE DATABASE cine;
USE cine;

CREATE TABLE Paises (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE Ciudades (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_postal INT,
    codigo_pais INT NOT NULL,
    FOREIGN KEY (codigo_pais) REFERENCES Paises(codigo)
);

CREATE TABLE Municipios (
    id_municipio INT PRIMARY KEY AUTO_INCREMENT,
    nombre_municipio VARCHAR(100) NOT NULL,
    id_ciudad INT NOT NULL,
    FOREIGN KEY (id_ciudad) REFERENCES Ciudades(codigo)
);

CREATE TABLE Sectores (
    id_sector INT PRIMARY KEY AUTO_INCREMENT,
    nombre_sector VARCHAR(100) NOT NULL,
    id_municipio INT NOT NULL,
    FOREIGN KEY (id_municipio) REFERENCES Municipios(id_municipio)
);

CREATE TABLE Personas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE,
    sexo CHAR(1) NOT NULL CHECK(sexo = 'M' OR sexo = 'F'),
    telefono VARCHAR(15),
    correo VARCHAR(50) UNIQUE,
    id_sector_residencia INT,
    FOREIGN KEY (id_sector_residencia) REFERENCES Sectores(id_sector)
);

CREATE TABLE Idiomas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40)
);

CREATE TABLE Generos (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Sucursales (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    calle VARCHAR(200),
    numero VARCHAR(20),
    telefono VARCHAR(15),
    correo VARCHAR(70) UNIQUE NOT NULL,
    id_sector INT NOT NULL,
    FOREIGN KEY (id_sector) REFERENCES Sectores(id_sector)
);

CREATE TABLE Puestos_Trabajo (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    salario_base FLOAT NOT NULL CHECK (salario_base > 0)
);

CREATE TABLE Clientes (
    codigo INT PRIMARY KEY,
    cantidad_entradas INT DEFAULT 0,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (codigo) REFERENCES Personas(codigo)
);

CREATE TABLE Actores (
    codigo INT PRIMARY KEY,
    FOREIGN KEY (codigo) REFERENCES Personas(codigo)
);

CREATE TABLE Directores (
    codigo INT PRIMARY KEY,
    FOREIGN KEY (codigo) REFERENCES Personas(codigo)
);

CREATE TABLE Empleados (
    codigo INT PRIMARY KEY,
    codigo_puesto INT NOT NULL,
    fecha_contratacion DATE NOT NULL,
    salario DECIMAL(10,2),
    codigo_sucursal INT NOT NULL,
    FOREIGN KEY (codigo) REFERENCES Personas(codigo),
    FOREIGN KEY (codigo_puesto) REFERENCES Puestos_Trabajo(codigo),
    FOREIGN KEY (codigo_sucursal) REFERENCES Sucursales(codigo)
);

CREATE TABLE Salas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    capacidad INT NOT NULL,
    codigo_sucursal INT NOT NULL,
    FOREIGN KEY (codigo_sucursal) REFERENCES Sucursales(codigo)
);

CREATE TABLE Peliculas (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_director INT NOT NULL,
    duracion_minutos INT,
    clasificacion VARCHAR(10),
    idioma_audio INT NOT NULL,
    portada BLOB,
    FOREIGN KEY (codigo_director) REFERENCES Directores(codigo),
    FOREIGN KEY (idioma_audio) REFERENCES Idiomas(codigo)
);

CREATE TABLE Peliculas_Subtitulos (
    codigo_pelicula INT NOT NULL,
    codigo_idioma INT NOT NULL,
    PRIMARY KEY (codigo_pelicula, codigo_idioma),
    FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo) ON DELETE CASCADE,
    FOREIGN KEY (codigo_idioma) REFERENCES Idiomas(codigo) ON DELETE CASCADE
);

CREATE TABLE Generos_Peliculas (
    codigo_pelicula INT NOT NULL,
    codigo_generos INT NOT NULL,
    PRIMARY KEY (codigo_pelicula, codigo_generos),
    FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo) ON DELETE CASCADE,
    FOREIGN KEY (codigo_generos) REFERENCES Generos(codigo) ON DELETE CASCADE
);

CREATE TABLE Actores_Peliculas (
    codigo_pelicula INT NOT NULL,
    codigo_actor INT NOT NULL,
    PRIMARY KEY (codigo_pelicula, codigo_actor),
    FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo) ON DELETE CASCADE,
    FOREIGN KEY (codigo_actor) REFERENCES Actores(codigo)
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
    codigo_idioma_subtitulo INT NULL,
    FOREIGN KEY (codigo_pelicula) REFERENCES Peliculas(codigo),
    FOREIGN KEY (codigo_sala) REFERENCES Salas(codigo),
    FOREIGN KEY (codigo_idioma_subtitulo) REFERENCES Idiomas(codigo)
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
-- =============================================
-- INSERTS DE DATOS
-- =============================================

-- 1. PAISES
INSERT INTO Paises (nombre) VALUES
('República Dominicana');

-- 2. CIUDADES
INSERT INTO Ciudades (nombre, codigo_postal, codigo_pais) VALUES
('La Vega', '41000', 1),
('Santo Domingo', '10100', 1),
('Santiago de los Caballeros', '51000', 1),
('San Francisco de Macorís', '31000', 1),
('San Pedro de Macorís', '21000', 1),
('La Romana', '22000', 1),
('Puerto Plata', '57000', 1),
('San Cristóbal', '91000', 1),
('Moca', '43000', 1),
('Bonao', '42000', 1),
('Baní', '94000', 1),
('San Juan de la Maguana', '71000', 1),
('Higüey', '23000', 1),
('Mao', '61000', 1),
('Azua', '81000', 1);

-- 3. MUNICIPIOS
INSERT INTO municipios (nombre_municipio, id_ciudad) VALUES
-- Santo Domingo (ciudad 2)
('Distrito Nacional', 2),
('Santo Domingo Norte', 2),
('Santo Domingo Este', 2),
('Santo Domingo Oeste', 2),
-- Santiago (ciudad 3)
('Santiago de los Caballeros', 3),
('Villa González', 3),
('Jánico', 3),
('San José de las Matas', 3),
-- La Vega (ciudad 1)
('Concepción de La Vega', 1),
('Jarabacoa', 1),
('Constanza', 1),
-- San Francisco de Macorís (ciudad 4)
('San Francisco de Macorís', 4),
-- Puerto Plata (ciudad 7)
('Puerto Plata', 7),
('Sosúa', 7),
('San Felipe de Puerto Plata', 7);

-- 4. SECTORES
INSERT INTO sectores (nombre_sector, id_municipio) VALUES
-- Sectores del Distrito Nacional (id_municipio 1)
('Zona Colonial', 1),
('Gazcue', 1),
('Naco', 1),
('Piantini', 1),
('Los Cacicazgos', 1),
('Arroyo Hondo', 1),
('Bella Vista', 1),
('La Esperilla', 1),
-- Santo Domingo Norte (id_municipio 2)
('Villa Mella', 2),
('Sabana Perdida', 2),
('Los Guaricanos', 2),
-- Santo Domingo Este (id_municipio 3)
('Los Mina', 3),
('San Isidro', 3),
('La Victoria', 3),
-- Santo Domingo Oeste (id_municipio 4)
('Herrera', 4),
('Manoguayabo', 4),
-- Santiago (id_municipio 5)
('Los Jardines', 5),
('El Monumento', 5),
('La Joya', 5),
('Ensanche La Fe', 5),
('Cerro Alto', 5),
('La Sirena', 5),
-- La Vega (id_municipio 9)
('La Vega Vieja', 9),
('Cerro Alto', 9),
('Los Rieles', 9),
-- San Francisco de Macorís (id_municipio 12)
('Santa Ana', 12),
('La Cruz', 12),
-- Puerto Plata (id_municipio 13)
('Playa Dorada', 13),
('El Mamey', 13);

-- 5. IDIOMAS
INSERT INTO Idiomas (nombre) VALUES
('Español Latino'),
('Español Castellano'),
('Inglés'),
('Japonés'),
('Francés'),
('Alemán'),
('Coreano');

-- 6. GENEROS
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

-- 7. SUCURSALES
INSERT INTO Sucursales (nombre, calle, numero, telefono, correo, id_sector) VALUES
('Cine Principal Santo Domingo', 'Av. Abraham Lincoln', '123', '809-555-0101', 'cine.sd@cine.com', 3),
('Cine Santiago', 'Calle Del Sol', '45', '809-555-0102', 'cine.santiago@cine.com', 18),
('Cine La Vega', 'Av. Independencia', '78', '809-555-0103', 'cine.lavega@cine.com', 23);

-- 8. PUESTOS DE TRABAJO
INSERT INTO Puestos_Trabajo (nombre, salario_base) VALUES
('Gerente General', 80000.00),
('Asistente de Gerencia', 50000.00),
('Taquillero', 35000.00),
('Proyeccionista', 40000.00),
('Acomodador', 30000.00),
('Limpieza', 25000.00),
('Barman', 32000.00),
('Guardia de Seguridad', 28000.00);

-- 9. PERSONAS (TODAS LAS PERSONAS SE REGISTRAN AQUÍ PRIMERO)
INSERT INTO Personas (nombres, apellidos, fecha_nacimiento, sexo, telefono, correo, id_sector_residencia) VALUES
-- Clientes (códigos 1-8)
('Juan Carlos', 'Pérez Martínez', '1990-05-15', 'M', '809-555-1111', 'juan.perez@email.com', 3),
('María Elena', 'Rodríguez Santos', '1985-08-22', 'F', '809-555-2222', 'maria.rodriguez@email.com', 5),
('Carlos Luis', 'Santos Méndez', '2000-03-10', 'M', '809-555-3333', 'carlos.santos@email.com', 19),
('Ana Cecilia', 'Martínez Peña', '1992-11-30', 'F', '809-555-4444', 'ana.martinez@email.com', 6),
('Pedro José', 'García Pérez', '1988-07-25', 'M', '809-555-5555', 'pedro.garcia@email.com', 20),
('Laura María', 'Méndez Cabrera', '1995-09-12', 'F', '809-555-6666', 'laura.mendez@email.com', 8),
('Roberto Carlos', 'Peña Reyes', '1993-12-18', 'M', '809-555-7777', 'roberto.pena@email.com', 24),
('Marta Susana', 'Reyes Castillo', '1998-04-05', 'F', '809-555-8888', 'marta.reyes@email.com', 25),
-- Directores (códigos 9-12)
('Steven', 'Spielberg', '1946-12-18', 'M', '809-555-9999', 'steven.spielberg@cine.com', 3),
('Christopher', 'Nolan', '1970-07-30', 'M', '809-555-1010', 'christopher.nolan@cine.com', 18),
('James', 'Cameron', '1954-08-16', 'M', '809-555-1111', 'james.cameron@cine.com', 3),
('Greta', 'Gerwig', '1983-08-04', 'F', '809-555-1212', 'greta.gerwig@cine.com', 5),
-- Actores (códigos 13-20)
('Tom', 'Hanks', '1956-07-09', 'M', '809-555-1313', 'tom.hanks@actor.com', 4),
('Leonardo', 'DiCaprio', '1974-11-11', 'M', '809-555-1414', 'leonardo.dicaprio@actor.com', 6),
('Meryl', 'Streep', '1949-06-22', 'F', '809-555-1515', 'meryl.streep@actor.com', 3),
('Morgan', 'Freeman', '1937-06-01', 'M', '809-555-1616', 'morgan.freeman@actor.com', 4),
('Scarlett', 'Johansson', '1984-11-22', 'F', '809-555-1717', 'scarlett.johansson@actor.com', 6),
('Robert', 'Downey Jr.', '1965-04-04', 'M', '809-555-1818', 'robert.downey@actor.com', 18),
('Kate', 'Winslet', '1975-10-05', 'F', '809-555-1919', 'kate.winslet@actor.com', 3),
('Samuel L.', 'Jackson', '1948-12-21', 'M', '809-555-2020', 'samuel.jackson@actor.com', 4),
-- Empleados (códigos 21-26)
('Laura', 'Méndez', '1985-09-20', 'F', '809-555-2121', 'laura.mendez@cine.com', 3),
('Roberto', 'Peña', '1990-06-15', 'M', '809-555-2222', 'roberto.pena@cine.com', 4),
('Marta', 'Reyes', '1992-12-10', 'F', '809-555-2323', 'marta.reyes@cine.com', 6),
('Luis', 'Castro', '1988-03-25', 'M', '809-555-2424', 'luis.castro@cine.com', 5),
('Ana', 'Vargas', '1995-07-08', 'F', '809-555-2525', 'ana.vargas@cine.com', 19),
('Carlos', 'Mendoza', '1987-11-12', 'M', '809-555-2626', 'carlos.mendoza@cine.com', 18);

-- 10. CLIENTES (códigos 1-8)
INSERT INTO Clientes (codigo, cantidad_entradas) VALUES
(1, 2),
(2, 3),
(3, 3),
(4, 2),
(5, 0),
(6, 0),
(7, 0),
(8, 1);

-- 11. DIRECTORES (códigos 9-12)
INSERT INTO Directores (codigo) VALUES
(9), (10), (11), (12);

-- 12. ACTORES (códigos 13-20)
INSERT INTO Actores (codigo) VALUES
(13), (14), (15), (16), (17), (18), (19), (20);

-- 13. EMPLEADOS (códigos 21-26)
INSERT INTO Empleados (codigo,codigo_puesto, fecha_contratacion, salario, codigo_sucursal) VALUES
(21, 1, '2023-01-15', 75000.00, 1),
(22, 2, '2023-03-01', 35000.00, 1),
(23, 3, '2023-06-15', 35000.00, 1),
(24, 4, '2023-12-10', 40000.00, 1),
(25, 5, '2024-01-20', 30000.00, 2),
(26, 6, '2023-02-01', 75000.00, 2);

-- 14. PELICULAS
INSERT INTO Peliculas (nombre, codigo_director, duracion_minutos, clasificacion, idioma_audio) VALUES
('La Lista de Schindler', 9, 195, 'R', 3),
('El Origen', 10, 148, 'PG-13', 3),
('Titanic', 11, 195, 'PG-13', 3),
('Barbie', 12, 114, 'PG-13', 3),
('Jurassic Park', 9, 127, 'PG-13', 3),
('Interestelar', 10, 169, 'PG-13', 3);

-- 15. GENEROS_PELICULAS
INSERT INTO Generos_Peliculas (codigo_pelicula, codigo_generos) VALUES
(1, 7),  -- La Lista de Schindler - Drama
(1, 18), -- La Lista de Schindler - Histórico
(2, 5),  -- El Origen - Ciencia Ficción
(2, 12), -- El Origen - Suspenso
(3, 7),  -- Titanic - Drama
(3, 11), -- Titanic - Romance
(4, 4),  -- Barbie - Comedia
(4, 19), -- Barbie - Familiar
(5, 1),  -- Jurassic Park - Acción
(5, 2),  -- Jurassic Park - Aventura
(6, 5);  -- Interestelar - Ciencia Ficción

-- 16. SALAS
INSERT INTO Salas (nombre, capacidad, codigo_sucursal) VALUES
('Sala 1 - 2D', 150, 1),
('Sala 2 - 3D', 120, 1),
('Sala 3 - VIP', 80, 1),
('Sala 1', 180, 2),
('Sala 2', 140, 2),
('Sala 1', 160, 3);

-- 17.1 ASIENTOS PARA SALA 1 (código 1) - 30 asientos
INSERT INTO Asientos (numero, fila, codigo_sala) VALUES
-- Fila A (Asientos 1-5)
(1, 'A', 1), (2, 'A', 1), (3, 'A', 1), (4, 'A', 1), (5, 'A', 1),
-- Fila B (Asientos 1-5)
(1, 'B', 1), (2, 'B', 1), (3, 'B', 1), (4, 'B', 1), (5, 'B', 1),
-- Fila C (Asientos 1-5)
(1, 'C', 1), (2, 'C', 1), (3, 'C', 1), (4, 'C', 1), (5, 'C', 1),
-- Fila D (Asientos 1-5)
(1, 'D', 1), (2, 'D', 1), (3, 'D', 1), (4, 'D', 1), (5, 'D', 1),
-- Fila E (Asientos 1-5)
(1, 'E', 1), (2, 'E', 1), (3, 'E', 1), (4, 'E', 1), (5, 'E', 1),
-- Fila F (Asientos 1-5)
(1, 'F', 1), (2, 'F', 1), (3, 'F', 1), (4, 'F', 1), (5, 'F', 1);

-- 17.2 ASIENTOS PARA SALA 2 (código 2) - 30 asientos
INSERT INTO Asientos (numero, fila, codigo_sala) VALUES
-- Fila A (Asientos 1-5)
(1, 'A', 2), (2, 'A', 2), (3, 'A', 2), (4, 'A', 2), (5, 'A', 2),
-- Fila B (Asientos 1-5)
(1, 'B', 2), (2, 'B', 2), (3, 'B', 2), (4, 'B', 2), (5, 'B', 2),
-- Fila C (Asientos 1-5)
(1, 'C', 2), (2, 'C', 2), (3, 'C', 2), (4, 'C', 2), (5, 'C', 2),
-- Fila D (Asientos 1-5)
(1, 'D', 2), (2, 'D', 2), (3, 'D', 2), (4, 'D', 2), (5, 'D', 2),
-- Fila E (Asientos 1-5)
(1, 'E', 2), (2, 'E', 2), (3, 'E', 2), (4, 'E', 2), (5, 'E', 2),
-- Fila F (Asientos 1-5)
(1, 'F', 2), (2, 'F', 2), (3, 'F', 2), (4, 'F', 2), (5, 'F', 2);

-- 17.3 ASIENTOS PARA SALA 3 (código 3) - 30 asientos
INSERT INTO Asientos (numero, fila, codigo_sala) VALUES
-- Fila A (Asientos 1-5)
(1, 'A', 3), (2, 'A', 3), (3, 'A', 3), (4, 'A', 3), (5, 'A', 3),
-- Fila B (Asientos 1-5)
(1, 'B', 3), (2, 'B', 3), (3, 'B', 3), (4, 'B', 3), (5, 'B', 3),
-- Fila C (Asientos 1-5)
(1, 'C', 3), (2, 'C', 3), (3, 'C', 3), (4, 'C', 3), (5, 'C', 3),
-- Fila D (Asientos 1-5)
(1, 'D', 3), (2, 'D', 3), (3, 'D', 3), (4, 'D', 3), (5, 'D', 3),
-- Fila E (Asientos 1-5)
(1, 'E', 3), (2, 'E', 3), (3, 'E', 3), (4, 'E', 3), (5, 'E', 3),
-- Fila F (Asientos 1-5)
(1, 'F', 3), (2, 'F', 3), (3, 'F', 3), (4, 'F', 3), (5, 'F', 3);

-- 17.4 ASIENTOS PARA SALA 4 (código 4) - 30 asientos
INSERT INTO Asientos (numero, fila, codigo_sala) VALUES
-- Fila A (Asientos 1-5)
(1, 'A', 4), (2, 'A', 4), (3, 'A', 4), (4, 'A', 4), (5, 'A', 4),
-- Fila B (Asientos 1-5)
(1, 'B', 4), (2, 'B', 4), (3, 'B', 4), (4, 'B', 4), (5, 'B', 4),
-- Fila C (Asientos 1-5)
(1, 'C', 4), (2, 'C', 4), (3, 'C', 4), (4, 'C', 4), (5, 'C', 4),
-- Fila D (Asientos 1-5)
(1, 'D', 4), (2, 'D', 4), (3, 'D', 4), (4, 'D', 4), (5, 'D', 4),
-- Fila E (Asientos 1-5)
(1, 'E', 4), (2, 'E', 4), (3, 'E', 4), (4, 'E', 4), (5, 'E', 4),
-- Fila F (Asientos 1-5)
(1, 'F', 4), (2, 'F', 4), (3, 'F', 4), (4, 'F', 4), (5, 'F', 4);

-- 17.5 ASIENTOS PARA SALA 5 (código 5) - 30 asientos
INSERT INTO Asientos (numero, fila, codigo_sala) VALUES
-- Fila A (Asientos 1-5)
(1, 'A', 5), (2, 'A', 5), (3, 'A', 5), (4, 'A', 5), (5, 'A', 5),
-- Fila B (Asientos 1-5)
(1, 'B', 5), (2, 'B', 5), (3, 'B', 5), (4, 'B', 5), (5, 'B', 5),
-- Fila C (Asientos 1-5)
(1, 'C', 5), (2, 'C', 5), (3, 'C', 5), (4, 'C', 5), (5, 'C', 5),
-- Fila D (Asientos 1-5)
(1, 'D', 5), (2, 'D', 5), (3, 'D', 5), (4, 'D', 5), (5, 'D', 5),
-- Fila E (Asientos 1-5)
(1, 'E', 5), (2, 'E', 5), (3, 'E', 5), (4, 'E', 5), (5, 'E', 5),
-- Fila F (Asientos 1-5)
(1, 'F', 5), (2, 'F', 5), (3, 'F', 5), (4, 'F', 5), (5, 'F', 5);
-- 18. FUNCIONES
INSERT INTO Funciones (fecha, hora_inicio, hora_fin, precio_entrada, codigo_pelicula, codigo_sala) VALUES
('2024-08-01', '14:00:00', '17:15:00', 350.00, 1, 1),
('2024-08-01', '18:30:00', '21:45:00', 400.00, 1, 1),
('2024-08-01', '15:00:00', '17:28:00', 320.00, 2, 2),
('2024-08-01', '20:00:00', '22:28:00', 380.00, 2, 2),
('2024-08-01', '16:00:00', '19:15:00', 350.00, 3, 3),
('2024-08-01', '19:30:00', '22:45:00', 400.00, 3, 1),
('2024-08-02', '14:30:00', '16:24:00', 280.00, 4, 4),
('2024-08-02', '17:00:00', '19:07:00', 320.00, 5, 5);

-- 19. ACTORES_PELICULAS
INSERT INTO Actores_Peliculas (codigo_pelicula, codigo_actor) VALUES
-- La Lista de Schindler (película 1)
(1, 13), -- Tom Hanks
(1, 14), -- Leonardo DiCaprio
-- El Origen (película 2)
(2, 14), -- Leonardo DiCaprio
(2, 16), -- Morgan Freeman
-- Titanic (película 3)
(3, 14), -- Leonardo DiCaprio
(3, 19), -- Kate Winslet
-- Barbie (película 4)
(4, 17), -- Scarlett Johansson
(4, 20), -- Samuel L. Jackson
-- Jurassic Park (película 5)
(5, 13), -- Tom Hanks
(5, 16), -- Morgan Freeman
-- Interestelar (película 6)
(6, 16), -- Morgan Freeman
(6, 18); -- Robert Downey Jr.

DELIMITER //

CREATE PROCEDURE sp_RegistrarCliente(
    IN p_nombres VARCHAR(50),
    IN p_apellidos VARCHAR(50),
    IN p_fecha_nacimiento DATE,
    IN p_sexo CHAR(1),
    IN p_telefono VARCHAR(15),
    IN p_correo VARCHAR(50),
    IN p_id_sector_residencia INT,
    IN p_cantidad_entradas INT
)
BEGIN
    DECLARE v_codigo_persona INT DEFAULT 0;
    DECLARE v_existe_correo INT DEFAULT 0;
    DECLARE v_existe_sector INT DEFAULT 0;

DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
SELECT 'Error al registrar el cliente. La operación ha sido cancelada.' AS mensaje_error;
END;

START TRANSACTION;

IF p_sexo NOT IN ('M', 'F') THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'El sexo debe ser M (Masculino) o F (Femenino)';
END IF;

SELECT COUNT(*) INTO v_existe_sector
FROM Sectores
WHERE id_sector = p_id_sector_residencia;

IF v_existe_sector = 0 THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'El sector de residencia no existe';
END IF;

SELECT COUNT(*) INTO v_existe_correo
FROM Personas
WHERE correo = p_correo;

IF v_existe_correo > 0 THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'El correo electrónico ya está registrado';
END IF;

INSERT INTO Personas (
nombres,
apellidos,
fecha_nacimiento,
sexo,
telefono,
correo,
id_sector_residencia
) VALUES (
p_nombres,
p_apellidos,
p_fecha_nacimiento,
p_sexo,
p_telefono,
p_correo,
p_id_sector_residencia
);

SET v_codigo_persona = LAST_INSERT_ID();

INSERT INTO Clientes (
codigo,
cantidad_entradas,
fecha_registro
) VALUES (
v_codigo_persona,
IFNULL(p_cantidad_entradas, 0),
CURRENT_TIMESTAMP
);

COMMIT;

SELECT
v_codigo_persona AS codigo_cliente,
CONCAT(p_nombres, ' ', p_apellidos) AS nombre_completo,
p_correo AS correo,
'Cliente registrado exitosamente' AS mensaje;

END //

DELIMITER ;


DELIMITER //

CREATE TRIGGER trg_boleto_fidelidad
    BEFORE INSERT ON Boletos
    FOR EACH ROW
BEGIN
    DECLARE v_codigo_cliente INT;
    DECLARE v_puntos_actuales INT;

    SELECT codigo_cliente INTO v_codigo_cliente
    FROM Ventas
    WHERE codigo = NEW.codigo_venta;

    SELECT cantidad_entradas INTO v_puntos_actuales
    FROM Clientes
    WHERE codigo = v_codigo_cliente;

    IF v_puntos_actuales = 9 THEN
        SET NEW.precio_aplicado = 0;

    UPDATE Clientes
    SET cantidad_entradas = 0
    WHERE codigo = v_codigo_cliente;
    ELSE
    UPDATE Clientes
    SET cantidad_entradas = cantidad_entradas + 1
    WHERE codigo = v_codigo_cliente;
END IF;
END //

DELIMITER ;
