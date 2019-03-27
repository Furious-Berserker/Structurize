package com.svatoslavbulgakov.structurize.util;

import com.svatoslavbulgakov.structurize.model.Task;

import java.util.ArrayList;

public class DataBaseHandler {
    private static ArrayList<Task> tasks = new ArrayList<Task>(){
        {
            add(new Task("Погладить кота","Хорошо погладить","19 Feb",1));
            add(new Task("Стать машиной","Решить все задачи","15 Jun",1));
            add(new Task("Забыть Java","Совсем забыть","10 Dec",1));
        }
    };

    public ArrayList<Task> getAllTasks(){
        return tasks;
    }
}
