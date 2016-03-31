# xemail
一个基于javax封装的邮件收发组件
```java
Sendmail sendemail=new Sendmail();
sendemail.sendText("一条纯文本消息", "meixixizf24@gmail.com");
```

###输出结果

```
DEBUG: setDebug: JavaMail version 1.5.0-b01
DEBUG: getProvider() returning javax.mail.Provider[TRANSPORT,smtp,com.sun.mail.smtp.SMTPTransport,Oracle]
DEBUG SMTP: useEhlo true, useAuth true
DEBUG SMTP: trying to connect to host "smtp.189.cn", port 25, isSSL false
220 zm-as3 smtp
DEBUG SMTP: connected to host "smtp.189.cn", port: 25

EHLO Hezf-PC
250-21cn.com
250-AUTH LOGIN
250-AUTH LOGIN
250 8BITMIME
DEBUG SMTP: Found extension "AUTH", arg "LOGIN"
DEBUG SMTP: Found extension "AUTH", arg "LOGIN"
DEBUG SMTP: Found extension "8BITMIME", arg ""
DEBUG SMTP: Attempt to authenticate using mechanisms: LOGIN PLAIN DIGEST-MD5 NTLM 
DEBUG SMTP: AUTH LOGIN command trace suppressed
DEBUG SMTP: AUTH LOGIN succeeded
DEBUG SMTP: use8bit false
MAIL FROM:<xxxxxxxx@189.cn>
250 Ok
RCPT TO:<meixixizf24@gmail.com>
250 Ok
DEBUG SMTP: Verified Addresses
DEBUG SMTP:   meixixizf24@gmail.com
DATA
354 End data with <CR><LF>.<CR><LF>
From: xxxxxxxx@189.cn
To: meixixizf24@gmail.com
Message-ID: <697556912.0.1459414099582.JavaMail.Hezf@smtp.189.cn>
Subject: sdfsdf
MIME-Version: 1.0
Content-Type: text/html;charset=UTF-8
Content-Transfer-Encoding: 7bit

一条纯文本消息
.
250 Ok: queued as 1EB8B1B81BC
QUIT
221 goodbye

```

###配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<property>
		<name>xemail.from</name>
		<value>xxxxxxx@189.cn</value><!--发件人邮箱-->
	</property>
	<property>
		<name>xemail.pwd</name>
		<value>sdfsdfsdfsdf</value><!--发件人密码-->
	</property>
	<property>
		<name>xemail.smtp</name>
		<value>smtp.189.cn</value><!--smtp-->
	</property>
	<property>
		<name>xemail.store.path</name>
		<value>Y:\\attachMail.eml</value><!--发送邮件后邮件本地存储位置，只针对含有附件的-->
	</property>
</configuration>
```
