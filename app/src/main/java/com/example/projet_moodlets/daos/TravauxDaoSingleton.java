package com.example.projet_moodlets.daos;

public class TravauxDaoSingleton {
    private static TravauxDao daoInstance = null;

    public static TravauxDao getInstance() {
        if (daoInstance==null)
            daoInstance = new TravailLocalDao();
        return daoInstance;
    }
}
