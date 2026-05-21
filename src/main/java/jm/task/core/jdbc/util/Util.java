package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Slf4j
public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/IT_Mentor";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "";

    private static SessionFactory sessionFactory;

    static {
        try {
            ((Logger) LoggerFactory.getLogger("org.hibernate")).setLevel(Level.ERROR);
            ((Logger) LoggerFactory.getLogger("org.jboss.logging")).setLevel(Level.ERROR);
        } catch (Exception e) {
            System.err.println("Не удалось программно отключить логи Hibernate: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Ошибка при установке JDBC соединения с БД", e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                log.info("Hibernate SessionFactory успешно инициализирована");

            } catch (HibernateException e) {
                log.error("Ошибка инициализации Hibernate SessionFactory", e);
                throw new ExceptionInInitializerError("Не удалось создать SessionFactory: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            try {
                sessionFactory.close();
                log.info("Hibernate SessionFactory успешно закрыта");
            } catch (HibernateException e) {
                log.error("Ошибка при закрытии Hibernate SessionFactory: ", e);
            }
        }
    }
}
