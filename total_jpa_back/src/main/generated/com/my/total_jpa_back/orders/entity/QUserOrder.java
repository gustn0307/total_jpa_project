package com.my.total_jpa_back.orders.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrder is a Querydsl query type for UserOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrder extends EntityPathBase<UserOrder> {

    private static final long serialVersionUID = 1276056462L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrder userOrder = new QUserOrder("userOrder");

    public final com.my.total_jpa_back.common.entity.QBaseEntity _super = new com.my.total_jpa_back.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productName = createString("productName");

    public final EnumPath<com.my.total_jpa_back.common.entity.OrderStatus> status = createEnum("status", com.my.total_jpa_back.common.entity.OrderStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.my.total_jpa_back.users.entity.QUsers user;

    public QUserOrder(String variable) {
        this(UserOrder.class, forVariable(variable), INITS);
    }

    public QUserOrder(Path<? extends UserOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrder(PathMetadata metadata, PathInits inits) {
        this(UserOrder.class, metadata, inits);
    }

    public QUserOrder(Class<? extends UserOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.my.total_jpa_back.users.entity.QUsers(forProperty("user")) : null;
    }

}

