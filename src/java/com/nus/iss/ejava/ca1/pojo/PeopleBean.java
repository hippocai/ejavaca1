/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.pojo;

import java.io.Serializable;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hippo
 */
@Entity
@Table(name = "people")
@XmlRootElement
public class PeopleBean implements Serializable{
    @Id
    private String pid;
    
    private String name;
    
    private String email;
    @OneToMany(mappedBy = "people")
    private List<AppointmentBean> appointments;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<AppointmentBean> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentBean> appointments) {
        this.appointments = appointments;
    }
    public JsonObject toJson() {
        return (Json.createObjectBuilder()
                .add("email", email)
                .add("name", name)
                .add("pid", pid)
                .build());
    }
    
    @Override
    public String toString(){
       return this.toJson().toString();
    }
     
    
}
