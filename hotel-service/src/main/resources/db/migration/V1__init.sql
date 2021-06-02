CREATE TABLE IF NOT EXISTS public.hotel
(
  id serial PRIMARY KEY,
  address varchar,
  hotline varchar,
  name varchar
);

CREATE TABLE IF NOT EXISTS public.room_type
(
  id serial PRIMARY KEY,
  name varchar,
  is_virtual boolean default false,
  price decimal,
  number_people integer
);

CREATE TABLE IF NOT EXISTS public.room
(
  id serial PRIMARY KEY,
  room_type_id integer,
  name varchar,
  status varchar,
  hotel_id integer,
  description varchar,
  FOREIGN KEY (hotel_id) REFERENCES public.hotel (id)
                      ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (room_type_id) REFERENCES public.room_type (id)
                        ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO public.room_type(name, is_virtual, price, number_people)
	VALUES ('PRESIDENT', false, 1000, 10);
INSERT INTO public.room_type(name, is_virtual, price, number_people)
	VALUES ('SUIT', false, 500, 2);
INSERT INTO public.room_type(name, is_virtual, price, number_people)
	VALUES ('NORMAL', false, 400, 4);
INSERT INTO public.room_type(name, is_virtual, price, number_people)
	VALUES ('VIRTUAL', true, 200, 2);