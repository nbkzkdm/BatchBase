package jp.co.kdm.runner;

import jp.co.kdm.core.BatchRuntimeException;
import jp.co.kdm.core.IBaseExecute;
import jp.co.kdm.proxy.LoggingProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainRunner {

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new BatchRuntimeException("機能IDを指定してください");
            }

            String functionId = args[0];
            BatchFunctionEnum function = BatchFunctionEnum.fromFunctionId(functionId);

            // BatchTemplate のインスタンス生成
            IBaseExecute<?> batch = function.getBatchClass().getDeclaredConstructor().newInstance();
            IBaseExecute<?> proxy = LoggingProxy.create(IBaseExecute.class, batch);

            log.info("機能ID: {}, {}", functionId, function.getBatchClass().getSimpleName());
            proxy.execute(args);

        }
        catch (BatchRuntimeException e) {
            log.error("バッチエラー: {}", e.getMessage());
        }
        catch (Exception e) {
            log.error("予期せぬエラー", e);
        }
    }

}

