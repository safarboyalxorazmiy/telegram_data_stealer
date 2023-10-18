package uz.predict.step;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "step")
public class StepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Step step;

    @Column
    private Long chatId;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();
}