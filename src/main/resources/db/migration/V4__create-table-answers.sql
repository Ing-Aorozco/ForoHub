create TABLE respuesta(
    id bigint not null auto_increment,
    mensaje varchar(500) not null,
    solucionado boolean not null,
    topico_id bigint not null,
    usuario_id bigint not null,
    estado boolean not null,

    primary key(id),

    constraint fk_answers_topic_id foreign key(topico_id) references topicos(id),
    constraint fk_answers_user_id foreign key(usuario_id) references usuarios(id)
)