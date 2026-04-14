package com.example.projet_moodlets.vue.adapteurs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projet_moodlets.vue.fragementsInscription.Etape1Fragement;
import com.example.projet_moodlets.vue.fragementsInscription.Etape2Fragement;
import com.example.projet_moodlets.vue.fragementsInscription.Etape3Fragement;


public class InscriptionPageAdapter extends FragmentStateAdapter {

    public InscriptionPageAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Etape1Fragement();
            case 1: return new Etape2Fragement();
            case 2: return new Etape3Fragement();
            default: return new Etape1Fragement();
        }
    }

    @Override
    public int getItemCount() { return 3; }
}
