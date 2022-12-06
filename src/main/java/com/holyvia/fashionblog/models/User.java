package com.holyvia.fashionblog.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.holyvia.fashionblog.enums.Gender;
import com.holyvia.fashionblog.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "user_tbl",
        uniqueConstraints = @UniqueConstraint(
                name = "email_unique",
                columnNames = "user_email"
        )
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @NotNull(message = "Name cannot be missing or empty")
    @Column(nullable = false, length = 50)
    private String name;

    @Email
    @Column(name = "user_email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotNull(message="Password is required")
    @Column(nullable = false)
    @Size(min = 8, max=25, message="Password must be equal to or greater than 8 character and less than 25 characters")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "postAuthor", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<BlogPost> blogPosts = new HashSet<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Comment> comments = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Likes> likesList = new ArrayList<>();
}
