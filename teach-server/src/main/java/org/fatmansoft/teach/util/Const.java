package org.fatmansoft.teach.util;

import java.util.HashMap;

public class Const {
    public static int REGISTER_OK=1;
    //↑1
    public static int PAY_OK=1<<1;
    //↑2
    public static int DEMAND_OK=1<<2;
    //↑3
    public static int HOTEL_OK=1<<3;
    public static int TRANSPORT_OK=1<<4;
    //↑4
    public static int AWARD_OK=1<<5;
    //↑5
    public static int FEELING_OK=1<<6;
    public static int RECEIPT_OK=1<<7;
    public static int CERTIFICATE_OK=1<<8;
    //↑6
    public static int REIMBURSE_OK=1<<9;
    //↑7
    public static HashMap<String,Integer> Str2Int=new HashMap<String, Integer>() {{
            put("register",REGISTER_OK);
            put("pay",PAY_OK);
            put("demand",DEMAND_OK);
            put("hotel",HOTEL_OK);
            put("transport",TRANSPORT_OK);
            put("award",AWARD_OK);
            put("feeling",FEELING_OK);
            put("receipt",RECEIPT_OK);
            put("certificate",CERTIFICATE_OK);
            put("reimburse",REIMBURSE_OK);
    }};


    public static int toActive(int p){
        int res=0;
        if((p&REGISTER_OK)>0)res++;
        if((p&PAY_OK)>0)res++;
        if((p&DEMAND_OK)>0)res++;
        if((p&(HOTEL_OK|TRANSPORT_OK))==(HOTEL_OK|TRANSPORT_OK))res++;
        if((p&AWARD_OK)>0)res++;
        if((p&(FEELING_OK|RECEIPT_OK|CERTIFICATE_OK))==(FEELING_OK|RECEIPT_OK|CERTIFICATE_OK))res++;
        if((p&REIMBURSE_OK)>0)res+=2;
        return res;
    }
}
