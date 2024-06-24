INSERT INTO Fighters (fighter_id, first_name, last_name, middle_name, date_of_birth, nationality) VALUES 
('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'John', 'Doe', 'M', '1990-01-01', 'USA'),
('b2c3d4e5-f678-90ab-cdef-1234567890ab', 'Jane', 'Smith', 'L', '1985-05-15', 'UK'),
('c3d4e5f6-7890-abcd-ef12-34567890abcd', 'Alice', 'Brown', 'K', '1992-07-20', 'Canada'),
('d4e5f678-90ab-cdef-1234-567890abcdef', 'Bob', 'White', 'J', '1988-03-10', 'Australia');

INSERT INTO Bouts (bout_id, fighter1_id, fighter2_id, bout_date, location, weight_class) VALUES 
('e5f67890-abcd-ef12-3456-7890abcdef12', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'b2c3d4e5-f678-90ab-cdef-1234567890ab', '2023-01-15', 'Madison Square Garden', 'LIGHTWEIGHT'),
('f67890ab-cdef-1234-5678-90abcdef1234', 'c3d4e5f6-7890-abcd-ef12-34567890abcd', 'd4e5f678-90ab-cdef-1234-567890abcdef', '2023-06-20', 'Staples Center', 'WELTERWEIGHT');

INSERT INTO Outcomes (outcome_id, bout_id, winner_id, loser_id, method, round, time) VALUES 
('67890abc-def1-2345-6789-0abcdef12345', 'e5f67890-abcd-ef12-3456-7890abcdef12', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'b2c3d4e5-f678-90ab-cdef-1234567890ab', 'KO', 3, '00:02:45'),
('7890abcd-ef12-3456-7890-abcdef123456', 'f67890ab-cdef-1234-5678-90abcdef1234', 'c3d4e5f6-7890-abcd-ef12-34567890abcd', 'd4e5f678-90ab-cdef-1234-567890abcdef', 'DECISION_DRAW', 5, '00:05:00');