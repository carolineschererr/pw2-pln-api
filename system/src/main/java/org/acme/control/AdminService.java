package org.acme.control;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import org.acme.model.Admin;

@Path("/admin")
@Transactional
public class AdminService {

    //criar administrador
    // role Admin
    @POST
    @Path("/create_admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    public void addAdmin(@RequestBody CreateAdmin admin){
        Admin adm = new Admin();
        adm.setUsername(admin.getUsername());
        adm.setPassword(admin.getPassword());
        adm.setEmail(admin.getEmail());
        adm.persist();
    }
    
    //lista administradores
    @GET
    @Path("/list_admins")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    public List<Admin> listAdmin() {
        return Admin.listAll(); 
    }


    private static final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    @Inject
    @Claim(standard = Claims.full_name)
    String fullName;

     @Inject
     @RestClient
     BackendLogin backend;

    // login
    @GET
    @Path("/login/{username}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ "Admin" })
    public String loginAdmin(@PathParam("username") String username, @PathParam("password") String password){
        if(Admin.findByCredentials(username, password).isEmpty() == false){
            LOGGER.log(Level.INFO, "LoginAdmin: {0}", fullName);
            return backend.login(username);
        }
        else {
            return "Incorrect credentials (username and password).";
        }
    }
}
