DO $$ BEGIN
  IF current_setting('zenflow.env', true) = 'dev' THEN
    INSERT INTO users (id, is_guest) VALUES
      ('00000000-0000-0000-0000-000000000001', true);
  END IF;
END $$;
