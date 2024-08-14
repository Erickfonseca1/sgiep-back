-- Criação de usuários
-- Professores
INSERT INTO users (name, role) VALUES
                                   ('Prof. Carlos', 'professor'),
                                   ('Prof. Maria', 'professor');

-- Cidadãos (Alunos)
INSERT INTO users (name, role) VALUES
                                   ('John Doe', 'citizen'),
                                   ('Jane Doe', 'citizen');

-- Criação de professores na tabela de herança
-- O ID dos professores na tabela `professors` deve corresponder ao ID dos usuários na tabela `users`
INSERT INTO professors (id) VALUES
                                (1), -- Correspondente a 'Prof. Carlos'
                                (2); -- Correspondente a 'Prof. Maria'

-- Criação de cidadãos na tabela de herança
-- O ID dos cidadãos na tabela `citizens` deve corresponder ao ID dos usuários na tabela `users`
INSERT INTO citizens (id) VALUES
                              (3), -- Correspondente a 'John Doe'
                              (4); -- Correspondente a 'Jane Doe'

-- Criação de atividades
INSERT INTO activities (name, description, location, professor_id) VALUES
                                                                       ('Natação', 'Aulas de natação', 'Piscina Municipal', 1), -- Professor responsável: Prof. Carlos
                                                                       ('Futebol', 'Aulas de futebol', 'Campo Municipal', 2);  -- Professor responsável: Prof. Maria

-- Criação de horários (schedules) para Natação
INSERT INTO schedules (day_of_week, start_time, end_time, activity_id) VALUES
                                                                           (2, '19:00:00', '21:00:00', 1), -- Terça-feira
                                                                           (4, '19:00:00', '21:00:00', 1); -- Quinta-feira

-- Criação de horários (schedules) para Futebol
INSERT INTO schedules (day_of_week, start_time, end_time, activity_id) VALUES
                                                                           (1, '18:00:00', '20:00:00', 2), -- Segunda-feira
                                                                           (3, '18:00:00', '20:00:00', 2); -- Quarta-feira

-- Criação de inscrições (enrollments)
-- Agora, as inscrições serão geridas pela tabela de relacionamento `activity_student`
INSERT INTO activity_student (activity_id, student_id) VALUES
                                                           (1, 3), -- John Doe inscrito na Natação
                                                           (2, 4); -- Jane Doe inscrita no Futebol
