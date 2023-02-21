package CalculatorWebApi;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.DeferredAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Base64;

public class DummyBasicAuthenticator extends BasicAuthenticator
{
    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory)
    {
        HttpServletRequest request = (HttpServletRequest)req;
        String credentials = request.getHeader(HttpHeader.AUTHORIZATION.asString());

        if (!mandatory)
            return new DeferredAuthentication(this);

        if (credentials != null)
        {
            int space = credentials.indexOf(' ');
            if (space > 0)
            {
                String method = credentials.substring(0, space);
                if ("basic".equalsIgnoreCase(method))
                {
                    credentials = credentials.substring(space + 1);
                    Charset charset = getCharset();
                    if (charset == null)
                        charset = StandardCharsets.ISO_8859_1;
                    credentials = new String(Base64.getDecoder().decode(credentials), charset);
                    int i = credentials.indexOf(':');
                    if (i > 0)
                    {
                        String username = credentials.substring(0, i);
                        String password = credentials.substring(i + 1);

                        if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin"))
                        {
                            UserIdentity user = new DummyUserIdentity();
                            return new UserAuthentication(getAuthMethod(), user);
                        }
                    }
                }
            }
        }

        return Authentication.UNAUTHENTICATED;
    }

    private static class DummyUserIdentity implements UserIdentity {

        @Override
        public Subject getSubject() {
            return new Subject();
        }

        @Override
        public Principal getUserPrincipal() {
            return new UserPrincipal() {
                @Override
                public String getName() {
                    return "admin";
                }
            };
        }

        @Override
        public boolean isUserInRole(String s, Scope scope) {
            return true;
        }
    }
}
