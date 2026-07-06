create table Clientes(
	codigo int auto_increment primary key,
	nombres varchar(50) not null,
	apellidos varchar(50) not null,
	telefono varchar(15) not null,
	fechaRegistro date not null,
	cantidadEntradas int not null

);

create table Salas(
	codigo int auto_increment primary key,
	nombre varchar(10) not null,
	capacidad int not null

);

create table Peliculas(
	codigo int auto_increment primary key,
	nombre varchar(70) not null,
	director varchar(50) not null,
	genero varchar(20) not null,
	duracion_minutos int not null,
	clasificacion varchar(5) not null
);

create table Funciones(
	codigo int auto_increment primary key,
	codigo_pelicula int not null,
	foreign key (codigo_pelicula) references Peliculas (codigo),
	codigo_sala int not null,
	foreign key (codigo_sala) references Salas (codigo),
	fecha date not null,
	hora_inicio time not null,
	hora_fin time not null,
	precio_entrada decimal(10,2) not null
);

create table Asientos(
	codigo_asiento int auto_increment primary key,
	codigo_sala int not null,
	foreign key (codigo_sala) references Salas (codigo) on delete cascade,
	tipo varchar(20)
);

create table Facturas(
	codigo int auto_increment primary key,
	precio_total decimal(10,2) not null,
	fecha date not null,
	hora time not null,
	codigo_cliente int not null,
	foreign key (codigo_cliente) references Clientes (codigo)
);
create table Boletos(
	codigo int auto_increment primary key,
	codigo_factura int not null,
	foreign key (codigo_factura) references Facturas (codigo),
	codigo_funcion int not null,
	foreign key (codigo_funcion) references Funciones (codigo),
	codigo_asiento int not null,
	foreign key (codigo_asiento) references Asientos (codigo_asiento)
);
create table Generos(
  codigo int auto_increment primary key,
  nombre varchar(30);
);
create table Generos_Peliculas(
    codigo_pelicula int not null,
    foreign key (codigo_pelicula) references Peliculas(codigo),
    codigo_generos int not null,
    foreign key (codigo_generos) references Generos(codigo)

);