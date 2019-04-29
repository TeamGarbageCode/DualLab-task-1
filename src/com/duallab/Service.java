package com.duallab;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Service {
    private Companies company;
    private Date departureTime;
    private Date arrivalTime;

    public Service(Companies company, Date departureTime, Date arrivalTime) {
        this.company = company;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Companies getCompany() {
        return company;
    }

    public Date getDepartureTime() {
        return departureTime;
    }
    public long getDepartureTimeInMilisec(){
        return departureTime.getTime();
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }
    public long getArrivalTimeInMilisec(){
        return arrivalTime.getTime();
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return  company.toString() + " " + format.format(departureTime) + " " + format.format(arrivalTime);
    }
//
//    @Override
//    public String toString() {
//        return "Service{" +
//                "company=" + company +
//                ", departureTime=" + departureTime +
//                ", arrivalTime=" + arrivalTime +
//                '}';
//    }
}
