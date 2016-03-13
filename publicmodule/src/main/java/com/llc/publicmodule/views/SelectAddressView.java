package com.llc.publicmodule.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.llc.publicmodule.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2015/12/3.
 */
public class SelectAddressView extends LinearLayout {
    RadioGroup mRadioGroup;
    ListView mlvProvince, mlvCity, mlvTown, mlvStreet;
    RadioButton mrbProvince, mrbCity, mrbTown, mrbStreet;
    String jsonStr = "";
    JSONArray mProvinceArray, mCityArray;
    String[] mTownArray, mStreetArray = {"全区"};
    ProvinceAdapter mProvinceAdapter;
    CityAdapter mCityAdapter;
    TownAdapter mTownAdapter;
    StreetAdapter mStreetAdapter;
    Handler mHandler;
    boolean[] selectFlags = {true, false, false, false};
    RadioButton[] radioButtons;
    String[] infoData = {"省", "市", "区", "街道"};
    ProgressBar mpbBarWaiting;
    public SelectAddressView(Context context) {
        super(context);
    }

    public SelectAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initData(context);
        setListener();
    }

    public String getAddress() {
        return mrbProvince.getText().toString() + "," + mrbCity.getText().toString() + "," + mrbTown.getText().toString() + "," + mrbStreet.getText().toString();
    }

    private void showListView(int id) {
        mlvTown.setVisibility(GONE);
        mlvCity.setVisibility(GONE);
        mlvProvince.setVisibility(GONE);
        mlvStreet.setVisibility(GONE);

        if (id == R.id.select_address_view_rb_province) {
            mlvProvince.setVisibility(VISIBLE);

        } else if (id == R.id.select_address_view_rb_city) {
            mlvCity.setVisibility(VISIBLE);

        } else if (id == R.id.select_address_view_rb_town) {
            mlvTown.setVisibility(VISIBLE);

        } else if (id == R.id.select_address_view_rb_street) {
            mlvStreet.setVisibility(VISIBLE);

        }

    }

    public interface OnAddressDoneSelectedListener {
        public void onSelectDone(String address);
    }

    OnAddressDoneSelectedListener onAddressDoneSelectedListener = null;

    public void setOnAddressDoneSelectedListener(OnAddressDoneSelectedListener listener) {
        onAddressDoneSelectedListener = listener;
    }

    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showListView(checkedId);
                int index = 1;
                if (checkedId == R.id.select_address_view_rb_province) {
                    index = 1;

                } else if (checkedId == R.id.select_address_view_rb_city) {
                    index = 2;

                } else if (checkedId == R.id.select_address_view_rb_town) {
                    index = 3;

                } else if (checkedId == R.id.select_address_view_rb_street) {
                    index = 4;

                }
                for (; index < 4; index++) {
                    selectFlags[index] = false;
                    radioButtons[index].setClickable(false);
                    radioButtons[index - 1].setText(infoData[index - 1]);
                    radioButtons[index].setText(infoData[index]);
                }
            }
        });
        mlvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFlags[1] = true;
                JSONObject jsonObject = (JSONObject) parent.getAdapter().getItem(position);
                try {
                    mCityArray = jsonObject.getJSONArray("city");
                    mrbProvince.setText(jsonObject.getString("name"));
                    mCityAdapter.setData(mCityArray);
                    mrbCity.setChecked(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        mlvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFlags[2] = true;
                mrbTown.setChecked(true);
                mrbCity.setClickable(true);
                JSONObject jsonObject = (JSONObject) parent.getAdapter().getItem(position);
                try {
                    mrbCity.setText(jsonObject.getString("name"));
                    mTownArray = jsonObject.getString("area").replace("\"", "").replace("[", "").replace("]", "").split(",");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTownAdapter.setData(mTownArray);
                    }
                });

            }
        });
        mlvTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFlags[3] = true;
                mrbStreet.setChecked(true);
                mrbTown.setClickable(true);
                mrbTown.setText((CharSequence) parent.getAdapter().getItem(position));
            }
        });
        mlvStreet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mrbStreet.setText((CharSequence) parent.getAdapter().getItem(position));
                if (onAddressDoneSelectedListener!=null) onAddressDoneSelectedListener.onSelectDone(getAddress());
            }
        });


    }

    public SelectAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.select_address_view, this);
        mRadioGroup = (RadioGroup) this.findViewById(R.id.select_address_view_radiogroup);
        mlvProvince = (ListView) this.findViewById(R.id.select_address_view_lv_province);
        mrbCity = (RadioButton) findViewById(R.id.select_address_view_rb_city);
        mrbProvince = (RadioButton) this.findViewById(R.id.select_address_view_rb_province);
        mrbStreet = (RadioButton) this.findViewById(R.id.select_address_view_rb_street);
        mrbTown = (RadioButton) this.findViewById(R.id.select_address_view_rb_town);
        mlvCity = (ListView) this.findViewById(R.id.select_address_view_lv_city);
        mlvTown = (ListView) this.findViewById(R.id.select_address_view_lv_town);
        mlvStreet = (ListView) this.findViewById(R.id.select_address_view_lv_street);
        mHandler = new Handler();
        radioButtons = new RadioButton[]{mrbProvince, mrbCity, mrbTown, mrbStreet};
        mpbBarWaiting= (ProgressBar) this.findViewById(R.id.select_address_view_pb_waiting);
    }

    private void initData(Context context) {
        initAddress(context);

        mProvinceAdapter = new ProvinceAdapter(context, mProvinceArray);
        mlvProvince.setAdapter(mProvinceAdapter);
        mCityAdapter = new CityAdapter(context, mCityArray);
        mlvCity.setAdapter(mCityAdapter);
        mTownAdapter = new TownAdapter(context, mTownArray);
        mlvTown.setAdapter(mTownAdapter);
        mStreetAdapter = new StreetAdapter(context, mStreetArray);
        mlvStreet.setAdapter(mStreetAdapter);
    }

    private void initAddress(final Context context) {
        new Thread(new Runnable() {

            StringBuffer jsonData=new StringBuffer();
            @Override
            public void run() {
                InputStreamReader inputReader = null;
                try {
                    inputReader = new InputStreamReader(context.getResources().getAssets().open("cities.html"));
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line = "";
                    char [] tempStr=new char[1024];
                   while( bufReader.read(tempStr,0,tempStr.length)!=-1){
                       jsonData.append(tempStr);
                   }
                    mProvinceArray = new JSONArray(jsonData.toString());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProvinceAdapter.setData(mProvinceArray);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (inputReader != null) {
                    try {
                        inputReader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
        ).start();


    }

    class ProvinceAdapter extends BaseAdapter {
        Context context;
        JSONArray array;

        public void setData(JSONArray array) {
            this.array = array;
            if (array==null||array.length()==0){
                mpbBarWaiting.setVisibility(VISIBLE);
            }else{
                mpbBarWaiting.setVisibility(GONE);
            }
            notifyDataSetChanged();

        }

        public ProvinceAdapter(Context context, JSONArray array) {
            this.context = context;
            this.array = array;
            if (array==null||array.length()==0){
                mpbBarWaiting.setVisibility(VISIBLE);
            }else{
                mpbBarWaiting.setVisibility(GONE);
            }
        }

        @Override
        public int getCount() {
            if (array == null) return 0;
            return array.length();
        }

        @Override
        public JSONObject getItem(int position) {

            try {
                return array.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View layout, ViewGroup parent) {
            layout = View.inflate(context, android.R.layout.simple_list_item_1, null);
            try {
                ((TextView) layout).setText(getItem(position).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
                layout.setVisibility(View.GONE);
            }
            return layout;
        }
    }

    class CityAdapter extends BaseAdapter {
        Context context;
        JSONArray array;

        public void setData(JSONArray array) {
            this.array = array;
            notifyDataSetChanged();

        }

        public CityAdapter(Context context, JSONArray array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public int getCount() {
            if (array == null) return 0;
            return array.length();
        }

        @Override
        public JSONObject getItem(int position) {

            try {
                return array.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View layout, ViewGroup parent) {
            layout = View.inflate(context, android.R.layout.simple_list_item_1, null);
            try {
                ((TextView) layout).setText(getItem(position).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
                layout.setVisibility(View.GONE);
            }
            return layout;
        }
    }

    class TownAdapter extends BaseAdapter {
        Context context;
        String[] array;

        public void setData(String[] array) {
            this.array = array;
            notifyDataSetChanged();
        }

        public TownAdapter(Context context, String[] array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public int getCount() {
            if (array == null) return 0;
            return array.length;
        }

        @Override
        public String getItem(int position) {
            return array[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View layout, ViewGroup parent) {
            layout = View.inflate(context, android.R.layout.simple_list_item_1, null);
            ((TextView) layout).setText(getItem(position));
            return layout;
        }
    }

    class StreetAdapter extends BaseAdapter {
        Context context;
        String[] array;

        public StreetAdapter(Context context, String[] array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public int getCount() {
            if (array == null) return 0;
            return array.length;
        }

        @Override
        public String getItem(int position) {
            return array[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View layout, ViewGroup parent) {
            layout = View.inflate(context, android.R.layout.simple_list_item_1, null);
            ((TextView) layout).setText(getItem(position));
            return layout;
        }
    }


}
