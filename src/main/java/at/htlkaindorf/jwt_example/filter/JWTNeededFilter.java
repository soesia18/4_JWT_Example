package at.htlkaindorf.jwt_example.filter;

import at.htlkaindorf.jwt_example.LoginResource;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.text.ParseException;

@JWTNeeded
@Provider
@Priority(Priorities.AUTHORIZATION)
public class JWTNeededFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String token = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            JWSObject object = JWSObject.parse(token);
            boolean verified = object.verify(new MACVerifier(LoginResource.KEY));
            if (!verified) {
                throw new Exception("unverified");
            }
            String payload = object.getPayload().toString();
            containerRequestContext.setProperty("payload", payload);
        } catch (Exception e) {
            e.printStackTrace();

            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("UNAUTHORIZED access!").build());
        }
    }
}
