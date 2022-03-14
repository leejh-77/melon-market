package app.melon.domain.models.chat;

import app.melon.domain.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from", nullable = false)
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to", nullable = false)
    private User to;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static Chat create(User from, User to, String message, LocalDateTime createdAt) {
        Chat c = new Chat();
        c.from = from;
        c.to = to;
        c.message = message;
        c.createdAt = createdAt;
        return c;
    }

    public long getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
