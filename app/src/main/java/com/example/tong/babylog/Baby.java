package com.example.tong.babylog;

import java.util.Date;

/**
 * Created by Tong on 10/29/15.
 */
public class Baby {
    String babyName;
    String babyGender;
    Date babyBirthday;

    public Baby(String babyName, String babyGender, Date babyBirthday){
        this.babyBirthday= babyBirthday;
        this.babyGender = babyGender;
        this.babyName = babyName;
    }


}
