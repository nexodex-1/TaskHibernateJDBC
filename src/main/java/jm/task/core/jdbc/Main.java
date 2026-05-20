package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // объект сервиса для управления пользователями
        UserService userService = new UserServiceImpl();

        // создание таблицы пользователей
        userService.createUsersTable();

        // доабавление пользователей в БД
        userService.saveUser("Илья", "Бурнос", (byte) 20);
        userService.saveUser("Даниил", "Савченко", (byte) 19);
        userService.saveUser("Дмитрий", "Филиппов", (byte) 21);
        userService.saveUser("Екатерина", "Садовьякова", (byte) 25);

        log.info("--- Все пользователи успешно добавлены ---");

        // получение пользователей из БД и вывод
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }

        // очистка таблицы пользователей
        userService.cleanUsersTable();

        // удаление таблицы из БД
        userService.dropUsersTable();
    }
}