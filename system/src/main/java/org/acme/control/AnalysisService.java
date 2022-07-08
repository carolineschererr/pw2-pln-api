package org.acme.control;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import org.acme.model.Analysis;
import org.acme.model.User;

@Path("/analysis")
@Transactional
public class AnalysisService {

    //cria uma análise
    // requer ser registrado como User ou Admin
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public void addAnalysis(@RequestBody CreateAnalysis analysis){
        Analysis nAnalysis = new Analysis();
        nAnalysis.setText(analysis.getText());
        nAnalysis.setSearchWord(analysis.getSearchWord());
        nAnalysis.persist();
    }

    //lista todas análises registradas
    @GET
    @Path("/list_analysis")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    @Transactional
    public List<Analysis> list() {
        return Analysis.listAll();  
    }
    
    //exclui uma análise
    // requer Admin
    @GET
    @Path("/delete/{idAnalysis}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin" })
    @Transactional
    public void delete(@PathParam("idAnalysis") Long idAnalysis){
        Analysis analysis = Analysis.findById(idAnalysis);
        if (analysis == null)
            throw new BadRequestException("Analysis not found.");
        analysis.delete();
    }

}
