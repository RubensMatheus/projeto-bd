package br.ufrn.imd.bd.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException, ServletException, IOException {
        request.setAttribute("errorMessage", "Você não tem permissão para acessar esta página!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login?denied=true");
        dispatcher.forward(request, response);
    }
}