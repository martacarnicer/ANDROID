-- Tabla Centro
CREATE TABLE centro (
    id_centro SERIAL PRIMARY KEY,
    nombre_centro VARCHAR(255) NOT NULL,
    ubicacion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    capacidad_maxima INT NOT NULL,
    horario_apertura VARCHAR(10) NOT NULL,
    horario_cierre VARCHAR(10) NOT NULL
);

-- Tabla Rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla Usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    tipo_cuota VARCHAR(50) NOT NULL,
    fecha_registro DATE,
    telefono VARCHAR(20),
    estado_activo BOOLEAN NOT NULL,
    id_rol INT NOT NULL,
    CONSTRAINT fk_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- Tabla Instructor
CREATE TABLE instructor (
    id_instructor SERIAL PRIMARY KEY,
    nombre_instructor VARCHAR(255) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(20)
);

-- Tabla Clase
CREATE TABLE clase (
    id_clase SERIAL PRIMARY KEY,
    nombre_clase VARCHAR(255) NOT NULL,
    capacidad_maxima INT NOT NULL,
    fecha_clase DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    id_instructor INT NOT NULL,
    id_centro INT NOT NULL,
    CONSTRAINT fk_instructor FOREIGN KEY (id_instructor) REFERENCES instructor(id_instructor),
    CONSTRAINT fk_centro FOREIGN KEY (id_centro) REFERENCES centro(id_centro)
);

-- Tabla Acceso
CREATE TABLE acceso (
    id_acceso SERIAL PRIMARY KEY,
    fecha_inicio_acceso DATE NOT NULL,
    fecha_fin_acceso DATE,
    id_usuario INT NOT NULL,
    id_centro INT NOT NULL,
    CONSTRAINT fk_usuario_acceso FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_centro_acceso FOREIGN KEY (id_centro) REFERENCES centro(id_centro)
);

-- Tabla Notificación
CREATE TABLE notificacion (
    id_notificacion SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    contenido TEXT NOT NULL,
    fecha_programada TIMESTAMP NOT NULL,
    enviado BOOLEAN NOT NULL,
    tipo_notificacion VARCHAR(50) NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT fk_usuario_notificacion FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Tabla Inscripción
CREATE TABLE inscripcion (
    id_inscripcion SERIAL PRIMARY KEY,
    fecha_inscripcion DATE NOT NULL,
    id_usuario INT NOT NULL,
    id_clase INT NOT NULL,
    CONSTRAINT fk_usuario_inscripcion FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_clase_inscripcion FOREIGN KEY (id_clase) REFERENCES clase(id_clase)
);
