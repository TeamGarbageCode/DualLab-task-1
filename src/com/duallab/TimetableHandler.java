package com.duallab;

import java.io.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public abstract class TimetableHandler {
    public static Timetable readTimetableFile(String path){
        Timetable timetable = new Timetable();
        try(FileInputStream inStream = new FileInputStream(path)){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String serviceString;
            while((serviceString = bufferedReader.readLine()) != null){
                timetable.addService(Service.parse(serviceString));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return timetable;
    }
    public static void writeTimetableFile(Timetable timetable){
        File output = new File("output.txt");
        try(FileWriter fileWriter = new FileWriter(output)){
            output.createNewFile();
            fileWriter.write(timetable.toString());
            fileWriter.flush();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static Timetable getRefactoredTimetable(Timetable timetable){
        Timetable newTimetable = new Timetable();
        for(Service service : timetable.getServices()){
            newTimetable.addService(service);
        }
        removeTheSameServices(newTimetable);
        sortAccordingToDepartureTime(newTimetable);
        removeInefficientService(newTimetable);
        separateCompaniesInTimetable(newTimetable);
        return newTimetable;
    }
    private static void removeTooLongService(Timetable timetable){
        Duration maxTravelDuration = Duration.ofHours(1);
        timetable.getServices().removeIf(
                service -> Duration.between(service.getArrivalTime(), service.getDepartureTime())
                        .compareTo(maxTravelDuration) > 0
        );
    }
    private static void removeTheSameServices(Timetable timetable){
        timetable.getServices().removeIf(
                service -> service.getCompany() == Companies.Grotty
                        && timetable.getServices()
                        .contains(new Service(Companies.Posh, service.getDepartureTime(), service.getArrivalTime()))
        );

    }
    private static void sortAccordingToDepartureTime(Timetable timetable) {
        timetable.getServices().sort(Comparator.comparing(Service::getDepartureTime));
    }
    private static void removeInefficientService(Timetable timetable){
        removeTooLongService(timetable);
        List<Service> services = timetable.getServices();
        for(int i = 0; i < services.size()-1; i++){
            if(services.get(i).getDepartureTime().compareTo(services.get(i+1).getDepartureTime())==0){
                if(services.get(i).getArrivalTime().compareTo(services.get(i+1).getArrivalTime())<0){
                    services.remove(i+1);
                    i--;
                }else{
                    services.remove(i--);
                }
            }else{
                if(services.get(i).getDepartureTime().compareTo(services.get(i+1).getDepartureTime())>0){
                    if(services.get(i).getArrivalTime().compareTo(services.get(i+1).getArrivalTime())>0){
                        continue;
                    }else{
                        services.remove(i+1);
                        i--;
                    }
                }else{
                    if(services.get(i).getArrivalTime().compareTo(services.get(i+1).getArrivalTime())<0){
                        continue;
                    }else{
                        services.remove(i--);
                    }
                }
            }
        }
    }
    private static void separateCompaniesInTimetable(Timetable timetable){
        List<Service> services = new ArrayList<Service>();
        List<Service> poshServices = new ArrayList<Service>();
        List<Service> grottyServices = new ArrayList<Service>();
        for(Service service : timetable.getServices()){
            switch (service.getCompany()){
                case Posh: poshServices.add(service);break;
                case Grotty:grottyServices.add(service);break;
            }
        }
        services.addAll(poshServices);
        services.addAll(grottyServices);
        timetable.setServices(services);
    }
}
