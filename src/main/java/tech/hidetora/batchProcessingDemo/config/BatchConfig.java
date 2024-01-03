package tech.hidetora.batchProcessingDemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import tech.hidetora.batchProcessingDemo.student.Student;
import tech.hidetora.batchProcessingDemo.student.StudentProcessor;
import tech.hidetora.batchProcessingDemo.student.StudentRepository;

/**
 * @author hidetora
 * @version 1.0.0
 * @since 2022/04/18
 */

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Student> itemReader() {
        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/students.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public StudentProcessor processor() {
        return new StudentProcessor();
    }

    @Bean
    public RepositoryItemWriter<Student> itemWriter() {
        RepositoryItemWriter<Student> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(studentRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step importStep() {
        return new StepBuilder("csvImportStep", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("csvImportJob", jobRepository)
                .start(importStep())
                .build();
    }

    private LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstname", "lastname", "age");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}














