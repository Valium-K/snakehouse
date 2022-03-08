package dev.valium.snakehouse.module.basichello;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test @DisplayName("helloTest")
    public void helloTest() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk());
    }
}