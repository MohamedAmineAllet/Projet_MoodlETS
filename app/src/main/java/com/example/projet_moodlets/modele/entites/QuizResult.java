package com.example.projet_moodlets.modele.entites;

/**
 * Représente le résultat simplifié d'un quiz terminé par l'utilisateur.
 * Utilisé pour le transfert de données (DTO) vers le serveur JSON.
 */
public class QuizResult {
    private String quizId;
    private int score, total;

    /**
     * Constructeur vide requis pour la désérialisation JSON.
     */
    public QuizResult() {
    }

    /**
     * Constructeur complet pour initialiser un résultat de quiz.
     *
     * @param quizId L'identifiant du quiz concerné.
     * @param score  Le nombre de bonnes réponses obtenues.
     * @param total  Le nombre total de questions du quiz.
     */
    public QuizResult(String quizId, int score, int total) {
        this.quizId = quizId;
        this.score = score;
        this.total = total;
    }

    public String getQuizId() {
        return quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }
}
