package com.smic.cf.constants;
/**
 * 枚举类，用来定义排序的参数
 *
 * @return
 * @author 蔡明涛
 * @date 2020/4/1 21:52
 */
public enum SortItem {
    /*
     *  当前确诊
     */
    CURRENT_CONFIRMED("currentConfirmedCount", "current_confirmed_count"),
    /*
     *  累计确诊
     */
    CONFIRMED("confirmedCount", "confirmed_count"),
    /*
     *  治愈病例
     */
    CURED("curedCount", "cured_count"),
    /*
     *  死亡病例
     */
    DEAD("deadCount", "dead_count"),
    /*
     *  死亡率%'
     */
    DEAD_RATE("deadRate", "dead_rate+0");


    private String field;
    private String column;

    private SortItem(String field, String column) {
        this.field = field;
        this.column = column;
    }

    public static String getColumn(String field) {
        SortItem[] sortItems = values();
        for (SortItem sortItem : sortItems) {
            if (sortItem.field.equals(field)){
                return sortItem.column();
            }
        }
        return null;
    }

    public static String getField(String column) {
        SortItem[] sortItems = values();
        for (SortItem sortItem : sortItems) {
            if (sortItem.column().equals(column)){
                return sortItem.field();
            }
        }
        return null;
    }

    public void setField(String field) {
        this.field = field;
    }

    private String field(){
        return this.field;
    }

    private String column(){
        return this.column;
    }

}


