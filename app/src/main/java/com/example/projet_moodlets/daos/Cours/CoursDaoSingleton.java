package com.example.projet_moodlets.daos.Cours;

// garantit qu'il n'y a qu'une seule instance
public class CoursDaoSingleton {

    private static CoursDao daoInstance = null;

    public static CoursDao getDaoInstance(){
        if(daoInstance==null){
            daoInstance = new HttpJsonCoursDao();
        }
        return daoInstance;
    }

}
