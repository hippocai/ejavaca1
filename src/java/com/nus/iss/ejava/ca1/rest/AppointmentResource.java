/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.rest;

import com.nus.iss.ejava.ca1.EJB.AppointmentService;
import com.nus.iss.ejava.ca1.EJB.PeopleService;
import com.nus.iss.ejava.ca1.task.AppointmentTask;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author hippo
 */
@RequestScoped
@Path("appointment")
public class AppointmentResource {
     @Resource
    private ManagedExecutorService executors;

    @EJB
    private AppointmentService apptService;
    @EJB private PeopleService peopleService;
    
    @GET
    @Path("{email}")
    public void getApptByEmail(@PathParam("email") String email, @Suspended AsyncResponse asyncResponse){
        executors.execute(new AppointmentTask.FindAppointmentsByEmail(apptService, asyncResponse, email,peopleService));
    }
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createAppt(MultivaluedMap<String, String> form,@Suspended AsyncResponse asyncResponse){
        String emailString=form.getFirst("email");
        String dateString=form.getFirst("date");
        String desc=form.getFirst("description");
        Date date=new Date(Long.parseLong(dateString));
        executors.execute(new AppointmentTask.CreateAppointments(apptService, peopleService, asyncResponse, emailString, date, desc));
        
    }
}
