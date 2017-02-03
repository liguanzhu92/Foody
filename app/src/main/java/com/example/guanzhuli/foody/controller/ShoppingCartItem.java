package com.example.guanzhuli.foody.controller;
// Done by Xiao.

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.foody.CartPage.CartActivity;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.model.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.guanzhuli.foody.controller.DBHelper.TABLENAME;

/**
 * Created by liuxi on 2017/1/16.
 */

public class ShoppingCartItem {
    private static ArrayList<Integer> foodsId;
    private static HashMap<Food, Integer> foodMap;
    private static ShoppingCartItem instance = null;
    private int totalNumber;
    private int totalPrice;

    ShoppingCartItem(){

        foodsId = new ArrayList<Integer>();
        foodMap = new HashMap<Food, Integer>();
        totalNumber = 0;
        totalPrice = 0;
    }

    ShoppingCartItem(ArrayList<Integer> foodsId, HashMap<Food, Integer> foodMap, int totalNumber, int totalPrice){
        this.foodsId = foodsId;
        this.foodMap = foodMap;
        this.totalNumber = totalNumber;
        this.totalPrice = totalPrice;
    }

    public void clear(){
        foodMap.clear();
        totalNumber = 0;
        totalPrice = 0;
        foodsId.clear();
    }

    public ArrayList<Integer> getFoodInCart(){
        return foodsId;
    }

    public void setNull(){
        instance = null;
    }

    public static synchronized ShoppingCartItem getInstance(Context context){
        if (instance == null){
            Cursor cursor = DBManipulation.getInstance(context).getDb()
                    .rawQuery("SELECT * FROM " + TABLENAME, null);
            if (cursor.getCount() > 0){
                final ArrayList<Integer> idList = new ArrayList<Integer>();
                final HashMap<Food, Integer> foodInDb = new HashMap<Food, Integer>();
                int totalNumberInDb = 0;
                int totalPriceInDb = 0;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String curName = cursor.getString(cursor.getColumnIndex(DBHelper.ITEMNAME));
                    final int curId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ITEMID)));
                    final int curQuantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                    double curPrice = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE)));
                    String curCategory = cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY));
                    String curUrl = cursor.getString(cursor.getColumnIndex(DBHelper.IMAGEURL));
                    final Food curFood = new Food();
                    curFood.setId(curId);
                    curFood.setName(curName);
                    curFood.setPrice(curPrice);
                    curFood.setCategory(curCategory);
                    curFood.setImageUrl(curUrl);
                    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
                    imageLoader.get(curFood.getImageUrl(), new ImageLoader.ImageListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("INIT FOOD", "Image Load Error: " + error.getMessage());
                        }
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                            if (response.getBitmap() != null) {
                                curFood.setImage(response.getBitmap());
                                idList.add(curId);
                                foodInDb.put(curFood, curQuantity);
                            }
                        }
                    });
                    totalNumberInDb += curQuantity;
                    totalPriceInDb += (curFood.getPrice() * curQuantity);
                    cursor.moveToNext();
                }
                DBManipulation.getInstance(context).deleteAll();
                instance = new ShoppingCartItem(idList, foodInDb, totalNumberInDb, totalPriceInDb);
            }
            else {
                instance = new ShoppingCartItem();
            }
        }
        return instance;
    }

    public void addToCart(Food food){
        if (foodMap.containsKey(food)){
            int curNum = foodMap.get(food) + 1;
            foodMap.put(food, curNum);
        }
        else {
            foodsId.add(food.getId());
            foodMap.put(food, 1);
        }
        totalPrice += food.getPrice();
        totalNumber++;
    }

    public Food getFoodById(int id){
        if (foodsId.contains(id)){
            Iterator it = foodMap.keySet().iterator();
            while (it.hasNext()){
                Food curFood = (Food) it.next();
                int foodNumber = foodMap.get(curFood);
                if (curFood.getId() == id){
                    return curFood;
                }
            }
        }
        Log.e("GET FOOD BY ID", "No such item found");
        return null;
    }

    public void setFoodNumber(Food food, int number){
        int curNumber = foodMap.get(food);
        int numberChange = Math.abs(curNumber - number);
        if (number > curNumber){
            totalNumber += numberChange;
            totalPrice += numberChange * food.getPrice();
        }
        else{
            totalNumber -= numberChange;
            totalPrice -= numberChange * food.getPrice();
        }
        if (number == 0){
            foodMap.remove(food);
            for (int i=0; i<foodsId.size(); i++){
                if (foodsId.get(i) == food.getId()){
                    foodsId.remove((Object)foodsId.get(i));
                    break;
                }
            }
            return;
        }
        foodMap.put(food, number);
    }

    public double getPrice(){
        return totalPrice;
    }

    public int getFoodNumber(Food food){
        if (foodMap.containsKey(food)){
            return foodMap.get(food);
        }
        return 0;
    }


    public int getSize(){
        return totalNumber;
    }

    public int getFoodTypeSize(){
        return foodsId.size();
    }


    public void placeOrder(String addr, String mobile){
        Iterator it = foodMap.keySet().iterator();
        while (it.hasNext()){
            Food curFood = (Food) it.next();
            int cnt = foodMap.get(curFood);
            Log.e(curFood.getName(), "" + cnt);
            objRequestMethod(curFood, cnt, addr, mobile);
        }
    }


    private void objRequestMethod(Food curFood, int cnt, String addr, String mobile){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, buildUrl(curFood, cnt, addr, mobile), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Place_Order", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("Place_Order", "ERROR" + volleyError.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private String buildUrl(Food food, int cnt, String addr, String mobile) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new  Date(System.currentTimeMillis());
        String curTime = formatter.format(curDate);
        String tmpUrl = "http://rjtmobile.com/ansari/fos/fosapp/order_request.php?"
                + "&order_category=" + food.getCategory()
                + "&order_name=" + food.getName()
                + "&order_quantity=" + cnt
                + "&total_order=" + (cnt * food.getPrice())
                + "&order_delivery_add=" + addr
                + "&order_date=" + curTime
                + "&user_phone=" + mobile;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<tmpUrl.length(); i++){
            if (tmpUrl.charAt(i) == ' '){
                sb.append("%20");
            }
            else {
                sb.append(tmpUrl.charAt(i));
            }
        }
        Log.e("Build URL", sb.toString());
        return sb.toString();
    }

    public void addToDb(Context context){
        DBManipulation.getInstance(context).addAll();
    }

}
