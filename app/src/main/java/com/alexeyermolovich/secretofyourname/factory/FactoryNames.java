package com.alexeyermolovich.secretofyourname.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.model.DaoMaster;
import com.alexeyermolovich.secretofyourname.model.DaoSession;
import com.alexeyermolovich.secretofyourname.model.FullNameObject;
import com.alexeyermolovich.secretofyourname.model.NameObject;
import com.alexeyermolovich.secretofyourname.model.NameObjectDao;
import com.alexeyermolovich.secretofyourname.model.NameObjectDaoDao;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ermolovich on 23.9.16.
 */

public class FactoryNames {

    private final String TAG = this.getClass().getName();
    public final static String ARG_OBJECT = "arg_names";
    public final static String DB_NAME = "FavoriteName";

    public final static String FIELD_NAME = "name";

    private final byte SEARCH_FOR_NAME = 0;
    private String[] searchFiels = new String[]{
            "available_name",
            "middle_name_is_combined"
    };

    private JSONObject jsonNames;

    private LinkedList<NameObject> listNames;
    private LinkedList<NameObject> listFavorite;
    private LinkedList<NameObject> listSearch;
    private FullNameObject nameObject;

    private SQLiteDatabase sqLiteDatabase;
    private Gson gson;

    private OnGetNameListListener onGetNameListListener;
    private OnGetNameListener onGetNameListener;
    private OnGetFavoriteNamesListener onGetFavoriteNamesListener;
    private OnSearchDataListener onSearchDataListener;

    public FactoryNames(Context context) {
        this.listSearch = new LinkedList<>();
        this.listNames = new LinkedList<>();
        this.listFavorite = new LinkedList<>();
        this.gson = new Gson();
        this.jsonNames = readJsonFromFile(context);
    }

    private JSONObject readJsonFromFile(Context context) {
        String line = null;
        JSONObject jsonObject = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("data.json")));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private NameObjectDaoDao openDao() {
        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(Core.getInstance().getApplicationContext(), DB_NAME, null);
        sqLiteDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getNameObjectDaoDao();
    }

    private void closeDao() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }


    /*All Names*/
    public void loadListNames() {
        listNames.clear();
        Observable<NameObject> nameObjectObservable = Observable.create(new Observable.OnSubscribe<NameObject>() {
            @Override
            public void call(Subscriber<? super NameObject> subscriber) {
                try {
                    JSONArray jsonArray = jsonNames.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        subscriber.onNext(gson.fromJson(jsonObject.toString(), NameObject.class));
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    try {
                        subscriber.onError(e);
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        nameObjectObservable.subscribe(initSubscriberNames());

    }

    private Subscriber<? super NameObject> initSubscriberNames() {
        return new Subscriber<NameObject>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted loadNameList");
                Collections.sort(listNames, new Comparator<NameObject>() {
                    @Override
                    public int compare(NameObject o1, NameObject o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                if (onGetNameListListener != null)
                    onGetNameListListener.onGetNames(true);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError loadNameList");
                e.printStackTrace();
                if (onGetNameListListener != null)
                    onGetNameListListener.onGetNames(false);
            }

            @Override
            public void onNext(NameObject nameObject) {
                listNames.add(nameObject);
                if (onGetNameListListener != null)
                    onGetNameListListener.onGetCountNames(listNames.size());
            }
        };
    }

    public List<NameObject> getListNames() {
        return listNames;
    }
    /*End All Names*/

    /*Favorite Names*/
    public void loadFavoriteNames() {
        NameObjectDaoDao nameObjectDaoDao = openDao();
        List<NameObjectDao> nameObjectDaos = nameObjectDaoDao.loadAll();
        listFavorite.clear();
        for (NameObjectDao nameObjectDao : nameObjectDaos) {
            listFavorite.add(new NameObject(nameObjectDao.getName(), nameObjectDao.getSex()));
        }
        Collections.sort(listFavorite, new Comparator<NameObject>() {
            @Override
            public int compare(NameObject o1, NameObject o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        if (onGetFavoriteNamesListener != null) {
            onGetFavoriteNamesListener.onGetFavoriteNames(true);
        }
    }

    public List<NameObject> getListFavoriteNames() {
        return this.listFavorite;
    }
    /*End Favorite Names*/

    /*Load Name*/
    public void loadFullNameObject(final String nameObject) {
        this.nameObject = null;
        Observable<FullNameObject> fullNameObjectObservable = Observable.create(new Observable.OnSubscribe<FullNameObject>() {
            @Override
            public void call(Subscriber<? super FullNameObject> subscriber) {
                try {
                    JSONArray jsonArray = jsonNames.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = null;
                        if (jsonObject.has(FIELD_NAME)) {
                            name = jsonObject.getString(FIELD_NAME);
                            if (nameObject.equals(name)) {
                                subscriber.onNext(initFullNameObject(jsonObject));
                            }
                        }
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    try {
                        subscriber.onError(e);
                    } catch (Exception ignored) {
                    }
                }
            }

            private FullNameObject initFullNameObject(JSONObject jsonObject) {
                return gson.fromJson(jsonObject.toString(), FullNameObject.class);
            }
        });

        fullNameObjectObservable.subscribe(initSubscriberFullName());
    }

    private Subscriber<? super FullNameObject> initSubscriberFullName() {
        return new Subscriber<FullNameObject>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted loadName - " + nameObject.getName());
                if (onGetNameListener != null) {
                    NameObjectDaoDao nameObjectDaoDao = openDao();
                    NameObjectDao load = nameObjectDaoDao.load(nameObject.getName());
                    onGetNameListener.onGetName(nameObject, load != null);
                    closeDao();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError loadName");
                e.printStackTrace();
                nameObject = null;
                if (onGetNameListener != null) {
                    onGetNameListener.onGetName(null, false);
                }
            }

            @Override
            public void onNext(FullNameObject object) {
                nameObject = object;
            }
        };
    }

    public void addFavorite(NameObject nameObject) {
        NameObjectDaoDao nameObjectDaoDao = openDao();
        NameObjectDao load = nameObjectDaoDao.load(nameObject.getName());
        if (load == null) {
            nameObjectDaoDao.insert(new NameObjectDao(nameObject.getName(), nameObject.getSexByte()));
            closeDao();
        }
        if (onGetNameListener != null) {
            onGetNameListener.onGetFavorite(true);
        }
        loadFavoriteNames();
    }

    public void deleteFavorite(NameObject nameObject) {
        NameObjectDaoDao nameObjectDaoDao = openDao();
        nameObjectDaoDao.delete(new NameObjectDao(nameObject.getName(), nameObject.getSexByte()));
        closeDao();
        if (onGetNameListener != null) {
            onGetNameListener.onGetFavorite(false);
        }
        loadFavoriteNames();
    }


    public String showListData(List<String> strings, boolean item) {
        String res = "";
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            s = s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toUpperCase());
            if (item) {
                res += " • ";
            }
            res += s;
            if (i != strings.size() - 1) {
                res += ",";
                if (item) {
                    res += "\n";
                } else {
                    res += " ";
                }
            } else {
                res += ".";
            }
        }
        return res;
    }
    /*End Load Name*/

    /*Search*/
    public List<NameObject> getListSearch() {
        return this.listSearch;
    }

    public void loadDataSearch(String search, byte searchType) {
        listSearch.clear();
        if (search != null && search.length() != 0) {
            searchData(formatString(search), searchType);
        } else {
            if (onSearchDataListener != null) {
                onSearchDataListener.onSearchResult(false);
            }
        }
    }

    public void searchData(final String search, final byte searchType) {
        Observable<NameObject> nameObjectObservable = Observable.create(new Observable.OnSubscribe<NameObject>() {
            @Override
            public void call(Subscriber<? super NameObject> subscriber) {
                try {
                    JSONArray jsonArray = jsonNames.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.has(searchFiels[searchType])) {
                            JSONArray items = jsonObject.getJSONArray(searchFiels[searchType]);
                            for (int j = 0; j < items.length(); j++) {
                                String s = formatString(items.getString(j));
                                if (s.contains(search)) {
                                    subscriber.onNext(gson.fromJson(jsonObject.toString(), NameObject.class));
                                    break;
                                }
                            }
                        }
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    try {
                        subscriber.onError(e);
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        nameObjectObservable.subscribe(initSubscriberSearch());
    }

    private String formatString(String s) {
        String res = null;
        res = s.replace("Ё", "Е");
        res = res.replace(" ", "");
        return res.toUpperCase();
    }

    private Subscriber<? super NameObject> initSubscriberSearch() {
        return new Subscriber<NameObject>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted search");
                if (onSearchDataListener != null) {
                    onSearchDataListener.onSearchResult(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError search");
                e.printStackTrace();
                if (onSearchDataListener != null) {
                    onSearchDataListener.onSearchResult(false);
                }
            }

            @Override
            public void onNext(NameObject object) {
                Log.d("TEST", object.getName());
                listSearch.add(object);
            }
        };
    }
    /*End Search*/


    public void setOnSearchDataListener(OnSearchDataListener onSearchDataListener) {
        this.onSearchDataListener = onSearchDataListener;
    }

    public void setOnGetNameListListener(OnGetNameListListener onGetNameListListener) {
        this.onGetNameListListener = onGetNameListListener;
    }

    public void setOnGetNameListener(OnGetNameListener onGetNameListener) {
        this.onGetNameListener = onGetNameListener;
    }

    public void setOnGetFavoriteNamesListener(OnGetFavoriteNamesListener onGetFavoriteNamesListener) {
        this.onGetFavoriteNamesListener = onGetFavoriteNamesListener;
    }

    public interface OnGetFavoriteNamesListener {
        void onGetFavoriteNames(boolean isSuccess);
    }

    public interface OnSearchDataListener {
        void onSearchResult(boolean isSuccess);
    }

    public interface OnGetNameListListener {
        void onGetNames(boolean isSuccess);

        void onGetCountNames(int count);
    }

    public interface OnGetNameListener {
        void onGetFavorite(boolean favorite);

        void onGetName(FullNameObject nameObject, boolean isFavorite);
    }
}
