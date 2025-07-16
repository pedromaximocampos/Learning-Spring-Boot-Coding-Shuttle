package com.Week3Homework.Homework.service;


import com.Week3Homework.Homework.dto.AdmissionDto;
import com.Week3Homework.Homework.entities.AdmissionEntity;
import com.Week3Homework.Homework.entities.StudentEntity;
import com.Week3Homework.Homework.repository.AdmissionRepository;
import com.Week3Homework.Homework.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    public AdmissionService(AdmissionRepository admissionRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


    public List<AdmissionDto> getAllAdmissions() {
        List<AdmissionEntity> admissions = admissionRepository.findAllAdmissionsWithStudentsOrderedByStudentName()
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhuma admissão encontrada"));

        if (admissions.isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Nenhuma admissão encontrada");
        }

        return admissions.stream()
                .map(admission -> modelMapper.map(admission, AdmissionDto.class))
                .toList();
    }


    public AdmissionDto getAdmissionById(Long id) {
        AdmissionEntity admission = admissionRepository.findAdmissionWithStudentById(id)
                .orElseThrow(() -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Admissão não encontrada"));

        return modelMapper.map(admission, AdmissionDto.class);
    }


    public AdmissionDto createAdmission(AdmissionDto admissionDto, Long studentId) {

        StudentEntity student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Estudante não encontrado"));


        AdmissionEntity admissionEntity = modelMapper.map(admissionDto, AdmissionEntity.class);
        admissionEntity.setStudent(student);

        AdmissionEntity savedAdmission = admissionRepository.save(admissionEntity);

        return modelMapper.map(savedAdmission, AdmissionDto.class);
    }
}
