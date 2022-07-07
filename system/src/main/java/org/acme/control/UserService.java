package org.acme.control;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.acme.model.User;
import org.acme.model.Analysis;

@Path("/user")
@Transactional
public class UserService {

    //Criar usuário. 
    @POST
    @Path("/create_account")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    public void addUser(@RequestBody CreateUser user){
        User nUser = new User();
        nUser.setUsername(user.getUsername());
        nUser.setEmail(user.getEmail());
        nUser.setPassword(user.getPassword());
        nUser.setFirstName(user.getFirstName());
        nUser.setLastName(user.getLastName());
        nUser.setBirthDate(user.getBirthDate());
        nUser.persist();
    }

    //lista os usuários
    @GET
    @Path("/list_all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<User> listUser() {
        // 3 - O método `listAll` recupera todos os objetos da classe User.
        return User.listAll(); 
    }
    
    //lista as análises do usuário a partir do ID
    @GET
    @Path("/list/user_analysis/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Set<Analysis> listAnalysis(@PathParam("idUser") Long idUser) {
        User nUser = User.findById(idUser);
        if (nUser == null)
            throw new BadRequestException("User not found."); 
        
        return nUser.getAnalysis(); 
    }

    //deleta usuário a partir do ID. precisa excluir as análises antes de excluir o usuário
    @GET
    @Path("/delete/{idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("idUser") Long idUser){
        User nUser = User.findById(idUser);
        if (nUser == null)
            throw new BadRequestException("User not found.");
        nUser.delete();
    }

    private static final Logger LOGGER = Logger.getLogger(AdminService.class.getName());

    /* Recuperando uma informação do token */
    @Inject
    @Claim(standard = Claims.full_name)
    String fullName;

     /* Rest client */
     @Inject
     @RestClient
     BackendLogin backend;

    // login com nome e senha
    @GET
    @Path("/login/{username}/{password}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ "User" })
    public String loginPac(@PathParam("username") String username, @PathParam("password") String password){
        if(User.findByCredentials(username, password).isEmpty() == false){
            LOGGER.log(Level.INFO, "LoginUser: {0}", fullName);
            return backend.login(username);
        }
        else {
            return "Incorrect credentials (username and password).";
        }
    }

}