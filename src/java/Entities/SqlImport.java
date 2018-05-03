/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import static Entities.Bd_.nom;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import service.BdFacadeREST;

/**
 *
 * @author Samsung
 */
public class SqlImport {

    
    public String sqlImport(String path) throws SQLException, FileNotFoundException, IOException {

    Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=");         
        boolean booleanAutoCommit = false,booleanStopOnerror = true;

        Random rand = new Random(); 
        int value = rand.nextInt(9999);
        
        /*
        int x = 0 ;
        while(x==0)
        {
            value = rand.nextInt(9999);
            x=1;
        for (Iterator it = l.iterator(); it.hasNext();) {
            Bd bd = (Bd) it.next();
            if(bd.getNom().equals("client"+rand))
            x=0;    
            }
        } */
        
        String table="client"+value;    
    Statement stmt = Conn.createStatement();
    int Result=stmt.executeUpdate("CREATE DATABASE "+table+" ;");
        Conn.close();
     Conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+table,"root",""); 
    ScriptRunner runner = new ScriptRunner(Conn, booleanAutoCommit, booleanStopOnerror);
    runner.runScript(new BufferedReader(new FileReader(path)));
    Conn.close();
    return table;
    }
    
}
