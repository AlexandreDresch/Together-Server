CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE participants (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  is_confirmed BOOLEAN NOT NULL,
  event_id UUID,
  FOREIGN KEY (event_id) REFERENCES events(id)
)