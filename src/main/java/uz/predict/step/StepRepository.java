package uz.predict.step;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StepRepository extends CrudRepository<StepEntity, Long> {
    @Query(value = "select * from step where chat_id = ?1 order by id desc limit 1;", nativeQuery = true)
    Optional<StepEntity> getLast(Long chatId);
}