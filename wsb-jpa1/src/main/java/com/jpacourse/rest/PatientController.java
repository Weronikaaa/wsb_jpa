// package com.jpacourse.rest.exception;


// import com.jpacourse.dto.PatientTO;
// import com.jpacourse.service.PatientService;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// class PatientController {

//     private final PatientService patientService;

//     public PatientController(PatientService patientService) {
//         this.patientService = patientService;
//     }

//     @GetMapping("/patient/{id}")
//     public PatientTO findById(@PathVariable Long id) {
//         final PatientTO patient = patientService.findById(id);
//         if(patient != null) {
//             return patient;
//         } else {
//             throw new EntityNotFoundException(id);
//         }

//     }
// }