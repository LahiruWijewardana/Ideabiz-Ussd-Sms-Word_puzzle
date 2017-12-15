-- phpMyAdmin SQL Dump
-- version 4.7.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 15, 2017 at 04:14 AM
-- Server version: 5.7.20
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `word_puzzle`
--

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `msisdn` varchar(11) NOT NULL,
  `register` enum('0','1') NOT NULL DEFAULT '0',
  `status` enum('0','1','2','3') NOT NULL DEFAULT '0',
  `last_sent` int(11) NOT NULL DEFAULT '0',
  `wins` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `msisdn`, `register`, `status`, `last_sent`, `wins`) VALUES
(1, '94776627735', '0', '0', 0, 0),
(2, '94776620417', '0', '0', 0, 0),
(3, '94766691500', '1', '0', 4, 0),
(4, '94766691501', '1', '0', 2, 0),
(6, '94777123456', '1', '0', 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Words`
--

CREATE TABLE `Words` (
  `id` int(11) NOT NULL,
  `word` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Words`
--

INSERT INTO `Words` (`id`, `word`) VALUES
(1, 'HAPPY'),
(2, 'SMARTPHONE'),
(3, 'KRISCO'),
(4, 'NOTEBOOK');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Words`
--
ALTER TABLE `Words`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Words`
--
ALTER TABLE `Words`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
