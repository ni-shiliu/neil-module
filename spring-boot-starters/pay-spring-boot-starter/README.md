# 使用说明：

## 微信支付：

### 直连商户模式：

1. 在Spring Boot项目里，引入maven依赖

```xml
    <dependency>
        <groupId>com.github.neil</groupId>
        <artifactId>pay-spring-boot-starter</artifactId>
        <version>${version}</version>
    </dependency>
```

2. 配置(application.yml)

## 1）V3版本

```yml
wx:
  pay:
    config-map:
      mchId1: 
        appId: xxxxxxxxxxx
        mchId: 15xxxxxxxxx #商户id
        apiV3Key: Dc1DBwSc094jACxxxxxxxxxxxxxxx #V3密钥
        certSerialNo: 62C6CEAA360BCxxxxxxxxxxxxxxx
        privateKeyPath: cert/mch1/apiclient_key.pem #apiclient_key.pem证书文件类路径
        privateCertPath: cert/mch1/apiclient_cert.pem #apiclient_cert.pem证书文件类路径
      mchId2: 
        appId: xxxxxxxxxxx
        mchId: 15xxxxxxxxx #商户id
        apiV3Key: Dc1DBwSc094jACxxxxxxxxxxxxxxx #V3密钥
        certSerialNo: 62C6CEAA360BCxxxxxxxxxxxxxxx
        privateKeyPath: classpath:cert/mch2/apiclient_key.pem #apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径
        privateCertPath: classpath:cert/mch2/apiclient_cert.pem #apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径
```

> 支持多商户，如下配置`mchId1`，`mchId2`代表不同的唯一的商户标识

3. demo
- 3.1：单一商户模式

```java
@Service
@RequiredArgsConstructor
public class DemoService {

    private final WxPayService wxPayService;

    private String prePay(PrePayDTO prePayDTO) throws PayException {
        // 构建请求参数
        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = generateRequest();
        // v3统一下单支付
        T response = wxPayService.createOrderV3(WxTradeTypeEnum.H5, wxPayUnifiedOrderV3Request);
        return response;
    }

    private WxPayUnifiedOrderV3Request generateRequest() {
        WxPayConfig config = wxPayService.getConfig();

        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = new WxPayUnifiedOrderV3Request();
        BeanCopyUtils.copyProperties(config, wxPayUnifiedOrderV3Request);
        wxPayUnifiedOrderV3Request.setDescription("apple watch");
        wxPayUnifiedOrderV3Request.setOutTradeNo(RandomUtil.randomNumbers(15));
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(1);
        amount.setCurrency("CNY");
        wxPayUnifiedOrderV3Request.setAmount(amount);

        WxPayUnifiedOrderV3Request.SceneInfo sceneInfo = new WxPayUnifiedOrderV3Request.SceneInfo();
        sceneInfo.setPayerClientIp("14.23.150.211");
        sceneInfo.setH5Info(new WxPayUnifiedOrderV3Request.H5Info().setType("IOS"));
        wxPayUnifiedOrderV3Request.setSceneInfo(sceneInfo);
        wxPayUnifiedOrderV3Request.setNotifyUrl("https://www.baidu.com/aaa/bbb");
        return wxPayUnifiedOrderV3Request;
    }
}
```

- 3.2: 多商户模式

```java
@Service
@RequiredArgsConstructor
public class DemoService {

    private final WxPayService wxPayService;

    private String prePay(PrePayDTO prePayDTO) throws PayException {
        // 切换商户
        wxPayService.switchover(prePayDTO.getMchid());
        // 构建请求参数
        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = generateRequest();
        // v3统一下单支付
        T response;
        try {
            response = wxPayService.createOrderV3(WxTradeTypeEnum.H5, wxPayUnifiedOrderV3Request);
        } finally {
            // remove thread_local
            WxPayConfigHolder.remove();
        }
        return response;
    }
}
```

