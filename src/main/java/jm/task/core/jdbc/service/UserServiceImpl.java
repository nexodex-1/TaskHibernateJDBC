package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.NoArgsConstructor;

import java.util.List;

@Slf4j
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoHibernateImpl();

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
        log.info("{}Сервис: вызвана команда создания таблицы пользователей{}", GREEN, RESET);
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
        log.info("{}Сервис: вызвана команда удаления таблицы пользователей{} ", RED, RESET);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        log.info("{}Сервис: успешно сохранен пользователь: {} {}{}", GREEN, name, lastName, RESET);
    }

    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
        log.info("{}Сервис: удален пользователь с id: {}{}", YELLOW, id, RESET);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("{}Сервис: вызвана команда получения списка всех пользователей{}", CYAN, RESET);
        return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        log.info("{}Сервис: вызвана команда очистки таблицы пользователей{}", YELLOW, RESET);
    }
}