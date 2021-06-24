-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 24, 2021 at 11:43 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `collections`
--

CREATE TABLE `collections` (
  `id` int(11) NOT NULL,
  `book_title` text DEFAULT NULL,
  `book_total` int(11) DEFAULT NULL,
  `book_current` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `collections`
--

INSERT INTO `collections` (`id`, `book_title`, `book_total`, `book_current`, `created_at`, `updated_at`) VALUES
(1, 'Harry Potter and the Philosopher\'s Stone', 5, 5, '2021-06-23 23:53:38', NULL),
(2, 'Harry Potter and the Chamber of Secrets', 5, 5, '2021-06-23 23:53:38', NULL),
(3, 'Harry Potter and the Prisoner of Azkaban', 10, 10, '2021-06-23 23:54:12', NULL),
(4, 'Harry Potter and the Goblet of Fire', 7, 7, '2021-06-23 23:54:12', NULL),
(5, 'Harry Potter and the Order of the Phoenix', 3, 3, '2021-06-23 23:55:15', NULL),
(6, 'Harry Potter and the Half-Blood Prince', 8, 8, '2021-06-23 23:55:15', NULL),
(7, 'Harry Potter and the Deathly Hallows – Part 1', 5, 5, '2021-06-23 23:55:48', NULL),
(8, 'Harry Potter and the Deathly Hallows – Part 2', 3, 3, '2021-06-23 23:55:48', NULL),
(9, 'Memulai Pemrograman Flutter', 2, 2, '2021-06-23 23:56:06', NULL),
(10, 'Algorithm and Data Structure', 3, 3, '2021-06-23 23:56:35', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `collection_id` int(11) DEFAULT NULL,
  `borrowed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `due_at` timestamp NULL DEFAULT NULL,
  `returned_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `user_id`, `collection_id`, `borrowed_at`, `due_at`, `returned_at`) VALUES
(2, 4, 10, '2021-06-24 01:32:27', '2021-07-01 01:32:27', '2021-06-24 01:36:19'),
(3, 22, 10, '2021-06-24 09:03:06', '2021-07-01 09:03:06', '2021-06-24 09:03:20');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `name` text NOT NULL,
  `address` text NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `name`, `address`, `password`, `created_at`, `updated_at`, `deleted_at`) VALUES
(4, 'asep123', 'Asep', 'Dimana atuh', '$2a$12$kgEVIz755FqkX.D8rwlqNev.6EJ9PQGIGkKGRnnF0p/RhsJGynMDa', '2021-06-24 00:24:32', '2021-06-24 09:01:16', NULL),
(21, 'testbug', 'testbug', 'testbug', '$2a$12$iRFJxXMmSIPRbxwX4eK33uG3/kCSmeM3RMowYzwQ08NFLreistumK', '2021-06-24 09:01:18', NULL, NULL),
(22, 'jajang123', 'Jajang Lucu', 'Dirumah Tercinta', '$2a$12$c3a5vwkrIaMijopnjS6TmuoOFSfbbuf68crnLUenrVN1QN/rnjeXy', '2021-06-24 09:02:23', '2021-06-24 09:07:43', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `collections`
--
ALTER TABLE `collections`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `collection_id` (`collection_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `collections`
--
ALTER TABLE `collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`collection_id`) REFERENCES `collections` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
