
CREATE TABLE IF NOT EXISTS resultado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_Jugador VARCHAR(255) NOT NULL,
    puntuacion INT NOT NULL,
    categoria VARCHAR(255) NOT NULL
);
