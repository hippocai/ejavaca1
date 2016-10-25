/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.rest;

import com.nus.iss.ejava.ca1.EJB.PeopleService;
import com.nus.iss.ejava.ca1.task.PeopleTask;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author hippo
 */
@RequestScoped
@Path("people")
public class PeopleResource {
    @EJB
    private PeopleService peopleService;
    
    @Resource
    private ManagedExecutorService executors;
    
     @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     @Produces(MediaType.APPLICATION_JSON)
    public void insertPeople(@FormParam("name")String name,@FormParam("email")String email,@Suspended AsyncResponse asyncResponse) {
        executors.execute(
                new PeopleTask.CreatePeople(peopleService, name, email, asyncResponse)
        );
    }
    
     @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void findPeopleByEmail(@QueryParam("email") String email,@Suspended AsyncResponse asyncResponse) {
        executors.execute(new PeopleTask.FindPeopleByEmail(email, asyncResponse, peopleService));
    }
}
