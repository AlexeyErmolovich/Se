package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.MainActivity;
import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.FullNameObject;
import com.alexeyermolovich.secretofyourname.model.NameObject;
import com.alexeyermolovich.secretofyourname.view.SectionView;

/**
 * Created by ermolovich on 23.9.16.
 */

public class FragmentNamesDetails extends Fragment
        implements FactoryNames.OnGetNameListener {

    private Core core;
    private MainActivity mainActivity;

    private RelativeLayout containerMain;
    private TextView textWarning;
    private ScrollView scrollView;
    private SectionView sectionAvailableName;
    private SectionView sectionHistory;
    private SectionView sectionCharacter;
    private SectionView sectionCharacterTraits;
    private SectionView sectionHealth;
    private SectionView sectionSexuality;
    private SectionView sectionCompatibilityName;
    private SectionView sectionNotCompatibilityName;
    private SectionView sectionMiddleNameIsCombined;
    private SectionView sectionProfessions;
    private SectionView sectionCatholicBirthday;
    private SectionView sectionOrthodoxBirthday;
    private SectionView sectionHobbies;
    private SectionView sectionBadges;
    private SectionView sectionColors;
    private SectionView sectionPlant;
    private SectionView sectionAnimals;
    private SectionView sectionMineral;
    private SectionView sectionPlanet;
    private SectionView sectionSuccessfulDay;
    private SectionView sectionCelebrities;

    private MenuItem menuAddFavorite;
    private MenuItem menuDeleteFavorite;
    private NameObject nameObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names_details, container, false);

        mainActivity = (MainActivity) getActivity();
        mainActivity.invalidateOptionsMenu();
        setHasOptionsMenu(true);

        core = Core.getInstance();

        nameObject = (NameObject) getArguments().getSerializable(FactoryNames.ARG_OBJECT);
        if (nameObject == null) {
            getFragmentManager().popBackStack();
        }

        containerMain = (RelativeLayout) view.findViewById(R.id.containerDetails);
        textWarning = (TextView) view.findViewById(R.id.textWarning);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        sectionAvailableName = (SectionView) view.findViewById(R.id.sectionAvailableName);
        sectionHistory = (SectionView) view.findViewById(R.id.sectionHistory);
        sectionCharacter = (SectionView) view.findViewById(R.id.sectionCharacter);
        sectionCharacterTraits = (SectionView) view.findViewById(R.id.sectionCharacterTraits);
        sectionHealth = (SectionView) view.findViewById(R.id.sectionHealth);
        sectionSexuality = (SectionView) view.findViewById(R.id.sectionSexuality);
        sectionCompatibilityName = (SectionView) view.findViewById(R.id.sectionCompatibilityName);
        sectionNotCompatibilityName = (SectionView) view.findViewById(R.id.sectionNotCompatibilityName);
        sectionMiddleNameIsCombined = (SectionView) view.findViewById(R.id.sectionMiddleNameIsCombined);
        sectionProfessions = (SectionView) view.findViewById(R.id.sectionProfessions);
        sectionCatholicBirthday = (SectionView) view.findViewById(R.id.sectionCatholicBirthday);
        sectionOrthodoxBirthday = (SectionView) view.findViewById(R.id.sectionOrthodoxBirthday);
        sectionHobbies = (SectionView) view.findViewById(R.id.sectionHobbies);
        sectionBadges = (SectionView) view.findViewById(R.id.sectionBadges);
        sectionColors = (SectionView) view.findViewById(R.id.sectionColors);
        sectionPlant = (SectionView) view.findViewById(R.id.sectionPlant);
        sectionAnimals = (SectionView) view.findViewById(R.id.sectionAnimals);
        sectionMineral = (SectionView) view.findViewById(R.id.sectionMineral);
        sectionPlanet = (SectionView) view.findViewById(R.id.sectionPlanet);
        sectionSuccessfulDay = (SectionView) view.findViewById(R.id.sectionSuccessfulDay);
        sectionCelebrities = (SectionView) view.findViewById(R.id.sectionCelebrities);

        core.getFactoryNames().setOnGetNameListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mainActivity.getSupportActionBar().setTitle(nameObject.getName());
        mainActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.hideKeyboard();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_names_details_menu, menu);

        menuAddFavorite = menu.findItem(R.id.action_add_favorite);
        menuDeleteFavorite = menu.findItem(R.id.action_delete_favorite);

        menuAddFavorite.setEnabled(true);
        menuDeleteFavorite.setEnabled(true);

        core.getFactoryNames().loadFullNameObject(nameObject.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.action_add_favorite:
                core.getFactoryNames().addFavorite(nameObject);
                return true;
            case R.id.action_delete_favorite:
                core.getFactoryNames().deleteFavorite(nameObject);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetFavorite(boolean favorite) {
        menuAddFavorite.setVisible(!favorite);
        menuDeleteFavorite.setVisible(favorite);
        if (favorite) {
            Toast.makeText(core.getApplicationContext(), R.string.message_add_favorite, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(core.getApplicationContext(), R.string.message_delete_favorite, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetName(FullNameObject nameObject, boolean isFavorite) {
        if (nameObject != null) {
            containerMain.setBackgroundResource(R.color.colorBackgroundMain);
            menuAddFavorite.setVisible(!isFavorite);
            menuDeleteFavorite.setVisible(isFavorite);

            if (nameObject.getAvailableName() != null)
                sectionAvailableName.setTextAndData(getString(R.string.text_title_available_name), nameObject.getAvailableName());
            else
                sectionAvailableName.setVisibility(View.GONE);

            if (nameObject.getHistory() != null)
                sectionHistory.setTextAndData(getString(R.string.text_title_history), nameObject.getHistory());
            else
                sectionHistory.setVisibility(View.GONE);

            if (nameObject.getCharacter() != null)
                sectionCharacter.setTextAndData(getString(R.string.text_title_character), nameObject.getCharacter());
            else
                sectionCharacter.setVisibility(View.GONE);

            if (nameObject.getCharacterTraits() != null)
                sectionCharacterTraits.setTextAndData(getString(R.string.text_title_character_traits), nameObject.getCharacterTraits());
            else
                sectionCharacterTraits.setVisibility(View.GONE);

            if (nameObject.getHealth() != null)
                sectionHealth.setTextAndData(getString(R.string.text_title_health), nameObject.getHealth());
            else
                sectionHealth.setVisibility(View.GONE);

            if (nameObject.getSexuality() != null)
                sectionSexuality.setTextAndData(getString(R.string.text_title_sexuality), nameObject.getSexuality());
            else
                sectionSexuality.setVisibility(View.GONE);

            if (nameObject.getCompatibilityName() != null)
                sectionCompatibilityName.setTextAndData(getString(R.string.text_title_compatibility_name), nameObject.getCompatibilityName());
            else
                sectionCompatibilityName.setVisibility(View.GONE);

            if (nameObject.getNotCompatibilityName() != null)
                sectionNotCompatibilityName.setTextAndData(getString(R.string.text_title_not_compatibility_name), nameObject.getNotCompatibilityName());
            else
                sectionNotCompatibilityName.setVisibility(View.GONE);

            if (nameObject.getMiddleNameIsCombined() != null)
                sectionMiddleNameIsCombined.setTextAndData(getString(R.string.text_title_middle_name_is_combined), nameObject.getMiddleNameIsCombined());
            else
                sectionMiddleNameIsCombined.setVisibility(View.GONE);

            if (nameObject.getProfessions() != null)
                sectionProfessions.setTextAndData(getString(R.string.text_title_professions), nameObject.getProfessions());
            else
                sectionProfessions.setVisibility(View.GONE);

            if (nameObject.getCatholicBirthday() != null)
                sectionCatholicBirthday.setTextAndData(getString(R.string.text_title_catholic_birthday), nameObject.getCatholicBirthday());
            else
                sectionCatholicBirthday.setVisibility(View.GONE);

            if (nameObject.getOrthodoxBirthday() != null)
                sectionOrthodoxBirthday.setTextAndData(getString(R.string.text_title_orthodox_birthday), nameObject.getOrthodoxBirthday());
            else
                sectionOrthodoxBirthday.setVisibility(View.GONE);

            if (nameObject.getHobbies() != null)
                sectionHobbies.setTextAndData(getString(R.string.text_title_hobbies), nameObject.getHobbies());
            else
                sectionHobbies.setVisibility(View.GONE);

            if (nameObject.getBadges() != null)
                sectionBadges.setTextAndData(getString(R.string.text_title_badges), nameObject.getBadges());
            else
                sectionBadges.setVisibility(View.GONE);

            if (nameObject.getColors() != null)
                sectionColors.setTextAndData(getString(R.string.text_title_colors), nameObject.getColors());
            else
                sectionColors.setVisibility(View.GONE);

            if (nameObject.getPlant() != null)
                sectionPlant.setTextAndData(getString(R.string.text_title_plant), nameObject.getPlant());
            else
                sectionPlant.setVisibility(View.GONE);

            if (nameObject.getAnimals() != null)
                sectionAnimals.setTextAndData(getString(R.string.text_title_animals), nameObject.getAnimals());
            else
                sectionAnimals.setVisibility(View.GONE);

            if (nameObject.getMineral() != null)
                sectionMineral.setTextAndData(getString(R.string.text_title_mineral), nameObject.getMineral());
            else
                sectionMineral.setVisibility(View.GONE);

            if (nameObject.getPlanet() != null)
                sectionPlanet.setTextAndData(getString(R.string.text_title_planet), nameObject.getPlanet());
            else
                sectionPlanet.setVisibility(View.GONE);

            if (nameObject.getSuccessfulDay() != null)
                sectionSuccessfulDay.setTextAndData(getString(R.string.text_title_successful_day), nameObject.getSuccessfulDay());
            else
                sectionSuccessfulDay.setVisibility(View.GONE);

            if (nameObject.getCelebrities() != null)
                sectionCelebrities.setTextAndData(getString(R.string.text_title_celebrities),
                        core.getFactoryNames().showListData(nameObject.getCelebrities(), true));
            else
                sectionCelebrities.setVisibility(View.GONE);

        } else {
            scrollView.setVisibility(View.GONE);
            containerMain.setBackgroundResource(R.color.colorBackgroundEmpty);
            textWarning.setVisibility(View.VISIBLE);
        }
    }
}
