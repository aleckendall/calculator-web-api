package CalculatorWebApp;

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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;

public class DummyAuthenticator extends BasicAuthenticator
{
    @Override
    public Authentication validateRequest(ServletRequest req, ServletResponse res, boolean mandatory) throws ServerAuthException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String credentials = request.getHeader(HttpHeader.AUTHORIZATION.asString());

        try
        {
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

            if (DeferredAuthentication.isDeferred(response))
                return Authentication.UNAUTHENTICATED;

            String value = "basic realm=\"" + _loginService.getName() + "\"";
            Charset charset = getCharset();
            if (charset != null)
                value += ", charset=\"" + charset.name() + "\"";
            response.setHeader(HttpHeader.WWW_AUTHENTICATE.asString(), value);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return Authentication.SEND_CONTINUE;
        }
        catch (IOException e)
        {
            throw new ServerAuthException(e);
        }
    }

    private static class DummyUserIdentity implements UserIdentity {

        @Override
        public Subject getSubject() {
            return null;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public boolean isUserInRole(String s, Scope scope) {
            return true;
        }
    }
}
