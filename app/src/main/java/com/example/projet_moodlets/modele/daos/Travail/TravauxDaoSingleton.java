package com.example.projet_moodlets.modele.daos.Travail;

/**
 * Singleton gérant l'accès à l'instance unique du DAO pour les Travaux.
 * Garantit qu'une seule instance de HttpJsonTravauxDao est utilisée dans l'application.
 */
public class TravauxDaoSingleton {

    private static TravauxDao daoInstance = null;

    /**
     * Retourne l'instance unique du DAO des Travaux.
     * Si elle n'existe pas, elle est initialisée avec l'implémentation JSON.
     *
     * @return L'instance partagée de TravauxDao.
     */
    public static TravauxDao getInstance() {
        if (daoInstance == null)
            daoInstance = new HttpJsonTravauxDao();
        return daoInstance;
    }
}
