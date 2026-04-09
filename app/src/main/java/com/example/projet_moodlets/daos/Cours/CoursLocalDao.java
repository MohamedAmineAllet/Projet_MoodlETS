package com.example.projet_moodlets.daos.Cours;

import com.example.projet_moodlets.entites.Cours;
import com.example.projet_moodlets.entites.Travail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoursLocalDao implements CoursDao{
    private List<Cours> cours = new ArrayList<>();


    @Override
    public List<Cours> getTousLesCours() {
        return cours;
    }
}
