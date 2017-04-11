//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xpand.common.core.page;

import com.xpand.common.core.utils.StringUtils;

import java.io.Serializable;

public class Pagination implements Serializable {
    private static final long serialVersionUID = 1L;
    private int total;
    private int size;
    private int pages;
    private int current;
    private boolean searchCount;
    private boolean openSort;
    private boolean optimizeCount;
    private String orderByField;
    private boolean isAsc;

    public Pagination() {
        this.size = 10;
        this.current = 1;
        this.searchCount = true;
        this.openSort = true;
        this.optimizeCount = false;
        this.isAsc = true;
    }

    public Pagination(int current, int size) {
        this(current, size, true);
    }

    public Pagination(int current, int size, boolean searchCount) {
        this(current, size, searchCount, true);
    }

    public Pagination(int current, int size, boolean searchCount, boolean openSort) {
        this.size = 10;
        this.current = 1;
        this.searchCount = true;
        this.openSort = true;
        this.optimizeCount = false;
        this.isAsc = true;
        if(current > 1) {
            this.current = current;
        }

        this.size = size;
        this.searchCount = searchCount;
        this.openSort = openSort;
    }

    protected static int offsetCurrent(int current, int size) {
        return current > 0?(current - 1) * size:0;
    }

    public int getOffsetCurrent() {
        return offsetCurrent(this.current, this.size);
    }

    public boolean hasPrevious() {
        return this.current > 1;
    }

    public boolean hasNext() {
        return this.current < this.pages;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        if(this.size == 0) {
            return 0;
        } else {
            this.pages = this.total / this.size;
            if(this.total % this.size != 0) {
                ++this.pages;
            }

            return this.pages;
        }
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return this.current;
    }

    public boolean isSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isOptimizeCount() {
        return this.optimizeCount;
    }

    public void setOptimizeCount(boolean optimizeCount) {
        this.optimizeCount = optimizeCount;
    }

    public String getOrderByField() {
        return this.orderByField;
    }

    public void setOrderByField(String orderByField) {
        if(StringUtils.isNotEmpty(orderByField)) {
            this.orderByField = orderByField;
        }

    }

    public boolean isOpenSort() {
        return this.openSort;
    }

    public void setOpenSort(boolean openSort) {
        this.openSort = openSort;
    }

    public boolean isAsc() {
        return this.isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public String toString() {
        return "Pagination { total=" + this.total + " ,size=" + this.size + " ,pages=" + this.pages + " ,current=" + this.current + " }";
    }
}
