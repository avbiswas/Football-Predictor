-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2016 at 11:02 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `predictor`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `distinct_matchdays`
--
DROP VIEW IF EXISTS `distinct_matchdays`;
CREATE TABLE IF NOT EXISTS `distinct_matchdays` (
`matchday` int(5)
,`no_of_matches` bigint(21)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `extra_points_table`
--
DROP VIEW IF EXISTS `extra_points_table`;
CREATE TABLE IF NOT EXISTS `extra_points_table` (
`user_id` int(20)
,`matchday` int(5)
,`no_of_matches` bigint(21)
,`cutoff` decimal(16,0)
,`no_of_pdtcn` bigint(21)
,`def` decimal(21,0)
,`deduction_coeff` decimal(21,0)
,`bonus_coeff` int(1)
,`deduction` decimal(23,0)
,`bonus` int(4)
,`extra_points` decimal(24,0)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `fixture_results`
--
DROP VIEW IF EXISTS `fixture_results`;
CREATE TABLE IF NOT EXISTS `fixture_results` (
`match_id` int(4)
,`matchday` bigint(11)
,`date` datetime
,`team1_id` int(2)
,`team2_id` int(2)
,`home` varchar(100)
,`away` varchar(100)
,`score` varchar(25)
,`predictions` bigint(21)
,`avg` decimal(14,4)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `friends`
--
DROP VIEW IF EXISTS `friends`;
CREATE TABLE IF NOT EXISTS `friends` (
`user_id` int(10)
,`friend_id` int(10)
,`username` varchar(200)
,`friendname` varchar(200)
,`name` varchar(200)
,`name2` varchar(200)
);
-- --------------------------------------------------------

--
-- Table structure for table `friend_request`
--

DROP TABLE IF EXISTS `friend_request`;
CREATE TABLE IF NOT EXISTS `friend_request` (
  `user_id` int(10) NOT NULL,
  `friend_id` int(10) NOT NULL,
  `date` date NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `user_id` (`user_id`),
  KEY `friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend_request`
--

INSERT INTO `friend_request` (`user_id`, `friend_id`, `date`, `status`) VALUES
(1, 3, '2016-08-12', 1),
(2, 1, '2016-08-18', 1),
(2, 7, '2016-08-15', 1),
(3, 2, '2016-08-13', 1),
(4, 3, '2016-08-13', 1),
(4, 5, '2016-08-13', 0),
(6, 3, '2016-08-13', 1);

-- --------------------------------------------------------

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
CREATE TABLE IF NOT EXISTS `group` (
  `group_id` int(5) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL,
  `matchday` int(5) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `group_name` (`group_name`),
  KEY `matc` (`matchday`),
  KEY `matchday` (`matchday`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `group`
--

INSERT INTO `group` (`group_id`, `group_name`, `matchday`) VALUES
(1, 'yooy', 2),
(23, 'mygro', 1),
(32, 'newgroup', 3);

-- --------------------------------------------------------

--
-- Stand-in structure for view `groups_overview`
--
DROP VIEW IF EXISTS `groups_overview`;
CREATE TABLE IF NOT EXISTS `groups_overview` (
`group_id` int(5)
,`group_name` varchar(100)
,`matchday` int(5)
,`members` bigint(21)
,`max_points` decimal(56,0)
,`avg_points` decimal(60,4)
,`leader` varchar(200)
);
-- --------------------------------------------------------

--
-- Table structure for table `group_details`
--

DROP TABLE IF EXISTS `group_details`;
CREATE TABLE IF NOT EXISTS `group_details` (
  `group_id` int(2) NOT NULL,
  `user_id` int(10) NOT NULL,
  UNIQUE KEY `group_id_3` (`group_id`,`user_id`),
  KEY `group_id` (`group_id`,`user_id`),
  KEY `group_id_2` (`group_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_details`
--

INSERT INTO `group_details` (`group_id`, `user_id`) VALUES
(1, 1),
(1, 3),
(1, 4),
(1, 6),
(1, 7),
(23, 1),
(23, 2),
(23, 3),
(23, 4),
(23, 5),
(23, 7),
(32, 1),
(32, 2),
(32, 3),
(32, 4);

-- --------------------------------------------------------

--
-- Table structure for table `group_permissions`
--

DROP TABLE IF EXISTS `group_permissions`;
CREATE TABLE IF NOT EXISTS `group_permissions` (
  `permission_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `group_id` int(10) NOT NULL,
  `time` datetime NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `user_id_2` (`user_id`,`group_id`),
  KEY `user_id` (`user_id`,`group_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `group_permissions`
--

INSERT INTO `group_permissions` (`permission_id`, `user_id`, `group_id`, `time`, `status`) VALUES
(19, 4, 23, '2016-07-25 22:18:56', 1),
(21, 5, 23, '2016-07-25 22:02:29', 2),
(23, 1, 32, '2016-07-28 19:44:20', 1);

-- --------------------------------------------------------

--
-- Stand-in structure for view `group_stat`
--
DROP VIEW IF EXISTS `group_stat`;
CREATE TABLE IF NOT EXISTS `group_stat` (
`group_id` int(2)
,`group_name` varchar(100)
,`matchday` int(5)
,`user_id` int(10)
,`username` varchar(200)
,`point` decimal(56,0)
,`predictions` decimal(42,0)
);
-- --------------------------------------------------------

--
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
CREATE TABLE IF NOT EXISTS `match` (
  `match_id` int(4) NOT NULL AUTO_INCREMENT,
  `team1_id` int(2) NOT NULL,
  `team2_id` int(2) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`match_id`),
  KEY `team1_id` (`team1_id`),
  KEY `team2_id` (`team2_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `match`
--

INSERT INTO `match` (`match_id`, `team1_id`, `team2_id`, `date`) VALUES
(1, 1, 3, '2016-07-18 08:00:00'),
(2, 0, 8, '2016-08-06 10:00:00'),
(3, 0, 9, '2016-07-26 23:00:00'),
(4, 7, 3, '2016-07-19 23:00:00'),
(5, 1, 0, '2016-09-22 06:00:00'),
(6, 1, 8, '2016-10-23 07:00:00'),
(7, 1, 7, '2016-07-31 00:00:00'),
(8, 2, 0, '2016-08-07 00:00:00'),
(9, 0, 3, '2016-07-31 00:00:00'),
(10, 3, 7, '2016-08-01 00:00:00'),
(11, 2, 7, '2016-08-06 00:00:00'),
(12, 3, 8, '2016-08-06 00:00:00'),
(13, 0, 10, '2016-08-24 00:00:00'),
(14, 6, 8, '2016-08-24 00:00:00'),
(15, 5, 16, '2016-08-24 08:26:00'),
(16, 14, 2, '2016-08-25 13:00:00'),
(17, 1, 18, '2016-08-26 18:00:00'),
(18, 4, 11, '2016-08-24 20:00:00'),
(19, 13, 12, '2016-08-23 18:45:00'),
(20, 4, 8, '2016-08-26 21:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `matchday`
--

DROP TABLE IF EXISTS `matchday`;
CREATE TABLE IF NOT EXISTS `matchday` (
  `matchday` int(5) NOT NULL AUTO_INCREMENT,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`matchday`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `matchday`
--

INSERT INTO `matchday` (`matchday`, `startDate`, `endDate`) VALUES
(1, '2016-07-16', '2016-07-22'),
(2, '2016-07-23', '2016-07-29'),
(3, '2016-07-30', '2016-08-05'),
(4, '2016-08-06', '2016-08-12'),
(5, '2016-08-13', '2016-08-26'),
(6, '2016-08-27', '2016-09-02'),
(7, '2016-09-03', '2016-09-16'),
(8, '2016-09-17', '2016-09-23'),
(9, '2016-09-24', '2016-09-30'),
(10, '2016-10-01', '2016-10-07'),
(11, '2016-10-08', '2016-10-14'),
(12, '2016-10-15', '2016-10-21'),
(13, '2016-10-22', '2016-10-28'),
(14, '2016-10-29', '2016-11-04'),
(15, '2016-11-05', '2016-11-11'),
(16, '2016-11-12', '2016-11-18'),
(17, '2016-11-19', '2016-11-25'),
(18, '2016-11-26', '2016-12-09'),
(19, '2016-12-10', '2016-12-16'),
(20, '2016-12-17', '2016-12-23'),
(21, '2016-12-24', '2016-12-30'),
(22, '2016-12-31', '2017-01-06'),
(23, '2017-01-07', '2017-01-13'),
(24, '2017-01-14', '2017-02-03'),
(25, '2017-02-04', '2017-02-10'),
(26, '2017-02-11', '2017-02-17'),
(27, '2017-02-18', '2017-02-24'),
(28, '2017-02-25', '2017-03-03'),
(29, '2017-03-04', '2017-03-10'),
(30, '2017-03-11', '2017-03-17'),
(31, '2017-03-18', '2017-03-24'),
(32, '2017-03-25', '2017-03-31'),
(33, '2017-04-01', '2017-04-07'),
(34, '2017-04-08', '2017-04-14'),
(35, '2017-04-15', '2017-04-21'),
(36, '2017-04-22', '2017-04-28'),
(37, '2017-04-29', '2017-05-05'),
(38, '2017-05-06', '2017-05-12');

-- --------------------------------------------------------

--
-- Stand-in structure for view `matchday_chart`
--
DROP VIEW IF EXISTS `matchday_chart`;
CREATE TABLE IF NOT EXISTS `matchday_chart` (
`matchday` int(5)
,`match_id` int(4)
);
-- --------------------------------------------------------

--
-- Table structure for table `match_result`
--

DROP TABLE IF EXISTS `match_result`;
CREATE TABLE IF NOT EXISTS `match_result` (
  `match_id` int(11) NOT NULL,
  `score1` int(11) NOT NULL,
  `score2` int(11) NOT NULL,
  `scorers` varchar(200) DEFAULT NULL,
  `point_update` tinyint(1) NOT NULL DEFAULT '0',
  UNIQUE KEY `match_id_2` (`match_id`),
  KEY `match_id` (`match_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `match_result`
--

INSERT INTO `match_result` (`match_id`, `score1`, `score2`, `scorers`, `point_update`) VALUES
(1, 0, 0, NULL, 1),
(2, 1, 1, '1', 1),
(3, 1, 5, '1|2|2|7', 1),
(4, 2, 2, '1|2|1|6', 1),
(5, 3, 0, '1|1|1|2', 1),
(6, 0, 0, NULL, 1),
(7, 0, 0, NULL, 1),
(8, 1, 1, NULL, 1),
(12, 2, 1, '280|279|116', 1);

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `player_id` int(11) NOT NULL AUTO_INCREMENT,
  `jersey` varchar(4) NOT NULL,
  `player_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `team_id` int(11) NOT NULL,
  `position` varchar(2) NOT NULL DEFAULT '?',
  PRIMARY KEY (`player_id`),
  KEY `team_id` (`team_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=699 ;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`player_id`, `jersey`, `player_name`, `team_id`, `position`) VALUES
(1, '33', 'Petr Cech', 0, 'g'),
(2, '49', 'Matt Macey', 0, 'g'),
(3, '13', 'David Ospina', 0, 'g'),
(4, '21', 'Calum Chambers', 0, 'd'),
(5, '5', 'Gabriel Paulista', 0, 'd'),
(6, '3', 'Kieran Gibbs', 0, 'd'),
(7, '24', 'Hector Beller', 0, 'd'),
(8, '6', 'Laurent Koscielny', 0, 'd'),
(9, '4', 'Per Mertesacker', 0, 'd'),
(10, '25', 'Carl Jenkinson', 0, 'd'),
(11, '16', 'Rob Holding', 0, 'd'),
(12, '18', 'Nacho Monreal', 0, 'd'),
(13, '36', 'Ismael Bennacer', 0, ''),
(14, '36', 'Krystian Bielik', 0, ''),
(15, '34', 'Francis Coquelin', 0, 'm'),
(16, '35', 'Mohamed Elneny', 0, 'm'),
(17, '20', 'Mathieu Flamini', 0, ''),
(18, '8', 'Mikel Arteta', 0, ''),
(19, '11', 'Mesut Ozil', 0, 'm'),
(20, '16', 'Aaron Ramsey', 0, 'm'),
(21, '29', 'Granit Xhaka', 0, 'm'),
(22, '54', 'Jeff Reine-Adelaide', 0, ''),
(23, '7', 'Tomas Rosicky', 0, 'm'),
(24, '19', 'Santi Cazorla', 0, 'm'),
(25, '56', 'Ben Sheaf', 0, ''),
(26, '10', 'Jack Wilshere', 0, 'm'),
(27, '28', 'Joel Campbell', 0, 'f'),
(28, '12', 'Olivier Giroud', 0, 'f'),
(29, '27', 'Serge Gnabry', 0, 'f'),
(30, '45', 'Alex Iwobi', 0, 'f'),
(31, '15', 'Alex Oxlade-Chamberlain', 0, 'f'),
(32, '17', 'Alexis Sanchez', 0, 'f'),
(33, '14', 'Theo Walcott', 0, 'f'),
(34, '23', 'Danny Welbeck', 0, 'f'),
(35, '32', 'Chuba Akpom', 0, 'f'),
(36, '22', 'Yaya Sanogo', 0, 'f'),
(37, '59', 'Chris Willock', 0, ''),
(38, '21', 'Ryan Allsop', 1, 'g'),
(39, '1', 'Artur Boruc', 1, 'g'),
(40, '23', 'Adam Federici', 1, 'g'),
(41, '45', 'Jordan Holmes', 1, 'g'),
(42, '43', 'Callum Buckley', 1, ''),
(43, '36', 'Matt Butcher', 1, ''),
(44, '3', 'Steve Cook', 1, 'd'),
(45, '25', 'Sylvain Distin', 1, ''),
(46, '5', 'Tommy Elphick', 1, ''),
(47, '2', 'Simon Francis', 1, 'd'),
(48, '37', 'Corey Jordan', 1, ''),
(49, '47', 'Jordan Lee', 1, ''),
(50, '14', 'Tyrone Mings', 1, 'd'),
(51, '42', 'Jack Simpson', 1, ''),
(52, '15', 'Adam Smith', 1, 'd'),
(53, '29', 'Rhoys Wiggins', 1, 'd'),
(54, '40', 'Stephane Zubar', 1, ''),
(55, '8', 'Harry Arter', 1, 'm'),
(56, '11', 'Charlie Daniels', 1, 'm'),
(57, '4', 'Dan Gosling', 1, 'm'),
(58, '16', 'Shaun MacDonald', 1, 'm'),
(59, '32', 'Eunan O''Kane', 1, 'm'),
(60, '7', 'Marc Pugh', 1, 'm'),
(61, '30', 'Matt Ritchie', 1, ''),
(62, '19', 'Junior Stanislas', 1, 'm'),
(63, '27', 'Sam Matthews', 1, 'm'),
(64, '43', 'Ben Whitifield', 1, 'm'),
(65, '36', 'Matt Butcher', 1, 'm'),
(66, '6', 'Andrew Surman', 1, 'm'),
(67, '10', 'Max Gradel', 1, 'm'),
(68, '44', 'Sam Surridge', 1, ''),
(69, '20', 'Benik Afobe', 1, 'f'),
(70, '28', 'Lewis Grabban', 1, 'f'),
(71, '46', 'Jordan Green', 1, ''),
(72, '12', 'Juan Iturbe', 1, ''),
(73, '50', 'Brandon Goodship', 1, 'f'),
(74, '17', 'Joshua King', 1, 'f'),
(75, '27', 'Glenn Murray', 1, ''),
(76, '9', 'Tokelo Rantie', 1, 'f'),
(77, '13', 'Callum Wilson', 1, 'f'),
(78, '1', 'Tom Heaton', 2, 'g'),
(79, '17', 'Paul Robinson', 2, 'g'),
(80, '29', 'Nick Pope', 2, 'g'),
(81, '34', 'Tom Anderson', 2, 'd'),
(82, '??', 'Luke Hendrie', 2, ''),
(83, '5', 'Michael Keane', 2, 'd'),
(84, '3', 'Daniel Lafferty', 2, 'd'),
(85, '2', 'Matthew Lowton', 2, 'd'),
(86, '23', 'Stephen Ward', 2, 'd'),
(87, '27', 'Tendayi Darikwa', 2, 'd'),
(88, '4', 'Jon Flanagan', 2, 'd'),
(89, '6', 'Ben Mee', 2, 'd'),
(90, '26', 'James Tarkowski', 2, 'd'),
(91, '28', 'Kevin Long', 2, 'd'),
(92, '38', 'Cameron Dummigan', 2, 'd'),
(93, '25', 'Johann Guomundsson', 2, 'm'),
(94, '14', 'David Jones', 2, 'm'),
(95, '11', 'Michael Kightly', 2, 'm'),
(96, '8', 'Dean Marney', 2, 'm'),
(97, '41', 'Aiden O''Neill', 2, 'm'),
(98, '37', 'Scott Arfield', 2, 'm'),
(99, '20', 'Fredrik Ulvestad', 2, 'm'),
(100, '10', 'Ashley Barnes', 2, 'f'),
(101, '21', 'George Boyd', 2, 'f'),
(102, '7', 'Andre Gray', 2, 'f'),
(103, '18', 'Rouwen Hennings', 2, 'f'),
(104, '19', 'Lukas Jutkiewicz', 2, 'f'),
(105, '24', 'Chris Long', 2, 'f'),
(106, '9', 'Sam Vokes', 2, 'f'),
(107, '32', 'Marco Amelia', 3, ''),
(108, '1', 'Asmir Begovic', 3, 'g'),
(109, '13', 'Thibaut Courtois', 3, 'g'),
(110, '', 'Jamal Blackman', 3, 'g'),
(111, '34', 'Ola Aina', 3, 'd'),
(112, '28', 'Azpilicueta', 3, 'd'),
(113, '6', 'Abdul Rahman Baba', 3, ''),
(114, '24', 'Gary Cahill', 3, 'd'),
(115, '37', 'Jake Clarke-Salter', 3, ''),
(116, '2', 'Branislav Ivanovic', 3, 'd'),
(117, '16', 'Kenedy', 3, 'm'),
(118, '20', 'Matt Miazga', 3, 'd'),
(119, '26', 'John Terry', 3, 'd'),
(120, '43', 'Fikayo Tomori', 3, 'd'),
(121, '5', 'Kurt Zouma', 3, 'd'),
(122, '4', 'Cesc Fabregas', 3, 'm'),
(123, '41', 'Charlie Colkett', 3, ''),
(124, '10', 'Eden Hazard', 3, 'm'),
(125, '36', 'Ruben Loftus-Cheek', 3, 'm'),
(126, '21', 'Nemanja Matic', 3, 'm'),
(127, '12', 'John obi Mikel', 3, 'm'),
(128, '8', 'Oscar', 3, 'm'),
(129, '22', 'Willian', 3, 'm'),
(130, '15', 'Victor Moses', 3, 'm'),
(131, '', 'Juan Cuadrado', 3, 'm'),
(132, '42', 'Tammy Abraham', 3, 'f'),
(133, '11', 'Alexandre Pato', 3, 'f'),
(134, '19', 'Diego Costa', 3, 'f'),
(135, '23', 'Michy Batshuayi', 3, 'f'),
(136, '9', 'Radamel Falcao', 3, 'f'),
(137, '38', 'Kasey Palmer', 3, ''),
(138, '11', 'Pedro', 3, 'f'),
(139, '18', 'Loic Remy', 3, 'f'),
(140, '14', 'Bertrand Traore', 3, 'f'),
(141, '13', 'Wayne Hennessey', 4, 'g'),
(142, '', 'Steve Mandanda', 4, 'g'),
(143, '1', 'Julian Speroni', 4, 'g'),
(144, '44', 'Luke Croll', 4, ''),
(145, '6', 'Scott Dann', 4, 'd'),
(146, '27', 'Damien Delaney', 4, 'd'),
(147, '19', 'Ezekiel Fryers', 4, 'd'),
(148, '4', 'Brede Hangeland', 4, ''),
(149, '34', 'Martin Kelly', 4, 'd'),
(150, '3', 'Adrian Mariappa', 4, ''),
(151, '5', 'Paddy McCarthy', 4, ''),
(152, '23', 'Pape Souare', 4, 'd'),
(153, '', 'James Tomkins', 4, 'd'),
(154, '2', 'Joel Ward', 4, 'd'),
(155, '10', 'Yala Bolasie', 4, 'm'),
(156, '7', 'Yohan Cabaye', 4, 'm'),
(157, '36', 'Luke Dreher', 4, ''),
(158, '41', 'Jake Gray', 4, ''),
(159, '15', 'Mile Jedinak', 4, 'm'),
(160, '43', 'Sullay Kaikai', 4, ''),
(161, '16', 'Joe Ledley', 4, 'm'),
(162, '14', 'Chung-yong Lee', 4, 'm'),
(163, '18', 'James McArthur', 4, 'm'),
(164, '22', 'Jordon Mutch', 4, 'm'),
(165, '42', 'Jason Puncheon', 4, 'm'),
(166, '38', 'Hiram Boateng', 4, 'm'),
(167, '26', 'Bakary Sako', 4, 'm'),
(168, '17', 'Andros Townsend', 4, 'm'),
(169, '20', 'Jonathan Williams', 4, 'm'),
(170, '25', 'Emmanuel Adebayor', 4, ''),
(171, '43', 'Keshi Anderson', 4, ''),
(172, '10', 'Yannick Bolasie', 4, 'f'),
(173, '32', 'Kwesi Appiah', 4, 'f'),
(174, '9', 'Frazier Campbell', 4, 'f'),
(175, '29', 'Marouane Chamakh', 4, ''),
(176, '16', 'Dwight Gayle', 4, ''),
(177, '??', 'Freddie Ladapo', 4, ''),
(178, '21', 'Connor Wickham', 4, 'f'),
(179, '11', 'Wilfried Zaha', 4, 'f'),
(180, '22', 'Maarten Steklenburg', 5, 'g'),
(181, '1', 'Joel', 5, 'g'),
(182, '54', 'Connor Hunt', 5, 'g'),
(183, '36', 'Jind?ich Stan?k', 5, ''),
(184, '3', 'Leighton Baines', 5, 'd'),
(185, '27', 'Tyias Browning', 5, 'd'),
(186, '23', 'Seamus Coleman', 5, 'd'),
(187, '', 'Ashley Williams', 5, 'd'),
(188, '33', 'Callum Connolly', 5, 'd'),
(189, '25', 'Ramiro Funes Mori', 5, 'd'),
(190, '32', 'Brendan Galloway', 5, 'd'),
(191, '2', 'Tony Hibbert', 5, 'd'),
(192, '30', 'Mason Holgate', 5, 'd'),
(193, '6', 'Phil Jagielka', 5, 'd'),
(194, '43', 'Jonjoe Kenny', 5, ''),
(195, '20', 'Bryan Oviedo', 5, 'd'),
(196, '38', 'Matthew Pennington', 5, 'd'),
(197, '50', 'Gethin Jones', 5, 'd'),
(198, '20', 'Ross Barkley', 5, 'm'),
(199, '18', 'Gareth Barry', 5, 'm'),
(200, '21', 'Muhamed Besic', 5, 'm'),
(201, '15', 'Tom Cleverley', 5, 'm'),
(202, '17', 'Idrissa Gueye', 5, 'm'),
(203, '26', 'Thomas Davies', 5, 'm'),
(204, '51', 'Kieran Dowell', 5, ''),
(205, '4', 'Darron Gibson', 5, 'm'),
(206, '12', 'Aaron Lennon', 5, 'm'),
(207, '16', 'James McCarthy', 5, 'm'),
(208, '11', 'Kevin Mirallas', 5, 'm'),
(209, '14', 'Oumar Niasse', 5, ''),
(210, '21', 'Leon Osman', 5, 'm'),
(211, '22', 'Steven Pienaar', 5, 'm'),
(212, '7', 'Deulofeu', 5, 'm'),
(213, '46', 'Joe Williams', 5, 'm'),
(214, '42', 'Ryan Ledson', 5, 'm'),
(215, '9', 'Arouna Kone', 5, 'f'),
(216, '10', 'Romelu Lukaku', 5, 'f'),
(217, '53', 'David Henen', 5, 'f'),
(218, '28', 'Leandro Rodriguez', 5, 'f'),
(219, '30', 'Dusan Kuciak', 6, 'g'),
(220, '1', 'Allan McGregor', 6, 'g'),
(221, '16', 'Eldin Jakupovic', 6, 'g'),
(222, '4', 'Alex Bruce', 6, 'd'),
(223, '21', 'Michael Dawson', 6, 'd'),
(224, '12', 'Harry Maguire', 6, 'd'),
(225, '2', 'Moses Odubajo', 6, 'd'),
(226, '6', 'Curtis Davies', 6, 'd'),
(227, '31', 'Brian Lenihan', 6, 'd'),
(228, '33', 'Josh Tymon', 6, 'd'),
(229, '26', 'Andrew Robertson', 6, 'd'),
(230, '11', 'Sam Clucas', 6, 'm'),
(231, '17', 'Mohamed Diame', 6, 'm'),
(232, '14', 'Jake Livermore', 6, 'm'),
(233, '7', 'David Meyler', 6, 'm'),
(234, '8', 'Tom Huddlestone', 6, 'm'),
(235, '22', 'Nick Powell', 6, 'm'),
(236, '27', 'Ahmed Elmohamady', 6, 'm'),
(237, '15', 'Shaun Maloney', 6, 'm'),
(238, '10', 'Robert Snodgrass', 6, 'm'),
(239, '25', 'Adama Diomande', 6, 'f'),
(240, '9', 'Abel Hernandez', 6, 'f'),
(241, '34', 'Calaum Jahraldo-Martin', 6, ''),
(242, '32', 'Greg Luer', 6, ''),
(243, '', 'Ron-Robert Zieler', 7, 'g'),
(244, '1', 'Kasper Schmeichel', 7, 'g'),
(245, '12', 'Ben Hamer', 7, 'g'),
(246, '33', 'Michael Cain', 7, 'm'),
(247, '30', 'Ben Chilwell', 7, 'd'),
(248, '28', 'Christian Fuchs', 7, 'd'),
(249, '6', 'Robert Huth', 7, 'd'),
(250, '2', 'Ritchie de Laet', 7, 'd'),
(251, '', 'Luis Hernandez', 7, 'd'),
(252, '18', 'Liam Moore', 7, 'd'),
(253, '5', 'Wes Morgan', 7, 'd'),
(254, '15', 'Jeffrey Schlupp', 7, 'f'),
(255, '17', 'Danny Simpson', 7, 'd'),
(256, '27', 'Marcin Wasilewski', 7, 'd'),
(257, '11', 'Marc Albrighton', 7, 'm'),
(258, '4', 'Danny Drinkwater', 7, 'm'),
(259, '24', 'Nathan Dyer', 7, 'm'),
(260, '33', 'Gokhan Inler', 7, 'm'),
(261, '8', 'Matty James', 7, 'm'),
(262, '10', 'Andy King', 7, 'm'),
(263, '26', 'Riyad Mahrez', 7, 'm'),
(264, '13', 'Daniel Amartey', 7, 'm'),
(265, '22', 'Demarai Gray', 7, 'm'),
(266, '', 'Nampalis Mendy', 7, 'm'),
(267, '39', 'Andre Olukanmi', 7, ''),
(268, '', 'Ryan Watson', 7, ''),
(269, '36', 'Joseph Dodoo', 7, ''),
(270, '7', 'Ahmed Musa', 7, 'f'),
(271, '20', 'Shinji Okazaki', 7, 'f'),
(272, '23', 'Leonardo Ulloa', 7, 'f'),
(273, '9', 'Jamie Vardy', 7, 'f'),
(274, '1', 'Loris Karius', 8, 'g'),
(275, '13', 'Alex Manninger', 8, 'g'),
(276, '22', 'Simon Mignolet', 8, 'g'),
(277, '52', 'Danny Ward', 8, ''),
(278, '18', 'Alberto Moreno', 8, 'd'),
(279, '19', 'Steven Caulker', 8, ''),
(280, '58', 'Daniel Cleary', 8, ''),
(281, '2', 'Nathaniel Clyne', 8, 'd'),
(282, '38', 'Jon Flanagan', 8, 'd'),
(283, '12', 'Joe Gomez', 8, 'd'),
(284, '51', 'Lloyd Jones', 8, ''),
(285, '3', 'Jose Enrique', 8, ''),
(286, '17', 'Ragnar Klavan', 8, 'd'),
(287, '6', 'Dejan Lovren', 8, 'd'),
(288, '32', 'Jole Matip', 8, 'd'),
(289, '57', 'Joe Maguire', 8, ''),
(290, '87', 'Conor Masterson', 8, ''),
(291, '56', 'Connor Randall', 8, 'd'),
(292, '17', 'Mamadou Sakho', 8, 'd'),
(293, '44', 'Brad Smith', 8, ''),
(294, '26', 'Tiago Ilori', 8, 'd'),
(295, '4', 'Kolo Toure', 8, 'd'),
(296, '23', 'Emre Can', 8, 'm'),
(297, '10', 'Coutinho', 8, 'm'),
(298, '41', 'Jack Dunn', 8, ''),
(299, '14', 'Jordan Henderson', 8, 'm'),
(300, '53', 'Jo?o Carlos', 8, ''),
(301, '40', 'Ryan Kent', 8, ''),
(302, '20', 'Adam Lallana', 8, 'm'),
(303, '21', 'Lucas Leiva', 8, 'm'),
(304, '7', 'James Milner', 8, 'm'),
(305, '5', 'Georginio Wijnaldum', 8, 'm'),
(306, '16', 'Marko Grujic', 8, 'm'),
(307, '', 'Luis Alberto', 8, 'm'),
(308, '', 'Cameron Brannagan', 8, 'm'),
(309, '54', 'Sheyi Ojo', 8, 'm'),
(310, '68', 'Pedro Chirivella', 8, 'm'),
(311, '', 'Adam Phillips', 8, ''),
(312, '11', 'Roberto Firmino', 8, 'f'),
(313, '46', 'Jordan Rossiter', 8, ''),
(314, '35', 'Kevin Stewart', 8, 'm'),
(315, '9', 'Christian Benteke', 8, 'f'),
(316, '19', 'Sadio Mane', 8, 'f'),
(317, '28', 'Danny Ings', 8, 'f'),
(318, '27', 'Divock Origi', 8, 'f'),
(319, '', 'Taiwo Awoniyi', 8, 'f'),
(320, '64', 'Sergi Can', 8, 'f'),
(321, '48', 'Jerome Sinclair', 8, ''),
(322, '15', 'Daniel Sturridge', 8, 'f'),
(323, '13', 'Willy Caballero', 9, 'g'),
(324, '1', 'Joe Hart', 9, 'g'),
(325, '29', 'Richard Wright', 9, ''),
(326, '53', 'Tosin Adarabioyo', 9, ''),
(327, '22', 'Gael Clichy', 9, 'd'),
(328, '24', 'John Stones', 9, 'd'),
(329, '26', 'Mart?n Demichelis', 9, ''),
(330, '61', 'James Horsfield', 9, ''),
(331, '77', 'Cameron Humphreys', 9, ''),
(332, '69', 'Jose Angelino', 9, ''),
(333, '11', 'Aleksandar Kolarov', 9, 'd'),
(334, '4', 'Vincent Kompany', 9, 'd'),
(335, '20', 'Eliaquim Mangala', 9, 'd'),
(336, '30', 'Nicolas Otamendi', 9, 'd'),
(337, '3', 'Bacary Sagna', 9, 'd'),
(338, '5', 'Pablo Zabaleta', 9, 'd'),
(339, '75', 'Aleix Garc', 9, ''),
(340, '62', 'Brandon Barker', 9, ''),
(341, '', 'Luke Brattan', 9, ''),
(342, '21', 'David Silva', 9, 'm'),
(343, '17', 'Kevin De Bruyne', 9, 'm'),
(344, '21', 'Ilkay Gundogan', 9, 'm'),
(345, '19', 'Leroy Sane', 9, 'm'),
(346, '18', 'Fabian Delph', 9, 'm'),
(347, '25', 'Fernandinho', 9, 'm'),
(348, '6', 'Fernando', 9, 'm'),
(349, '73', 'George Glendon', 9, ''),
(350, '15', 'Jes?s Navas', 9, 'm'),
(351, '76', 'Manu Garc', 9, ''),
(352, '8', 'Samir Nasri', 9, 'm'),
(353, '7', 'Raheem Sterling', 9, 'm'),
(354, '42', 'Yaya Toure', 9, 'm'),
(355, '10', 'Kun Ag?ero', 9, 'f'),
(356, '14', 'Wilfried Bony', 9, 'f'),
(357, '59', 'Bersant Celina', 9, ''),
(358, '51', 'David Faupala', 9, ''),
(359, '', 'Ptrick Roberts', 9, 'f'),
(360, '24', 'Nolito', 9, 'f'),
(361, '72', 'Kelechi Iheanacho', 9, 'f'),
(362, '1', 'De Gea', 10, 'g'),
(363, '34', 'Dean Henderson', 10, ''),
(364, '40', 'Joel Pereira', 10, ''),
(365, '32', 'Sam Johnstone', 10, 'g'),
(366, '20', 'Sergio Romero', 10, 'g'),
(367, '17', 'Daley Blind', 10, 'd'),
(368, '43', 'Cameron Borthwick-Jackson', 10, 'd'),
(369, '36', 'Matteo Darmian', 10, 'd'),
(370, '24', 'Timothy Fosu-Mensah', 10, 'd'),
(371, '4', 'Phil Jones', 10, 'd'),
(372, '37', 'Donald Love', 10, ''),
(373, '33', 'Paddy McNair', 10, 'd'),
(374, '41', 'Regan Poole', 10, ''),
(375, '49', 'Joe Riley', 10, ''),
(376, '5', 'Marcos Rojo', 10, 'd'),
(377, '3', 'Eric Bailly', 10, 'd'),
(378, '23', 'Luke Shaw', 10, 'd'),
(379, '12', 'Chris Smalling', 10, 'd'),
(380, '38', 'Axel Tuanzebe', 10, ''),
(381, '25', 'Antonio Valencia', 10, ''),
(382, '30', 'Guillermo Varela', 10, 'd'),
(383, '52', 'Ro-Shaun Williams', 10, ''),
(384, '21', 'Ander Herrera', 10, 'm'),
(385, '44', 'Andreas Pereira', 10, 'm'),
(386, '6', 'Paul Pogba', 10, 'm'),
(387, '16', 'Michael Carrick', 10, 'm'),
(388, '22', 'Henry Mkhitaryan', 0, ''),
(389, '27', 'Marouane Fellaini', 10, 'm'),
(390, '15', 'Adnan Januzaj', 10, 'm'),
(391, '35', 'Jesse Lingard', 10, 'm'),
(392, '8', 'Mata', 10, 'm'),
(393, '46', 'Joe Rothwell', 10, ''),
(394, '28', 'Morgan Schneiderlin', 10, 'm'),
(395, '31', 'Bastian Schweinsteiger', 10, 'm'),
(396, '47', 'James Weir', 10, ''),
(397, '18', 'Ashley Young', 10, 'm'),
(398, '7', 'Memphis Depay', 10, 'f'),
(399, '48', 'William Keane', 10, 'f'),
(400, '9', 'Zlatan Ibrahimovic', 10, 'f'),
(401, '11', 'Anthony Martial', 10, 'f'),
(402, '19', 'Marcus Rashford', 10, 'f'),
(403, '10', 'Wayne Rooney', 10, 'f'),
(404, '1', 'Dimitrios Konstantopoulos', 11, 'g'),
(405, '12', 'Brad Guzan', 11, 'g'),
(406, '13', 'Tomas Mejias', 11, 'g'),
(407, '26', 'V?ctor Valdes', 11, 'g'),
(408, '15', 'Alex Baptiste', 11, 'd'),
(409, '17', 'Barragan', 11, 'd'),
(410, '3', 'Gerorge Friend', 11, 'd'),
(411, '22', 'Dael Fry', 11, 'd'),
(412, '24', 'Emilio N''Sue', 11, 'd'),
(413, '4', 'Daniel Ayala', 11, 'd'),
(414, '', 'Bernardo Espinosa', 11, ''),
(415, '40', 'James Husband', 11, 'd'),
(416, '20', 'Adam Reach', 11, 'm'),
(417, '7', 'Grant Leadbitter', 11, 'm'),
(418, '16', 'Carlos de Pena', 11, 'm'),
(419, '8', 'Adam Clayton', 11, 'm'),
(420, '14', 'Marten de Roon', 11, 'm'),
(421, '23', 'Julien De Sart', 11, 'm'),
(422, '19', 'Stewart Downing', 11, 'm'),
(423, '34', 'Adam Forshaw', 11, 'm'),
(424, '10', 'alvaro Negredo', 11, 'f'),
(425, '', 'Viktor Fischer', 11, ''),
(426, '18', 'Christian Stuani', 11, 'f'),
(427, '35', 'David Nugent', 11, 'f'),
(428, '9', 'Jordan Rhodes', 11, ''),
(429, '1', 'Kelvin Davis', 12, ''),
(430, '1', 'Fraser Forster', 12, 'g'),
(431, '13', 'Alex McCarthy', 12, 'g'),
(432, '25', 'Paulo Gazzaniga', 12, ''),
(433, '22', 'Maarten Stekelenburg', 12, ''),
(434, '21', 'Ryan Bertrand', 12, 'd'),
(435, '2', 'Cedric Soares', 12, 'd'),
(436, '5', 'Florin Gardos', 12, 'd'),
(437, '6', 'Jose Fonte', 12, 'd'),
(438, '15', 'Cuco Martina', 12, 'd'),
(439, '33', 'Matt Targett', 12, 'd'),
(440, '17', 'Virgil van Dijk', 12, 'd'),
(441, '3', 'Maya Yoshida', 12, 'd'),
(442, '4', 'Jordy Clasie', 12, 'm'),
(443, '8', 'Steven Davis', 12, 'm'),
(444, '23', 'Pierre-Emile Hojberg', 12, 'm'),
(445, '27', 'Lloyd Isgrove', 12, 'm'),
(446, '38', 'Sam McQueen', 12, 'm'),
(447, '', 'Dominic Gape', 12, 'm'),
(448, '14', 'Oriol Romeu', 12, 'm'),
(449, '18', 'Harrison Reed', 12, 'm'),
(450, '11', 'Dusan Tadic', 12, 'm'),
(451, '16', 'James Ward-Prowse', 12, 'm'),
(452, '28', 'Charlie Austin', 12, 'f'),
(453, '40', 'Sam Gallagher', 12, 'f'),
(454, '20', 'Juanmi', 12, ''),
(455, '7', 'Shane Long', 12, 'f'),
(456, '49', 'Olufela Olomola', 12, ''),
(457, '9', 'Jay Rodriguez', 12, 'f'),
(458, '', 'Ryan Seager', 12, ''),
(459, '35', 'Daniel Bachmann', 13, 'g'),
(460, '1', 'Jack Butland', 13, 'g'),
(461, '24', 'Shay Given', 13, 'g'),
(462, '29', 'Jakob Haugaard', 13, 'g'),
(463, '2', 'Phil Bardsley', 13, 'd'),
(464, '20', 'Geoff Cameron', 13, 'd'),
(465, '8', 'Glen Johnson', 13, 'd'),
(466, '5', 'Mark Muniesa', 13, 'd'),
(467, '3', 'Erik Pieters', 13, 'd'),
(468, '17', 'Ryan Shawcross', 13, 'd'),
(469, '12', 'Marc Wilson', 13, 'd'),
(470, '26', 'Philipp Wollscheid', 13, 'd'),
(471, '23', 'Dionatan Teixeira', 13, 'd'),
(472, '', 'Joe Allen', 13, 'm'),
(473, '', 'Ramadan Sodhi', 13, 'm'),
(474, '16', 'Charlie Adam', 13, 'm'),
(475, '14', 'Ibrahim Afellay', 13, 'm'),
(476, '21', 'Giannelli Imbula', 13, 'm'),
(477, '7', 'Stephen Ireland', 13, 'm'),
(478, '22', 'Xherdan Shaqiri', 13, 'm'),
(479, '34', 'Oliver Shenton', 13, 'm'),
(480, '6', 'Glenn Whelan', 13, 'm'),
(481, '10', 'Marko Arnautovic', 13, 'f'),
(482, '27', 'Bojan', 13, 'f'),
(483, '25', 'Peter Crouch', 13, 'f'),
(484, '18', 'Mame Diouf', 13, 'f'),
(485, '30', 'Mohamed El Ouriachi', 13, ''),
(486, '11', 'Mato Joselu', 13, 'f'),
(487, '19', 'Jon Walters', 13, 'f'),
(488, '33', 'Steve Harper', 14, ''),
(489, '25', 'Vito Mannone', 14, 'g'),
(490, '13', 'Jordan Pickford', 14, 'g'),
(491, '32', 'Max Stryjek', 14, 'g'),
(492, '31', 'Tom Beadling', 14, 'd'),
(493, '5', 'Wes Brown', 14, 'd'),
(494, '14', 'Emmanuel Eboue', 14, ''),
(495, '2', 'Billy Jones', 14, 'd'),
(496, '15', 'Youn?s Kaboul', 14, 'd'),
(497, '23', 'Lamine Kone', 14, 'd'),
(498, '', 'Adam Matthews', 14, ''),
(499, '16', 'John O''Shea', 14, 'd'),
(500, '29', 'Valentin Roberge', 14, ''),
(501, '48', 'Josh Robson', 14, 'd'),
(502, '34', 'Tom Robson', 14, 'd'),
(503, '3', 'Patrick van Aanholt', 14, 'd'),
(504, '27', 'Jan Kirchhoff', 14, 'm'),
(505, '6', 'Lee Cattermole', 14, 'm'),
(506, '14', 'Jordi Gomez', 14, 'm'),
(507, '39', 'George Honeyman', 14, 'm'),
(508, '22', 'Wahbi Khazri', 14, 'm'),
(509, '7', 'Sebastian Larsson', 14, 'm'),
(510, '21', 'Yann M''Vila', 14, 'm'),
(511, '8', 'Jack Rodwell', 14, 'm'),
(512, '20', 'Ola Toivonen', 14, 'm'),
(513, '9', 'Fabio Borini', 14, 'f'),
(514, '18', 'Jermain Defoe', 14, 'f'),
(515, '37', 'Rees Greenwood', 14, 'f'),
(516, '17', 'Jeremain Lens', 14, 'f'),
(517, '38', 'Mikael Mandron', 14, 'f'),
(518, '10', 'Dame N''Doye', 14, ''),
(519, '41', 'Duncan Watmore', 14, 'f'),
(520, '1', '?ukasz Fabia?ski', 15, 'g'),
(521, '19', 'Mark Birighitti', 15, 'g'),
(522, '25', 'Gerhard Tremmel', 15, 'g'),
(523, '13', 'Kristoffer Nordfeldt', 15, 'g'),
(524, '29', 'Josh Vickers', 15, ''),
(525, '22', 'angel Rangel', 15, 'd'),
(526, '27', 'Kyle Bartley', 15, ''),
(527, '33', 'Federico Fernandez', 15, 'd'),
(528, '2', 'Jordi Amat', 15, 'd'),
(529, '98', 'Stephen Kingsley', 15, 'd'),
(530, '5', 'Mike van der Hoorn', 15, 'd'),
(531, '26', 'Kyle Naughton', 15, 'd'),
(532, '61', 'Joe Rodon', 15, ''),
(533, '3', 'Neil Taylor', 15, 'd'),
(534, '63', 'Ryan Blair', 15, ''),
(535, '7', 'Leon Britton', 15, 'm'),
(536, '12', 'Nathan Dyer', 15, 'm'),
(537, '14', 'Franck Tabanou', 15, 'm'),
(538, '24', 'Jack Cork', 15, 'm'),
(539, '8', 'Leroy Fer', 15, 'm'),
(540, '56', 'Jay Fulton', 15, 'm'),
(541, '21', 'Matt Grimes', 15, 'm'),
(542, '55', 'Raheem Hanley', 15, ''),
(543, '51', 'Daniel James', 15, ''),
(544, '4', 'Sung-yueng Ki', 15, 'm'),
(545, '53', 'Adam King', 15, 'm'),
(546, '20', 'Jefferson Montero', 15, 'm'),
(547, '15', 'Wayne Routledge', 15, 'm'),
(548, '30', 'Josh Sheehan', 15, 'm'),
(549, '23', 'Gylfi Sigurosson', 15, 'm'),
(550, '10', 'Andre Ayew', 15, 'f'),
(551, '58', 'Modou Barrow', 15, 'f'),
(552, '11', 'Marvin Emnes', 15, 'f'),
(553, '18', 'Bafetimbi Gomis', 15, ''),
(554, '46', 'Kenji Gorre', 15, ''),
(555, '9', 'Alberto Paloschi', 15, ''),
(556, '', 'Tom Glover', 16, ''),
(557, '1', 'Hugo Lloris', 16, 'g'),
(558, '31', 'Luke McGee', 16, 'g'),
(559, '13', 'Michel Vorm', 16, 'g'),
(560, '4', 'Toby Alderweireld', 16, 'd'),
(561, '38', 'Cameron Carter-Vickers', 16, ''),
(562, '33', 'Ben Davies', 16, 'd'),
(563, '3', 'Danny Rose', 16, 'd'),
(564, '16', 'Kieran Trippier', 16, 'd'),
(565, '5', 'Jan Vertonghen', 16, 'd'),
(566, '2', 'Kyle Walker', 16, 'd'),
(567, '43', 'Kyle Walker-Peters', 16, ''),
(568, '12', 'DeAndre Yedlin', 16, 'd'),
(569, '27', 'Kevin Wimmer', 16, 'd'),
(570, '', 'Victor Wanyama', 16, 'm'),
(571, '20', 'Dele Alli', 16, 'm'),
(572, '6', 'Nabil Bentaleb', 16, 'm'),
(573, '28', 'Tom Carroll', 16, 'm'),
(574, '19', 'Mousa Dembele', 16, 'm'),
(575, '15', 'Eric Dier', 16, 'm'),
(576, '23', 'Christian Eriksen', 16, 'm'),
(577, '11', '?rik Lamela', 16, 'm'),
(578, '8', 'Ryan Mason', 16, 'm'),
(579, '29', 'Harry Winks', 16, ''),
(580, '22', 'Nacer Chadli', 16, 'f'),
(581, '', 'Vincent Janssen', 16, 'f'),
(582, '', 'Shayon Harrison', 16, ''),
(583, '10', 'Harry Kane', 16, 'f'),
(584, '14', 'Clinton N''Jie', 16, 'f'),
(585, '25', 'Joshua Onomah', 16, ''),
(586, '7', 'Heung-min Son', 16, 'f'),
(587, '13', 'Rene Gilmartin', 17, 'g'),
(588, '1', 'Gomes', 17, 'g'),
(589, '34', 'Giedrius Arlauskis', 17, 'g'),
(590, '18', 'Costel Pantilimon', 17, 'g'),
(591, '', 'Luke Simpson', 17, ''),
(592, '16', 'Nathan Ake', 17, ''),
(593, '27', 'Essaid Belkalem', 17, 'd'),
(594, '3', 'Miguel Britos', 17, 'd'),
(595, '15', 'Craig Cathcart', 17, 'd'),
(596, '6', 'Joel Ekstrand', 17, ''),
(597, '31', 'Tommie Hoban', 17, 'd'),
(598, '30', 'Jorell Johnson', 17, ''),
(599, '2', 'Allan Nyom', 17, 'd'),
(600, '14', 'Juan Paredes', 17, 'd'),
(601, '5', 'Sebastian PrOdl', 17, 'd'),
(602, '37', 'Alfie Young', 17, ''),
(603, '22', 'Almen Abdi', 17, ''),
(604, '25', 'Jose Holebas', 17, 'm'),
(605, '39', 'Dennon Lewis', 17, 'm'),
(606, '21', 'Ikechi Anya', 17, 'm'),
(607, '8', 'Valon Behrami', 17, 'm'),
(608, '40', 'George Byers', 17, ''),
(609, '29', 'etienne Capoue', 17, 'm'),
(610, '35', 'Josh Doherty', 17, ''),
(611, '17', 'Adl?ne Guedioura', 17, 'm'),
(612, '7', 'Jurado', 17, ''),
(613, '4', 'Mario Suarez', 17, 'm'),
(614, '38', 'Mahlondo Martin', 17, ''),
(615, '', 'Sean Murray', 17, 'm'),
(616, '23', 'Ben Watson', 17, 'm'),
(617, '11', 'Nordin Amrabat', 17, 'f'),
(618, '20', 'Steven Berghuis', 17, 'f'),
(619, '9', 'Troy Deeney', 17, 'f'),
(620, '24', 'Odion Ighalo', 17, 'f'),
(621, '10', 'Matej Vydra', 17, 'f'),
(622, '36', 'Alex Jakubiak', 17, 'f'),
(623, '39', 'Dennon Lewis', 17, ''),
(624, '26', 'Bernard Mensah', 17, ''),
(625, '10', 'Mamadou Oulare', 17, ''),
(626, '1', 'Ben Foster', 18, 'g'),
(627, '13', 'Boaz Myhill', 18, 'g'),
(628, '40', 'Alex Palmer', 18, ''),
(629, '4', 'James Chester', 18, 'd'),
(630, '25', 'Craig Dawson', 18, 'd'),
(631, '6', 'Jonny Evans', 18, 'd'),
(632, '16', 'Cristian Gamboa', 18, 'd'),
(633, '23', 'Gareth McAuley', 18, 'd'),
(634, '3', 'Jonas Olsson', 18, 'd'),
(635, '15', 'Sebastien Pocognoli', 18, 'd'),
(636, '11', 'Chris Brunt', 18, 'm'),
(637, '42', 'Kyle Edwards', 18, ''),
(638, '47', 'Sam Field', 18, 'm'),
(639, '24', 'Darren Fletcher', 18, 'm'),
(640, '8', 'Craig Gardner', 18, 'm'),
(641, '7', 'James Morrison', 18, 'm'),
(642, '20', 'Alex Pritchard', 18, ''),
(643, '30', 'Sandro', 18, 'm'),
(644, '29', 'Stephane Sessegnon', 18, ''),
(645, '5', 'Claudio Yacob', 18, 'm'),
(646, '10', 'Matt Phillips', 18, 'm'),
(647, '10', 'Victor Anichebe', 18, ''),
(648, '18', 'Saido Berahino', 18, 'f'),
(649, '46', 'Shaun Donnellan', 18, ''),
(650, '17', 'Rickie Lambert', 18, 'f'),
(651, '45', 'Jonathan Leko', 18, 'm'),
(652, '14', 'James McClean', 18, 'm'),
(653, '19', 'Callum McManaman', 18, 'm'),
(654, '44', 'Tyler Roberts', 18, ''),
(655, '33', 'Salom?n Rond', 18, 'f'),
(656, '41', 'Joe Ward', 18, ''),
(657, '13', 'Adrian', 19, 'g'),
(658, '46', 'Sam Howes', 19, ''),
(659, '1', 'Darren Randolph', 19, 'g'),
(660, '34', 'Raphael Spiegel', 19, 'g'),
(661, '22', 'Sam Byram', 19, 'd'),
(662, '19', 'James Collins', 19, 'd'),
(663, '3', 'Aaron Cresswell', 19, 'd'),
(664, '33', 'Stephen Hendrie', 19, 'd'),
(665, '35', 'Reece Oxford', 19, 'd'),
(666, '25', 'Doneil Henry', 19, 'd'),
(667, '12', 'Carl Jenkinson', 19, ''),
(668, '21', 'Angelo Ogbonna', 19, 'd'),
(669, '40', 'Manny Onariase', 19, ''),
(670, '37', 'Lewis Page', 19, ''),
(671, '65', 'Josh Pask', 19, ''),
(672, '2', 'Winston Reid', 19, 'd'),
(673, '5', 'James Tomkins', 19, ''),
(674, '30', 'Michail Antonio', 19, 'm'),
(675, '41', 'Marcus Browne', 19, ''),
(676, '44', 'George Dobson', 19, ''),
(677, '8', 'Cheikhou Kouyate', 19, 'm'),
(678, '28', 'Manuel Lanzini', 19, 'm'),
(679, '62', 'Amos Nasha', 19, ''),
(680, '16', 'Mark Noble', 19, 'm'),
(681, '17', 'Joey O''Brien', 19, ''),
(682, '27', 'Dimitri Payet', 19, 'm'),
(683, '39', 'Josh Cullen', 19, 'm'),
(684, '17', 'Gokhan Tore', 19, 'm'),
(685, '4', 'Havard Nordtveit', 19, 'm'),
(686, '7', 'Sofiane Feghouli', 19, 'm'),
(687, '42', 'Matin Samuelsen', 19, 'm'),
(688, '14', 'Pedro Obiang', 19, 'm'),
(689, '4', 'Alex Song', 19, ''),
(690, '51', 'Jordan Brown', 19, ''),
(691, '9', 'Andy Carroll', 19, 'f'),
(692, '29', 'Emmanuel Emenike', 19, ''),
(693, '61', 'Nathan Mavila', 19, ''),
(694, '20', 'Victor Moses', 19, ''),
(695, '64', 'Djair Parfitt-Williams', 19, ''),
(696, '66', 'Alex Pike', 19, ''),
(697, '15', 'Diafra Sakho', 19, 'f'),
(698, '11', 'Enner Valencia', 19, 'f');

-- --------------------------------------------------------

--
-- Table structure for table `points_breakdown`
--

DROP TABLE IF EXISTS `points_breakdown`;
CREATE TABLE IF NOT EXISTS `points_breakdown` (
  `match_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `point` int(11) NOT NULL,
  UNIQUE KEY `match_id_2` (`match_id`,`user_id`),
  KEY `match_id` (`match_id`,`user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `points_breakdown`
--

INSERT INTO `points_breakdown` (`match_id`, `user_id`, `point`) VALUES
(1, 1, 20),
(1, 4, 0),
(1, 6, 0),
(2, 1, 20),
(2, 2, 20),
(2, 4, 20),
(3, 1, -60),
(3, 2, 90),
(3, 4, 80),
(3, 5, 30),
(4, 1, 80),
(4, 2, 20),
(4, 5, 80),
(4, 6, 20),
(5, 5, -40),
(6, 3, 0),
(7, 2, 70),
(7, 3, -80),
(7, 6, 0),
(8, 1, 0),
(8, 4, -50),
(12, 1, 40),
(12, 2, -20),
(12, 3, -30);

-- --------------------------------------------------------

--
-- Stand-in structure for view `points_per_cmmatchday`
--
DROP VIEW IF EXISTS `points_per_cmmatchday`;
CREATE TABLE IF NOT EXISTS `points_per_cmmatchday` (
`user_id` int(10)
,`matchday` int(5)
,`cdf_predictions` decimal(42,0)
,`cdf_points` decimal(56,0)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `points_predictions_per_matchday`
--
DROP VIEW IF EXISTS `points_predictions_per_matchday`;
CREATE TABLE IF NOT EXISTS `points_predictions_per_matchday` (
`user_id` int(10)
,`matchday` int(5)
,`predictions` bigint(21)
,`match_points` decimal(32,0)
,`bonus` bigint(11)
,`deduction` decimal(23,0)
,`points` decimal(34,0)
);
-- --------------------------------------------------------

--
-- Table structure for table `prediction`
--

DROP TABLE IF EXISTS `prediction`;
CREATE TABLE IF NOT EXISTS `prediction` (
  `prediction_id` int(5) NOT NULL AUTO_INCREMENT,
  `match_id` int(5) NOT NULL,
  `user_id` int(5) NOT NULL,
  `team1_score` int(2) NOT NULL,
  `team2_score` int(2) NOT NULL,
  `scorers` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`prediction_id`),
  UNIQUE KEY `user_id_3` (`user_id`,`match_id`),
  KEY `user_id` (`user_id`),
  KEY `match_id` (`match_id`),
  KEY `match_id_2` (`match_id`),
  KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=63 ;

--
-- Dumping data for table `prediction`
--

INSERT INTO `prediction` (`prediction_id`, `match_id`, `user_id`, `team1_score`, `team2_score`, `scorers`) VALUES
(1, 1, 1, 3, 3, NULL),
(3, 2, 2, 2, 2, NULL),
(4, 2, 1, 2, 0, '6|7'),
(6, 2, 4, 0, 1, NULL),
(7, 1, 4, 0, 2, NULL),
(8, 1, 6, 1, 1, '3'),
(9, 3, 1, 2, 0, NULL),
(10, 3, 2, 1, 5, '7'),
(12, 3, 4, 2, 5, '1|2|3'),
(15, 3, 5, 1, 2, '2|3'),
(34, 4, 2, 1, 3, '1'),
(35, 4, 6, 2, 0, '2'),
(36, 4, 1, 1, 2, '2|2|6'),
(37, 5, 5, 1, 2, ''),
(38, 4, 5, 1, 2, '1|2'),
(39, 8, 1, 2, 1, '84|6|31'),
(40, 7, 3, 2, 1, '1|2|2|5'),
(41, 8, 4, 3, 0, '2'),
(42, 6, 3, 1, 1, '6'),
(43, 7, 2, 0, 0, NULL),
(44, 7, 6, 1, 2, NULL),
(50, 8, 2, 1, 2, '73|31|7'),
(58, 8, 3, 1, 0, '73'),
(59, 12, 1, 1, 1, '91|116|279'),
(60, 12, 2, 3, 0, '95|116|114'),
(61, 12, 3, 0, 2, '279|273'),
(62, 13, 2, 3, 1, '8|26|28|387');

-- --------------------------------------------------------

--
-- Stand-in structure for view `predictions_per_matchday`
--
DROP VIEW IF EXISTS `predictions_per_matchday`;
CREATE TABLE IF NOT EXISTS `predictions_per_matchday` (
`user_id` int(11)
,`matchday` int(5)
,`match_id` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `ranking_mod`
--

DROP TABLE IF EXISTS `ranking_mod`;
CREATE TABLE IF NOT EXISTS `ranking_mod` (
  `rank` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`rank`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `ranking_mod`
--

INSERT INTO `ranking_mod` (`rank`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8);

-- --------------------------------------------------------

--
-- Stand-in structure for view `scorehistory`
--
DROP VIEW IF EXISTS `scorehistory`;
CREATE TABLE IF NOT EXISTS `scorehistory` (
`matchday` int(5)
,`match_id` int(4)
,`team1_id` int(2)
,`team2_id` int(2)
,`home` varchar(100)
,`score1` int(11)
,`score2` int(11)
,`away` varchar(100)
,`date` datetime
);
-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
CREATE TABLE IF NOT EXISTS `teams` (
  `team_id` int(5) NOT NULL,
  `team_name` varchar(100) NOT NULL,
  `stadium` varchar(200) NOT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`team_id`, `team_name`, `stadium`) VALUES
(0, 'Arsenal', 'Emirates Stadium - London'),
(1, 'Bournemouth', 'Dean Court - Bournemouth'),
(2, 'Burnley', 'Turf Moor - Burnley'),
(3, 'Chelsea', 'Stamford Bridge - London'),
(4, 'Crystal Palace', 'Selhurst Park - London'),
(5, 'Everton', 'Goodison Park'),
(6, 'Hull City', 'KC Stadium - Hull'),
(7, 'Leicester City', 'King Power Stadium - Leicester'),
(8, 'Liverpool', 'Anfield - Liverpool'),
(9, 'Manchester City', 'Etihad Stadium - Manchester'),
(10, 'Manchester United', 'Old Trafford - Manchester'),
(11, 'Middlesbrough', 'Riverside Stadium - Middlesbrough'),
(12, 'Southampton', 'St Mary''s Stadium - Southampton'),
(13, 'Stoke City', 'Bet365 Stadium - Stoke-on-Trent'),
(14, 'Sunderland', 'Stadium of Light - Sunderland'),
(15, 'Swansea City', 'Liberty Stadium - Swansea'),
(16, 'Tottenham Hotspur', 'White Hart Lane - London'),
(17, 'Watford', 'Vicarage Road - Watford'),
(18, 'West Bromwich Albion', 'The Hawthorns - West Bromwich'),
(19, 'West Ham United', 'Olympic Stadium - London');

-- --------------------------------------------------------

--
-- Stand-in structure for view `team_stats`
--
DROP VIEW IF EXISTS `team_stats`;
CREATE TABLE IF NOT EXISTS `team_stats` (
`team_id` int(5)
,`team_name` varchar(100)
,`home_matches` bigint(21)
,`away_matches` bigint(21)
,`wins_home` bigint(21)
,`wins_away` bigint(21)
,`loss_away` bigint(21)
,`loss_home` bigint(21)
,`draws_home` bigint(21)
,`draws_away` bigint(21)
,`gf_h` decimal(32,0)
,`gf_a` decimal(32,0)
,`ga_h` decimal(32,0)
,`ga_a` decimal(32,0)
,`gf` decimal(33,0)
,`ga` decimal(33,0)
,`matches` bigint(22)
,`wins` bigint(22)
,`losses` bigint(22)
,`draws` bigint(22)
,`gd` decimal(34,0)
,`points` bigint(24)
);
-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `reg_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`reg_id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`reg_id`, `user_id`, `email`, `password`) VALUES
(1, 'nitro', 'yo@yo', '123'),
(2, 'chika', 'yo', 'q'),
(3, 'dean', 'alal', 'q'),
(4, 'q', 'g', 'w'),
(5, 'seth', 'sda', 'w'),
(6, 'nitro2', 'yog', 'qw'),
(7, 'newguy', 'asa', 'q');

-- --------------------------------------------------------

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
CREATE TABLE IF NOT EXISTS `user_details` (
  `user_id` int(10) NOT NULL,
  `name` varchar(200) NOT NULL,
  `fav_club_id` int(10) NOT NULL,
  `indian` tinyint(1) NOT NULL,
  `points` int(20) NOT NULL DEFAULT '0',
  `dp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_2` (`user_id`),
  KEY `user_id` (`user_id`),
  KEY `fav_club_id` (`fav_club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_details`
--

INSERT INTO `user_details` (`user_id`, `name`, `fav_club_id`, `indian`, `points`, `dp`) VALUES
(1, 'Psycho', 0, 1, 100, NULL),
(2, 'avi', 0, 1, 180, NULL),
(3, 'dean', 7, 0, -110, NULL),
(4, 'avi', 0, 1, 50, NULL),
(5, 'SETH', 3, 0, 70, NULL),
(6, 'a', 0, 1, 20, NULL),
(7, 'shawn', 8, 0, 0, NULL);

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_points_matches`
--
DROP VIEW IF EXISTS `user_points_matches`;
CREATE TABLE IF NOT EXISTS `user_points_matches` (
`user_id` int(10)
,`points` int(20)
,`matches` bigint(21)
);
-- --------------------------------------------------------

--
-- Structure for view `distinct_matchdays`
--
DROP TABLE IF EXISTS `distinct_matchdays`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `distinct_matchdays` AS select distinct `mc`.`matchday` AS `matchday`,(select count(`mc2`.`match_id`) from `matchday_chart` `mc2` where (`mc2`.`matchday` = `mc`.`matchday`)) AS `no_of_matches` from `matchday_chart` `mc`;

-- --------------------------------------------------------

--
-- Structure for view `extra_points_table`
--
DROP TABLE IF EXISTS `extra_points_table`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `extra_points_table` AS select `u`.`reg_id` AS `user_id`,`d`.`matchday` AS `matchday`,`d`.`no_of_matches` AS `no_of_matches`,(select ceiling((`d`.`no_of_matches` / 2))) AS `cutoff`,(select count(0) from `predictions_per_matchday` `ppm` where ((`ppm`.`user_id` like `u`.`reg_id`) and (`ppm`.`matchday` like `d`.`matchday`))) AS `no_of_pdtcn`,(select (`cutoff` - `no_of_pdtcn`)) AS `def`,(select if((`def` > 0),`def`,0)) AS `deduction_coeff`,(select if((`no_of_pdtcn` = `d`.`no_of_matches`),1,0)) AS `bonus_coeff`,(select (-(50) * `deduction_coeff`)) AS `deduction`,(select (`bonus_coeff` * 100)) AS `bonus`,(select (`deduction` + `bonus`)) AS `extra_points` from (`user` `u` join `distinct_matchdays` `d`) order by `u`.`reg_id`,`d`.`matchday`;

-- --------------------------------------------------------

--
-- Structure for view `fixture_results`
--
DROP TABLE IF EXISTS `fixture_results`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `fixture_results` AS select `m`.`match_id` AS `match_id`,(select `mc`.`matchday` from `matchday_chart` `mc` where (`mc`.`match_id` = `m`.`match_id`)) AS `matchday`,`m`.`date` AS `date`,`m`.`team1_id` AS `team1_id`,`m`.`team2_id` AS `team2_id`,(select `teams`.`team_name` from `teams` where (`teams`.`team_id` like `m`.`team1_id`)) AS `home`,(select `teams`.`team_name` from `teams` where (`teams`.`team_id` like `m`.`team2_id`)) AS `away`,ifnull((select concat(`mr`.`score1`,' - ',`mr`.`score2`) from `match_result` `mr` where (`mr`.`match_id` = `m`.`match_id`)),' - ') AS `score`,(select count(0) from `points_breakdown` `pd` where (`pd`.`match_id` = `m`.`match_id`)) AS `predictions`,ifnull((select avg(`pd`.`point`) from `points_breakdown` `pd` where (`pd`.`match_id` = `m`.`match_id`)),0) AS `avg` from `match` `m`;

-- --------------------------------------------------------

--
-- Structure for view `friends`
--
DROP TABLE IF EXISTS `friends`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `friends` AS select `fr`.`user_id` AS `user_id`,`fr`.`friend_id` AS `friend_id`,(select `u`.`user_id` from `user` `u` where (`u`.`reg_id` = `fr`.`user_id`)) AS `username`,(select `u`.`user_id` from `user` `u` where (`u`.`reg_id` = `fr`.`friend_id`)) AS `friendname`,(select `ud`.`name` from `user_details` `ud` where (`ud`.`user_id` = `fr`.`user_id`)) AS `name`,(select `ud`.`name` from `user_details` `ud` where (`ud`.`user_id` = `fr`.`friend_id`)) AS `name2` from `friend_request` `fr` where (`fr`.`status` = 1);

-- --------------------------------------------------------

--
-- Structure for view `groups_overview`
--
DROP TABLE IF EXISTS `groups_overview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `groups_overview` AS select `g`.`group_id` AS `group_id`,`g`.`group_name` AS `group_name`,`g`.`matchday` AS `matchday`,(select count(`g2`.`user_id`) from `group_details` `g2` where (`g`.`group_id` = `g2`.`group_id`)) AS `members`,(select max(`gs`.`point`) from `group_stat` `gs` where (`gs`.`group_id` like `g`.`group_id`)) AS `max_points`,(select avg(`gs3`.`point`) from `group_stat` `gs3` where (`gs3`.`group_id` like `g`.`group_id`)) AS `avg_points`,(select `gs2`.`username` from `group_stat` `gs2` where ((`gs2`.`point` = `max_points`) and (`gs2`.`group_id` = `g`.`group_id`)) order by `gs2`.`predictions` desc limit 1) AS `leader` from `group` `g`;

-- --------------------------------------------------------

--
-- Structure for view `group_stat`
--
DROP TABLE IF EXISTS `group_stat`;

CREATE ALGORITHM=TEMPTABLE DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `group_stat` AS select `gd`.`group_id` AS `group_id`,`g`.`group_name` AS `group_name`,`g`.`matchday` AS `matchday`,`gd`.`user_id` AS `user_id`,`u`.`user_id` AS `username`,`pcm`.`cdf_points` AS `point`,`pcm`.`cdf_predictions` AS `predictions` from (((`group_details` `gd` join `points_per_cmmatchday` `pcm`) join `group` `g`) join `user` `u` on(((`g`.`group_id` = `gd`.`group_id`) and (`gd`.`user_id` = `pcm`.`user_id`) and (`u`.`reg_id` = `pcm`.`user_id`) and (`pcm`.`matchday` = `g`.`matchday`)))) order by `gd`.`group_id`,`pcm`.`cdf_points` desc,`pcm`.`cdf_predictions` desc,`gd`.`user_id` desc;

-- --------------------------------------------------------

--
-- Structure for view `matchday_chart`
--
DROP TABLE IF EXISTS `matchday_chart`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `matchday_chart` AS select `md`.`matchday` AS `matchday`,`m`.`match_id` AS `match_id` from (`matchday` `md` join `match` `m`) where (`m`.`date` between `md`.`startDate` and `md`.`endDate`);

-- --------------------------------------------------------

--
-- Structure for view `points_per_cmmatchday`
--
DROP TABLE IF EXISTS `points_per_cmmatchday`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `points_per_cmmatchday` AS select `p`.`user_id` AS `user_id`,`p`.`matchday` AS `matchday`,(select sum(`p2`.`predictions`) from `points_predictions_per_matchday` `p2` where ((`p`.`user_id` like `p2`.`user_id`) and (`p`.`matchday` <= `p2`.`matchday`))) AS `cdf_predictions`,(select sum(`p2`.`points`) from `points_predictions_per_matchday` `p2` where ((`p`.`user_id` like `p2`.`user_id`) and (`p`.`matchday` <= `p2`.`matchday`))) AS `cdf_points` from `points_predictions_per_matchday` `p`;

-- --------------------------------------------------------

--
-- Structure for view `points_predictions_per_matchday`
--
DROP TABLE IF EXISTS `points_predictions_per_matchday`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `points_predictions_per_matchday` AS select `u`.`user_id` AS `user_id`,`mc`.`matchday` AS `matchday`,(select count(0) from `points_breakdown` `pd` where ((`pd`.`user_id` like `u`.`user_id`) and `pd`.`match_id` in (select `mc2`.`match_id` from `matchday_chart` `mc2` where (`mc2`.`matchday` like `mc`.`matchday`)))) AS `predictions`,ifnull((select sum(`pd2`.`point`) from `points_breakdown` `pd2` where ((`pd2`.`user_id` like `u`.`user_id`) and `pd2`.`match_id` in (select `mc3`.`match_id` from `matchday_chart` `mc3` where (`mc3`.`matchday` like `mc`.`matchday`)))),0) AS `match_points`,(select `e`.`bonus` from `extra_points_table` `e` where ((`e`.`user_id` = `u`.`user_id`) and (`e`.`matchday` = `mc`.`matchday`))) AS `bonus`,(select `e`.`deduction` from `extra_points_table` `e` where ((`e`.`user_id` = `u`.`user_id`) and (`e`.`matchday` = `mc`.`matchday`))) AS `deduction`,(select ((`match_points` + `bonus`) + `deduction`)) AS `points` from (`distinct_matchdays` `mc` join `user_details` `u`) order by `u`.`user_id`,`mc`.`matchday`;

-- --------------------------------------------------------

--
-- Structure for view `predictions_per_matchday`
--
DROP TABLE IF EXISTS `predictions_per_matchday`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `predictions_per_matchday` AS select `pd`.`user_id` AS `user_id`,`mc`.`matchday` AS `matchday`,`pd`.`match_id` AS `match_id` from (`points_breakdown` `pd` join `matchday_chart` `mc` on((`pd`.`match_id` = `mc`.`match_id`))) order by `pd`.`user_id`,`mc`.`matchday`;

-- --------------------------------------------------------

--
-- Structure for view `scorehistory`
--
DROP TABLE IF EXISTS `scorehistory`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `scorehistory` AS select `mc`.`matchday` AS `matchday`,`m`.`match_id` AS `match_id`,`m`.`team1_id` AS `team1_id`,`m`.`team2_id` AS `team2_id`,(select `teams`.`team_name` from `teams` where (`teams`.`team_id` like `m`.`team1_id`)) AS `home`,`mr`.`score1` AS `score1`,`mr`.`score2` AS `score2`,(select `teams`.`team_name` from `teams` where (`teams`.`team_id` like `m`.`team2_id`)) AS `away`,`m`.`date` AS `date` from ((`match` `m` join `matchday_chart` `mc` on((`mc`.`match_id` = `m`.`match_id`))) join `match_result` `mr` on((`m`.`match_id` = `mr`.`match_id`))) order by `mc`.`matchday`;

-- --------------------------------------------------------

--
-- Structure for view `team_stats`
--
DROP TABLE IF EXISTS `team_stats`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `team_stats` AS select `t`.`team_id` AS `team_id`,`t`.`team_name` AS `team_name`,(select count(0) from `scorehistory` `sh` where (`sh`.`team1_id` = `t`.`team_id`)) AS `home_matches`,(select count(0) from `scorehistory` `sh` where (`sh`.`team2_id` = `t`.`team_id`)) AS `away_matches`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score1` > `sh`.`score2`) and (`sh`.`team1_id` like `t`.`team_id`))) AS `wins_home`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score2` > `sh`.`score1`) and (`sh`.`team2_id` like `t`.`team_id`))) AS `wins_away`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score1` > `sh`.`score2`) and (`sh`.`team2_id` like `t`.`team_id`))) AS `loss_away`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score1` < `sh`.`score2`) and (`sh`.`team1_id` like `t`.`team_id`))) AS `loss_home`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score1` = `sh`.`score2`) and (`sh`.`team1_id` like `t`.`team_id`))) AS `draws_home`,(select count(`sh`.`match_id`) from `scorehistory` `sh` where ((`sh`.`score1` = `sh`.`score2`) and (`sh`.`team2_id` like `t`.`team_id`))) AS `draws_away`,ifnull((select sum(`sh`.`score1`) from `scorehistory` `sh` where (`sh`.`team1_id` = `t`.`team_id`)),0) AS `gf_h`,ifnull((select sum(`sh`.`score2`) from `scorehistory` `sh` where (`sh`.`team2_id` = `t`.`team_id`)),0) AS `gf_a`,ifnull((select sum(`sh`.`score2`) from `scorehistory` `sh` where (`sh`.`team1_id` = `t`.`team_id`)),0) AS `ga_h`,ifnull((select sum(`sh`.`score1`) from `scorehistory` `sh` where (`sh`.`team2_id` = `t`.`team_id`)),0) AS `ga_a`,(select (`gf_a` + `gf_h`)) AS `gf`,(select (`ga_h` + `ga_a`)) AS `ga`,(select (`home_matches` + `away_matches`)) AS `matches`,(select (`wins_home` + `wins_away`)) AS `wins`,(select (`loss_home` + `loss_away`)) AS `losses`,(select (`draws_home` + `draws_away`)) AS `draws`,(select (`gf` - `ga`)) AS `gd`,(select ((`wins` * 3) + `draws`)) AS `points` from `teams` `t` order by `points` desc,`gd` desc;

-- --------------------------------------------------------

--
-- Structure for view `user_points_matches`
--
DROP TABLE IF EXISTS `user_points_matches`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_points_matches` AS select `u1`.`user_id` AS `user_id`,`u1`.`points` AS `points`,(select count(0) from `points_breakdown` `pd` where (`pd`.`user_id` like `u1`.`user_id`)) AS `matches` from `user_details` `u1` order by `u1`.`points` desc,`matches` desc;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `friend_request`
--
ALTER TABLE `friend_request`
  ADD CONSTRAINT `friend_request_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`),
  ADD CONSTRAINT `friend_request_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `user` (`reg_id`);

--
-- Constraints for table `group`
--
ALTER TABLE `group`
  ADD CONSTRAINT `group_ibfk_1` FOREIGN KEY (`matchday`) REFERENCES `matchday` (`matchday`);

--
-- Constraints for table `group_details`
--
ALTER TABLE `group_details`
  ADD CONSTRAINT `group_details_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_details_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `group_permissions`
--
ALTER TABLE `group_permissions`
  ADD CONSTRAINT `group_permissions_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_permissions_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `match`
--
ALTER TABLE `match`
  ADD CONSTRAINT `match_ibfk_1` FOREIGN KEY (`team1_id`) REFERENCES `teams` (`team_id`),
  ADD CONSTRAINT `match_ibfk_2` FOREIGN KEY (`team2_id`) REFERENCES `teams` (`team_id`);

--
-- Constraints for table `match_result`
--
ALTER TABLE `match_result`
  ADD CONSTRAINT `match_result_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `match` (`match_id`);

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`);

--
-- Constraints for table `points_breakdown`
--
ALTER TABLE `points_breakdown`
  ADD CONSTRAINT `points_breakdown_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `match` (`match_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `points_breakdown_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prediction`
--
ALTER TABLE `prediction`
  ADD CONSTRAINT `prediction_ibfk_1` FOREIGN KEY (`match_id`) REFERENCES `match` (`match_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `prediction_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`) ON UPDATE CASCADE;

--
-- Constraints for table `user_details`
--
ALTER TABLE `user_details`
  ADD CONSTRAINT `user_details_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`reg_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_details_ibfk_2` FOREIGN KEY (`fav_club_id`) REFERENCES `teams` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
