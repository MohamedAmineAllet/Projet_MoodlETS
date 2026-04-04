package com.example.projet_moodlets.daos;

import com.example.projet_moodlets.modeles.Travail;

import java.util.ArrayList;
import java.util.List;

public class TravailLocalDao {
    private static TravailLocalDao instance = null;

    private List<Travail> travaux = new ArrayList<>();

    public static TravailLocalDao getInstance(){
        if(instance == null)
            instance = new TravailLocalDao();
        return instance;
    }

    private TravailLocalDao(){
        Travail t;

        int[] ids = {1, 2, 3};
        int[] courseIds = {1, 1, 2};

        String[] titles = {
                "Devoir 1",
                "Devoir 2",
                "Devoir 1"
        };

        String[] descriptions = {
                "Détails architecture.",
                "Assembleur.",
                "Modélisation."
        };

        String[] dueDates = {
                "20 mars 2026",
                "5 avril 2026",
                "25 mars 2026"
        };

        String[] instructions = {
                "PDF 5 pages",
                "Boucle",
                "ER Diagram"
        };

        String[] statuts = {
                "Non soumis",
                "Non soumis",
                "Non soumis"
        };

        double[] grades = {0.0, 0.0, 0.0};

        String[] comments = {
                "null",
                "null",
                "null"
        };

        double[] totalPoints = {20.0, 20.0, 20.0};

        String[] types = {
                "text",
                "file",
                "file"
        };

        for (int i = 0; i < titles.length; i++) {
            t = new Travail(ids[i], titles[i], dueDates[i]);

            // On ajoute tous les autres attributs
            t.setCourseId(courseIds[i]);
            t.setDescription(descriptions[i]);
            t.setInstruction(instructions[i]);
            t.setStatus(statuts[i]);
            t.setGrade(grades[i]);
            t.setComment(comments[i]);
            t.setTotalPoints(totalPoints[i]);
            t.setType(types[i]);


            travaux.add(t);
        }
    }

    public List<Travail> getTravail(){
        return travaux;
    }

    public Travail getTravailParTitre(String title){
        for(Travail t:travaux){
            if(title.equals(t.getTitle()))
                return t;
        }
        return null;
    }
}


