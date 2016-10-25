/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.EJB;

import com.nus.iss.ejava.ca1.Constants.QueryString;
import com.nus.iss.ejava.ca1.pojo.PeopleBean;
import java.util.List;
import java.util.Optional;
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
public class PeopleService {
    private final static Logger log=Logger.getLogger(PeopleService.class);
    
    @PersistenceContext(name = "appointmentPU")
    private EntityManager em;
    
    public void insert(PeopleBean peopleBean){
        log.info("Insert People:"+peopleBean.toString());
        em.persist(peopleBean);
        log.info("Finished");
    }
    public Optional<PeopleBean> getPeopleByEmail(String email){
        log.info("Get people by email,email:"+email);
        TypedQuery<PeopleBean> query=em.createQuery(QueryString.People.GET_BY_EMAIL, PeopleBean.class);
        query.setParameter("email", email);
        List<PeopleBean>resultList=query.getResultList();
        //return resultList==null||resultList.isEmpty()?Optional.empty():Optional.of(resultList.get(0));
        if(resultList==null||resultList.isEmpty()){
            log.info("Not Found");
            return Optional.empty();
        }else{
            log.info("Found");
            return Optional.of(resultList.get(0));
        }
    }
    
}
