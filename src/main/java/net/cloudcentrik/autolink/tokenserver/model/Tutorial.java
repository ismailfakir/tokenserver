package net.cloudcentrik.autolink.tokenserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "tutorials")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tutorial {
    @Id
    private String id;

    private String title;
    private String description;
    private boolean published;
    private String token;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}