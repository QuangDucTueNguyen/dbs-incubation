CREATE TABLE IF NOT EXISTS public.user_credential
(
    id uuid PRIMARY KEY,
    guest_id uuid,
    username varchar NOT NULL,
    password varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS public.roles
(
    id serial PRIMARY KEY,
    name varchar NOT NULL,
    description varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
    id serial PRIMARY KEY,
    role_id integer NOT NULL,
    user_credential_id uuid NOT NULL,
    FOREIGN KEY (role_id) REFERENCES public.roles (id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (user_credential_id) REFERENCES public.user_credential (id)
                ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO roles (description, name) VALUES ('Supper', 'ROOT');
INSERT INTO roles (description, name) VALUES ('Admin role', 'ADMIN');
INSERT INTO roles (description, name) VALUES ('User role', 'USER');

INSERT INTO user_credential (id, username, password) VALUES ('205cc834-7042-4361-95c0-58927ceacede', 'root', 'root');

INSERT INTO user_roles (role_id, user_credential_id) VALUES (1, '205cc834-7042-4361-95c0-58927ceacede');