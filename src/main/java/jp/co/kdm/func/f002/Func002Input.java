package jp.co.kdm.func.f002;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jp.co.kdm.core.BaseInputEntity;
import lombok.Data;

@Data
public class Func002Input extends BaseInputEntity {

    @NotNull(message = "inputFilePathは必須です")
    private String inputFilePath;

    @Override
    @NotNull(message = "functionIdは必須です")
    @Pattern(regexp = "Func\\d{3}", message = "functionIdはFuncXXX形式である必要があります")
    public String getFunctionId() {
        return super.getFunctionId();
    }
}
