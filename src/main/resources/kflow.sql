-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 12 mai 2025 à 10:02
-- Version du serveur : 5.7.40
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `kflow`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `competition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6s6afj2ks4klekxkjgj1uv5dw` (`competition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `name`, `competition_id`) VALUES
(1, 'Kayak Monoplace Homme Senior', 1),
(2, 'Kayak Monoplace Homme Senior', 2),
(3, 'Kayak Monoplace Homme Senior', 3),
(4, 'Kayak Monoplace Homme Senior', 4),
(5, 'Kayak Monoplace Homme Senior', 5);

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

DROP TABLE IF EXISTS `competition`;
CREATE TABLE IF NOT EXISTS `competition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `endDate` date NOT NULL,
  `level` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `startDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `competition`
--

INSERT INTO `competition` (`id`, `endDate`, `level`, `place`, `startDate`) VALUES
(1, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18'),
(2, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18'),
(3, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18'),
(4, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18'),
(5, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18');

-- --------------------------------------------------------

--
-- Structure de la table `participant`
--

DROP TABLE IF EXISTS `participant`;
CREATE TABLE IF NOT EXISTS `participant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bibNb` int(11) NOT NULL,
  `club` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `participant`
--

INSERT INTO `participant` (`id`, `bibNb`, `club`, `name`) VALUES
(1, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(2, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(3, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(4, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(5, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(6, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(7, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(8, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(9, 27, 'CNLorgues', 'Julien FAYOL'),
(10, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(11, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(12, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(13, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(14, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(15, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(16, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(17, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(18, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(19, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(20, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(21, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(22, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(23, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(24, 27, 'CNLorgues', 'Julien FAYOL'),
(25, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(26, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(27, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(28, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(29, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(30, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(31, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(32, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(33, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(34, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(35, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(36, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(37, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(38, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(39, 27, 'CNLorgues', 'Julien FAYOL'),
(40, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(41, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(42, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(43, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(44, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(45, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(46, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(47, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(48, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(49, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(50, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(51, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(52, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(53, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(54, 27, 'CNLorgues', 'Julien FAYOL'),
(55, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(56, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(57, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(58, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(59, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(60, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(61, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(62, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(63, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(64, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(65, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(66, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(67, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(68, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(69, 27, 'CNLorgues', 'Julien FAYOL'),
(70, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(71, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(72, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(73, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(74, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(75, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS');

-- --------------------------------------------------------

--
-- Structure de la table `participant_categories`
--

DROP TABLE IF EXISTS `participant_categories`;
CREATE TABLE IF NOT EXISTS `participant_categories` (
  `participants_id` bigint(20) NOT NULL,
  `categories_id` bigint(20) NOT NULL,
  KEY `FKhb7jm8ry38nvuc66bjldbdnxb` (`categories_id`),
  KEY `FK442yexwkcsl1nfu8v627n9k80` (`participants_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `participant_categories`
--

INSERT INTO `participant_categories` (`participants_id`, `categories_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 2),
(17, 2),
(18, 2),
(19, 2),
(20, 2),
(21, 2),
(22, 2),
(23, 2),
(24, 2),
(25, 2),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2),
(31, 3),
(32, 3),
(33, 3),
(34, 3),
(35, 3),
(36, 3),
(37, 3),
(38, 3),
(39, 3),
(40, 3),
(41, 3),
(42, 3),
(43, 3),
(44, 3),
(45, 3),
(46, 4),
(47, 4),
(48, 4),
(49, 4),
(50, 4),
(51, 4),
(52, 4),
(53, 4),
(54, 4),
(55, 4),
(56, 4),
(57, 4),
(58, 4),
(59, 4),
(60, 4),
(61, 5),
(62, 5),
(63, 5),
(64, 5),
(65, 5),
(66, 5),
(67, 5),
(68, 5),
(69, 5),
(70, 5),
(71, 5),
(72, 5),
(73, 5),
(74, 5),
(75, 5);

-- --------------------------------------------------------

--
-- Structure de la table `run`
--

DROP TABLE IF EXISTS `run`;
CREATE TABLE IF NOT EXISTS `run` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration` int(11) NOT NULL,
  `score` float NOT NULL,
  `participant_id` bigint(20) NOT NULL,
  `stage_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhdto5nb9tcvl37fnd1kgxnu2l` (`participant_id`),
  KEY `FKhui0nynn9stj1w85159mwdsr` (`stage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `run`
--

INSERT INTO `run` (`id`, `duration`, `score`, `participant_id`, `stage_id`) VALUES
(1, 45, 600, 1, 2),
(2, 45, 260, 1, 2),
(3, 45, 675, 1, 3),
(4, 45, 645, 1, 3),
(5, 45, 580, 1, 4),
(6, 45, 385, 1, 1),
(7, 45, 420, 1, 1),
(8, 45, 540, 2, 2),
(9, 45, 450, 2, 2),
(10, 45, 535, 2, 3),
(11, 45, 580, 2, 3),
(12, 45, 380, 2, 4),
(13, 45, 420, 2, 1),
(14, 45, 495, 2, 1),
(15, 45, 135, 3, 2),
(16, 45, 425, 3, 2),
(17, 45, 535, 3, 3),
(18, 45, 515, 3, 3),
(19, 45, 515, 3, 4),
(20, 45, 515, 3, 1),
(21, 45, 515, 3, 1),
(22, 45, 255, 4, 2),
(23, 45, 320, 4, 2),
(24, 45, 290, 4, 3),
(25, 45, 325, 4, 3),
(26, 45, 290, 4, 4),
(27, 45, 530, 4, 1),
(28, 45, 0, 4, 1),
(29, 45, 270, 5, 2),
(30, 45, 120, 5, 2),
(31, 45, 325, 5, 3),
(32, 45, 275, 5, 3),
(33, 45, 200, 6, 2),
(34, 45, 90, 6, 2),
(35, 45, 225, 6, 3),
(36, 45, 180, 6, 3),
(37, 45, 100, 7, 2),
(38, 45, 180, 7, 2),
(39, 45, 160, 7, 3),
(40, 45, 225, 7, 3),
(41, 45, 110, 8, 2),
(42, 45, 60, 8, 2),
(43, 45, 170, 8, 3),
(44, 45, 180, 8, 3),
(45, 45, 95, 9, 2),
(46, 45, 110, 9, 2),
(47, 45, 135, 9, 3),
(48, 45, 40, 9, 3),
(49, 45, 165, 10, 2),
(50, 45, 60, 10, 2),
(51, 45, 225, 10, 3),
(52, 45, 50, 10, 3),
(53, 45, 50, 11, 2),
(54, 45, 90, 11, 2),
(55, 45, 20, 12, 2),
(56, 45, 20, 12, 2),
(57, 45, 20, 13, 2),
(58, 45, 20, 13, 2),
(59, 45, 0, 14, 2),
(60, 45, 30, 14, 2),
(61, 45, 20, 15, 2),
(62, 45, 0, 15, 2),
(63, 45, 600, 16, 6),
(64, 45, 260, 16, 6),
(65, 45, 675, 16, 7),
(66, 45, 645, 16, 7),
(67, 45, 580, 16, 8),
(68, 45, 385, 16, 5),
(69, 45, 420, 16, 5),
(70, 45, 540, 17, 6),
(71, 45, 450, 17, 6),
(72, 45, 535, 17, 7),
(73, 45, 580, 17, 7),
(74, 45, 380, 17, 8),
(75, 45, 420, 17, 5),
(76, 45, 495, 17, 5),
(77, 45, 135, 18, 6),
(78, 45, 425, 18, 6),
(79, 45, 535, 18, 7),
(80, 45, 515, 18, 7),
(81, 45, 515, 18, 8),
(82, 45, 515, 18, 5),
(83, 45, 515, 18, 5),
(84, 45, 255, 19, 6),
(85, 45, 320, 19, 6),
(86, 45, 290, 19, 7),
(87, 45, 325, 19, 7),
(88, 45, 290, 19, 8),
(89, 45, 530, 19, 5),
(90, 45, 0, 19, 5),
(91, 45, 270, 20, 6),
(92, 45, 120, 20, 6),
(93, 45, 325, 20, 7),
(94, 45, 275, 20, 7),
(95, 45, 200, 21, 6),
(96, 45, 90, 21, 6),
(97, 45, 225, 21, 7),
(98, 45, 180, 21, 7),
(99, 45, 100, 22, 6),
(100, 45, 180, 22, 6),
(101, 45, 160, 22, 7),
(102, 45, 225, 22, 7),
(103, 45, 110, 23, 6),
(104, 45, 60, 23, 6),
(105, 45, 170, 23, 7),
(106, 45, 180, 23, 7),
(107, 45, 95, 24, 6),
(108, 45, 110, 24, 6),
(109, 45, 135, 24, 7),
(110, 45, 40, 24, 7),
(111, 45, 165, 25, 6),
(112, 45, 60, 25, 6),
(113, 45, 225, 25, 7),
(114, 45, 50, 25, 7),
(115, 45, 50, 26, 6),
(116, 45, 90, 26, 6),
(117, 45, 20, 27, 6),
(118, 45, 20, 27, 6),
(119, 45, 20, 28, 6),
(120, 45, 20, 28, 6),
(121, 45, 0, 29, 6),
(122, 45, 30, 29, 6),
(123, 45, 20, 30, 6),
(124, 45, 0, 30, 6),
(125, 45, 600, 31, 10),
(126, 45, 260, 31, 10),
(127, 45, 675, 31, 11),
(128, 45, 645, 31, 11),
(129, 45, 580, 31, 12),
(130, 45, 385, 31, 9),
(131, 45, 420, 31, 9),
(132, 45, 540, 32, 10),
(133, 45, 450, 32, 10),
(134, 45, 535, 32, 11),
(135, 45, 580, 32, 11),
(136, 45, 380, 32, 12),
(137, 45, 420, 32, 9),
(138, 45, 495, 32, 9),
(139, 45, 135, 33, 10),
(140, 45, 425, 33, 10),
(141, 45, 535, 33, 11),
(142, 45, 515, 33, 11),
(143, 45, 515, 33, 12),
(144, 45, 515, 33, 9),
(145, 45, 515, 33, 9),
(146, 45, 255, 34, 10),
(147, 45, 320, 34, 10),
(148, 45, 290, 34, 11),
(149, 45, 325, 34, 11),
(150, 45, 290, 34, 12),
(151, 45, 530, 34, 9),
(152, 45, 0, 34, 9),
(153, 45, 270, 35, 10),
(154, 45, 120, 35, 10),
(155, 45, 325, 35, 11),
(156, 45, 275, 35, 11),
(157, 45, 200, 36, 10),
(158, 45, 90, 36, 10),
(159, 45, 225, 36, 11),
(160, 45, 180, 36, 11),
(161, 45, 100, 37, 10),
(162, 45, 180, 37, 10),
(163, 45, 160, 37, 11),
(164, 45, 225, 37, 11),
(165, 45, 110, 38, 10),
(166, 45, 60, 38, 10),
(167, 45, 170, 38, 11),
(168, 45, 180, 38, 11),
(169, 45, 95, 39, 10),
(170, 45, 110, 39, 10),
(171, 45, 135, 39, 11),
(172, 45, 40, 39, 11),
(173, 45, 165, 40, 10),
(174, 45, 60, 40, 10),
(175, 45, 225, 40, 11),
(176, 45, 50, 40, 11),
(177, 45, 50, 41, 10),
(178, 45, 90, 41, 10),
(179, 45, 20, 42, 10),
(180, 45, 20, 42, 10),
(181, 45, 20, 43, 10),
(182, 45, 20, 43, 10),
(183, 45, 0, 44, 10),
(184, 45, 30, 44, 10),
(185, 45, 20, 45, 10),
(186, 45, 0, 45, 10),
(187, 45, 600, 46, 14),
(188, 45, 260, 46, 14),
(189, 45, 675, 46, 15),
(190, 45, 645, 46, 15),
(191, 45, 580, 46, 16),
(192, 45, 385, 46, 13),
(193, 45, 420, 46, 13),
(194, 45, 540, 47, 14),
(195, 45, 450, 47, 14),
(196, 45, 535, 47, 15),
(197, 45, 580, 47, 15),
(198, 45, 380, 47, 16),
(199, 45, 420, 47, 13),
(200, 45, 495, 47, 13),
(201, 45, 135, 48, 14),
(202, 45, 425, 48, 14),
(203, 45, 535, 48, 15),
(204, 45, 515, 48, 15),
(205, 45, 515, 48, 16),
(206, 45, 515, 48, 13),
(207, 45, 515, 48, 13),
(208, 45, 255, 49, 14),
(209, 45, 320, 49, 14),
(210, 45, 290, 49, 15),
(211, 45, 325, 49, 15),
(212, 45, 290, 49, 16),
(213, 45, 530, 49, 13),
(214, 45, 0, 49, 13),
(215, 45, 270, 50, 14),
(216, 45, 120, 50, 14),
(217, 45, 325, 50, 15),
(218, 45, 275, 50, 15),
(219, 45, 200, 51, 14),
(220, 45, 90, 51, 14),
(221, 45, 225, 51, 15),
(222, 45, 180, 51, 15),
(223, 45, 100, 52, 14),
(224, 45, 180, 52, 14),
(225, 45, 160, 52, 15),
(226, 45, 225, 52, 15),
(227, 45, 110, 53, 14),
(228, 45, 60, 53, 14),
(229, 45, 170, 53, 15),
(230, 45, 180, 53, 15),
(231, 45, 95, 54, 14),
(232, 45, 110, 54, 14),
(233, 45, 135, 54, 15),
(234, 45, 40, 54, 15),
(235, 45, 165, 55, 14),
(236, 45, 60, 55, 14),
(237, 45, 225, 55, 15),
(238, 45, 50, 55, 15),
(239, 45, 50, 56, 14),
(240, 45, 90, 56, 14),
(241, 45, 20, 57, 14),
(242, 45, 20, 57, 14),
(243, 45, 20, 58, 14),
(244, 45, 20, 58, 14),
(245, 45, 0, 59, 14),
(246, 45, 30, 59, 14),
(247, 45, 20, 60, 14),
(248, 45, 0, 60, 14),
(249, 45, 600, 61, 18),
(250, 45, 260, 61, 18),
(251, 45, 675, 61, 19),
(252, 45, 645, 61, 19),
(253, 45, 580, 61, 20),
(254, 45, 385, 61, 17),
(255, 45, 420, 61, 17),
(256, 45, 540, 62, 18),
(257, 45, 450, 62, 18),
(258, 45, 535, 62, 19),
(259, 45, 580, 62, 19),
(260, 45, 380, 62, 20),
(261, 45, 420, 62, 17),
(262, 45, 495, 62, 17),
(263, 45, 135, 63, 18),
(264, 45, 425, 63, 18),
(265, 45, 535, 63, 19),
(266, 45, 515, 63, 19),
(267, 45, 515, 63, 20),
(268, 45, 515, 63, 17),
(269, 45, 515, 63, 17),
(270, 45, 255, 64, 18),
(271, 45, 320, 64, 18),
(272, 45, 290, 64, 19),
(273, 45, 325, 64, 19),
(274, 45, 290, 64, 20),
(275, 45, 530, 64, 17),
(276, 45, 0, 64, 17),
(277, 45, 270, 65, 18),
(278, 45, 120, 65, 18),
(279, 45, 325, 65, 19),
(280, 45, 275, 65, 19),
(281, 45, 200, 66, 18),
(282, 45, 90, 66, 18),
(283, 45, 225, 66, 19),
(284, 45, 180, 66, 19),
(285, 45, 100, 67, 18),
(286, 45, 180, 67, 18),
(287, 45, 160, 67, 19),
(288, 45, 225, 67, 19),
(289, 45, 110, 68, 18),
(290, 45, 60, 68, 18),
(291, 45, 170, 68, 19),
(292, 45, 180, 68, 19),
(293, 45, 95, 69, 18),
(294, 45, 110, 69, 18),
(295, 45, 135, 69, 19),
(296, 45, 40, 69, 19),
(297, 45, 165, 70, 18),
(298, 45, 60, 70, 18),
(299, 45, 225, 70, 19),
(300, 45, 50, 70, 19),
(301, 45, 50, 71, 18),
(302, 45, 90, 71, 18),
(303, 45, 20, 72, 18),
(304, 45, 20, 72, 18),
(305, 45, 20, 73, 18),
(306, 45, 20, 73, 18),
(307, 45, 0, 74, 18),
(308, 45, 30, 74, 18),
(309, 45, 20, 75, 18),
(310, 45, 0, 75, 18);

-- --------------------------------------------------------

--
-- Structure de la table `stage`
--

DROP TABLE IF EXISTS `stage`;
CREATE TABLE IF NOT EXISTS `stage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `nbRun` int(11) NOT NULL,
  `rules` varchar(255) NOT NULL,
  `categorie_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6fr624joel1gih7kgju4f29gw` (`categorie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `stage`
--

INSERT INTO `stage` (`id`, `name`, `nbRun`, `rules`, `categorie_id`) VALUES
(1, 'Finale', 2, 'Standard', 1),
(2, 'Qualification', 2, 'Standard', 1),
(3, 'Quart', 2, 'Standard', 1),
(4, 'Demi', 2, 'Standard', 1),
(5, 'Finale', 2, 'Standard', 2),
(6, 'Qualification', 2, 'Standard', 2),
(7, 'Quart', 2, 'Standard', 2),
(8, 'Demi', 2, 'Standard', 2),
(9, 'Finale', 2, 'Standard', 3),
(10, 'Qualification', 2, 'Standard', 3),
(11, 'Quart', 2, 'Standard', 3),
(12, 'Demi', 2, 'Standard', 3),
(13, 'Finale', 2, 'Standard', 4),
(14, 'Qualification', 2, 'Standard', 4),
(15, 'Quart', 2, 'Standard', 4),
(16, 'Demi', 2, 'Standard', 4),
(17, 'Finale', 2, 'Standard', 5),
(18, 'Qualification', 2, 'Standard', 5),
(19, 'Quart', 2, 'Standard', 5),
(20, 'Demi', 2, 'Standard', 5);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `name`, `password`, `role`) VALUES
(1, 'admin@admin.admin', 'admin', '{bcrypt}$2a$10$o5tPj8/uDy2CjgnpaBauaeFdZwwUhirk8XLbJXcAupBzQyUL4fp6G', 1),
(2, 'user@user.user', 'user', '{bcrypt}$2a$10$vufUR4z4kb279H/2B4NIF.NRjSav7bnNgzLzJUsWEIsm5OXZiitRK', 0);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD CONSTRAINT `FK6s6afj2ks4klekxkjgj1uv5dw` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`);

--
-- Contraintes pour la table `participant_categories`
--
ALTER TABLE `participant_categories`
  ADD CONSTRAINT `FK442yexwkcsl1nfu8v627n9k80` FOREIGN KEY (`participants_id`) REFERENCES `participant` (`id`),
  ADD CONSTRAINT `FKhb7jm8ry38nvuc66bjldbdnxb` FOREIGN KEY (`categories_id`) REFERENCES `categorie` (`id`);

--
-- Contraintes pour la table `run`
--
ALTER TABLE `run`
  ADD CONSTRAINT `FKhdto5nb9tcvl37fnd1kgxnu2l` FOREIGN KEY (`participant_id`) REFERENCES `participant` (`id`),
  ADD CONSTRAINT `FKhui0nynn9stj1w85159mwdsr` FOREIGN KEY (`stage_id`) REFERENCES `stage` (`id`);

--
-- Contraintes pour la table `stage`
--
ALTER TABLE `stage`
  ADD CONSTRAINT `FK6fr624joel1gih7kgju4f29gw` FOREIGN KEY (`categorie_id`) REFERENCES `categorie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
