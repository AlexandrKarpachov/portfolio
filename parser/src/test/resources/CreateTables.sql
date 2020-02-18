CREATE OR REPLACE FUNCTION create_if_not_exists ()
  RETURNS void AS
$func$
BEGIN
   IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables
              WHERE  schemaname = 'public'
              AND    tablename  = 'items_test') THEN
      RAISE NOTICE 'Table Items already exists.';
   ELSE
      CREATE TABLE items_test(id serial primary key, item_name varchar(50), description text, cr_date timestamp);
   END IF;
END
$func$ LANGUAGE plpgsql;

select create_if_not_exists ();