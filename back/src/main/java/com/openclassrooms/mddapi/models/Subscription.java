package com.openclassrooms.mddapi.models;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "SUBSCRIPTION")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @OneToOne
    @JoinColumn(name = "ID_Theme", referencedColumnName = "id")
    private Theme theme;

    @NonNull
    @OneToOne
    @JoinColumn(name = "ID_User", referencedColumnName = "id")
    private User user;
}
