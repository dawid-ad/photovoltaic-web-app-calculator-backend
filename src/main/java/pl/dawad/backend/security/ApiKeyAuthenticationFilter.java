package pl.dawad.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final String apiKey;

    public ApiKeyAuthenticationFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestApiKey = getApiKeyFromRequest(request);

        if (StringUtils.hasText(requestApiKey) && apiKey.equals(requestApiKey)) {
            ApiKeyAuthenticationToken authentication = new ApiKeyAuthenticationToken(requestApiKey);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            authentication.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    "admin-api-user", null, List.of(new SimpleGrantedAuthority("ROLE_API_KEY"))
            ));
        }

        filterChain.doFilter(request, response);
    }

    private String getApiKeyFromRequest(HttpServletRequest request) {
        return request.getHeader("X-API-Key");
    }
}

