package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age SMALLINT NOT NULL)";

            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            log.info("Таблица users успешно создана");
        } catch (HibernateException e) {
            log.error("Ошибка при создании таблицы через Hibernate: ", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            log.info("Таблица users успешно удалена");
        } catch (HibernateException e) {
            log.error("Ошибка при удалении таблицы через Hibernate: ", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
            log.info("User {} {} успешно добавлен в базу данных", name, lastName);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при сохранении пользователя: ", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                log.info("User с id {} успешно удален", id);
            } else {
                log.warn("User с id {} не найден в базе данных", id);
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при удалении пользователя по id {}: ", id, e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("from User", User.class).list();
            log.info("Успешно получено {} пользователей из БД", users.size());
        } catch (HibernateException e) {
            log.error("Ошибка при получении всех пользователей: ", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();

            transaction.commit();
            log.info("Таблица users успешно очищена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Ошибка при очистке таблицы: ", e);
        }
    }
}
