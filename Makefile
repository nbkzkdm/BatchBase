# Makefile

# プロファイル名（make dev / make prod）
dev:
    mvn clean package -Pdev

prod:
    mvn clean package -Pprod
