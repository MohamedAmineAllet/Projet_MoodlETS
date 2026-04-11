package com.example.projet_moodlets.modele.daos.Travail;

public class TravauxDaoSingleton {
    private static TravauxDao daoInstance = null;

    public static TravauxDao getInstance() {
        if (daoInstance==null)
            daoInstance = new HttpJsonTravauxDao();
        return daoInstance;
    }
}
