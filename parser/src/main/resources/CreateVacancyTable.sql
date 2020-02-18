CREATE OR REPLACE FUNCTION create_if_not_exists ()
RETURNS void AS
$func$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables
               WHERE  schemaname = 'public'
               AND    tablename  = 'vacancies') THEN
       RAISE NOTICE 'Table vacancies already exists.';
    ELSE
        CREATE TABLE vacancies(
            id serial primary key,
            "name" varchar(300) UNIQUE,
            reference varchar(300),
            description text,
            cr_date timestamp
        );
    END IF;
END
$func$ LANGUAGE plpgsql;

select create_if_not_exists ();