package com.yil.organization.model;

import com.yil.organization.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(schema = "ORG",
        name = "ORGANIZATION_USER",
        indexes = {
                @Index(name = "IDX_ORGANIZATION_USER_USER_ID", columnList = "USER_ID"),
                @Index(name = "IDX_ORGANIZATION_USER_ORGANIZATION_ID", columnList = "ORGANIZATION_ID")
        })
public class OrganizationUser implements IEntity {

    @EmbeddedId
    private Pk id;
    @Comment("Organizasyonun yöneticisi mi ?")
    @ColumnDefault("0")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "MANAGER", nullable = false)
    private Boolean manager;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME", updatable = false)
    private Date createdTime;
    @Column(name = "CREATED_USER_ID", updatable = false)
    private Long createdUserId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DATE")
    private Date lastModifyDate;
    @Column(name = "LAST_MODIFY_USER_ID")
    private Long lastModifyUserId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "ORGANIZATION_ID", nullable = false)
        private Long organizationId;
        @Column(name = "USER_ID", nullable = false)
        private Long userId;
    }
}
