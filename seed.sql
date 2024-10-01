-- Inserir usuários (professores, cidadãos e outros papéis)
INSERT INTO users (name, role, password, email, active) VALUES
('Prof. Carlos', 'PROFESSOR', 'professor123', 'carlos@example.com', false),
('Prof. Maria', 'PROFESSOR', 'maria123', 'maria@example.com', false),
('John Doe', 'CITIZEN', 'john123', 'john@example.com', true),
('Jane Doe', 'CITIZEN', 'jane123', 'jane@example.com', true),
('Lucas Silva', 'CITIZEN', 'lucas123', 'lucas@example.com', true),
('Ana Clara', 'CITIZEN', 'ana123', 'ana@example.com', true),
('Pedro Souza', 'MANAGER', 'pedro123', 'pedro@example.com', false),
('Mariana Costa', 'ADMIN', 'mariana123', 'mariana@example.com', true),
('Rafael Lima', 'CITIZEN', 'rafael123', 'rafael@example.com', true),
('Gabriela Santos', 'CITIZEN', 'gabriela123', 'gabriela@example.com', true);
('Prof. João Álvares', 'PROFESSOR', 'joao123', 'joao.alvares@example.com', false);


-- Inserir atividades com limite máximo de vagas (maxVacancies)
INSERT INTO activities (id, name, description, location, professor_id, max_vacancies) VALUES
(1, 'Futebol', 'Jogo de futebol entre equipes.', 'Campo A', 1, 20),
(2, 'Vôlei', 'Partida de vôlei amistosa.', 'Quadra B', 2, 15),
(3, 'Basquete', 'Treino de basquete para iniciantes.', 'Quadra C', 1, 10);

-- Inserir agendamentos (schedules) para as atividades
INSERT INTO schedules (id, day_of_week, start_time, end_time, activity_id) VALUES
(1, 1, '10:00:00', '12:00:00', 1),  -- Futebol segunda-feira (1 = Monday)
(2, 3, '14:00:00', '16:00:00', 2),  -- Vôlei quarta-feira (3 = Wednesday)
(3, 5, '08:00:00', '10:00:00', 3);  -- Basquete sexta-feira (5 = Friday)

-- Inserir inscrições de cidadãos em atividades (enrollments)
INSERT INTO enrollments (id, enrollment_date, activity_id, user_id) VALUES
(1, '2024-09-01', 1, 3),  -- John Doe inscrito no Futebol
(2, '2024-09-02', 2, 4),  -- Jane Doe inscrita no Vôlei
(3, '2024-09-03', 3, 5),  -- Lucas Silva inscrito no Basquete
(4, '2024-09-04', 1, 6),  -- Ana Clara inscrita no Futebol
(5, '2024-09-05', 2, 9);  -- Rafael Lima inscrito no Vôlei

-- Inserir relacionamento entre atividades e estudantes (citizens)
INSERT INTO activity_student (activity_id, student_id) VALUES
(1, 3),  -- John Doe inscrito no Futebol
(2, 4),  -- Jane Doe inscrita no Vôlei
(3, 5),  -- Lucas Silva inscrito no Basquete
(1, 6),  -- Ana Clara inscrita no Futebol
(2, 9);  -- Rafael Lima inscrito no Vôlei
