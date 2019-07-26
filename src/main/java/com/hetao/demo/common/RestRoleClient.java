package com.hetao.demo.common;

import com.everstar.mobile.common.rest.entity.StateMessage;
import com.everstar.mobile.common.rest.entity.XhUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

public class RestRoleClient {

    private static RestTemplate restTemplate = new RestTemplate();
    private static RestRoleClient instance = new RestRoleClient();
    
    static {
		//解决网络传输乱码问题
    	restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}
    
    public RestRoleClient() {
    	List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
		for(HttpMessageConverter<?> mapper : list) {
			if(mapper instanceof MappingJackson2HttpMessageConverter) {
				ObjectMapper om = new ObjectMapper();
				om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				((MappingJackson2HttpMessageConverter)mapper).setObjectMapper(om);
	//			break;
			} else if(mapper instanceof StringHttpMessageConverter) {
	//			((StringHttpMessageConverter)mapper).setSupportedMediaTypes(supportedMediaTypes);
				
			}
		}
	}
    
    public static RestRoleClient getInstance() {
    	return instance;
    }
    
	private final static String url = "http://password.xinghengedu.com/xh/";
	

	
	public XhUser query(String username) {
		try {
			return restTemplate.getForObject(url+"query/{userName}", XhUser.class, username);
		} catch (RestClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	public StateMessage reg(XhUser xhUser) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
			postParameters.add("userName", xhUser.getUserName());
			postParameters.add("password", xhUser.getPassword());
			postParameters.add("phone", xhUser.getPhone()==null?"":xhUser.getPhone());
			postParameters.add("realName", xhUser.getRealName()==null?"":xhUser.getRealName());
			postParameters.add("sourceType", xhUser.getSourceType());
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postParameters, headers);
			ResponseEntity<StateMessage> res = restTemplate.exchange(url+"reg", HttpMethod.POST, requestEntity, StateMessage.class);
			return res.getBody();
//			return restTemplate.postForObject(url+"reg", requestEntity, StateMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
