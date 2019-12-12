package edisonbro.com.edisonbroautomation.MutatorMethods;

/**
 * Created by shreeshail on 19/12/18.
 */

public class DeviceListArray {
    private String DeviceNumber;
    private String DeviceShortName;
    private String DeviceType;
    private String DeviceName;
    private Integer DeviceImage;
    private String DeviceRoomNumber;
    private String DeviceRoomName;
    private String DeviceId;
    private String devgroupid;

    public DeviceListArray(String DeviceNumber,String DeviceShortName,String DeviceType,String DeviceName,Integer DeviceImage,String DeviceRoomNumber,String DeviceRoomName,String DeviceId,String devgroupid){

        this.DeviceNumber = DeviceNumber ;
        this.DeviceShortName = DeviceShortName ;
        this.DeviceType = DeviceType ;
        this.DeviceName = DeviceName ;
        this.DeviceImage = DeviceImage ;
        this.DeviceRoomNumber = DeviceRoomNumber ;
        this.DeviceRoomName = DeviceRoomName ;
        this.DeviceId = DeviceId ;
        this.devgroupid = devgroupid ;

    }

    public String getDeviceNumber(){
        return  this.DeviceNumber;
    }
    public void setDeviceNumber(String DeviceNumber){
        this.DeviceNumber = DeviceNumber ;
    }


    public String getDeviceType(){
        return  this.DeviceType;
    }
    public void setDeviceType(String DeviceType){
        this.DeviceType = DeviceType ;
    }

    public String getDeviceName(){
        return  this.DeviceName;
    }
    public void setDeviceName(String DeviceName){
        this.DeviceName = DeviceName ;
    }

    public Integer getDeviceImage(){
        return  this.DeviceImage;
    }
    public void setDeviceName(Integer DeviceImage){
        this.DeviceImage = DeviceImage ;
    }

    public String getDeviceRoomNumber(){
        return  this.DeviceRoomNumber;
    }
    public void setDeviceRoomNumber(String DeviceRoomNumber){
        this.DeviceRoomNumber = DeviceRoomNumber ;
    }

    public String getDeviceRoomName(){
        return  this.DeviceRoomName;
    }
    public void setDeviceRoomName(String DeviceRoomName){
        this.DeviceRoomName = DeviceRoomName ;
    }

    public String getDeviceId(){
        return  this.DeviceId;
    }
    public void setDeviceId(String DeviceId){
        this.DeviceId = DeviceId ;
    }

    public String getDeviceShortName(){
        return  this.DeviceShortName;
    }
    public void setDeviceShortName(String DeviceShortName){
        this.DeviceShortName = DeviceShortName ;
    }

    public String getDevgroupId(){
        return  this.devgroupid;
    }
    public void setDevgroupId(String devgroupid){
        this.devgroupid = devgroupid ;
    }

}
