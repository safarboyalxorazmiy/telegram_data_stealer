package uz.predict.step;

import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public void createStep(Long chatId, Step step) {
        StepEntity stepEntity = new StepEntity();
        stepEntity.setChatId(chatId);
        stepEntity.setStep(step);
        stepRepository.save(stepEntity);
    }


    public Step getLastStep(Long chatId) {
        Optional<StepEntity> last = stepRepository.getLast(chatId);
        if (last.isEmpty()){
            return Step.NO_STEP;
        }

        return last.get().getStep();
    }
}