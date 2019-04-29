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
    public void removeService(int i){
        services.remove(i);
    }
    public void removeService(Service service){
        services.remove(services.indexOf(service));
    }
    public String toString() {
        String result = "";
        Companies prevCompany = services.get(0).getCompany();

        for(Service service : services){
            if(prevCompany != service.getCompany()){
                result += "\n" + service +  "\n";
                prevCompany = service.getCompany();
            }else{
                result += service + "\n";
            }
        }
        return result;
    }
}
