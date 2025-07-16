package com.Week3Homework.Homework.service;


import com.Week3Homework.Homework.dto.SubjectDto;
import com.Week3Homework.Homework.dto.SubjectWithProfessorDto;
import com.Week3Homework.Homework.entities.ProfessorEntity;
import com.Week3Homework.Homework.entities.SubjectEntity;
import com.Week3Homework.Homework.repository.ProfessorRepository;
import com.Week3Homework.Homework.repository.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;


    public SubjectService(SubjectRepository subjectRepository, ProfessorRepository professorRepositor, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepositor;
        this.modelMapper = modelMapper;
    }


    public List<SubjectWithProfessorDto> getAllSubjects() {
        List<SubjectEntity> subjects = subjectRepository.findAll();

        if(subjects.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhuma matéria encontrada");
        }

        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectWithProfessorDto.class))
                .toList();
    }

    public SubjectWithProfessorDto getSubjectById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        return modelMapper.map(subject, SubjectWithProfessorDto.class);
    }

    public SubjectDto createSubject(SubjectDto subjectDto) {
        SubjectEntity subjectEntity = modelMapper.map(subjectDto, SubjectEntity.class);
        SubjectEntity savedSubject = subjectRepository.save(subjectEntity);
        return modelMapper.map(savedSubject, SubjectDto.class);
    }


    public ResponseEntity<Object> deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Matéria não encontrada");
        }
        subjectRepository.deleteById(id);
        return ResponseEntity.ok("Matéria deletada com sucesso");
    }


    public SubjectWithProfessorDto assignProfessorToSubject(Long subjectId, Long professorId) {
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        ProfessorEntity professorEntity = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Professor não encontrado"));

        subjectEntity.setProfessor(professorEntity);
        SubjectEntity updatedSubject = subjectRepository.save(subjectEntity);
        return modelMapper.map(updatedSubject, SubjectWithProfessorDto.class);
    }

    public List<SubjectDto> getSubjectsByProfessorId(Long professorId) {
        if(professorRepository.existsById(professorId)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Professor não encontrado");
        }

        List<SubjectEntity> subjects = subjectRepository.findSubjectsWithProfessorById(professorId);

        if (subjects.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhuma matéria encontrada para este professor");
        }

        return subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class))
                .toList();
    }
}
