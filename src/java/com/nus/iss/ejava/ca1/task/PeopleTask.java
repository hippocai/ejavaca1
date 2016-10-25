/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.task;

import com.nus.iss.ejava.ca1.EJB.PeopleService;
import com.nus.iss.ejava.ca1.pojo.PeopleBean;
import com.nus.iss.ejava.ca1.pojo.PeopleFactory;
import java.math.BigDecimal;
import java.util.Optional;
import javax.json.Json;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author hippo
 */
public class PeopleTask {
    public static class CreatePeople implements Runnable{
        private final PeopleService peopleService;
        private final String name;
        private final String email;
        private final AsyncResponse asyncResponse;

        public CreatePeople(PeopleService peopleService, String name, String email, AsyncResponse asyncResponse) {
            this.peopleService = peopleService;
            this.name = name;
            this.email = email;
            this.asyncResponse = asyncResponse;
        }
        
        
        @Override
        public void run() {
            Optional<PeopleBean> opt=peopleService.getPeopleByEmail(email);
            if(opt.isPresent()){
                Response response=Response.status(Response.Status.CONFLICT).entity(Json.createObjectBuilder().add("Message", "The Email:"+email+" Already Existed!").build()).build();
                asyncResponse.resume(response);
            }else{
                peopleService.insert(PeopleFactory.createPeopleEntity(name, email));
                Response response=Response.ok().build();
                asyncResponse.resume(response);
            }
        }
    }
    
    public static class FindPeopleByEmail implements Runnable{
        private final PeopleService peopleService;
        private final String email;
        private final AsyncResponse asyncResponse;

        public FindPeopleByEmail(String emailString,AsyncResponse asyncResponse,PeopleService peopleService){
            this.email=emailString;
            this.asyncResponse=asyncResponse;
            this.peopleService=peopleService;
        }
        @Override
        public void run() {
            Optional<PeopleBean> opt=peopleService.getPeopleByEmail(email);
            if(opt.isPresent()){
               // JsonObject jsonObject = Json.createObjectBuilder().add("data", opt.get().toJson()).build();
                asyncResponse.resume(Response.ok().build());
            }else{
                asyncResponse.resume(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity("Error,Email:"+email+" Not Found")
                                .build()
                );
            }
        }
    }
}
