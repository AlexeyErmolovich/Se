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
            sectionAvailableName.setTextAndData(getString(R.string.text_title_available_name),
                    core.getFactoryNames().showListData(nameObject.getAvailableName(), false));
            sectionHistory.setTextAndData(getString(R.string.text_title_history), nameObject.getHistory());
            sectionCharacter.setTextAndData(getString(R.string.text_title_character), nameObject.getCharacter());
            sectionCharacterTraits.setTextAndData(getString(R.string.text_title_character_traits),
                    core.getFactoryNames().showListData(nameObject.getCharacterTraits(), false));
            sectionHealth.setTextAndData(getString(R.string.text_title_health), nameObject.getHealth());
            sectionSexuality.setTextAndData(getString(R.string.text_title_sexuality), nameObject.getSexuality());
            sectionCompatibilityName.setTextAndData(getString(R.string.text_title_compatibility_name),
                    core.getFactoryNames().showListData(nameObject.getCompatibilityName(), false));
            sectionNotCompatibilityName.setTextAndData(getString(R.string.text_title_not_compatibility_name),
                    core.getFactoryNames().showListData(nameObject.getNotCompatibilityName(), false));
            sectionMiddleNameIsCombined.setTextAndData(getString(R.string.text_title_middle_name_is_combined),
                    core.getFactoryNames().showListData(nameObject.getMiddleNameIsCombined(), false));
            sectionProfessions.setTextAndData(getString(R.string.text_title_professions), nameObject.getProfessions());
            sectionCatholicBirthday.setTextAndData(getString(R.string.text_title_catholic_birthday), nameObject.getCatholicBirthday());
            sectionOrthodoxBirthday.setTextAndData(getString(R.string.text_title_orthodox_birthday), nameObject.getOrthodoxBirthday());
            sectionHobbies.setTextAndData(getString(R.string.text_title_hobbies), nameObject.getHobbies());
            sectionBadges.setTextAndData(getString(R.string.text_title_badges),
                    core.getFactoryNames().showListData(nameObject.getBadges(), false));
            sectionColors.setTextAndData(getString(R.string.text_title_colors),
                    core.getFactoryNames().showListData(nameObject.getColors(), false));
            sectionPlant.setTextAndData(getString(R.string.text_title_plant),
                    core.getFactoryNames().showListData(nameObject.getPlant(), false));
            sectionAnimals.setTextAndData(getString(R.string.text_title_animals),
                    core.getFactoryNames().showListData(nameObject.getAnimals(), false));
            sectionMineral.setTextAndData(getString(R.string.text_title_mineral),
                    core.getFactoryNames().showListData(nameObject.getMineral(), false));
            sectionPlanet.setTextAndData(getString(R.string.text_title_planet),
                    core.getFactoryNames().showListData(nameObject.getPlanet(), false));
            sectionSuccessfulDay.setTextAndData(getString(R.string.text_title_successful_day),
                    core.getFactoryNames().showListData(nameObject.getSuccessfulDay(), false));
            sectionCelebrities.setTextAndData(getString(R.string.text_title_celebrities),
                    core.getFactoryNames().showListData(nameObject.getCelebrities(), true));
        } else {
            scrollView.setVisibility(View.GONE);
            containerMain.setBackgroundResource(R.color.colorBackgroundEmpty);
            textWarning.setVisibility(View.VISIBLE);
        }
    }
}
