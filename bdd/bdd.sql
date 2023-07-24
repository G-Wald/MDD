CREATE TABLE Utilisateur (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `last_name` VARCHAR(40),
    `first_name` VARCHAR(40),
    `email` VARCHAR(100),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Themes (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `titre` VARCHAR(40),
    `description` VARCHAR(2000)
);

CREATE TABLE Articles (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `titre` VARCHAR(40),
    `contenu` VARCHAR(2000),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `ID_Utilisateur` INT,
    `ID_Theme` INT,
    FOREIGN KEY (`ID_Utilisateur`) REFERENCES Utilisateur(`id`),
    FOREIGN KEY (`ID_Theme`) REFERENCES Themes(`id`)
);

CREATE TABLE Commentaire (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `message` VARCHAR(2000),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `ID_Article` INT,
    `ID_Utilisateur` INT,
    FOREIGN KEY (`ID_Article`) REFERENCES Articles(`id`),
    FOREIGN KEY (`ID_Utilisateur`) REFERENCES Utilisateur(`id`)
);

