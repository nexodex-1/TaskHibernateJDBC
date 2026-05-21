package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jm.task.core.jdbc.util.Util;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            userService.createUsersTable();

            userService.saveUser("Илья", "Бурнос", (byte) 22);
            userService.saveUser("Даниил", "Савченков", (byte) 26);
            userService.saveUser("Дмитрий", "Филиппов", (byte) 34);
            userService.saveUser("Екатерина", "Садовьякова", (byte) 25);

            log.info("--- Начинаем чтение пользователей из БД через Hibernate ---");

            List<User> allUsers = userService.getAllUsers();
            for (User user : allUsers) {
                System.out.println(user);
            }

            userService.cleanUsersTable();
            userService.dropUsersTable();
        } finally {
            Util.closeSessionFactory();
        }
    }
}
