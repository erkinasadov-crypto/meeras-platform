-- V2__seed_meeras.sql
-- Create minimal normalized schema for reference data and seed it

-- Cities
CREATE TABLE IF NOT EXISTS cities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Areas (optional relation to city)
CREATE TABLE IF NOT EXISTS areas (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    city_id INTEGER REFERENCES cities(id) ON DELETE SET NULL,
    UNIQUE(name, city_id)
);

-- Operators
CREATE TABLE IF NOT EXISTS operators (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

-- Service catalog (catalog column allows grouping like 'GRAVE_CARE')
CREATE TABLE IF NOT EXISTS service_catalog (
    id SERIAL PRIMARY KEY,
    catalog VARCHAR(100) NOT NULL,
    code VARCHAR(100),
    name VARCHAR(200) NOT NULL,
    base_price NUMERIC(12,2) NOT NULL DEFAULT 0,
    UNIQUE(catalog, name)
);

-- Flower catalog
CREATE TABLE IF NOT EXISTS flower_catalog (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE,
    base_price NUMERIC(12,2) NOT NULL DEFAULT 0
);

-- Seed Cities
INSERT INTO cities (name)
VALUES
  ('Baku'),
  ('Ganja'),
  ('Sumgait')
ON CONFLICT (name) DO NOTHING;

-- Seed Areas (assign to Baku where logical)
WITH baku AS (
  SELECT id FROM cities WHERE name = 'Baku' LIMIT 1
)
INSERT INTO areas (name, city_id)
SELECT a.name, b.id
FROM (VALUES
  ('Volchiye Vorota'),
  ('Khirdalan'),
  ('Buzovna'),
  ('Nardaran')
) AS a(name), baku b
ON CONFLICT (name, city_id) DO NOTHING;

-- Seed Operators
INSERT INTO operators (name)
VALUES
  ('Operator1'),
  ('Operator2'),
  ('Operator3')
ON CONFLICT (name) DO NOTHING;

-- Seed Service Catalog (GRAVE_CARE)
INSERT INTO service_catalog (catalog, code, name, base_price)
VALUES
  ('GRAVE_CARE','wash_headstone','Wash headstone', 30.00),
  ('GRAVE_CARE','water_plants','Watering plants/lawn', 15.00),
  ('GRAVE_CARE','agro_care','Agro care', 25.00),
  ('GRAVE_CARE','refresh_flowers','Refresh flowers', 20.00)
ON CONFLICT (catalog, name) DO NOTHING;

-- Seed Flower Catalog
INSERT INTO flower_catalog (name, base_price)
VALUES
  ('Red carnations', 5.00),
  ('Red roses', 12.00),
  ('White roses', 12.00),
  ('Carnation wreath 50', 50.00),
  ('Carnation wreath 100', 100.00)
ON CONFLICT (name) DO NOTHING;
