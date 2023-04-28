package com.scaler.greet;

import java.time.LocalDateTime;

public class Greeting {

    public String getGreet(){
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.getHour() <=12){
            return "Good Morning";
        }
        else if(localDateTime.getHour()<=18){
            return "Good Evening";
        }
        else{
            return  "Good Night";
        }

    }
}
