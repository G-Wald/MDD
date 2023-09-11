package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
@Entity
@Table(name = "THEME")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Size(max = 40)
    @Column(name = "title")
    private String title;

    @NonNull
    @Size(max = 2000)
    @Column(name = "description")
    private String description;
}
