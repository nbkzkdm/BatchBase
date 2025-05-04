package jp.co.kdm.func.f001;

import java.io.File;

import jp.co.kdm.core.BatchStatus;
import jp.co.kdm.core.BatchTemplate;
import jp.co.kdm.core.OutputEntity;
import jp.co.kdm.func.f002.Func002Output;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Func001 extends BatchTemplate<Func001Input, OutputEntity> {

    @Override
    protected Func001Input input(String[] args) {
        Func001Input input = new Func001Input();
        input.setFunctionId(args[0]);
        return input;
    }

    @Override
    protected OutputEntity process(Func001Input input) {
        Func002Output output = new Func002Output();

        File file = new File("input/" + input.getFileName());
        if (file.exists() && file.isFile()) {
            output.setStatus(BatchStatus.OK);
            output.addMessage("ファイルが見つかりました: " + file.getPath());
        } else {
            output.setStatus(BatchStatus.OK);
            output.addMessage("ファイルが見つかりません: " + file.getPath());
        }

        return output;
    }

    @Override
    protected void postProcess(Func001Input input, OutputEntity output) {
        log.info("バッチ処理結果: {}", output.getStatus());
        
    }
}

