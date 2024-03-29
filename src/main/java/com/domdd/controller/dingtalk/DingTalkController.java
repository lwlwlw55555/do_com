//package com.domdd.controller.dingtalk;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
//import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
//import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
//import com.aliyun.tea.TeaException;
//import com.aliyun.teaopenapi.models.Config;
//import com.aliyun.teautil.models.RuntimeOptions;
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.DingTalkClient;
//import com.dingtalk.api.request.OapiGettokenRequest;
//import com.dingtalk.api.request.OapiRobotSendRequest;
//import com.dingtalk.api.response.OapiGettokenResponse;
//import com.dingtalk.api.response.OapiRobotSendResponse;
//import com.taobao.api.ApiException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.crypto.Mac;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author lw
// * @date 2022/8/29 6:01 下午
// */
//@Slf4j
//@RestController
//@RequestMapping("dingTalk")
//public class DingTalkController {
//    private static final String INFOS = "校验消息是否是合法请求";
//
//    @RequestMapping(value = "robots1", method = RequestMethod.POST)
//    public String helloRobots(@RequestBody(required = false) JSONObject json) throws Exception {
//        log.info(JSON.toJSONString(json));
//        String content = json.getJSONObject("text").getString("content");
//        String userId = json.get("senderStaffId").toString();
//        log.info(content);
//        if (content.equals("发票")) {
//            sendMessage1(userId);
//        } else if (content.equals("发票要普票还是专票")) {
//            sendMessage2(userId);
//        } else if (content.equals("发票内容开什么")) {
//            sendMessage3(userId);
//        } else if (content.equals("差旅住宿普通发票可以报销吗")) {
//            sendMessage4(userId);
//        }
//        return null;
//    }
//
//    public static com.aliyun.dingtalkrobot_1_0.Client createClient() throws Exception {
//        Config config = new Config();
//        config.protocol = "https";
//        config.regionId = "central";
//        return new com.aliyun.dingtalkrobot_1_0.Client(config);
//    }
//
//    public void sendMessage1(String userId) throws Exception {
//        com.aliyun.dingtalkrobot_1_0.Client client = createClient();
//        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
//        batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
//        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
//                .setRobotCode("ding2kpicn5wl49wvjfd")
//                .setUserIds(java.util.Arrays.asList(userId))
//                .setMsgKey("sampleActionCard3")
//                .setMsgParam("{\n" +
//                        "        \"title\": \"发票\",\n" +
//                        "        \"text\": \"请问您是要咨询发票的什么内容呢\",\n" +
//                        "        \"actionTitle1\": \"发票要普票还是专票\",\n" +
//                        "        \"actionURL1\": \"dtmd://dingtalkclient/sendMessage?content=%E5%8F%91%E7%A5%A8%E8%A6%81%E6%99%AE%E7%A5%A8%E8%BF%98%E6%98%AF%E4%B8%93%E7%A5%A8\",\n" +
//                        "        \"actionTitle2\": \"发票内容开什么\",\n" +
//                        "        \"actionURL2\": \"dtmd://dingtalkclient/sendMessage?content=%E5%8F%91%E7%A5%A8%E5%86%85%E5%AE%B9%E5%BC%80%E4%BB%80%E4%B9%88\",\n" +
//                        "        \"actionTitle3\": \"差旅住宿普通发票可以报销吗\",\n" +
//                        "        \"actionURL3\": \"dtmd://dingtalkclient/sendMessage?content=%E5%B7%AE%E6%97%85%E4%BD%8F%E5%AE%BF%E6%99%AE%E9%80%9A%E5%8F%91%E7%A5%A8%E5%8F%AF%E4%BB%A5%E6%8A%A5%E9%94%80%E5%90%97\"\n" +
//                        "    }");
//        try {
//            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
//            log.info(JSON.toJSONString(batchSendOTOResponse.getBody()));
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        }
//    }
//
//    public void sendMessage2(String userId) throws Exception {
//        com.aliyun.dingtalkrobot_1_0.Client client = createClient();
//        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
//        batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
//        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
//                .setRobotCode("ding2kpicn5wl49wvjfd")
//                .setUserIds(java.util.Arrays.asList(
//                        userId
//                ))
//                .setMsgKey("sampleText")
//                .setMsgParam("{\"content\": \"发票需要普票\"}");
//        try {
//            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
//            log.info(JSON.toJSONString(batchSendOTOResponse.getBody()));
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        }
//    }
//
//    public void sendMessage3(String userId) throws Exception {
//        com.aliyun.dingtalkrobot_1_0.Client client = createClient();
//        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
//        batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
//        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
//                .setRobotCode("ding2kpicn5wl49wvjfd")
//                .setUserIds(java.util.Arrays.asList(
//                        userId
//                ))
//                .setMsgKey("sampleMarkdown")
//                .setMsgParam("{\n" +
//                        "        \"title\": \"发票内容\",\n" +
//                        "        \"text\": \"发票内容一般包括:票头、字轨号码、联次及用途、客户名称、银行开户账号、商(产)品名称或经营项目、计量单位、数量、单价、金额,以及大小写金额、经手人、单位印章、开票日期等。实行增值税的单位所使用的增值税专用发票还应有税种、税率、税额等内容。\"\n" +
//                        "    }");
//        try {
//            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
//            log.info(JSON.toJSONString(batchSendOTOResponse.getBody()));
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        }
//    }
//
//    public void sendMessage4(String userId) throws Exception {
//        com.aliyun.dingtalkrobot_1_0.Client client = createClient();
//        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
//        batchSendOTOHeaders.xAcsDingtalkAccessToken = getAccessToken();
//        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest()
//                .setRobotCode("ding2kpicn5wl49wvjfd")
//                .setUserIds(java.util.Arrays.asList(
//                        userId
//                ))
//                .setMsgKey("sampleText")
//                .setMsgParam("{\"content\": \"差旅住宿普通发票可以报销\"}");
//        try {
//            BatchSendOTOResponse batchSendOTOResponse = client.batchSendOTOWithOptions(batchSendOTORequest, batchSendOTOHeaders, new RuntimeOptions());
//        } catch (TeaException err) {
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        } catch (Exception _err) {
//            TeaException err = new TeaException(_err.getMessage(), _err);
//            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
//                // err 中含有 code 和 message 属性，可帮助开发定位问题
//                log.info(err.code);
//                log.info(err.message);
//            }
//        }
//    }
//
////    public String getAccessToken() throws ApiException {
////        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
////        OapiGettokenRequest request = new OapiGettokenRequest();
////        request.setAppkey("ding2kpicn5wl49wvjfd");
////        request.setAppsecret("PTNBLXGz-NqsqRoEHbe96jECvywjof-lC9bgpSASW-AjlfloiFz1AJ4mjYKOF4oS");
////        request.setHttpMethod("GET");
////        OapiGettokenResponse response = client.execute(request);
////        return response.getAccessToken();
////    }
//
//
//    @RequestMapping(value = "/robots", method = RequestMethod.POST)
//    public String helloRobots(@RequestBody(required = false) JSONObject json, HttpServletRequest request) {
//        try {
//            log.info("helloRobots:{}", JSON.toJSONString(json));
//            //获取请求头header中钉钉发送的timestamp时间戳
//            Long dingTimestamp = Long.parseLong(request.getHeader("timestamp"));
//            //获取当前时间的时间戳
//            Long currentTimeMillis = System.currentTimeMillis();
//            //比较两者差值
//            Long time = currentTimeMillis - dingTimestamp;
//            //获取请求头header中钉钉发送的sign签名
//            String dingSign = request.getHeader("sign");
//            //开发者后台应用信息中机器人应用的AppSecret
//            String appSecret = "PTNBLXGz-NqsqRoEHbe96jECvywjof-lC9bgpSASW-AjlfloiFz1AJ4mjYKOF4oS";
//            //使用请求头header中钉钉发送的timestamp时间戳和开发者后台机器人应用的AppSecret，进行加密操作
//            String stringToSign = dingTimestamp + "\n" + appSecret;
//            Mac mac = Mac.getInstance("HmacSHA256");
//            mac.init(new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256"));
//            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
//            //获取加密sign
//            String sign = new String(Base64.encodeBase64(signData));
//            //相差时间在1小时内且sign同时验证通过，才能认为是来自钉钉的合法请求
//            if (time < 3600000 && sign.equals(dingSign)) {
//                String content = json.getJSONObject("text").get("content").toString().replaceAll(" ", "");
//                log.info(content);
//                String sessionWebhook = json.getString("sessionWebhook");
//                DingTalkClient client = new DefaultDingTalkClient(sessionWebhook);
//                if (INFOS.equals(content)) {
//                    infos(client);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 发送消息
//     *
//     * @param client
//     */
//    private void infos(DingTalkClient client) {
//        try {
//            OapiRobotSendRequest request = new OapiRobotSendRequest();
//            //消息类型
//            request.setMsgtype("text");
//            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//            //消息文本
//            text.setContent("测试消息是合法请求");
//            request.setText(text);
//            OapiRobotSendResponse response = client.execute(request);
//            log.info(response.getBody());
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }
//}
