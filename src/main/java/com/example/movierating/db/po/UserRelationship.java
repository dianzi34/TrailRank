package com.example.movierating.db.po;

import java.util.Date;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRelationship {
    private Integer relationshipId;

    private Integer followerId;

    private Integer followedId;

    private Boolean isactive;

    private Date createDate;

    private Date updateDate;

    public Integer getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}