CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE activities (
  id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  occurs_at TIMESTAMP NOT NULL,
  event_id UUID,
  FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
)