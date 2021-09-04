package cz.ptw.databasebrowser.view.rest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for integration testing of endpoint for CRUD connections operations.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConnectionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return Ok State when get On List Endpoint.")
    public void should_returnOkState_when_getOnListEndpoint() throws Exception {
        this.mockMvc.perform(get("/api/v1/connections/"))
                .andExpect(status().isOk());
    }
}
