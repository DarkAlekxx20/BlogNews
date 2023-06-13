-- ------------------------------------------------------------------------ --
-- Archivo: BlogNews_DLL.sql		        								--
-- Version: 1.0                                                     		--
-- Autor:   Israel Alejandro López Tovar   								    --
-- Email:   niko.beelik@hotmail.com									        --
-- Fecha de elaboracion: 08-06-2023                                 		--
-- ------------------------------------------------------------------------ --
DROP DATABASE IF EXISTS blognews;
CREATE DATABASE blognews;
USE blognews;

CREATE TABLE personal(
idPersonal INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
nombre 			VARCHAR(60) NOT NULL,
apePaterno 		VARCHAR(40) NOT NULL,
apeMaterno 		VARCHAR(40) NOT NULL,
direccion 		VARCHAR(120) NOT NULL,
fechaIngreso 	DATE NOT NULL
);
-- REALICE EL INSERT DE PERSONAL PARA QUE PUEDA DAR DE ALTA NOTICIAS.
INSERT INTO personal(apePaterno, apeMaterno, nombre, direccion, fechaIngreso) VALUES("Lopez","Tovar","Israel","Pedregales de Echeveste",STR_TO_DATE("08/06/2023",'%d/%m/%Y'));

CREATE TABLE usuario(
	idUsuario			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario		VARCHAR(20) NOT NULL,
    contrasenia			VARCHAR(40) NOT NULL,
    correo				VARCHAR(45) NOT NULL,
    rol					VARCHAR(45) NOT NULL
);

CREATE TABLE noticia(
	idNoticia			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    titulo				VARCHAR(45),
    contenido			VARCHAR(1000),
    fechaPublicacion	DATETIME,
    idPersonal			INT NOT NULL,
    CONSTRAINT noticia_idPersonal_fk FOREIGN KEY(idPersonal) REFERENCES personal(idPersonal)
);

CREATE TABLE comentario(
	idComentario		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    texto				VARCHAR(1000) NOT NULL,
    fechayHora			DATETIME,
    idNoticia			INT NOT NULL,
    idUsuario			INT NOT NULL,
    CONSTRAINT comentario_idNoticia_fk FOREIGN KEY(idNoticia) REFERENCES noticia(idNoticia),
    CONSTRAINT comentario_idUsuario_fk FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario)
);

CREATE TABLE respuesta(
	idRespuesta			INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    texto				VARCHAR(1000) NOT NULL,
    fechayHora			DATETIME,
    idComentario		INT NOT NULL,
    idUsuario			INT NOT NULL,
    CONSTRAINT respuesta_idComentario_fk FOREIGN KEY(idComentario) REFERENCES comentario(idComentario),
    CONSTRAINT respuesta_idUsuario_fk FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario)
);

-- INSERTS
INSERT INTO usuario(nombreUsuario, contrasenia, correo, rol) VALUES("Israel", ",Enero2003", "niko.beelik@hotmail.com", "Interno");
INSERT INTO usuario(nombreUsuario, contrasenia, correo, rol) VALUES("Marilyn", "5678", "Marilyn@gmail.com", "Externo");

-- VIEWS
CREATE VIEW caja_comentarios AS
SELECT r.* FROM respuesta r INNER JOIN comentario c ON r.idComentario=c.idComentario INNER JOIN usuario u ON r.idUsuario=u.idUsuario;
SELECT * FROM caja_comentarios;