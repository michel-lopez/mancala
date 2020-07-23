package com.tealorange.mancala.service;

import com.tealorange.mancala.MancalaBackendApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MancalaBackendApplication.class)
@Disabled
public class GameControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GameService service;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new GameController(service)).build();
    }

    @Test
    public void test() throws Exception {
        mvc.perform(get("/newgame")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
