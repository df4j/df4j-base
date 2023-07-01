package com.df4j.base.pojo;

public class PageDto {

    // 每页大小
    private int size;

    // 当前页 从1开始编号
    private int num;

    // 共计页数
    private int total;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static PageDto of(int size, int num) {
        PageDto pageDto = new PageDto();
        pageDto.setNum(num);
        pageDto.setSize(size);
        return pageDto;
    }

    public static PageDto of(int size, int num, int total) {
        PageDto pageDto = of(size, num);
        pageDto.setTotal(total);
        return pageDto;
    }
}
