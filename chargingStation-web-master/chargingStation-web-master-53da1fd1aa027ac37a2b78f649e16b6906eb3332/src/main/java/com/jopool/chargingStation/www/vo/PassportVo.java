package com.jopool.chargingStation.www.vo;

import com.jopool.chargingStation.www.base.entity.Passport;

/**
 * Created by synn on 2017/9/4.
 */
public class PassportVo extends Passport {
    private String pic;
    private String picId;

    public  PassportVo(){
     super();
    }
    public PassportVo(Passport passport){
        this.setId(passport.getId());
        this.setRealName(passport.getRealName());
        this.setUserName(passport.getUserName());
        this.setPhone(passport.getPhone());
        this.setCreationTime(passport.getCreationTime());
        this.setModifyTime(passport.getModifyTime());
        this.setStatus(passport.getStatus());
        this.setIsDeleted(passport.getIsDeleted());
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }
}
