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

INSERT INTO `users` (`user_id`, `age`, `description`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`, `status`) VALUES (1, 20, NULL, 'email1', 'fname1', '2020-05-25', 'login1', 'pass1', 'phone1', 0, 'Executor', 'fname1', 'Male', 'math', NULL);
INSERT INTO `users` (`user_id`, `age`, `description`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`, `status`) VALUES (2, 20, NULL, 'email2', 'fname2', '2020-05-25', 'login2', 'pass2', 'phone2', 0, 'Customer', 'fname2', 'Female', 'math', NULL);
INSERT INTO `users` (`user_id`, `age`, `description`, `email`, `firstName`, `lastActivity`, `login`, `password`, `phone`, `rating`, `role`, `secondName`, `sex`, `specialty`, `status`) VALUES (3, 20, NULL, 'email3', 'fname3', '2020-05-25', 'login3', 'pass3', 'phone3', 0, 'Admin', 'fname3', 'Other', 'math', NULL);
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

INSERT INTO `messages` (`message_id`, `date`, `text`, `time`, `from_user_id`, `to_user_id`) VALUES (1, '2020-05-25', 'test', '18:51:49', 1, 2);

INSERT INTO `message_user` (`user`, `message_id`) VALUES (2, 2);

-- /*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
-- /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
