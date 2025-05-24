-- Insertion des utilisateurs de test
INSERT INTO "User" (email, name, password, role) VALUES
('test@example.com', 'Test User', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'USER'),
('admin@example.com', 'Admin User', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'ADMIN');

-- Note: Le mot de passe encodé correspond à 'password123' 