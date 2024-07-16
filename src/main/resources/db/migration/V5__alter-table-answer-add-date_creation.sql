ALTER TABLE respuesta ADD fecha_creacion datetime;
update respuesta set fecha_creacion = NOW();