-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Май 24 2020 г., 09:16
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.4.1

-- SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
-- SET AUTOCOMMIT = 0;
-- START TRANSACTION;
-- SET time_zone = "+00:00";


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
--   `age` int(11) DEFAULT NULL,
--   `email` varchar(255) NOT NULL,
--   `firstName` varchar(255) DEFAULT NULL,
--   `lastActivity` date DEFAULT NULL,
--   `login` varchar(255) NOT NULL,
--   `password` varchar(255) DEFAULT NULL,
--   `phone` varchar(255) NOT NULL,
--   `rating` double DEFAULT NULL,
--   `role` varchar(255) DEFAULT NULL,
--   `secondName` varchar(255) DEFAULT NULL,
--   `sex` varchar(255) DEFAULT NULL,
--   `specialty` varchar(255) DEFAULT NULL
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`user_id`, `age`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`) VALUES (1, 20, 'email', 'name', '2020-05-24', 'login', 'pass', 'phone', 0, 'Customer', 'name', 'Other', 'math');
INSERT INTO `users` (`user_id`, `age`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`) VALUES (2, 22, 'email2', 'name2', '2020-05-24', 'login2', 'pass2', 'phone2', 0, 'Executor', 'name2', 'male', 'math');
INSERT INTO `users` (`user_id`, `age`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`) VALUES (3, 21, 'email3', 'name3', '2020-05-24', 'login3', 'pass3', 'phone3', 0, 'Admin', 'name3', 'female', 'math');
--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `users`
--
-- ALTER TABLE `users`
--   ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users` MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

-- /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
-- /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
