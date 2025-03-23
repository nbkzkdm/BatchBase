package jp.co.kdm.func.f002;

import jp.co.kdm.core.BatchStatus;
import jp.co.kdm.core.BatchTemplate;
import jp.co.kdm.domain.entity.TableTwoEntity;
import jp.co.kdm.domain.mapper.TableTwoMapper;
import jp.co.kdm.core.db.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Func002 extends BatchTemplate<Func002Input, Func002Output> {

    private static final int BATCH_SIZE = 100;

    @Override
    protected Func002Input input(String[] args) {
        Func002Input input = new Func002Input();
        input.setFunctionId(args[0]);
        input.setInputFilePath(args[1]);
        return input;
    }

    @Override
    protected Func002Output process(Func002Input input) {
        Func002Output output = new Func002Output();
        List<TableTwoEntity> buffer = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(input.getInputFilePath()));
             SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession(false)) {

            TableTwoMapper mapper = session.getMapper(TableTwoMapper.class);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                TableTwoEntity entity = new TableTwoEntity();
                entity.setId(parts[0]);
                entity.setDate(Timestamp.valueOf(parts[1]));
                entity.setValue(Integer.parseInt(parts[2]));
                buffer.add(entity);

                if (buffer.size() >= BATCH_SIZE) {
                    mapper.insertBatch(buffer);
                    session.commit();
                    buffer.clear();
                }
            }

            if (!buffer.isEmpty()) {
                mapper.insertBatch(buffer);
                session.commit();
            }

            output.setStatus(BatchStatus.OK);
            output.addMessage("Func002: 正常終了");

        } catch (Exception e) {
            output.setStatus(BatchStatus.NG);
            output.addMessage("Func002: 異常終了 - " + e.getMessage());
            e.printStackTrace();
        }

        return output;
    }

    @Override
    protected void postProcess(Func002Input input, Func002Output output) {
        // 省略: 必要に応じて実装
    }
}
