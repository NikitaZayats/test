package com.alpha.test;

import com.alpha.test.entity.Client;
import com.alpha.test.entity.ClientConstants;
import com.alpha.test.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService service;


    @Test
    void registerClient() throws Exception {
        Client client = new Client(1, ClientConstants.LOW_RISK);

        mockMvc.perform(post("/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());

        Client getClient = service.getById(1);
        assertThat(getClient.getRiskProfile()).isEqualTo(ClientConstants.LOW_RISK);

    }
    @Test
    void testLevelRisks() throws Exception {
        Client client1 = new Client(1, ClientConstants.LOW_RISK);
        Client client2 = new Client(2, ClientConstants.LOW_RISK);
        Client client3 = new Client(3, ClientConstants.NORMAL_RISK);
        Client client4 = new Client(4, ClientConstants.HIGH_RISK);

        service.newClient(client1);
        service.newClient(client2);
        service.newClient(client3);
        service.newClient(client4);

        int[] array = new int[]{1, 3, 4};

        mockMvc.perform(post("/levelRisks")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(array)))
                .andExpect(status().isOk());

        for (int i = 0; i < array.length; i++) {
            Client byId = service.getById(array[i]);
            assertThat(byId.getRiskProfile()).isEqualTo(ClientConstants.HIGH_RISK);
        }
        Client byId = service.getById(2);
        assertThat(byId.getRiskProfile()).isEqualTo(ClientConstants.LOW_RISK);

    }

}
