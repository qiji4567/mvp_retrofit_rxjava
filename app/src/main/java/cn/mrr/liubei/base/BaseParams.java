package cn.mrr.liubei.base;

import java.util.ArrayList;
import java.util.List;

public class BaseParams {

    public static int noticeCount = 0; //通知公告数量 ,
    private static List<String> noticeCountIds; //通知公告新增ID ,
    public static int todoCount = 0; //待办事项数量
    private static List<String> todoCountIds; //待办事项新增ID ,

    public static int myTaskCount = 0; //待我审批数量 ,
    public static int newsCount = 0; //消息数量 ,

    public static boolean isMyResum = false;

    public static boolean matterFragmentCreat = false;


    public static List<String> getTodoCountIds(){
        if (null == todoCountIds){
            todoCountIds = new ArrayList<>();
        }
        return todoCountIds;
    }


    public static List<String> getNoticeCountIdss(){
        if (null == noticeCountIds){
            noticeCountIds = new ArrayList<>();
        }
        return noticeCountIds;
    }



}

