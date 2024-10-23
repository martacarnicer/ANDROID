-- Inserción de Roles
INSERT INTO rol (nombre_rol) VALUES ('Usuario');
INSERT INTO rol (nombre_rol) VALUES ('Instructor');
INSERT INTO rol (nombre_rol) VALUES ('Admin');

-- Inserción de Centros
INSERT INTO centro (nombre_centro, ubicacion, telefono, capacidad_maxima, horario_apertura, horario_cierre)
VALUES ('Centro 1', 'Calle Falsa 123', '555-1234', 100, '08:00', '22:00');
INSERT INTO centro (nombre_centro, ubicacion, telefono, capacidad_maxima, horario_apertura, horario_cierre)
VALUES ('Centro 2', 'Avenida Siempreviva 742', '555-5678', 150, '07:00', '23:00');
INSERT INTO centro (nombre_centro, ubicacion, telefono, capacidad_maxima, horario_apertura, horario_cierre)
VALUES ('Centro 3', 'Plaza Mayor 3', '555-9876', 200, '06:00', '21:00');
INSERT INTO centro (nombre_centro, ubicacion, telefono, capacidad_maxima, horario_apertura, horario_cierre)
VALUES ('Centro 4', 'Calle del Sol 45', '555-4321', 120, '09:00', '20:00');
INSERT INTO centro (nombre_centro, ubicacion, telefono, capacidad_maxima, horario_apertura, horario_cierre)
VALUES ('Centro 5', 'Calle Luna 12', '555-6789', 180, '08:00', '22:00');

-- Inserción de Usuarios
INSERT INTO usuario (nombre, apellido, email, password, fecha_nacimiento, tipo_cuota, fecha_registro, telefono, estado_activo, id_rol)
VALUES ('Juan', 'Perez', 'juan.perez@example.com', 'password123', '1990-01-01', 'Básica', '2024-10-01', '555-1234', TRUE, 1);
INSERT INTO usuario (nombre, apellido, email, password, fecha_nacimiento, tipo_cuota, fecha_registro, telefono, estado_activo, id_rol)
VALUES ('María', 'Lopez', 'maria.lopez@example.com', 'password123', '1985-05-12', 'Premium', '2024-10-02', '555-5678', TRUE, 1);
INSERT INTO usuario (nombre, apellido, email, password, fecha_nacimiento, tipo_cuota, fecha_registro, telefono, estado_activo, id_rol)
VALUES ('Luis', 'Gonzalez', 'luis.gonzalez@example.com', 'password123', '1995-07-20', 'Estudiante', '2024-10-03', '555-9876', TRUE, 1);
INSERT INTO usuario (nombre, apellido, email, password, fecha_nacimiento, tipo_cuota, fecha_registro, telefono, estado_activo, id_rol)
VALUES ('Ana', 'Martinez', 'ana.martinez@example.com', 'password123', '1992-09-15', 'Estándar', '2024-10-04', '555-4321', TRUE, 1);
INSERT INTO usuario (nombre, apellido, email, password, fecha_nacimiento, tipo_cuota, fecha_registro, telefono, estado_activo, id_rol)
VALUES ('Pedro', 'Hernandez', 'pedro.hernandez@example.com', 'password123', '1988-02-10', 'Básica', '2024-10-05', '555-6789', TRUE, 1);

-- Inserción de Instructores
INSERT INTO instructor (nombre_instructor, especialidad, email, telefono)
VALUES ('Carlos Fernández', 'Yoga', 'carlos.fernandez@example.com', '555-1234');
INSERT INTO instructor (nombre_instructor, especialidad, email, telefono)
VALUES ('Lucía Martínez', 'CrossFit', 'lucia.martinez@example.com', '555-5678');
INSERT INTO instructor (nombre_instructor, especialidad, email, telefono)
VALUES ('Roberto Gómez', 'Pilates', 'roberto.gomez@example.com', '555-8765');
INSERT INTO instructor (nombre_instructor, especialidad, email, telefono)
VALUES ('María López', 'Zumba', 'maria.lopez@example.com', '555-4321');
INSERT INTO instructor (nombre_instructor, especialidad, email, telefono)
VALUES ('Javier Hernández', 'Boxeo', 'javier.hernandez@example.com', '555-9876');

-- Inserción de Clases
INSERT INTO clase (nombre_clase, capacidad_maxima, fecha_clase, hora_inicio, hora_fin, id_instructor, id_centro)
VALUES ('Yoga Avanzado', 20, '2024-10-10', '10:00:00', '11:30:00', 1, 1);
INSERT INTO clase (nombre_clase, capacidad_maxima, fecha_clase, hora_inicio, hora_fin, id_instructor, id_centro)
VALUES ('CrossFit Básico', 25, '2024-10-15', '18:00:00', '19:00:00', 2, 2);
INSERT INTO clase (nombre_clase, capacidad_maxima, fecha_clase, hora_inicio, hora_fin, id_instructor, id_centro)
VALUES ('Pilates Intermedio', 15, '2024-10-20', '09:00:00', '10:30:00', 3, 3);
INSERT INTO clase (nombre_clase, capacidad_maxima, fecha_clase, hora_inicio, hora_fin, id_instructor, id_centro)
VALUES ('Zumba Fitness', 30, '2024-10-25', '17:00:00', '18:00:00', 4, 4);
INSERT INTO clase (nombre_clase, capacidad_maxima, fecha_clase, hora_inicio, hora_fin, id_instructor, id_centro)
VALUES ('Boxeo Avanzado', 20, '2024-10-30', '19:00:00', '20:30:00', 5, 5);

-- Inserción de Accesos
INSERT INTO acceso (fecha_inicio_acceso, fecha_fin_acceso, id_usuario, id_centro)
VALUES ('2024-10-01', NULL, 1, 1);
INSERT INTO acceso (fecha_inicio_acceso, fecha_fin_acceso, id_usuario, id_centro)
VALUES ('2024-10-01', '2024-10-05', 2, 2);
INSERT INTO acceso (fecha_inicio_acceso, fecha_fin_acceso, id_usuario, id_centro)
VALUES ('2024-10-02', NULL, 3, 3);
INSERT INTO acceso (fecha_inicio_acceso, fecha_fin_acceso, id_usuario, id_centro)
VALUES ('2024-10-03', NULL, 4, 4);
INSERT INTO acceso (fecha_inicio_acceso, fecha_fin_acceso, id_usuario, id_centro)
VALUES ('2024-10-04', '2024-10-06', 5, 5);

-- Inserción de Notificaciones
INSERT INTO notificacion (titulo, contenido, fecha_programada, enviado, tipo_notificacion, id_usuario)
VALUES ('Recordatorio de Clase', 'No olvides tu clase de Yoga mañana a las 10:00 AM', '2024-10-09 18:00:00', FALSE, 'recordatorio_clase', 1);
INSERT INTO notificacion (titulo, contenido, fecha_programada, enviado, tipo_notificacion, id_usuario)
VALUES ('Cambio de Horario', 'Tu clase de CrossFit ha cambiado a las 18:00', '2024-10-14 12:00:00', TRUE, 'informacion_clase', 2);
INSERT INTO notificacion (titulo, contenido, fecha_programada, enviado, tipo_notificacion, id_usuario)
VALUES ('Nueva Clase Disponible', 'Pilates Intermedio estará disponible el 20 de octubre', '2024-10-19 09:00:00', FALSE, 'informacion_asistentes', 3);
INSERT INTO notificacion (titulo, contenido, fecha_programada, enviado, tipo_notificacion, id_usuario)
VALUES ('Recordatorio de Inscripción', 'Tu clase de Zumba está programada para mañana', '2024-10-24 18:00:00', FALSE, 'recordatorio_clase', 4);
INSERT INTO notificacion (titulo, contenido, fecha_programada, enviado, tipo_notificacion, id_usuario)
VALUES ('Confirmación de Inscripción', 'Te has inscrito a la clase de Boxeo Avanzado', '2024-10-29 12:00:00', TRUE, 'informacion_asistentes', 5);

-- Inserción de Inscripciones
INSERT INTO inscripcion (fecha_inscripcion, id_usuario, id_clase)
VALUES ('2024-10-01', 1, 1);
INSERT INTO inscripcion (fecha_inscripcion, id_usuario, id_clase)
VALUES ('2024-10-02', 2, 2);
INSERT INTO inscripcion (fecha_inscripcion, id_usuario, id_clase)
VALUES ('2024-10-03', 3, 3);
INSERT INTO inscripcion (fecha_inscripcion, id_usuario, id_clase)
VALUES ('2024-10-04', 4, 4);
INSERT INTO inscripcion (fecha_inscripcion, id_usuario, id_clase)
VALUES ('2024-10-05', 5, 5);
