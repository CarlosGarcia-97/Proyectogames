-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 06-08-2021 a las 16:38:22
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `videogames`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_create` (`p_name` VARCHAR(120), `p_img` LONGBLOB, `p_date` DATE, `p_category` INT)  BEGIN 
	INSERT INTO games(name, img_game, date_premiere, Category_idCategory, status)
    VALUES (p_name, p_img, p_date, p_category, 1);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete` (`p_id` INT)  BEGIN
	UPDATE games SET status = 0 WHERE idGames = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findById` (`p_idGames` INT)  BEGIN SELECT * from games AS G INNER JOIN category AS C ON 
G.Category_idCategory = C.idCategory WHERE g.idGames = p_idGames;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_findGames` ()  BEGIN SELECT * from games AS G INNER JOIN category AS C ON G.Category_idCategory = C.idCategory;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update` (`p_idGames` INT, `p_name` VARCHAR(120), `p_img` LONGBLOB, `p_date` DATE, `p_category` INT)  BEGIN
	UPDATE games SET name = p_name, img_game = p_img, date_premiere = p_date,
    Category_idCategory = p_category WHERE idGames = p_idGames;    
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `idCategory` int(11) NOT NULL,
  `name` varchar(120) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `games`
--

CREATE TABLE `games` (
  `idGames` int(11) NOT NULL,
  `name` varchar(120) NOT NULL,
  `img_game` longblob NOT NULL,
  `date_premiere` date NOT NULL,
  `Category_idCategory` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idCategory`);

--
-- Indices de la tabla `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`idGames`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `idCategory` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `games`
--
ALTER TABLE `games`
  MODIFY `idGames` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
