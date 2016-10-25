/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hippo
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
public class AppointmentBean implements Serializable{
    @Id
    @Column(name ="appt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String desc;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "appt_date")
    private Date date;
    @ManyToOne
    @JoinColumn(name ="pid",referencedColumnName="pid")
    private PeopleBean people;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PeopleBean getPeople() {
        return people;
    }

    public void setPeople(PeopleBean people) {
        this.people = people;
    }
    
    public JsonObject toJson(){
          return (Json.createObjectBuilder()
                //.add("appointmentId", id)
                .add("dateTime", date.getTime())
                .add("description", desc)
                .add("personId", people.getPid())
                .build());
    }
    
    @Override
    public String toString(){
        return this.toJson().toString();
    }
    
};
