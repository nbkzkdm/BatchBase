@echo off
REM ========= Maven 環境変数設定 =========

REM Maven のインストール先パス（必要に応じて書き換えてください）
set "MAVEN_HOME=C:\tools\apache-maven-3.9.6"

REM PATH に %MAVEN_HOME%\bin を追加（セッション限定）
set "PATH=%MAVEN_HOME%\bin;%PATH%"

REM 確認出力
echo MAVEN_HOME=%MAVEN_HOME%
echo PATH=%PATH%

REM Maven バージョン確認
mvn -v

REM コマンドプロンプトを継続させる（任意）
cmd
