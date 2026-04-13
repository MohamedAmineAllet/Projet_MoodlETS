package com.example.projet_moodlets.modele.daos.Cours;

// garantit qu'il n'y a qu'une seule instance
public class CoursDaoSingleton {

    private static CoursDao daoInstance = null;


    public static CoursDao getInstance(){
        if(daoInstance==null){
            daoInstance = new HttpJsonCoursDao();
        }
        return daoInstance;
    }

}
