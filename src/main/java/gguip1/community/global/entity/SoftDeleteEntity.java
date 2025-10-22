package gguip1.community.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class SoftDeleteEntity extends BaseEntity{

    @Column(name = "status", nullable = false)
    protected Byte status = 0;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    public void softDelete() {
        this.status = 1;
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.status = 0;
        this.deletedAt = null;
    }
}
