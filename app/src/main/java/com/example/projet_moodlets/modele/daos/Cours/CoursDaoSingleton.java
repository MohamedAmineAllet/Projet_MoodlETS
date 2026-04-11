package com.example.projet_moodlets.modele.daos.Cours;

// garantit qu'il n'y a qu'une seule instance
public class CoursDaoSingleton {

    private static CoursLocalDao daoInstance = null;


    public static CoursLocalDao getInstance(){
        if(daoInstance==null){
            daoInstance = new CoursLocalDao();
        }
        return daoInstance;
    }

}
