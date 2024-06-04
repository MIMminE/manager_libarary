package nuts.lib.manager.verification_manager.annotation_verifier;

import lombok.RequiredArgsConstructor;
import nuts.lib.manager.verification_manager.VerificationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public class AnnotationVerifierResult implements VerificationResult<RuntimeException> {

    private final List<RuntimeException> resultList = new ArrayList<>();
    private final Function<List<RuntimeException>, RuntimeException> resultProcessor;

    public RuntimeException getResult() {
        if (resultList.isEmpty()){
            return null;
        }

        return resultProcessor.apply(resultList);
    }

    public void addResult(RuntimeException result) {
        this.resultList.add(result);
    }
}
