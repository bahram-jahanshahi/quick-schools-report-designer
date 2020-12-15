package com.quickschools.reportdesigner.shares.services;

import com.quickschools.reportdesigner.entities.Entity;
import com.quickschools.reportdesigner.entities.Field;
import com.quickschools.reportdesigner.entities.Join;
import com.quickschools.reportdesigner.entities.enums.CardinalityEnum;
import com.quickschools.reportdesigner.entities.enums.FieldTypeEnum;
import com.quickschools.reportdesigner.entities.enums.JoinTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityLookupService {

    private static EntityLookupService entityLookupService = null;

    private Set<Entity> entities;
    private Set<Field> fields;
    private Set<Join> joins;

    private EntityLookupService() {
        init();
    }

    public static EntityLookupService getInstance() {
        if (entityLookupService == null) {
            return new EntityLookupService();
        }
        return entityLookupService;
    }

    // Initialize data model
    private void init() {
        // Student
        Entity student = new Entity("Student", "student");
        Field studentId = new Field("ID", "id", FieldTypeEnum.Integer, false, student);
        Field studentName = new Field("Name", "name", FieldTypeEnum.String, false, student);
        Field studentGender = new Field("Gender", "gender", FieldTypeEnum.String, false, student);
        // Lesson
        Entity lesson = new Entity("Lesson", "lesson");
        Field lessonId = new Field("ID", "id", FieldTypeEnum.Integer, false, lesson);
        Field lessonTitle = new Field("Title", "title", FieldTypeEnum.String, false, lesson);
        // Grade
        Entity grade = new Entity("Grade", "grade");
        Field gradeId = new Field("ID", "id", FieldTypeEnum.Integer, false, grade);
        Field gradeName = new Field("Name", "name", FieldTypeEnum.String, false, grade);
        Field foreignStudentId = new Field("StudentId", "student_id", FieldTypeEnum.Integer, false, grade);
        Field foreignLessonId = new Field("LessonId", "lesson_id", FieldTypeEnum.Integer, false, grade);


        // Join (One student has zero to many grades)
        Join studentToGrade = new Join(studentId, foreignStudentId, CardinalityEnum.OneToMany, JoinTypeEnum.LeftJoin);
        // Join (Grade has one lesson)
        Join gradeToLesson = new Join(foreignLessonId, lessonId, CardinalityEnum.ManyToOne, JoinTypeEnum.FullJoin);

        // add entities, field and joins into memory data structure
        entities = Set.of(student, grade, lesson);
        fields = Set.of(studentId, studentName, studentGender, gradeId, gradeName, foreignStudentId, lessonId, lessonTitle);
        joins = Set.of(studentToGrade, gradeToLesson);
    }

    public List<Field> getFieldsByEntityNameAndFieldsName(String entityName, List<String> fieldNames) throws Exception {
        Entity entity = entities
                .stream()
                .filter(e -> e.getName().equals(entityName))
                .findFirst()
                .orElseThrow(() -> new Exception("Entity name doesn't exist: " + entityName));
        List<Field> result = new ArrayList<>();
        for (String fieldName: fieldNames) {
            if (fields.stream().anyMatch(field -> field.getName().equals(fieldName) && field.getEntity().getName().equals(entity.getName()))) {
                result.add(fields.stream().filter(field -> field.getName().equals(fieldName) && field.getEntity().getName().equals(entity.getName())).findFirst().get());
            }
        }
        return result;
    }

    public Join getJoin(String sourceEntityName, String sourcePrimaryKeyName, String destinationEntityName, String destinationPrimaryKeyName) throws Exception {
        return joins
                .stream()
                .filter(join -> join.getSourcePrimaryKey().getName().equals(sourcePrimaryKeyName))
                .filter(join -> join.getSourcePrimaryKey().getEntity().getName().equals(sourceEntityName))
                .filter(join -> join.getDestinationPrimaryKey().getName().equals(destinationPrimaryKeyName))
                .filter(join -> join.getDestinationPrimaryKey().getEntity().getName().equals(destinationEntityName))
                .findFirst()
                .orElseThrow(() -> new Exception("Join doesn't exist!"));
    }
}
