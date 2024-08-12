-- Insert data into courses table
INSERT INTO courses (course_name) VALUES
('Science'),
('Arts'),
('Commerce');

-- Insert data into subjects table
INSERT INTO subjects (subject_name) VALUES
('Computer'),
('Biology'),
('Accounting'),
('History');

-- Insert data into course_subjects table
INSERT INTO course_subjects (course_id, subject_id) VALUES
-- Science course subjects
(1, 1), -- Computer
(1, 2), -- Biology

-- Arts course subjects
(2, 4), -- History
(2, 1), -- Computer

-- Commerce course subjects
(3, 1), -- Computer
(3, 3); -- Accounting

-- Insert data into students table
INSERT INTO students (student_name, student_class, student_roll, course_id, subject_id) VALUES
('Alice Johnson', '11th Grade', 'R001', 1, 1), -- Alice is taking Science and Computer
('Bob Smith', '12th Grade', 'R002', 1, 2), -- Bob is taking Science and Biology
('Charlie Brown', '11th Grade', 'R003', 2, 4), -- Charlie is taking Arts and History
('Diana Prince', '12th Grade', 'R004', 3, 3); -- Diana is taking Commerce and Accounting
