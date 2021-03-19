CREATE TABLE IF NOT EXISTS books (
    id bigint not null,
    title varchar(256) not null,
    description varchar(1000) not null,
    read_pages integer not null,
    total_pages integer not null,
    user_id bigint not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS user_tokens (
    user_id bigint not null,
    token varchar(36) not null
);

CREATE TABLE IF NOT EXISTS users (
    id bigint not null,
    username varchar(40) not null,
    password varchar(64) not null
);

INSERT INTO books (id, title, description, read_pages, total_pages, user_id) VALUES
    (1, 'title0', 'desc0', 0, 0, 1),
    (2, 'title1', 'desc1', 0, 0, 2),
    (3, 'title2', 'desc2', 0, 0, 2),
    (4, 'title3', 'desc3', 0, 0, 1);

COMMIT;