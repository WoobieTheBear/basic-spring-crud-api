package ch.black.util.security;
import ch.black.util.security.auth.entities.AuthEntity;
import ch.black.util.security.auth.services.AuthEntityService;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class BlackSuccessHandler implements AuthenticationSuccessHandler {

    private AuthEntityService entityService;

    public BlackSuccessHandler(AuthEntityService injectedService) {
        this.entityService = injectedService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException {

        String entityName = authentication.getName();
        AuthEntity entity = entityService.findByName(entityName);

        HttpSession session = request.getSession();
        session.setAttribute("entity", entity);

        response.sendRedirect(request.getContextPath() + "/");
    }
    
}
