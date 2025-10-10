-- Тестовая миграция для проверки функциональности
-- Добавляем тестовые данные

INSERT INTO locations (x, y, name) VALUES 
(100, 25.5, 'Москва'),
(200, 30.2, 'Санкт-Петербург'),
(150, 28.8, 'Новосибирск');

INSERT INTO routes (name, coordinate_x, coordinate_y, creation_date, from_id, to_id, distance, rating) VALUES 
('Москва-СПб', 150.0, 45.0, CURRENT_TIMESTAMP, 1, 2, 635.0, 4.5),
('СПб-Новосибирск', 175.0, 40.0, CURRENT_TIMESTAMP, 2, 3, 3200.0, 4.2),
('Москва-Новосибирск', 125.0, 35.0, CURRENT_TIMESTAMP, 1, 3, 3355.0, 4.0);
