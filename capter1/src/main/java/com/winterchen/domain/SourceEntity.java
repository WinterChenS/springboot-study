package com.winterchen.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/11/29.
 */
@Entity
public class SourceEntity {


    @Id
    @GeneratedValue
    private long sourceId;

    /**
     * @OneToOne：一对一关联
     * mappedBy = "source"：一对一配置参考了card
     * mappedBy = "source"中的User类中的getCard()中的Care(去除get)
     * 如果User类getResource()改为getSources(),这里就要写成：mappedBy = "sources"
     */
    @OneToOne(mappedBy = "sourceEntity", fetch= FetchType.EAGER)
    private UserEntity userEntity;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
