package com.celestialapps.contactfilter;


import com.celestialapps.contactfilter.Utils.RegexUtils;
import com.celestialapps.contactfilter.model.Contact;
import com.celestialapps.contactfilter.repository.ContactRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContactfilterApplication.class})
@WebAppConfiguration
public class ContactControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ContactRepository contactRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void contactPaginationRegexTest() throws Exception {
        String regex = "^.*[aei].*$";
        String notRegex = RegexUtils.convertToNotRegex(regex);

        List<Contact> contacts = new ArrayList<>();
        List<Contact> contactsTwo = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            contacts.addAll(contactRepository.findByNameRegex(notRegex, PageRequest.of(i, 30, Sort.by(Sort.Direction.ASC, "name"))));
        }

        for (int i = 0; i < 4; i++) {
            String result = mockMvc.perform(get("/hello/contacts/by-page-list")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("page", i + "")
                    .param("size", "30")
                    .param("nameFilter", regex))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();


            Type listType = new TypeToken<List<Contact>>() {}.getType();
            List<Contact> contactList = new Gson().fromJson(result, listType);

            contactsTwo.addAll(contactList);
        }

        Assert.assertEquals(contacts, contactsTwo);
    }

    @Test
    public void contactsRegexTest() throws Exception {
        String regex = "^.*[aei].*$";
        String notRegex = RegexUtils.convertToNotRegex(regex);

        List<Contact> contacts = contactRepository.findByNameRegex(notRegex);

        String result = mockMvc.perform(get("/hello/contacts/")
                .param("nameFilter", regex))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Type listType = new TypeToken<List<Contact>>() {}.getType();
        List<Contact> contactsTwo = new Gson().fromJson(result, listType);

        Assert.assertEquals(contacts, contactsTwo);
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
