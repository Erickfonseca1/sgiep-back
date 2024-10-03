package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.model.Activity;
import com.sgiep.sgiep_back.services.AcitivityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcitivityService activityService;

    @Test
    public void testGetActivities() throws Exception {
        // Setup mock response from the service
        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setName("Football");

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setName("Basketball");

        given(activityService.findAll()).willReturn(Arrays.asList(activity1, activity2));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Football")))
                .andExpect(jsonPath("$[1].name", is("Basketball")));
    }

    @Test
    public void testGetActivityById() throws Exception {
        // Setup mock response from the service
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setName("Football");

        given(activityService.findById(1L)).willReturn(activity);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Football")));
    }

    @Test
    public void testCreateActivity() throws Exception {
        // Setup mock response from the service
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setName("Football");

        given(activityService.save(Mockito.any(Activity.class))).willReturn(activity);

        // JSON data for the request body
        String jsonContent = "{ \"name\": \"Football\", \"description\": \"Fun sport\" }";

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Football")));
    }

    @Test
    public void testUpdateActivity() throws Exception {
        // Setup mock response from the service
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setName("Football");

        given(activityService.findById(1L)).willReturn(activity);
        given(activityService.save(Mockito.any(Activity.class))).willReturn(activity);

        // JSON data for the request body
        String jsonContent = "{ \"name\": \"Football Updated\", \"description\": \"Updated description\" }";

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/activities/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Football")));
    }
}

