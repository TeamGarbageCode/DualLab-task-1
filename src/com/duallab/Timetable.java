package com.duallab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Timetable {
    private List<Service> services = new ArrayList<Service>();

    public void setServices(List<Service> services){
        this.services = services;
    }
    public List<Service> getServices(){
        return services;
    }

    public void addService(Service service){
        services.add(service);
    }

    public void removeService(Service service){
        services.remove(services.indexOf(service));
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        Companies prevCompany = services.get(0).getCompany();

        for(Service service : services){
            if(prevCompany != service.getCompany()){
                result.append("\n").append(service).append("\n");
                prevCompany = service.getCompany();
            }else{
                result.append(service).append("\n");
            }
        }
        return result.toString();
    }
}
