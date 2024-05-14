package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.RecordNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class StudentService {
    private static final String PREFIX = "A";
    Logger logger= LoggerFactory.getLogger(StudentService.class );
    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;
    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }
    public Student createStudent(Student student) {
        logger.info("Was invoked method create with parameter");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method findStudent with id={}", id);
        logger.debug("Find student {} by id",  studentRepository.findById(id).get());
        return studentRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method editStudent");
        logger.debug("Edit student {} ",  studentRepository.save(student));
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.info("Was invoked method removeStudent with id={}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> studentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method studentsByAgeBetween");
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }
    public int getAmountOfStudents () {
        logger.info("Was invoked method getAmountOfStudents");
        return studentRepository.amountOfStudents();
    }
    public int getAverageAge () {
        logger.info("Was invoked method getAverageAge");
        return studentRepository.averageAge();
    }
    public List<Student> getLastStudents() {
        logger.info("Was invoked method getLastStudents");
        return studentRepository.getLastStudents();
    }
    public List<String> getAllStudentStartWithA() {
        logger.info("Was invoked method getAllStudentStartWith{}",
                PREFIX);
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getName().startsWith(PREFIX))
                .map(s -> s.getName().toUpperCase())
                .sorted(String::compareTo)
                .toList();
    }

    public int getAverageAgeOfStudentsFromStream() {
        logger.info("Was invoked method getAverageAgeOfStudentsFromStream");
        return (int) studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average().orElse(0);
    }


}