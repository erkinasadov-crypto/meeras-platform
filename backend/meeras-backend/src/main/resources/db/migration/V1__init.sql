-- Initial schema for MEERAS Backend
CREATE TABLE IF NOT EXISTS app_health (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);
