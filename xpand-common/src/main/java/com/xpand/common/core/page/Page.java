//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xpand.common.core.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xpand.common.core.utils.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Page<T> extends Pagination {
    private static final long serialVersionUID = 1L;
    private List<T> records = Collections.emptyList();
    private Map<String, Object> condition = new ConcurrentHashMap();

    public Page() {
    }

    public Page(HttpServletRequest request, HttpServletResponse response) {
        InputStream in=null;
        StringBuffer str=new StringBuffer();
        try {
            in=request.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String s=null;
            while ((s=bufferedReader.readLine())!=null){
                str.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null){in.close();};
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject= JSON.parseObject(str.toString());

        String current=null;
        String size=null;
        if(jsonObject!=null){
            current = jsonObject.getString("current")!=null? jsonObject.getString("current"):getAttr(request,"current");
            size = jsonObject.getString("size")!=null? jsonObject.getString("size"):getAttr(request,"size");
        }
        current=StringUtils.isEmpty(current)?"0":current;
        size=StringUtils.isEmpty(size)?"10":size;
        this.setSize(Integer.valueOf(size));
        this.setCurrent(Integer.valueOf(current));
    }

    protected String getAttr(ServletRequest request,String attrKey) {
        if (StringUtils.isBlank(attrKey)) {
            attrKey = StringUtils.toString(request.getAttribute(attrKey), StringUtils.EMPTY);
        }
        return attrKey;
    }

    public Page(int current, int size) {
        super(current, size);
    }

    public Page(int current, int size, String orderByField) {
        super(current, size);
        this.setOrderByField(orderByField);
    }

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Map<String, Object> getCondition() {
        return this.condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public String toString() {
        StringBuilder pg = new StringBuilder();
        pg.append(" Page:{ [").append(super.toString()).append("], ");
        if(this.records != null) {
            pg.append("records-size:").append(this.records.size());
        } else {
            pg.append("records is null");
        }

        return pg.append(" }").toString();
    }
}
