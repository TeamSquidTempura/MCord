# MCordプラグイン
___
## 使い方
<br>1.まずは、[ここ](https://github.com/TeamSquidTempura/MCord/releases/latest)から最新のMCordをダウンロードして、導入したいサーバーのpluginsディレクトリに入れて起動してください。</br>
<br>__※導入前には念のため一度サーバーを停止してから導入してください__</br>
<br>2.次に、サーバーを起動します。サーバーを起動すると `plugins/MCord/config.yml`が生成されるのでサーバーを停止してconfigファイルを開いて設定してください。</br>
<br>__[設定方法はこちら](#YAML設定方法)__</br>
<br>3.設定が終わったら、サーバーを起動しましょう！</br>

___
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