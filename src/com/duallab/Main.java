package com.duallab;

import java.util.Scanner;
import static com.duallab.TimetableHandler.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        Timetable timetable = readTimetableFile(path);
        System.out.println(timetable);
        System.out.println("-----------------");
        Timetable newTimetable = getRefactoredTimetable(timetable);
        System.out.println(newTimetable);
        TimetableHandler.writeTimetableFile(newTimetable);
    }
}
