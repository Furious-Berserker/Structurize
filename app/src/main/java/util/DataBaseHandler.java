package util;

import java.util.ArrayList;

import model.Task;

public class DataBaseHandler {
    private static ArrayList<Task> tasks = new ArrayList<Task>(){
        {
            add(new Task("Погладить кота","Хорошо погладить","19.02.2019",1));
            add(new Task("Стать машиной","Решить все задачи","19.02.2019",1));
            add(new Task("Забыть Java","Совсем забыть","18.02.2019",1));
        }
    };

    public ArrayList<Task> getAllTasks(){
        return tasks;
    }
}
