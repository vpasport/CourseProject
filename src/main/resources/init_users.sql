SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


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
