package tech.hidetora.batchProcessingDemo.student;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */
public class StudentProcessor implements ItemProcessor<Student, Student> {
    /**
     * Process the provided item, returning a potentially modified or new item for
     * continued processing. If the returned result is {@code null}, it is assumed that
     * processing of the item should not continue.
     * <p>
     * A {@code null} item will never reach this method because the only possible sources
     * are:
     * <ul>
     * <li>an {@link ItemReader} (which indicates no more items)</li>
     * <li>a previous {@link ItemProcessor} in a composite processor (which indicates a
     * filtered item)</li>
     * </ul>
     *
     * @param item to be processed, never {@code null}.
     * @return potentially modified or new item for continued processing, {@code null} if
     * processing of the provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public Student process(Student item) throws Exception {
        return null;
    }
}
