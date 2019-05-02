package com.duallab;

import java.util.Scanner;
import static com.duallab.TimetableHandler.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        Timetable timetable = readTimetableFile(path);
        Timetable newTimetable = getRefactoredTimetable(timetable);
        TimetableHandler.writeTimetableFile(newTimetable);
    }
}
