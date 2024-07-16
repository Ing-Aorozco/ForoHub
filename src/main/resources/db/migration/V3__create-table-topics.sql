create TABLE topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(500) not null,
    fecha_creacion datetime not null,
    status varchar(100),
    usuario_id bigint not null,
    curso_id bigint not null,
    estado boolean not null,

    primary key(id),

    constraint fk_topics_user_id foreign key(usuario_id) references usuarios(id),
    constraint fk_topics_course_id foreign key(curso_id) references cursos(id)
)