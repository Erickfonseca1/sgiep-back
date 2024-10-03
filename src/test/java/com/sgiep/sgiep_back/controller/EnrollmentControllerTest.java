package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Enrollment;
import com.sgiep.sgiep_back.services.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    // Teste para listar todas as inscrições
    @Test
    public void testGetAllEnrollments() throws Exception {
    // Mockando o retorno do serviço com uma lista de objetos Enrollment
    Enrollment enrollment1 = new Enrollment();
    Enrollment enrollment2 = new Enrollment();

    given(enrollmentService.findAll()).willReturn(Arrays.asList(enrollment1, enrollment2));

    // Fazendo a requisição GET e verificando a resposta
    mockMvc.perform(get("/api/enrollments"))
            .andExpect(status().isOk());
    }

    // Teste para erro ao tentar cancelar uma inscrição inexistente
    @Test
    public void testCancelEnrollmentNotEnrolled() throws Exception {
        // Mockando o retorno de um cidadão que não está inscrito
        given(enrollmentService.cancelEnrollment(1L, 1L)).willReturn(false);

        // Fazendo a requisição POST e verificando a resposta
        mockMvc.perform(post("/api/enrollments/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"activityId\": 1, \"citizenId\": 1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Citizen is not enrolled in this activity."));
    }

}

