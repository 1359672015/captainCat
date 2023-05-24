//coding=gbk
package com.example.captaincat.api.core;

import com.example.captaincat.model.Gem;
import com.example.captaincat.model.Pet;
import com.example.captaincat.model.Thing;
import com.example.captaincat.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * api接口统一管理
 */
public interface ApiService {


    @GET("login")
    Call<RequestType<User>> login(@Query("account") String account, @Query("password") String password);

    @GET("getUserById")
    Call<RequestType<User>> getUserById(@Query("id") String id);

    @GET("register")
    Call<RequestType> register(@Query("account") String account,
                                     @Query("password") String pw,
                                     @Query("name") String name,
                                     @Query("email") String email,
                                     @Query("introduce") String introduce);
    @GET("update")
    Call<RequestType> update(@Query("id")String id,@Query("key")String key,@Query("value")String value);

    @GET("getAllThings")
    Call<List<Thing>> getAllThings();

    @GET("getBag")
    Call<List<Thing>> getBag(@Query("id")String id);

    @GET("addObjects")
    Call<RequestType> addThingNum(@Query("userId") String userId, @Query("objectId") String objectId, @Query("addNum") int addNum);

    @GET("spendCoins")
    Call<RequestType> spendMoney(@Query("userId") String userId ,@Query("spendNum")int spendNum);

    @GET("getPetInfo")
    Call<RequestType<Pet>> getPet(@Query("uId") int uid);

    @GET("feedPet")
    Call<RequestType> feedPet(@Query("pId") int pid ,@Query("mood") int mood ,@Query("energy")int energy);

    @GET("foundPet")
    Call<RequestType<Pet>> foundPet(@Query("uId") int uid , @Query("name") String name , @Query("look")int look);

    @GET("wear")
    Call<RequestType<String>> wear(@Query("uId") int uid , @Query("gemId") int gId, @Query("place") int place);


    @GET("tookOff")
    Call<RequestType<String>> tookOff(@Query("uId") int uid , @Query("place") int place);

    @GET("allGems")
    Call<List<Gem>> allGems();

    @GET("wearStatus")
    Call<RequestType<String>> getWearStatus(@Query("uId") int uid );

    @GET("uploadGame")
    Call<RequestType<String>> updateGame(@Query("uId") int uid,
                                         @Query("hit") int hit,
                                         @Query("shoot")int shoot,
                                         @Query("second") int time);
    @GET("getGame")
    Call<RequestType<Integer>> getGame(@Query("uId") int uid);
}
