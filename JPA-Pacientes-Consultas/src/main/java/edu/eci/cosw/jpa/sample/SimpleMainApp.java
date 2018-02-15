/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cosw.jpa.sample;

import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author hcadavid
 */
public class SimpleMainApp {
   
    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();
        
        Set<Consulta> consultas =  new HashSet<>();
        consultas.add(new Consulta(new Date(), "Revision general"));
        consultas.add(new Consulta(new Date(), "Consulta con ortopedista"));
        consultas.add(new Consulta(new Date(), "Cita Jhordy error "));
        
//        USADAS CUANDO DB ESTABA VACIA
//        s.save(new Paciente (new PacienteId(1,"cc"), "Jhordy", new Date(),consultas));
//        s.delete(new Paciente (new PacienteId(1,"cc"), "Jhordy", new Date(),consultas));
        
        Paciente pIn = new Paciente (new PacienteId(1,"cc"), "Jhordy", new Date(),consultas);
        s.saveOrUpdate(pIn);
        
        Paciente p = (Paciente) s.load(Paciente.class, new PacienteId(1, "cc"));
        //PACIENTE
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PACIENTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +"\n"+ p);
        // LAS CONSULTAS DEL PACIENTE
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CONSULTAS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        p.getConsultas().forEach(System.out::println);
        
        tx.commit();    
        s.close();
        sf.close();

    }

    public static SessionFactory getSessionFactory() {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        // builds a session factory from the service registry
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
    

}
