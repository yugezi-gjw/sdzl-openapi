RTSummary OpenAPI服务配置说明
1.	Openapi服务配置
application.yml配置内容说明
	配置项	配置内容	说明	备注
sysConfig	fhirUrl	http://10.120.116.204:44352	Fhir url	可参考ccip安装目录config/local.yaml文件进行配置
	fhirTockenAuthUserName	cciptokenuser	默认用户	
	fhirTockenAuthPwd	V@rianCN01	默认密码	
	fhirConnectionTimeout	40000		无需修改
	fhirConnectionRequestTimeout	40000		无需修改
	fhirSocketTimeout	40000		无需修改
	fhirLanguage	CHS		无需修改
	clientId	55d691d8-3a55-43b4-b6e2-1d3334fd2ec3	VAIS配置	可参考ccip安装目录config/integration/ VAIS.yaml文件进行配置
	clientSecret	z%i[KZ|l_fm#	VAIS配置	可参考ccip安装目录config/integration/ VAIS.yaml文件进行配置
	domainName		VAIS配置	可参考ccip安装目录config/integration/ VAIS.yaml文件进行配置
	gettingTokenEndPoint		VAIS配置	可参考ccip安装目录config/integration/ VAIS.yaml文件进行配置
	refreshTokenEndPoint		VAIS配置	可参考ccip安装目录config/integration/ VAIS.yaml文件进行配置
server	port	8000	服务端口	
logging	file	log/openapi.log	Log文件路径	

2.	start-openapi.bat启动脚本说明
修改bat启动脚本里的jdk路径为正确路径。
