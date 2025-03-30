# BatchBase


# VM

## vagrant

``` ruby
Vagrant.configure("2") do |config|
  config.vm.box = "centos/stream9"
  config.vm.box_version = "20250210.0"
end
```

## docker準備

``` shell
sudo dnf update -y
sudo dnf install -y dnf-plugins-core
sudo dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
sudo dnf install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl enable --now docker
docker version
```

## docker

dockerディレクトリをそのまま配置して

``` shell
cd docker
sudo docker compose up -d
```

### docker上のpostgresqlに入る

``` shell
docker exec -it pg-batch psql -U batchuser -d batchdb
```

### docker上に入る場合は以下（多分使わんだろ？）

``` shell
docker exec -it pg-batch bash
```

### init.sqlがうまく動いてないとき

``` shell
docker logs pg-batch
```

volume削除して、確認してください。volume残っているとうまくいかないことあります。

``` shell
docker compose down -v
docker volume ls
```



# build

ビルドの設定で、以下プロパティファイルを
application-{env}.properties

以下に置き換えます
application.properties

## maven

[Downloading Apache Maven](https://maven.apache.org/download.cgi)  
ダウンロードして解凍したファイルを
`C:\tools`直下に配置してください。  
`setup-maven-env.bat`を実行すると多分パスが通ります。※試してないｗ

## Productionビルド

``` shell
mvn clean package -Pdev
```

## Developビルド

``` shell
mvn clean package -Pprod
```


# shell

各バッチは以下のように記載する想定


## 開発環境用

``` shell
java -Dlogback.configurationFile=logback-dev.xml -jar target/your-batch.jar
```

## プロダクション用

``` shell
java -Dlogback.configurationFile=logback-prod.xml -jar target/your-batch.jar
```



