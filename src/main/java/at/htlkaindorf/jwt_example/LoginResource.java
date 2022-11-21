package at.htlkaindorf.jwt_example;

import at.htlkaindorf.jwt_example.beans.LoginData;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    //has to be 32 char long
    public static final String KEY = "my-jwt-secret-is-not-long-enough";

    public String createJWT(String payload) throws JOSEException {
        JWSObject object =
                new JWSObject(
                        new JWSHeader(JWSAlgorithm.HS256),
                        new Payload(payload));

        object.sign(new MACSigner(KEY.getBytes()));
        return object.serialize();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginData loginData) {

        try {
            if (loginData.getUsername().equals("admin") &&
                    loginData.getPassword().equals("ciscodisco")) {
                String jwt = createJWT("rw");
                return Response.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();

            } else if (loginData.getUsername().equals("user") &&
                    loginData.getPassword().equals("123456")) {
                String jwt = createJWT("r");
                return Response.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
            }
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}