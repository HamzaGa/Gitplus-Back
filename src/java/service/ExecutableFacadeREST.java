/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Bd;
import Entities.Executable;
import Entities.FileUpload;
import Entities.Utilisateur;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.unzip.Unzip;

/**
 *
 * @author Samsung
 */
@Stateless
@Path("entities.executable")
public class ExecutableFacadeREST extends AbstractFacade<Executable> {

    @EJB
    private BdFacadeREST bdFacadeREST;
    
    @EJB
    private UtilisateurFacadeREST utilisateurFacadeREST;

    @PersistenceContext(unitName = "PFAPU")
    private EntityManager em;

    String bdHost = "localhost:3306";
    String glassfishHost = "localhost:8080";
    String apacheHost = "localhost";
    
    public ExecutableFacadeREST() {
        super(Executable.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Executable entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Executable entity) {
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
    public Executable find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Executable> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Executable> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadFile(FileUpload file,@QueryParam("projectType")String projectType,@QueryParam("table")String table,@QueryParam("user")String user) throws FileNotFoundException, IOException, net.lingala.zip4j.exception.ZipException {
            String fileLocation = "C:\\Users\\Samsung\\PFA\\executables\\temp.txt"; 
            String fileFinal = "C:\\Users\\Samsung\\PFA\\executables\\"+file.getFileName(); 
            Executable executable = new Executable();
            Executable executable2 = new Executable();
            Bd bd = new Bd (); 
            Utilisateur utilisateur = utilisateurFacadeREST.find(user);
            System.out.println(table+" hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            if(!table.equals("undefined"))
            {
            executable2 = this.find(table);
            javax.persistence.Query q = em.createQuery("select b from Bd b where b.executable=:arg");
            q.setParameter("arg", executable2);
            bd = (Bd) q.getSingleResult();
            this.remove(executable2);
            }
            
            if(projectType.equals("php"))
            {
                new File("C:\\wamp64\\www\\"+file.getFileName().substring(0,file.getFileName().lastIndexOf("."))).mkdirs();
                fileFinal = "C:\\wamp64\\www\\"+file.getFileName().substring(0,file.getFileName().lastIndexOf("."))+"\\"+file.getFileName(); 
            }
                   
            
            
            InputStream uploadedInputStream = new ByteArrayInputStream(file.getFile().getBytes(StandardCharsets.UTF_8));
               
                    //saving file  
                try {  
                FileOutputStream out = new FileOutputStream(new File(fileLocation));  
                int read = 0;
                byte[] bytes = new byte[1024];  
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
                catch (Exception e) {System.out.println("exception");};
                System.out.println(text);
                byte[] decodedValue=null;
                if((file.getFileName().substring(file.getFileName().lastIndexOf(".")+1)).equals("rar"))
                      decodedValue = Base64.getDecoder().decode(text.substring(13));
                else if((file.getFileName().substring(file.getFileName().lastIndexOf(".")+1)).equals("zip"))
                      decodedValue = Base64.getDecoder().decode(text.substring(41)); 
                else if((file.getFileName().substring(file.getFileName().lastIndexOf(".")+1)).equals("war"))
                      decodedValue = Base64.getDecoder().decode(text.substring(13));
                
            FileOutputStream fichierFinal = new FileOutputStream(new File(fileFinal));  
            fichierFinal.write(decodedValue);
            fichierFinal.close();
            
          if (projectType.equals("j2ee"))
            {
               ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd C:/Users/Samsung/GlassFish_Server/bin && asadmin deploy "+fileFinal);
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (true) {
                    line = r.readLine();
                    if (line == null) { break; }
                }
            executable.setChemin(fileFinal);
            executable.setExtension("war");
            executable.setLien(glassfishHost+"/"+file.getFileName().substring(0,file.getFileName().lastIndexOf(".")));
            executable.setUtilisateur(utilisateur);
            this.create(executable);
            if(!table.equals("undefined"))
                {
                bd.setExecutable(executable);
                bdFacadeREST.edit(bd);
                }
            }
          
          
            if (projectType.equals("php"))
            {
            //unzipping zip file
            if((file.getFileName().substring(file.getFileName().lastIndexOf(".")+1)).equals("zip"))
            {
            String destination = "C:\\wamp64\\www\\"+file.getFileName().substring(0,file.getFileName().lastIndexOf("."));   
            ZipFile zipFile = new ZipFile(fileFinal);
            zipFile.extractAll(destination);
            executable.setUtilisateur(utilisateur);
            executable.setChemin(destination);
            executable.setExtension("php");
            executable.setLien(apacheHost+"/"+file.getFileName().substring(0,file.getFileName().lastIndexOf(".")));
            this.create(executable);
            if(!table.equals("undefined"))
                {
                bd.setExecutable(executable);
                bdFacadeREST.edit(bd);
                }
            }
           
            }
            
         
            
            if (projectType.equals("angular"))
            {
                
            } 
            
            String output = "{ \"message\" : \"DONE \" } "; 
            return output;  
        }  
}

