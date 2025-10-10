-- H2 совместимая версия миграции
-- Сначала создаем таблицу locations
CREATE TABLE locations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    x BIGINT,
    y REAL,
    name VARCHAR(255) NOT NULL
);

-- Затем создаем таблицу routes с координатами как embedded
CREATE TABLE routes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinate_x REAL,
    coordinate_y DOUBLE CHECK (coordinate_y <= 49),
    creation_date TIMESTAMP NOT NULL,
    from_id BIGINT,
    to_id BIGINT,
    distance DOUBLE,
    rating DOUBLE NOT NULL,
    FOREIGN KEY (from_id) REFERENCES locations(id),
    FOREIGN KEY (to_id) REFERENCES locations(id)
);

-- Создаем индексы
CREATE INDEX idx_route_from_location_id ON routes (from_id);
CREATE INDEX idx_route_to_location_id ON routes (to_id);
