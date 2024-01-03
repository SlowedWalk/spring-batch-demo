package tech.hidetora.batchProcessingDemo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
