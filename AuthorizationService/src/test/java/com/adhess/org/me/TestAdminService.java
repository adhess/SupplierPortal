package com.adhess.org.me;


import com.adhess.org.me.entities.Address;
import com.adhess.org.me.entities.AppUser;
import com.adhess.org.me.model.RegisterUser;
import com.adhess.org.me.service.AdminService;
import com.adhess.org.me.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AdminService.class, secure = false)
public class TestAdminService {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    public void testCreateAdminAccount() throws Exception {
        AppUser appUser = new AppUser("admin", "admin", "admin",
                "+(216) 66 666 666", "+(216) 66 666 666", "admin@gmail.com");

        String inputInString = new ObjectMapper().writeValueAsString(appUser);
        String url = "/admin/createAdminAccount";
        Mockito.when(adminService.createAdminAccount(Mockito.any(RegisterUser.class))).thenReturn(appUser);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(inputInString)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertEquals("really...: ", outputInJson, inputInString);
    }
}
