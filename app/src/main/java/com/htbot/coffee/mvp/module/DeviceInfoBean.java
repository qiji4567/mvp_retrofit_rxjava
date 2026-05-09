package com.htbot.coffee.mvp.module;

import com.google.gson.annotations.SerializedName;

public class DeviceInfoBean {

    private Integer operationStatus;
    private String serialNumber;
    private String name;
    private Integer id;
    @SerializedName("class")
    private String classX;
    private Integer trial;
    private Integer orgId;
    private Integer status;
    private int isTare;
    private String orgName;
    private String softwareVersion;
    private String padVersion;
    private String payType;
    private String doctorCoffee;
    private String milkFoam;
    private String syrupMaker;
    private String evebotPrinter;
    private String lattePrinter;
    private String iceMaker;
    private String picker;
    private String arm;
    private String os;
    private String sync;
    private String scanner;
    private String core1;
    private String core2;
    private String manager;
    private String hwManager;
    private String sensorHub;
    private String cleaner;
    private String recovery;
    private String display;
    private String diskSpace;
    private String modelName;
    private String diskAvailable;
    private String mcuVersion;
    private String coffeeApp;
    private Integer passwordlessLogin;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getPadVersion() {
        return padVersion;
    }

    public void setPadVersion(String padVersion) {
        this.padVersion = padVersion;
    }

    public int getIsTare() {
        return isTare;
    }

    public void setIsTare(int isTare) {
        this.isTare = isTare;
    }

    public Integer getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(Integer operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public Integer getTrial() {
        return trial;
    }

    public void setTrial(Integer trial) {
        this.trial = trial;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDoctorCoffee() {
        return doctorCoffee;
    }

    public void setDoctorCoffee(String value) {
        this.doctorCoffee = value;
    }

    public String getMilkFoam() {
        return milkFoam;
    }

    public void setMilkFoam(String value) {
        this.milkFoam = value;
    }

    public String getSyrupMaker() {
        return syrupMaker;
    }

    public void setSyrupMaker(String value) {
        this.syrupMaker = value;
    }

    public String getEvebotPrinter() {
        return evebotPrinter;
    }

    public void setEvebotPrinter(String value) {
        this.evebotPrinter = value;
    }

    public String getLattePrinter() {
        return lattePrinter;
    }

    public void setLattePrinter(String value) {
        this.lattePrinter = value;
    }

    public String getIceMaker() {
        return iceMaker;
    }

    public void setIceMaker(String value) {
        this.iceMaker = value;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String value) {
        this.picker = value;
    }

    public String getArm() {
        return arm;
    }

    public void setArm(String value) {
        this.arm = value;
    }


    public String getOs() {
        return os;
    }

    public void setOs(String value) {
        this.os = value;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String value) {
        this.sync = value;
    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String value) {
        this.scanner = value;
    }

    public String getCore1() {
        return core1;
    }

    public void setCore1(String value) {
        this.core1 = value;
    }

    public String getCore2() {
        return core2;
    }

    public void setCore2(String value) {
        this.core2 = value;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String value) {
        this.manager = value;
    }

    public String getHwManager() {
        return hwManager;
    }

    public void setHwManager(String value) {
        this.hwManager = value;
    }

    public String getSensorHub() {
        return sensorHub;
    }

    public void setSensorHub(String value) {
        this.sensorHub = value;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String value) {
        this.cleaner = value;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String value) {
        this.recovery = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String value) {
        this.display = value;
    }

    public String getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(String value) {
        this.diskSpace = value;
    }

    public String getDiskAvailable() {
        return diskAvailable;
    }

    public void setDiskAvailable(String value) {
        this.diskAvailable = value;
    }

    public String getMcuVersion() {
        return mcuVersion;
    }

    public void setMcuVersion(String value) {
        this.mcuVersion = value;
    }

    public String getCoffeeApp() {
        return coffeeApp;
    }

    public void setCoffeeApp(String value) {
        this.coffeeApp = value;
    }

    public Integer getPasswordlessLogin() {
        return passwordlessLogin;
    }

    public void setPasswordlessLogin(Integer passwordlessLogin) {
        this.passwordlessLogin = passwordlessLogin;
    }
}
