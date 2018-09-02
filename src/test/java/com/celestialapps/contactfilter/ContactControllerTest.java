package com.celestialapps.contactfilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContactfilterApplication.class})
@WebAppConfiguration
public class ContactControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getContactsTest() throws Exception {
        mockMvc.perform(get("/hello/contacts/")
                .param("nameFilter", "^A.*$"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getMongoContactsTest() throws Exception {
        mockMvc.perform(get("/hello/mongo/contacts/")
                .param("nameFilter", "^A.*$"))
                .andExpect(status().isOk())
                .andReturn();
    }

}
