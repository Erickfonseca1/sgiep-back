-- Criação de usuários
INSERT INTO users (name) VALUES
('John Doe'),
('Jane Doe');

-- Criação de atividades
INSERT INTO activities (name, description, location) VALUES
('Natação', 'Aulas de natação', 'Piscina Municipal'),
('Futebol', 'Aulas de futebol', 'Campo Municipal');

-- Obtenha os IDs das atividades para usar nas tabelas relacionadas
-- Para simplificação, consideramos que os IDs são gerados automaticamente sequencialmente
-- Se a sua configuração for diferente, ajuste os valores dos IDs conforme necessário

-- Criação de horários (schedules) para Natação
INSERT INTO schedules (day_of_week, start_time, end_time, activity_id) VALUES
(2, '19:00:00', '21:00:00', 1), -- Terça-feira
(4, '19:00:00', '21:00:00', 1); -- Quinta-feira

-- Criação de horários (schedules) para Futebol
INSERT INTO schedules (day_of_week, start_time, end_time, activity_id) VALUES
(1, '18:00:00', '20:00:00', 2), -- Segunda-feira
(3, '18:00:00', '20:00:00', 2); -- Quarta-feira

-- Criação de inscrições (enrollments)
-- Para simplificação, consideramos que os IDs dos usuários são 1 e 2
-- Se a sua configuração for diferente, ajuste os valores dos IDs conforme necessário
INSERT INTO enrollments (enrollment_date, user_id, activity_id) VALUES
(current_date, 1, 1), -- John Doe inscrito na Natação
(current_date, 2, 2); -- Jane Doe inscrito no Futebol
