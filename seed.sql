-- Inserir usuários (professores e cidadãos)
INSERT INTO users (id, name, role, password, email) VALUES
                                                        (1, 'Prof. Carlos', 'professor', 'professor123', 'carlos@example.com', false),
                                                        (2, 'Prof. Maria', 'professor', 'maria123', 'maria@example.com', false),
                                                        (3, 'John Doe', 'citizen', 'john123', 'john@example.com', active),
                                                        (4, 'Jane Doe', 'citizen', 'jane123', 'jane@example.com', active);

-- Inserir atividades
INSERT INTO activities (id, name, description, location, professor_id) VALUES
                                                                           (1, 'Futebol', 'Jogo de futebol entre equipes.', 'Campo A', 1),
                                                                           (2, 'Vôlei', 'Partida de vôlei amistosa.', 'Quadra B', 2);

-- Inserir agendamentos (schedules) para as atividades
-- Inserir agendamentos (schedules) para as atividades
INSERT INTO schedules (id, day_of_week, start_time, end_time, activity_id) VALUES
                                                                               (1, 1, '10:00:00', '12:00:00', 1),  -- Futebol segunda-feira (1 = Monday)
                                                                               (2, 3, '14:00:00', '16:00:00', 2);  -- Vôlei quarta-feira (3 = Wednesday)


-- Inserir inscrições de cidadãos em atividades (enrollments)
INSERT INTO enrollments (id, enrollment_date, activity_id, user_id) VALUES
                                                                        (1, '2024-09-01', 1, 3),  -- John Doe inscrito no Futebol
                                                                        (2, '2024-09-02', 2, 4);  -- Jane Doe inscrita no Vôlei

-- Inserir relacionamento entre atividades e estudantes (citizens)
INSERT INTO activity_student (activity_id, student_id) VALUES
                                                           (1, 3),  -- John Doe inscrito no Futebol
                                                           (2, 4);  -- Jane Doe inscrita no Vôlei
