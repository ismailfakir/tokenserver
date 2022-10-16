package net.cloudcentrik.autolink.tokenserver.model;

import lombok.*;
import lombok.experimental.Accessors;
import net.cloudcentrik.autolink.tokenserver.utils.TokenGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;
    private Role role;
    private String token;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
