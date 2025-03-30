.PHONY: dev prod test test-report clean

# Maven profileごとのビルド
dev:
	mvn clean package -Pdev

prod:
	mvn clean package -Pprod

# 単体テスト（JUnit）＋ 開発プロファイル
test:
	mvn clean test -Pdev

# テスト + HTMLレポート出力（Surefire）
test-report:
	mvn clean test -Pdev
	mvn surefire-report:report -Pdev
	@echo "✅ HTML report generated: target/site/surefire-report.html"

# ビルド成果物や中間ファイル削除
clean:
	mvn clean
