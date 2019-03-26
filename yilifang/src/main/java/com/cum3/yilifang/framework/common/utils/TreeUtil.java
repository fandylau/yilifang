package com.cum3.yilifang.framework.common.utils;

/**
 * 树工具
 * @version 2.0
 */
public class TreeUtil {

    /**
     * 获取内码的级数
     * @param isn
     *            内码
     * @return
     */
    public static int getIsnRank(String isn) {
        return isn == null || isn.equals("") ? 0 : isn.split("\\.").length;
    }

    /**
     * 获取父级内码
     * @param isn
     *            内码
     * @return
     */
    public static String getParentIsn(String isn) {
        if (getIsnRank(isn) > 1) {
            return isn.substring(0, isn.lastIndexOf("."));
        } else {
            return "";
        }
    }

    /**
     * 获取本级内码
     * @param isn
     *            内码
     * @return
     */
    public static String getSelfIsn(String isn) {
        if (isn == null || isn.equals("")) {
            return "";
        }

        int index = isn.lastIndexOf(".");

        return index == -1 ? isn : isn.substring(index + 1);
    }

    /**
     * 获取顶级内码
     * @param isn
     *            内码
     * @return
     */
    public static String getTopIsn(String isn) {
        if (isn == null || isn.equals("")) {
            return "";
        }

        int index = isn.indexOf(".");

        return index == -1 ? isn : isn.substring(0, index);
    }

   

}
