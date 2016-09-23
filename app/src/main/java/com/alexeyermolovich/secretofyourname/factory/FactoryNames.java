package com.alexeyermolovich.secretofyourname.factory;

import android.content.Context;
import android.util.Log;

import com.alexeyermolovich.secretofyourname.model.NameObject;

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

    private JSONObject jsonNames;

    private List<NameObject> listNames;

    private OnGetNamesListener onGetNamesListener;

    public FactoryNames(Context context) {
        this.listNames = new ArrayList<>();
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

    public void loadListNames() {
        Observable<NameObject> nameObjectObservable = Observable.create(new Observable.OnSubscribe<NameObject>() {
            @Override
            public void call(Subscriber<? super NameObject> subscriber) {
                try {
                    JSONArray jsonArray = jsonNames.getJSONArray("names");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = null;
                        String sex = null;
                        if (jsonObject.has("name")) {
                            name = jsonObject.getString("name");
                        }
                        if (jsonObject.has("sex")) {
                            sex = jsonObject.getString("sex");
                        }
                        NameObject nameObject = new NameObject(name, sex);
                        subscriber.onNext(nameObject);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

        nameObjectObservable.subscribe(initSubscriberNames());

    }

    private Subscriber<? super NameObject> initSubscriberNames() {
        return new Subscriber<NameObject>() {
            @Override
            public void onCompleted() {
                Collections.sort(listNames, new Comparator<NameObject>() {
                    @Override
                    public int compare(NameObject o1, NameObject o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                if (onGetNamesListener != null)
                    onGetNamesListener.onGetNames(true);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (onGetNamesListener != null)
                    onGetNamesListener.onGetNames(false);
            }

            @Override
            public void onNext(NameObject nameObject) {
                Log.d(TAG, "name: " + nameObject.getName() + " sex: " + nameObject.getSex());
                listNames.add(nameObject);
                if (onGetNamesListener != null)
                    onGetNamesListener.onGetCountNames(listNames.size());
            }
        };
    }

    public List<NameObject> getListNames() {
        return listNames;
    }

    public void setOnGetNamesListener(OnGetNamesListener onGetNamesListener) {
        this.onGetNamesListener = onGetNamesListener;
    }

    public interface OnGetNamesListener {
        void onGetNames(boolean isSuccess);

        void onGetCountNames(int count);
    }

}
