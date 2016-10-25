/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.task;

import com.nus.iss.ejava.ca1.EJB.AppointmentService;
import com.nus.iss.ejava.ca1.EJB.PeopleService;
import com.nus.iss.ejava.ca1.pojo.AppointmentBean;
import com.nus.iss.ejava.ca1.pojo.PeopleBean;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author hippo
 */
public class AppointmentTask {
    public static class FindAppointmentsByEmail implements Runnable{
        private final PeopleService peopleService;
        private final AppointmentService appointmentService;
        private final AsyncResponse async;
        private final String email;

        public FindAppointmentsByEmail(AppointmentService appointmentService, AsyncResponse async, String email,PeopleService peopleService) {
            this.appointmentService = appointmentService;
            this.async = async;
            this.email = email;
            this.peopleService=peopleService;
        }
        
        
        @Override
        public void run() {
            List<AppointmentBean> resultList=appointmentService.getApptByPeopleEmail(email);
            Optional<PeopleBean>opt=peopleService.getPeopleByEmail(email);
            if((resultList==null||resultList.isEmpty())&&!opt.isPresent()){
                async.resume(Response.status(Response.Status.NOT_FOUND).build());
                //return;
            }else{
                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                for (AppointmentBean appt : resultList) {
                    arrBuilder.add(appt.toJson());
                }
                async.resume(Response.ok().entity(arrBuilder.build()).build());
            }
        }
        
    }
    
    public static class CreateAppointments implements Runnable{

        private final AppointmentService apptService;
        private final PeopleService peopleService;
        private final AsyncResponse async;
        private final String email; 
        private final Date date;
        private final String desc;

        public CreateAppointments(AppointmentService apptService, PeopleService peopleService, AsyncResponse async, String email, Date date, String desc) {
            this.apptService = apptService;
            this.peopleService = peopleService;
            this.async = async;
            this.email = email;
            this.date = date;
            this.desc = desc;
        }
        
        @Override
        public void run() {
            Optional<PeopleBean>opt=peopleService.getPeopleByEmail(email);
            if (!opt.isPresent()) {
                async.resume(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity("Error,Email:"+email+" Not Found")
                                .build()
                );
            }else{
                AppointmentBean apptBean=new AppointmentBean();
                apptBean.setDate(date);
                apptBean.setDesc(desc);
                apptBean.setPeople(opt.get());
                apptService.insert(apptBean);
                async.resume(Response.ok().build());
            }
        }
    
    }
}
