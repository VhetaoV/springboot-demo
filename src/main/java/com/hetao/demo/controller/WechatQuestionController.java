package com.hetao.demo.controller;

import com.alibaba.fastjson.JSONObject;

import com.everstar.mobile.common.rest.entity.XhUser;
import com.hetao.demo.common.RestRoleClient;
import com.hetao.demo.entity.UserInfo;
import com.hetao.demo.entity.UserInfoDto;
import com.hetao.demo.service.UserInfoService;
import com.hetao.demo.service.WechatKnowledgeService;
import com.hetao.demo.service.WechatQuestionService;
import com.hetao.demo.util.HttpRequest;
import com.hetao.demo.util.WXBizDataCryptUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 微信小程序题目controller
 * @author: chenliang
 * @create: 2019-07-16 17:06
 **/
@Slf4j
@RestController
@RequestMapping("/sprint2019/pc-wx")
@Api(value = "PC微信接口")
@ApiResponses({
        @ApiResponse(code = 200, message = "正常返回"),
        @ApiResponse(code = 300, message = "异常返回")
})
@EnableCaching
public class WechatQuestionController {


    //小程序的key和秘钥
    private final String wechatAppId = "wxa70f4b3b764aa14f";
    private final String wechatSecretKey = "db1f2597a67726b849198454b1ee34f7";
    private final String grantType = "authorization_code";
    //根据微信登录返回的code获取解密的key的接口
    private final String wxhttpurl = "https://api.weixin.qq.com/sns/jscode2session";



    @Resource
    private WechatQuestionService wechatQuestionService;

    @Resource
    private WechatKnowledgeService wechatKnowledgeService;

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //根据微信登录返回的code获取openId和sessionkey
    @RequestMapping(value = "getCode.do", method = RequestMethod.POST)
    @ApiOperation(value = "获取微信登录返回的code",notes="获取微信登录返回的code",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "code", value = "微信登录返回值code",required = true, dataType = "String")
    })
    public Map<String,String> getCode(String code){
        Map map = new HashMap();
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }
        String params = "appid=" + wechatAppId + "&secret=" + wechatSecretKey + "&js_code=" + code + "&grant_type=" + grantType;
        String sr = HttpRequest.sendGet(wxhttpurl, params);
        JSONObject jsonObject = (JSONObject)JSONObject.parse(sr);
        if(!sr.contains("session_key")||!sr.contains("openid")){
            log.error(sr);
            map.put("status", jsonObject.get("errcode"));
            map.put("msg", jsonObject.get("errmsg"));
            return map;
        }
        String sessionKey = jsonObject.get("session_key").toString();
        String openId = jsonObject.get("openid").toString();
        //客户身份信息解密需要，考虑安全缓存在服务端
        stringRedisTemplate.opsForValue().set("sessionKey_"+openId,sessionKey,60*5,TimeUnit.SECONDS);
        log.info("设置缓存sessionKey_"+openId+"===="+sessionKey);
        map.put("200","OK");
        map.put("openId",openId);
        return map;
    }



    @ApiOperation(value = "获取授权用户信息",notes="获取授权用户信息",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "openId", value = "openId",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "iv", value = "iv",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "encryptedData", value = "encryptedData",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "belonger", value = "belonger",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="form", name = "productType", value = "专业",required = true, dataType = "String")
    })
    @RequestMapping(value = "decodeUserInfo.do", method = RequestMethod.POST)
    private Map decodeUserInfo(String openId, String iv, String encryptedData, String belonger, String productType) {
        log.info("入参=========openId=="+openId);
        log.info("入参=========iv=="+iv);
        log.info("入参=========encryptedData=="+encryptedData);
        log.info("入参=========belonger=="+belonger);
        log.info("入参=========productType=="+productType);
        Map map = new HashMap();
        String sessionKey = stringRedisTemplate.opsForValue().get("sessionKey_"+openId);
        log.info("获取缓存sessionKey_"+openId+"===="+sessionKey);
        try {

            com.alibaba.fastjson.JSONObject userInfoJSON = WXBizDataCryptUtil.decryptData(encryptedData,iv,sessionKey);
            log.info("解析信息========"+userInfoJSON.toJSONString());
            if(userInfoJSON!=null){
                String phoneNumber = (String)userInfoJSON.get("phoneNumber");
                log.info("解密到的手机号phoneNumber====="+phoneNumber);
                map.put("msg", "解密成功");
                String password = DigestUtils.md5Hex(phoneNumber.substring(phoneNumber.length() - 6, phoneNumber.length()));
                XhUser passtUser = RestRoleClient.getInstance().query(phoneNumber);
                UserInfoDto uInfo = this.userInfoService.selectByUsername(phoneNumber);

                if (passtUser == null) {
                    XhUser xhUser = new XhUser().setUserName(phoneNumber).setPassword(password);
                    xhUser.setSourceType("WXMINI");
                    RestRoleClient.getInstance().reg(xhUser);
                    log.info("新用户"+phoneNumber+"RestRoleClient注册");
                    this.userInfoService.addBelonger(phoneNumber,belonger,"三十天冲刺活动");
                    log.info("新用户"+phoneNumber+"添加belonger");
                }
                if (uInfo == null) {
                    UserInfo userInfo = new UserInfo(phoneNumber, password, new Date(), new Date(), phoneNumber);
                    userInfo.setProductType("ZHIYEYISHI");
                    this.userInfoService.addUserInfo(userInfo);
                    log.info("新用户"+phoneNumber+"注册");
                }
            }
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
            map.put("status", 0);
            map.put("msg", "解密失败");
        }
        return map;
    }


    @PostMapping("knowledgeDayCountQuery.do")
    @ApiOperation(value = "获取知识点每天的知识数量",notes="获取知识点每天的知识数量",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "productType", value = "专业类别",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "dayOrder", value = "哪一天",required = true, dataType = "Integer")
    })
    public ResponseEntity<Map<String ,Object>> findKnowledgeDayCount(@RequestParam(value = "productType",required = true)String productType){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            List<Map<String,Integer>> list = wechatKnowledgeService.findDayCount(productType);
            map.put("data",list);
        }catch(Exception e){
            log.error(e.toString());
            map.put("code", 500);
            map.put("data", "error");
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("knowledgeDayListQuery.do")
    @ApiOperation(value = "获取某一天的知识点",notes="获取某一天的知识点",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "productType", value = "专业类别",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "dayOrder", value = "哪一天",required = true, dataType = "Integer")
    })
    public ResponseEntity<Map<String,Object>> findKnowledgeDayList(String productType, Integer dayOrder){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            List<Map<String,String>> list = wechatKnowledgeService.findKnowledge(dayOrder,productType);
            map.put("data",list);
        }catch (Exception e){
            log.error(e.toString());
            map.put("code", 500);
            map.put("data", "error");
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("questionDayCountQuery.do")
    @ApiOperation(value = "获取练习题每天的知识数量",notes="获取练习题每天的知识数量",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "productType", value = "专业类别",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "dayOrder", value = "哪一天",required = true, dataType = "Integer")
    })
    public ResponseEntity<Map<String,Object>> findQuestionDayCount(@RequestParam(value = "productType",required = true)String productType){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            List<Map<String,Integer>> list = wechatQuestionService.findQuestionCount(productType);
            map.put("data",list);
        }catch (Exception e){
            log.error(e.toString());
            map.put("code", 500);
            map.put("data", "error");
        }
        return ResponseEntity.ok(map);
    }


    @PostMapping("/questionListQuery.do")
    @ApiOperation(value="获取某一天题目列表", notes="获取某一天题目列表",httpMethod="POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "productType", value = "专业类别",required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "dayOrder", value = "哪一天",required = true, dataType = "Integer")
    })
    public ResponseEntity<Map<String,Object>> findQuestionList(String productType, Integer dayOrder){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            List<Map<String,String>> list = this.wechatQuestionService.findQuestionList(productType,dayOrder);
            map.put("data",list);
        }catch (Exception e){
            log.error(e.toString());
            map.put("code", 500);
            map.put("data", "error");
        }
        return ResponseEntity.ok(map);
    }
}
