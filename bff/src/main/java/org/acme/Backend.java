package org.acme;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@Path("/login")
public class Backend {

    private static final Logger LOGGER = Logger.getLogger(Backend.class.getName());

    @Inject
    @Claim(standard = Claims.email)
    String email;

    @GET
    @Path("/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ "Admin", "User" })
    public String login(@PathParam("username") String username) {
        LOGGER.log(Level.INFO, "Backend: {0}", email);
        return "Successfully logged in.";
    }

}