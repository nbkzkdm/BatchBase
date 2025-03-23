/**
 * 
 */
package jp.co.kdm.runner;

import jp.co.kdm.core.BatchTemplate;
import jp.co.kdm.core.InputEntity;
import jp.co.kdm.func.f001.Func001;
import jp.co.kdm.func.f001.Func001Input;
import lombok.Getter;

@Getter
public enum BatchFunctionEnum {
    FUNC001("Func001", Func001.class, Func001Input.class);

    private final String functionId;
    private final Class<? extends BatchTemplate<?, ?>> batchClass;
    private final Class<? extends InputEntity> inputClass;

    BatchFunctionEnum(String functionId,
                      Class<? extends BatchTemplate<?, ?>> batchClass,
                      Class<? extends InputEntity> inputClass) {
        this.functionId = functionId;
        this.batchClass = batchClass;
        this.inputClass = inputClass;
    }

    public static BatchFunctionEnum fromFunctionId(String functionId) {
        for (BatchFunctionEnum f : values()) {
            if (f.functionId.equals(functionId)) {
                return f;
            }
        }
        throw new IllegalArgumentException("未定義の機能IDです: " + functionId);
    }
}

