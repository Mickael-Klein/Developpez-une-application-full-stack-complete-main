CREATE TABLE IF NOT EXISTS USER (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS SUBJECT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS USER_SUBJECT (
    user_id INT,
    subject_id INT,
    PRIMARY KEY(user_id, subject_id),
    FOREIGN KEY (user_id) REFERENCES USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS POST (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id INT NOT NULL,
    subject_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (author_id) REFERENCES USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS COMMENT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    author_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES POST(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user (email, username, password) VALUES
('test@test.com', 'testUser', '$2y$10$yvJ8sr97eg44163KD4wqkOiBwmNFENw7oyNBnNGAjNY4unVRUc8M6');

INSERT INTO subject (name) VALUES
('JavaScript'),
('Java'),
('Python'),
('Go');

INSERT INTO user_subject (user_id, subject_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

INSERT INTO post (title, content, author_id, subject_id, created_at) VALUES
('JavaScript Post', 'Content of JavaScript Post', 1, 1, NOW()),
('Java Post', 'Content of Java Post', 1, 2, NOW()),
('Python Post', 'Content of Python Post', 1, 3, NOW()),
('Go Post', 'Content of Go Post', 1, 4, NOW());

INSERT INTO comment (content, author_id, post_id, created_at) VALUES
('Comment on JavaScript Post', 1, 1, NOW());