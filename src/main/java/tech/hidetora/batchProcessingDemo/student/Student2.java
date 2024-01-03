package tech.hidetora.batchProcessingDemo.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Getter
@Setter
@Entity
public class Student2 {
    @Id @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    private int age;

}
