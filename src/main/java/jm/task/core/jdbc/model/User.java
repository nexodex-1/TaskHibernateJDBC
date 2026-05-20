package jm.task.core.jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table
public class User {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    // один конструктор для удобного создания юзеров в тестах
    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    // красивый вывод списка пользователей в консоль вместо toString()
    @Override
    public String toString() {
        return String.format("👤 ID: %-3d | Имя: %-10s | Фамилия: %-12s | Возраст: %d",
                id, name, lastName, age);
    }
}