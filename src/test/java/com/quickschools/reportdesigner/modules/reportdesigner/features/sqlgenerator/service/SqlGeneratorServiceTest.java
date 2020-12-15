package com.quickschools.reportdesigner.modules.reportdesigner.features.sqlgenerator.service;

import com.quickschools.reportdesigner.entities.Field;
import com.quickschools.reportdesigner.entities.Join;
import com.quickschools.reportdesigner.shares.services.EntityLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SqlGeneratorServiceTest {

    @Autowired
    SqlGeneratorService sqlGeneratorService;

    @Test
    void generate() throws Exception {

        // Test One Entity (Student)
        testOneEntity();

        // Test One Join (Student, Grade)
        testJoinStudentAndGrade();

        // Test Two Joins (Student, Grade, Lesson)
        testJoinStudentAndGradeAndLesson();
    }

    private void testOneEntity() throws Exception {
        List<Field> fields = EntityLookupService.getInstance()
                .getFieldsByEntityNameAndFieldsName("Student", Arrays.asList("Name", "Gender"));

        String generate = sqlGeneratorService.generate(fields, Arrays.asList());
        System.out.println(generate);
        assertEquals("select student.name, student.gender from student;", generate);
    }

    private void testJoinStudentAndGrade() throws Exception {
        List<Field> joinFields = EntityLookupService.getInstance()
                .getFieldsByEntityNameAndFieldsName("Student", Arrays.asList("Name", "Gender"));
        joinFields.addAll(
                EntityLookupService.getInstance()
                        .getFieldsByEntityNameAndFieldsName("Grade", Arrays.asList("Name"))
        );

        Join join = EntityLookupService.getInstance()
                .getJoin("Student", "ID", "Grade", "StudentId");

        String joinGenerate = sqlGeneratorService.generate(joinFields, Arrays.asList(join));
        System.out.println(joinGenerate);
        assertEquals("select student.name, student.gender, grade.name from student LEFT JOIN grade ON student.id = grade.student_id;", joinGenerate);
    }

    private void testJoinStudentAndGradeAndLesson() throws Exception {
        List<Field> joinFields = EntityLookupService.getInstance()
                .getFieldsByEntityNameAndFieldsName("Student", Arrays.asList("Name", "Gender"));
        joinFields.addAll(
                EntityLookupService.getInstance()
                        .getFieldsByEntityNameAndFieldsName("Grade", Arrays.asList("Name"))
        );
        joinFields.addAll(
                EntityLookupService.getInstance()
                        .getFieldsByEntityNameAndFieldsName("Lesson", Arrays.asList("Title"))
        );

        Join joinStudentGrade = EntityLookupService.getInstance()
                .getJoin("Student", "ID", "Grade", "StudentId");

        Join joinGradeLesson = EntityLookupService.getInstance()
                .getJoin("Grade", "LessonId", "Lesson", "ID");

        String joinGenerate = sqlGeneratorService.generate(joinFields, Arrays.asList(joinStudentGrade, joinGradeLesson));
        System.out.println(joinGenerate);
        assertEquals("select student.name, student.gender, grade.name, lesson.title from student LEFT JOIN grade ON student.id = grade.student_id JOIN lesson ON grade.lesson_id = lesson.id;", joinGenerate);
    }
}
