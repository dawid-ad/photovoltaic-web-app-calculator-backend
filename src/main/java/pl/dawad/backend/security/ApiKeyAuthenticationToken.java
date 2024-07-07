package pl.dawad.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final transient Object principal;

    public ApiKeyAuthenticationToken(String apiKey) {
        super(null);
        this.principal = apiKey;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
