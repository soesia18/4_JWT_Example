package at.htlkaindorf.jwt_example;

import at.htlkaindorf.jwt_example.filter.JWTNeeded;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;

@Path("/test")
public class TestResource {

    @Context
    HttpServletRequest request;

    @Context
    ContainerRequestContext cre;

    @GET
    @JWTNeeded
    public Response getData() {
        /*String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            JWSObject object = JWSObject.parse(token);
            boolean verified = object.verify(new MACVerifier(LoginResource.KEY));

            if (verified) {*/
                return Response.ok(cre.getProperty("payload")).build();
           /* } else{
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* if (token.equals("rw")) {
            return Response.ok("Your are admin!").build();
        } else if (token.equals("r")) {
            return Response.ok("Your are normal!").build();
        }*/

       // return Response.status(Response.Status.UNAUTHORIZED).build();

    }

    @GET
    @Path("/admin")
    @JWTNeeded
    public Response getAdminData () {
        /*String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            JWSObject object = JWSObject.parse(token);
            boolean verified = object.verify(new MACVerifier(LoginResource.KEY));

            if (verified && object.getPayload().toString().equals("rw")) {
                return Response.ok("You are admin").build();
        } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();*/
        if (cre.getProperty("payload").equals("rw")) {
            return Response.ok("You are admin").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
