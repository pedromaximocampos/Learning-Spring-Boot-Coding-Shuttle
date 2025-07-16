package com.Week3Homework.Homework.service;


import com.Week3Homework.Homework.dto.StudentDto;
import com.Week3Homework.Homework.dto.StudentWithSubjectsDto;
import com.Week3Homework.Homework.entities.ProfessorEntity;
import com.Week3Homework.Homework.entities.StudentEntity;
import com.Week3Homework.Homework.entities.SubjectEntity;
import com.Week3Homework.Homework.repository.StudentRepository;
import com.Week3Homework.Homework.repository.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class StudentService {

    private final SubjectRepository SubjectRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    public StudentService(SubjectRepository subjectRepository, ModelMapper modelMapper, StudentRepository studentRepository) {
        this.SubjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }


    public List<StudentDto> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhum aluno encontrado");
        }

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
    }


    public StudentWithSubjectsDto getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        return modelMapper.map(student, StudentWithSubjectsDto.class);
    }


    public StudentDto createStudent(StudentDto studentDto) {
        StudentEntity studentEntity = modelMapper.map(studentDto, StudentEntity.class);
        StudentEntity savedStudent = studentRepository.save(studentEntity);
        return modelMapper.map(savedStudent, StudentDto.class);
    }

    public StudentWithSubjectsDto assignSubjectsToStudent(Long id, List<Long> subjectIds) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        List<SubjectEntity> subjects = SubjectRepository.findAllById(subjectIds);
        if (subjects.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhuma matéria encontrada com os IDs fornecidos");
        }

        for( SubjectEntity subject : subjects) {
            ProfessorEntity professor = subject.getProfessor();
            if(professor != null){
                student.getProfessors().add(professor);
            }
            student.getSubjects().add(subject);
        }

        StudentEntity updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentWithSubjectsDto.class);
    }


}
