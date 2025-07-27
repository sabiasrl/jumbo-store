-- Create the store table for tests
CREATE TABLE IF NOT EXISTS store (
    id BIGSERIAL PRIMARY KEY,
    address_name VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

-- Insert test data
INSERT INTO store (address_name, latitude, longitude) VALUES 
('Jumbo Test Store 1', 51.6167, 5.5486),
('Jumbo Test Store 2', 51.6267, 5.5586),
('Jumbo Test Store 3', 51.6067, 5.5386),
('Jumbo Test Store 4', 51.6367, 5.5686),
('Jumbo Test Store 5', 51.5967, 5.5286); 