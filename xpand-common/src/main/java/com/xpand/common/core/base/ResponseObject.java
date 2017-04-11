package com.xpand.common.core.base;


import com.xpand.common.core.page.Page;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/*
使用示例说明：
{
   		"meta": {
       	        	 "msg": "success",                     //返回的是错误提示信息
       		         "error": 0                            //0代表成功  1，代表失败，其他错误码自己定义
                },
        "result": {
                     "data":{} or []                       //多行记录返回数组
                     "page":{                              //page 是可选项，data为数组时才会有page，也可以无page信息（不分页）。
                            "currentPage"":1 ,             //当前页,目前系统有从0或1开始。统一1开始
                            "pageSize":10,                 //分页的数量
                            "totalPage":1,                 //总页数
                            "totalCount":10                //总行数
                        }
                   }
}
*/

/**
 * 系统返回值对象
 *
 */
public class ResponseObject implements Serializable {

    public static final int FAILURECODE=1;
    public static final int SUCCESSCODE=0;
    public static final Meta SUCCESS = new Meta(SUCCESSCODE, "成功"); //成功
    public static final Meta FAILURE = new Meta(FAILURECODE, "失败"); //成功

    private Meta meta; // error=0：成功,error=1:失败

    private Result result;//数据

    /**
     * 成功返回值对象
     *
     * @return ResponseData
     */
    public static ResponseObject success() {
        return new ResponseObject(ResponseObject.SUCCESS, null);
    }

    /**
     * 成功返回值对象
     *
     * @param data 数据
     * @return ResponseData
     */
    public static <T> ResponseObject success(T data) {
        if (data instanceof Page) {
            return success(((Page) data).getRecords(),
                    ((Page) data).getCurrent(),
                    ((Page) data).getSize(),
                    ((Page) data).getTotal());
        } else {
            return new ResponseObject(ResponseObject.SUCCESS, new Result(data, null));
        }
    }

    /**
     * 分页成功返回值对象
     *
     * @param data        数据
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @param totalCount  总行数
     * @return ResponseData
     */
    public static <T> ResponseObject success(T data, Integer currentPage, Integer pageSize, Integer totalCount) {
        return new ResponseObject(ResponseObject.SUCCESS, data, currentPage, pageSize, totalCount);
    }


    /**
     * 失败码/失败信息回值对象
     *
     * @return ResponseData
     */
    /*public static ResponseObject failure(HttpCode code) {
        return new ResponseObject(new Meta(code), null);
    }*/
    public static ResponseObject failure() {
        return new ResponseObject(FAILURE,null);
    }
    public static ResponseObject failure(String msg) {
        return new ResponseObject(new Meta(FAILURECODE,msg), null);
    }

    public static ResponseObject failure(int code, String msg) {
        return new ResponseObject(new Meta(code, msg), null);
    }

    public static ResponseObject failure(Exception ex) {
        return new ResponseObject(new Meta(FAILURECODE, ex.getMessage().toString()), null);
    }

    private <T> ResponseObject(Meta meta, T data, Integer currentPage, Integer pageSize, Integer totalCount) {
        this.meta = meta;
        if (data != null && currentPage != null && pageSize != null && totalCount != null) {
            this.result = new Result(data, new Pager(currentPage, pageSize, totalCount));
        }
    }


    private ResponseObject(Meta meta, Result result) {
        this.meta = meta;
        this.result = result;
    }

    public Meta getMeta() {
        return meta;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public static class Result<T> implements Serializable {
        private Pager page;
        private T data;

        private Result() {
        }

        private Result(T data, Pager page) {
            this.data = data;
            this.page = page;
        }

        public Pager getPage() {
            return page;
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("page", page)
                    .append("data", data)
                    .toString();
        }
    }

    public static class Meta implements Serializable {
        private int code; //错误码
        private String msg;  //提示信息

        private Meta() {
        }

        private Meta(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }


        public int getCode() {
            return code;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Meta meta = (Meta) o;

            return new EqualsBuilder()
                    .append(code, meta.code)
                    .append(msg, meta.msg)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(code)
                    .append(msg)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("error", code)
                    .append("msg", msg)
                    .toString();
        }
    }

    public static class Pager implements Serializable {
        private Integer currentPage;//当前页：统一从1开始
        private Integer pageSize = 10; //每页行数
        private Integer totalCount; //总行数
        private Integer totalPage = 0;//总页数

        private Pager() {
        }

        private Pager(Integer currentPage, Integer pageSize, Integer totalCount) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalCount = totalCount;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }


        public Integer getPageSize() {
            return pageSize;
        }


        public Integer getTotalCount() {
            return totalCount;
        }

        /**
         * 总页数
         *
         * @return
         */
        public Integer getTotalPage() {
            if (this.pageSize != null && this.pageSize > 0) {
                if (totalCount % pageSize == 0) {
                    totalPage = totalCount / pageSize;
                } else {
                    totalPage = totalCount / pageSize + 1;
                }
            }
            return totalPage;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("currentPage", currentPage)
                    .append("pageSize", pageSize)
                    .append("totalCount", totalCount)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("meta", meta)
                .append("result", result)
                .toString();
    }

}

