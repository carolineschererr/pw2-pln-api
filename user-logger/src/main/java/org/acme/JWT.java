package org.acme;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@Path("/getjwt")
public class JWT {

    @POST
    @Path("/admin")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String getJWTA(@FormParam("username") String username, @FormParam("email") String email){
  
        return Jwt.issuer("https://localhost:8443")
            .upn(email)
            .groups(new HashSet<>(Arrays.asList("Admin")))
            .claim(Claims.full_name, username)
            .claim(Claims.email, email)
            .sign();
    }

    @POST
    @Path("/user")
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String getJWTP(@FormParam("username") String username, @FormParam("email") String email){

        return Jwt.issuer("https://localhost:8443")
            .upn(email)
            .groups(new HashSet<>(Arrays.asList("User")))
            .claim(Claims.full_name, username)
            .claim(Claims.email, email)
            .sign();
    }

}
