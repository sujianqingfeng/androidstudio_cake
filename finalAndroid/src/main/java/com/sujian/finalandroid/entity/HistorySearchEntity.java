package com.sujian.finalandroid.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

/**
 * 历史搜索实体  与数据库对应
 * Created by 12111 on 2016/5/12.
 */
@Table(name = "historysearch")
public class HistorySearchEntity {
    //历史搜索表的主键
    @Column(name = "id", isId = true)
    private int id;

    //历史搜索的内容
    @Column(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
