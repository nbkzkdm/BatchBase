package jp.co.kdm.func.f002;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jp.co.kdm.domain.entity.TableTwoEntity;
import jp.co.kdm.testutil.MyBatisTestUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Func002Test {

    private static Validator validator;
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    static void setup() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        sqlSessionFactory = MyBatisTestUtil.getSqlSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession();
             Connection conn = session.getConnection();
             Statement stmt = conn.createStatement()) {

            // テスト用テーブル作成（H2用 SQL）
            stmt.execute("CREATE TABLE IF NOT EXISTS table_two (" +
                    "id VARCHAR(10) NOT NULL," +
                    "date TIMESTAMP NOT NULL," +
                    "\"value\" INT NOT NULL);");
        }
    }
    @ParameterizedTest
    @MethodSource("invalidInputs")
    void testInvalidInputs(Func002Input input, String expectedInvalidField) {
        Set<ConstraintViolation<Func002Input>> violations = validator.validate(input);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(expectedInvalidField)));
    }

    static Stream<Arguments> invalidInputs() {
        return Stream.of(
            Arguments.of(createInput("Fuc001", "test.csv"), "functionId"),
            Arguments.of(createInput("Func002", null), "inputFilePath"),
            Arguments.of(createInput(null, null), "functionId")
        );
    }

    private static Func002Input createInput(String functionId, String inputFilePath) {
        Func002Input input = new Func002Input();
        input.setFunctionId(functionId);
        input.setInputFilePath(inputFilePath);
        return input;
    }

    @AfterEach
    void cleanup() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            session.delete("jp.co.kdm.domain.mapper.TableTwoMapper.deleteAll");
        }
    }
}
