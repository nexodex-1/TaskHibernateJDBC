package jm.task.core.jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Byte age;

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("👤 ID: %-1d | Имя: %-10s | Фамилия: %-12s | Возраст: %d",
                id, name, lastName, age);
    }
}
