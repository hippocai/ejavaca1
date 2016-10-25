/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.Constants;

/**
 *
 * @author hippo
 */
public class QueryString {
    public class People{
        public final static String GET_BY_EMAIL="select p from PeopleBean p where p.email = :email";
    };
    
    public class Appointment{
        public final static String GET_BY_PEOPLE_EMAIL="select appt from AppointmentBean appt where appt.people.email = :email";
    }
    
    
}
