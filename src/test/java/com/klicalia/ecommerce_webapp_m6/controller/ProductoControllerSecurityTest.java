package com.klicalia.ecommerce_webapp_m6.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accesoSinLogin_deberiaRedirigirALogin() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "cliente@test.com", roles = {"CLIENT"})
    void accesoConRolClient_deberiaSerRechazado() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
    void accesoConRolAdmin_deberiaSerPermitido() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk());
    }
}