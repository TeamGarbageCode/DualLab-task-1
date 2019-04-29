package com.duallab;

import java.io.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                timetable.addService(createService(serviceString));
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

            String text = timetable.toString();
            fileWriter.write(text);
            fileWriter.flush();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private static Service createService(String string){
        String[] fields = string.split(" ");
        Companies company = Companies.valueOf(fields[0]);
        Date departureTime = null;
        Date arrivalTime = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            departureTime = format.parse(fields[1]);
            arrivalTime = format.parse(fields[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Service(company,departureTime,arrivalTime);
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
        int hour = 3600000;
        List<Service> services = timetable.getServices();
        for (int i = 0; i < timetable.getServices().size(); i++){
            if(services.get(i).getArrivalTimeInMilisec() - services.get(i).getDepartureTimeInMilisec() >= hour){
                timetable.removeService(i--);
            }
        }
    }
    private static void removeTheSameServices(Timetable timetable){
        List<Service> services = timetable.getServices();
        for(int i=0;i < services.size();i++){
            Service posh = services.get(i);
            if(posh.getCompany() == Companies.Posh){
                for(int j = 0;j < services.size();j++ ){
                    Service grotty = services.get(j).getCompany()==Companies.Grotty ? services.get(j) : null;
                    if(grotty!= null && grotty.getDepartureTimeInMilisec()==posh.getDepartureTimeInMilisec()
                            && grotty.getArrivalTimeInMilisec()==posh.getArrivalTimeInMilisec()){
                        services.remove(grotty);
                        j--;
                        i--;
                    }
                }
            }
        }
    }
    private static void sortAccordingToDepartureTime(Timetable timetable) {
        timetable.getServices().sort((Service s1,Service s2) -> s1.getDepartureTime().compareTo(s2.getDepartureTime()));
    }
    private static void removeInefficientService(Timetable timetable){
        removeTooLongService(timetable);
        List<Service> services = timetable.getServices();
        for(int i = 0; i < services.size()-1; i++){
            if(services.get(i).getDepartureTimeInMilisec()==services.get(i+1).getDepartureTimeInMilisec()){
                if(services.get(i).getArrivalTimeInMilisec()<services.get(i+1).getArrivalTimeInMilisec()){
                    services.remove(i+1);
                    i--;
                }else{
                    services.remove(i--);
                }
            }else{
                if(services.get(i).getDepartureTimeInMilisec()>services.get(i+1).getDepartureTimeInMilisec()){
                    if(services.get(i).getArrivalTimeInMilisec()>services.get(i+1).getArrivalTimeInMilisec()){
                        continue;
                    }else{
                        services.remove(i+1);
                        i--;
                    }
                }else{
                    if(services.get(i).getArrivalTimeInMilisec()< services.get(i+1).getArrivalTimeInMilisec()){
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
