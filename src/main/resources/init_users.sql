-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Май 16 2020 г., 20:52
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
-- /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
-- /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
-- /*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `courseproject`
--

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

-- CREATE TABLE `users` (
--   `user_id` int(11) NOT NULL,
--   `lastName` varchar(255) DEFAULT NULL,
--   `login` varchar(255) NOT NULL,
--   `password` varchar(255) DEFAULT NULL,
--   `secondName` varchar(255) DEFAULT NULL
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`user_id`, `firstName`, `login`, `password`, `secondName`) VALUES (1, 'first', 'login', 'pass', 'second');
INSERT INTO `users` (`user_id`, `firstName`, `login`, `password`, `secondName`) VALUES (2, 'first2', 'login2', 'pass2', 'second2');
INSERT INTO `users` (`user_id`, `firstName`, `login`, `password`, `secondName`) VALUES (3, 'first3', 'login3', 'pass3', 'second3');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);
COMMIT;

-- /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
-- /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
