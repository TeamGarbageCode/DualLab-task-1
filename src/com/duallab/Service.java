package com.duallab;

import java.time.LocalTime;
import java.util.Objects;


public class Service {
    private Companies company;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public Service(Companies company, LocalTime departureTime, LocalTime arrivalTime) {
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Companies getCompany() {
        return company;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return  company.toString() + " " + departureTime + " " + arrivalTime;
    }

    public static Service parse(String string){
        String[] fields = string.split(" ");

        Companies company = Companies.valueOf(fields[0]);

        LocalTime departureTime = null;
        LocalTime arrivalTime   = null;
        try {
            departureTime = LocalTime.parse(fields[1]);
            arrivalTime   = LocalTime.parse(fields[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Service(company, departureTime, arrivalTime);
    }

    @Override
    public boolean equals(Object o) {
        if(this==o){
            return true;
        }
        if(o == null && getClass() != o.getClass()){
            return false;
        }
        Service obj = (Service) o;
        return obj.company.equals(company) && obj.departureTime.equals(departureTime) && obj.arrivalTime.equals(arrivalTime);
    }
}
