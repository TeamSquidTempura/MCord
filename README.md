# MCordプラグイン
<br><img alt="total" src="https://img.shields.io/github/downloads/TeamSquidTempura/MCord/total?style=for-the-badge"><img alt="ダウンロード数" src="https://img.shields.io/github/downloads/TeamSquidTempura/MCord/latest/total?style=for-the-badge"></br>
## 使い方
<br>1.まずは、[ここ](https://github.com/TeamSquidTempura/MCord/releases/latest)から最新のMCordをダウンロードして、導入したいサーバーのpluginsディレクトリに入れて起動してください。</br>
<br>__※導入前には念のため一度サーバーを停止してから導入してください__</br>
<br>2.次に、サーバーを起動します。サーバーを起動すると `plugins/MCord/config.yml`が生成されるのでサーバーを停止してconfigファイルを開いて設定してください。</br>
<br>__[設定方法はこちら](#YAML設定方法)__</br>
<br>3.設定が終わったら、サーバーを起動しましょう！</br>
## YAML設定方法
<br>token:BOTのトークン</br>
<br>guild-id:このプラグインがメッセージを転送するディスコードサーバーのID</br>
<br>status-channel:ステータスチャンネル用のチャンネルID</br>
<br>chat-channel:チャットチャンネル用のチャンネルID</br>
<br>admin-log-channel:管理者用チャンネル用のチャンネルID</br>
<br>rpc:BOTのステータスの表示させるメッセージ</br>
<br>death-location:プライヤーが死亡したときにチャットチャンネルに座標などを転送するかどうか true/false</br>
<br>server-name:サーバー名</br>
```yaml
token: "token"
#BOTのトークン(String)
guild-id: 0000000000000000
#GuildのID(Long)
status-channel: 0000000000000000
#ステータスチャンネルのID(Long)
chat-channel: 0000000000000000
#チャットチャンネルのID(Long)
admin-log-channel: 0000000000000000
#アドミンチャンネルのID(Long)
rpc: "bot-status"
#BOTのRPC(String)
death-location: false
#死亡ログ(Boolean)
server-name: "server-name"
#サーバー名(String)
```
## スラコマの使い方
<br>`/command` 管理者権限がある人のみが使えるコマンドです。このコマンドを使うとディスコードからマイクラ内のコマンドを実行することができます。</br>
<br> `/tell` 誰でも使えるコマンドです。このコマンドを使うとディスコードからマイクラにプライベートメッセージを送信できます。
<br> `/ping` 誰でも使えるコマンドです。プレイヤーのPINGを確認することができます。</br>
<br> `/maxplayer` 管理者権限がある人のみが使えるコマンドです。このコマンドを使うとサーバーの最大人数を設定できます。</br>
<br> `/player` 誰でも使えるコマンドです。オンラインのプレイヤーの情報を取得することができます </br>