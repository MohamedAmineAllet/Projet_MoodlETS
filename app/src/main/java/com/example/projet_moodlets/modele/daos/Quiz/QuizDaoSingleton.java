package com.example.projet_moodlets.modele.daos.Quiz;

/**
 * Singleton permettant d'accéder à une instance unique du DAO des Quiz.
 * Cela évite de recréer l'objet HttpJsonQuizDao plusieurs fois inutilement.
 */
public class QuizDaoSingleton {

    // Instance unique stockée en mémoire (partagée par toute l'app)
    private static QuizDao daoInstance = null;

    /**
     * Fournit l'unique instance de QuizDao.
     * Si l'instance n'existe pas encore, elle est créée (Lazy Initialization).
     *
     * @return L'instance unique du DAO configurée pour l'API JSON.
     */
    public static QuizDao getInstance() {
        if (daoInstance == null)
            daoInstance = new HttpJsonQuizDao();
        return daoInstance;
    }
}
