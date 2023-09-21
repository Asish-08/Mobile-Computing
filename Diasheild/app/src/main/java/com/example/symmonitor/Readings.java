package com.example.symmonitor;

public class Readings {
    String user_name;
    Double SHORT_BREATH;
    Double FEEL_TIRED;
    Double HEAD_ACHE;
    Double DIARRHEA;
    Double SOAR_THROAT;
    Double FEVER ;
    Double MUSCLE_ACHE ;
    Double NO_SMELL_TASTE ;
    Double RESP_RATE;
    Double COUGH ;
    Double HEART_RATE ;
    Double NAUSEA ;

    public Readings(String user_name, double RESP_RATE, double HEART_RATE, Double NAUSEA, double HEAD_ACHE, double DIARRHEA, double SOAR_THROAT, double FEVER, double MUSCLE_ACHE, double NO_SMELL_TASTE, double COUGH, double SHORT_BREATH, double FEEL_TIRED) {
        this.user_name = user_name;
        this.HEAD_ACHE = HEAD_ACHE;
        this.DIARRHEA = DIARRHEA;
        this.FEVER = FEVER;
        this.MUSCLE_ACHE = MUSCLE_ACHE;
        this.SOAR_THROAT = SOAR_THROAT;
        this.RESP_RATE = RESP_RATE;
        this.SHORT_BREATH = SHORT_BREATH;
        this.FEEL_TIRED = FEEL_TIRED;
        this.NO_SMELL_TASTE = NO_SMELL_TASTE;
        this.COUGH = COUGH;
        this.HEART_RATE = HEART_RATE;
        this.NAUSEA = NAUSEA;
    }


    public String getUser_name() {
        return user_name;
    }


    public Double getRESP_RATE() {
        return RESP_RATE;
    }


    public Double getSOAR_THROAT() {
        return SOAR_THROAT;
    }




    public Double getNO_SMELL_TASTE() {
        return NO_SMELL_TASTE;
    }



    public Double getCOUGH() {
        return COUGH;
    }



    public Double getSHORT_BREATH() {
        return SHORT_BREATH;
    }


    public Double getFEEL_TIRED() {
        return FEEL_TIRED;
    }


    public Double getHEART_RATE() {
        return HEART_RATE;
    }



    public Double getFEVER() {
        return FEVER;
    }



    public Double getMUSCLE_ACHE() {
        return MUSCLE_ACHE;
    }


    public Double getNAUSEA() {
        return NAUSEA;
    }



    public Double getHEAD_ACHE() {
        return HEAD_ACHE;
    }



    public Double getDIARRHEA() {
        return DIARRHEA;
    }



    public void setSOAR_THROAT(Double SOAR_THROAT) {
        this.SOAR_THROAT = SOAR_THROAT;
    }

    public void setFEVER(Double FEVER) {
        this.FEVER = FEVER;
    }

    public void setFEEL_TIRED(Double FEEL_TIRED) {
        this.FEEL_TIRED = FEEL_TIRED;
    }

    public void setNAUSEA(Double NAUSEA) {
        this.NAUSEA = NAUSEA;
    }

    public void setMUSCLE_ACHE(Double MUSCLE_ACHE) {
        this.MUSCLE_ACHE = MUSCLE_ACHE;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setRESP_RATE(Double RESP_RATE) {
        this.RESP_RATE = RESP_RATE;
    }

    public void setHEART_RATE(Double HEART_RATE) {
        this.HEART_RATE = HEART_RATE;
    }

    public void setNO_SMELL_TASTE(Double NO_SMELL_TASTE) {
        this.NO_SMELL_TASTE = NO_SMELL_TASTE;
    }

    public void setCOUGH(Double COUGH) {
        this.COUGH = COUGH;
    }

    public void setSHORT_BREATH(Double SHORT_BREATH) {
        this.SHORT_BREATH = SHORT_BREATH;
    }

    public void setHEAD_ACHE(Double HEAD_ACHE) {
        this.HEAD_ACHE = HEAD_ACHE;
    }

    public void setDIARRHEA(Double DIARRHEA) {
        this.DIARRHEA = DIARRHEA;
    }
}
