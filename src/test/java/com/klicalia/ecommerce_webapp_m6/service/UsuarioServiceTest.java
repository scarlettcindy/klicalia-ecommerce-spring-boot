package com.klicalia.ecommerce_webapp_m6.service;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void existeEmail_deberiaRetornarTrue_siElEmailYaEstaRegistrado() {
        when(usuarioRepository.findByEmail("cliente@test.com"))
                .thenReturn(Optional.of(new Usuario()));

        boolean resultado = usuarioService.existeEmail("cliente@test.com");

        assertTrue(resultado);
    }

    @Test
    void existeEmail_deberiaRetornarFalse_siElEmailNoExiste() {
        when(usuarioRepository.findByEmail("nuevo@test.com"))
                .thenReturn(Optional.empty());

        boolean resultado = usuarioService.existeEmail("nuevo@test.com");

        assertFalse(resultado);
    }

    @Test
    void registrarCliente_deberiaEncriptarPasswordYAsignarRolClient() {
        when(passwordEncoder.encode("miPasswordPlano")).thenReturn("HASH_SIMULADO");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.registrarCliente(
                "Ana", "Gomez", "ana@test.com", "miPasswordPlano");

        assertEquals("CLIENT", resultado.getRol());
        assertEquals("HASH_SIMULADO", resultado.getPassword());
        assertEquals("ana@test.com", resultado.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
    }
}