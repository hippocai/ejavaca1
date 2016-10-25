/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.EJB;

import com.nus.iss.ejava.ca1.Constants.QueryString;
import com.nus.iss.ejava.ca1.pojo.AppointmentBean;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author hippo
 */
@Stateless
public class AppointmentService {
    private final static Logger log=Logger.getLogger(AppointmentService.class);
    
    @PersistenceContext(name = "appointmentPU")
    private EntityManager em;
    
    public void insert(AppointmentBean apptBean){
        log.info("Insert Appointment:"+apptBean.toString());
        em.persist(apptBean);
        log.info("Finished");
    }
    public List<AppointmentBean> getApptByPeopleEmail(String email){
        log.info("Get Appointment By People Email:"+email);
        TypedQuery<AppointmentBean> query=em.createQuery(QueryString.Appointment.GET_BY_PEOPLE_EMAIL,AppointmentBean.class);
        query.setParameter("email", email);
        List<AppointmentBean> resultList=query.getResultList();
        if(resultList==null||resultList.isEmpty()){
            log.warn("Not Found Any Appointment");
        }
        log.info("Finished");
        return resultList;
    }
}
