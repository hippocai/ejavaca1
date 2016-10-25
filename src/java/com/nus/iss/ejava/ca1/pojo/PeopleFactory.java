/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.iss.ejava.ca1.pojo;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author hippo
 */
public class PeopleFactory {
    public static PeopleBean createPeopleEntity(String name,String email){
        PeopleBean peopleBean=new PeopleBean();
        peopleBean.setName(name);
        peopleBean.setEmail(email);
        peopleBean.setPid(generateID());
        return peopleBean;
    }
    
    private static String generateID(){
       byte[]uuid = UUID.randomUUID().toString().substring(0, 8).getBytes(); 
       byte[]time=Integer.toHexString((int)new Date().getTime()).getBytes();
       byte[]idByte=new byte[8];
       for(int i=0;i<idByte.length;++i){
            idByte[i]=(byte) (uuid[i]^time[i]);
       }
        int value = 0;
        for (byte b:idByte){
            value = (value << 8) + (b & 0xFF);
        }
        String idString=Integer.toHexString(value);
        while (idString.length()<8) {            
            idString="0"+idString;
        }
        return idString;
    }
}
