/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Bd;
import Entities.Executable;
import Entities.FileUpload;
import Entities.SqlImport;
import Entities.Utilisateur;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Samsung
 */
@Stateless
@Path("entities.bd")
public class BdFacadeREST extends AbstractFacade<Bd> {

    @EJB
    private UtilisateurFacadeREST utilisateurFacadeREST;
    
    @EJB
    private ExecutableFacadeREST executableFacadeREST;

    @PersistenceContext(unitName = "PFAPU")
    private EntityManager em;

    String bdHost = "localhost:3306";
    String glassfishHost = "localhost:8080";
    String apacheHost = "localhost";

    
    public BdFacadeREST() {
        super(Bd.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Bd entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Bd entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Bd find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bd> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bd> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
       
    @POST  
    @Path("/upload")  
    @Consumes(MediaType.APPLICATION_JSON)  
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(FileUpload file,@QueryParam("user") String userParam) throws FileNotFoundException, IOException, SQLException {
            
            String fileLocation = "C:\\Users\\Samsung\\PFA\\executables\\temp.txt"; 
            String fileFinal = "C:\\Users\\Samsung\\PFA\\executables\\"+file.getFileName(); 
            InputStream uploadedInputStream = new ByteArrayInputStream(file.getFile().getBytes(StandardCharsets.UTF_8));
               
                    //saving file  
                try {  
                FileOutputStream out = new FileOutputStream(new File(fileLocation));  
                int read = 0;
                byte[] bytes = new byte[1024];  
                out = new FileOutputStream(new File(fileLocation));  
                while ((read = uploadedInputStream.read(bytes)) != -1) {  
                    out.write(bytes, 0, read);  
                }  
                out.flush();  
                out.close();  
                    } catch (IOException e) {e.printStackTrace();}  
               
                InputStream targetStream = new FileInputStream(fileLocation);
                
                String text = null;
                
                 try {
                    Scanner scanner = new Scanner(targetStream, StandardCharsets.UTF_8.name());
                    text = scanner.useDelimiter("\\A").next();
                }
                catch (Exception e) {};
                
                byte[] decodedValue=null;
                
                if((file.getFileName().substring(file.getFileName().lastIndexOf(".")+1)).equals("sql"))
                      decodedValue = Base64.getDecoder().decode(text.substring(13));

                FileOutputStream fichierFinal = new FileOutputStream(new File(fileFinal));  
                fichierFinal.write(decodedValue);
              
            //Importing sql file
            SqlImport sqlImport = new SqlImport();
            String table;
            table=sqlImport.sqlImport(fileFinal);
            
            //Preparing bd persistance details
            Utilisateur user = utilisateurFacadeREST.find(userParam);
            Executable executable = new Executable(table, glassfishHost, "");
            executable.setUtilisateur(user);
            executableFacadeREST.create(executable);
            Bd bd = new Bd(table, fileFinal);   
            bd.setExecutable(executable);
            this.create(bd);
            
            String output = "{ \"message\" : \""+bdHost+"/" + table+"\" , \"table\" : \""+table+"\" }"; 
            
            
            return output;  
        }  
}

