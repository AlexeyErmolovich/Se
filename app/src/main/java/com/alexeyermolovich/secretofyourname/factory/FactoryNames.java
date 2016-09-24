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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public final static int DB_VERSION = 1;

    public final static String FIELD_NAME = "name";
    public final static String FIELD_SEX = "sex";

    private JSONObject jsonNames;

    private List<NameObject> listNames;
    private List<NameObject> listFavorite;
    private FullNameObject nameObject;

    private SQLiteDatabase sqLiteDatabase;
    private Gson gson;

    private OnGetNameListListener onGetNameListListener;
    private OnGetNameListener onGetNameListener;
    private OnGetFavoriteNamesListener onGetFavoriteNamesListener;

    public FactoryNames(Context context) {
        this.listNames = new ArrayList<>();
        this.listFavorite = new ArrayList<>();
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

    public void setOnGetNameListListener(OnGetNameListListener onGetNameListListener) {
        this.onGetNameListListener = onGetNameListListener;
    }

    public interface OnGetNameListListener {
        void onGetNames(boolean isSuccess);

        void onGetCountNames(int count);
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

    public void setOnGetFavoriteNamesListener(OnGetFavoriteNamesListener onGetFavoriteNamesListener) {
        this.onGetFavoriteNamesListener = onGetFavoriteNamesListener;
    }

    public interface OnGetFavoriteNamesListener {
        void onGetFavoriteNames(boolean isSuccess);
    }
    /*End Favorite Names*/

    /*Load Name*/
    public void setOnGetNameListener(OnGetNameListener onGetNameListener) {
        this.onGetNameListener = onGetNameListener;
    }

    public void loadFullNameObject(final String nameObject) {
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
                    onGetNameListener.onGetName(nameObject);

                    NameObjectDaoDao nameObjectDaoDao = openDao();
                    NameObjectDao load = nameObjectDaoDao.load(nameObject.getName());
                    onGetNameListener.onGetFavorite(load != null);
                    closeDao();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError loadName");
                e.printStackTrace();
                nameObject = null;
                if (onGetNameListener != null) {
                    onGetNameListener.onGetName(null);
                    onGetNameListener.onGetFavorite(false);
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


    public interface OnGetNameListener {
        void onGetFavorite(boolean favorite);

        void onGetName(FullNameObject nameObject);
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

}
