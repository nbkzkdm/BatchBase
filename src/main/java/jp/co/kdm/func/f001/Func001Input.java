package jp.co.kdm.func.f001;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jp.co.kdm.core.BaseInputEntity;
import lombok.Getter;

@Getter
public class Func001Input extends BaseInputEntity {

    @NotNull(message = "fileNameは必須です")
    private String fileName;

    @Override
    @Pattern(regexp = "Func\\d{3}", message = "functionIdはFuncXXX形式である必要があります")
    public String getFunctionId() {
        return super.getFunctionId();
    }
}

