-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 16 mai 2025 à 12:11
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `name`, `competition_id`) VALUES
(10, 'Kayak Monoplace Homme Senior', 10),
(11, 'Kayak Monoplace Homme Senior', 11);

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `competition`
--

INSERT INTO `competition` (`id`, `endDate`, `level`, `place`, `startDate`) VALUES
(10, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18'),
(11, '2024-03-18', 'Regional', 'Clermont-Ferrand', '2024-03-18');

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
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=latin1;

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
(75, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(76, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(77, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(78, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(79, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(80, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(81, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(82, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(83, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(84, 27, 'CNLorgues', 'Julien FAYOL'),
(85, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(86, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(87, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(88, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(89, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(90, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(91, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(92, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(93, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(94, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(95, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(96, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(97, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(98, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(99, 27, 'CNLorgues', 'Julien FAYOL'),
(100, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(101, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(102, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(103, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(104, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(105, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(106, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(107, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(108, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(109, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(110, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(111, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(112, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(113, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(114, 27, 'CNLorgues', 'Julien FAYOL'),
(115, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(116, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(117, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(118, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(119, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(120, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(121, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(122, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(123, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(124, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(125, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(126, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(127, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(128, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(129, 27, 'CNLorgues', 'Julien FAYOL'),
(130, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(131, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(132, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(133, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(134, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(135, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(136, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(137, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(138, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(139, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(140, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(141, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(142, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(143, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(144, 27, 'CNLorgues', 'Julien FAYOL'),
(145, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(146, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(147, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(148, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(149, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(150, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(151, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(152, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(153, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(154, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(155, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(156, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(157, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(158, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(159, 27, 'CNLorgues', 'Julien FAYOL'),
(160, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(161, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(162, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(163, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(164, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(165, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(167, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(168, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(169, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(170, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(171, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(172, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(173, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(174, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(175, 27, 'CNLorgues', 'Julien FAYOL'),
(176, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(177, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(178, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(179, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(180, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(181, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(182, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(183, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(184, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(185, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(186, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(187, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(188, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(189, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(190, 27, 'CNLorgues', 'Julien FAYOL'),
(191, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(192, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(193, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(194, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(195, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(196, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(197, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(198, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(199, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(200, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(201, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(202, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(203, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(204, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(205, 27, 'CNLorgues', 'Julien FAYOL'),
(206, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(207, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(208, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(209, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(210, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(211, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(212, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(213, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(214, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(215, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(216, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(217, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(218, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(219, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(220, 27, 'CNLorgues', 'Julien FAYOL'),
(221, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(222, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(223, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(224, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(225, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(226, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS'),
(227, 20, 'ISTRES ENTRESSEN CANOE KAYAK', 'Valentin PARASME'),
(228, 4, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'François ANDRIEUX'),
(229, 36, 'ISTRES ENTRESSEN CANOE KAYAK', 'Alexandre ROZANO'),
(230, 14, 'NO PASA NADA', 'Jean Baptiste BAL'),
(231, 5, 'A.D.S.C. ECUELLES', 'Florient BARACHON'),
(232, 16, 'TRAVERSES PAGAIES', 'Remy GARCIA'),
(233, 31, 'S5k clermont communauté', 'Charlie NGUYEN'),
(234, 8, 'LOISIRS EAUX VIVES BEAUGENCY', 'Samuel ROUX'),
(235, 27, 'CNLorgues', 'Julien FAYOL'),
(236, 13, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'NIls BERTRAND'),
(237, 45, 'S.V. VAULX EN VELIN', 'Yasin GULAY'),
(238, 35, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Cyril DE RYCKE'),
(239, 12, 'CLERMONT COMMUNAUTE CANOE KAYAK', 'Jean DOUSSON'),
(240, 30, 'S5k clermont communauté', 'Maxime MOUILLAUD'),
(241, 38, 'CLUB NAUTIQUE DE LONGUES', 'Ugo DE BARROS');

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
(197, 10),
(198, 10),
(199, 10),
(200, 10),
(201, 10),
(202, 10),
(203, 10),
(204, 10),
(205, 10),
(206, 10),
(207, 10),
(208, 10),
(209, 10),
(210, 10),
(211, 10),
(212, 11),
(213, 11),
(214, 11),
(215, 11),
(216, 11),
(217, 11),
(218, 11),
(219, 11),
(220, 11),
(221, 11),
(222, 11),
(223, 11),
(224, 11),
(225, 11),
(226, 11);

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
) ENGINE=InnoDB AUTO_INCREMENT=747 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `run`
--

INSERT INTO `run` (`id`, `duration`, `score`, `participant_id`, `stage_id`) VALUES
(559, 45, 600, 197, 38),
(560, 45, 260, 197, 38),
(561, 45, 675, 197, 39),
(562, 45, 645, 197, 39),
(563, 45, 580, 197, 40),
(564, 45, 385, 197, 37),
(565, 45, 420, 197, 37),
(566, 45, 540, 198, 38),
(567, 45, 450, 198, 38),
(568, 45, 535, 198, 39),
(569, 45, 580, 198, 39),
(570, 45, 380, 198, 40),
(571, 45, 420, 198, 37),
(572, 45, 495, 198, 37),
(573, 45, 135, 199, 38),
(574, 45, 425, 199, 38),
(575, 45, 535, 199, 39),
(576, 45, 515, 199, 39),
(577, 45, 515, 199, 40),
(578, 45, 515, 199, 37),
(579, 45, 515, 199, 37),
(580, 45, 255, 200, 38),
(581, 45, 320, 200, 38),
(582, 45, 290, 200, 39),
(583, 45, 325, 200, 39),
(584, 45, 290, 200, 40),
(585, 45, 530, 200, 37),
(586, 45, 0, 200, 37),
(587, 45, 270, 201, 38),
(588, 45, 120, 201, 38),
(589, 45, 325, 201, 39),
(590, 45, 275, 201, 39),
(591, 45, 200, 202, 38),
(592, 45, 90, 202, 38),
(593, 45, 225, 202, 39),
(594, 45, 180, 202, 39),
(595, 45, 100, 203, 38),
(596, 45, 180, 203, 38),
(597, 45, 160, 203, 39),
(598, 45, 225, 203, 39),
(599, 45, 110, 204, 38),
(600, 45, 60, 204, 38),
(601, 45, 170, 204, 39),
(602, 45, 180, 204, 39),
(603, 45, 95, 205, 38),
(604, 45, 110, 205, 38),
(605, 45, 135, 205, 39),
(606, 45, 40, 205, 39),
(607, 45, 165, 206, 38),
(608, 45, 60, 206, 38),
(609, 45, 225, 206, 39),
(610, 45, 50, 206, 39),
(611, 45, 50, 207, 38),
(612, 45, 90, 207, 38),
(613, 45, 20, 208, 38),
(614, 45, 20, 208, 38),
(615, 45, 20, 209, 38),
(616, 45, 20, 209, 38),
(617, 45, 0, 210, 38),
(618, 45, 30, 210, 38),
(619, 45, 20, 211, 38),
(620, 45, 0, 211, 38),
(621, 45, 600, 212, 42),
(622, 45, 260, 212, 42),
(623, 45, 675, 212, 43),
(624, 45, 645, 212, 43),
(625, 45, 580, 212, 44),
(626, 45, 385, 212, 41),
(627, 45, 420, 212, 41),
(628, 45, 420, 212, 41),
(629, 45, 540, 213, 42),
(630, 45, 450, 213, 42),
(631, 45, 535, 213, 43),
(632, 45, 580, 213, 43),
(633, 45, 380, 213, 44),
(634, 45, 420, 213, 41),
(635, 45, 495, 213, 41),
(636, 45, 135, 214, 42),
(637, 45, 425, 214, 42),
(638, 45, 535, 214, 43),
(639, 45, 515, 214, 43),
(640, 45, 515, 214, 44),
(641, 45, 515, 214, 41),
(642, 45, 515, 214, 41),
(643, 45, 255, 215, 42),
(644, 45, 320, 215, 42),
(645, 45, 290, 215, 43),
(646, 45, 325, 215, 43),
(647, 45, 290, 215, 44),
(648, 45, 530, 215, 41),
(649, 45, 0, 215, 41),
(650, 45, 270, 216, 42),
(651, 45, 120, 216, 42),
(652, 45, 325, 216, 43),
(653, 45, 275, 216, 43),
(654, 45, 200, 217, 42),
(655, 45, 90, 217, 42),
(656, 45, 225, 217, 43),
(657, 45, 180, 217, 43),
(658, 45, 100, 218, 42),
(659, 45, 180, 218, 42),
(660, 45, 160, 218, 43),
(661, 45, 225, 218, 43),
(662, 45, 110, 219, 42),
(663, 45, 60, 219, 42),
(664, 45, 170, 219, 43),
(665, 45, 180, 219, 43),
(666, 45, 95, 220, 42),
(667, 45, 110, 220, 42),
(668, 45, 135, 220, 43),
(669, 45, 40, 220, 43),
(670, 45, 165, 221, 42),
(671, 45, 60, 221, 42),
(672, 45, 225, 221, 43),
(673, 45, 50, 221, 43),
(674, 45, 50, 222, 42),
(675, 45, 90, 222, 42),
(676, 45, 20, 223, 42),
(677, 45, 20, 223, 42),
(678, 45, 20, 224, 42),
(679, 45, 20, 224, 42),
(680, 45, 0, 225, 42),
(681, 45, 30, 225, 42),
(682, 45, 20, 226, 42),
(683, 45, 0, 226, 42);

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `stage`
--

INSERT INTO `stage` (`id`, `name`, `nbRun`, `rules`, `categorie_id`) VALUES
(37, 'Finale', 2, 'Standard', 10),
(38, 'Qualification', 2, 'Standard', 10),
(39, 'Quart', 2, 'Standard', 10),
(40, 'Demi', 2, 'Standard', 10),
(41, 'Finale', 2, 'Standard', 11),
(42, 'Qualification', 2, 'Standard', 11),
(43, 'Quart', 2, 'Standard', 11),
(44, 'Demi', 2, 'Standard', 11);

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
