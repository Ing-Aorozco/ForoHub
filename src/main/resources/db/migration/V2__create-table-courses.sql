create TABLE cursos(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    categoria varchar(100) null,
    estado boolean not null,

    primary key(id)
)