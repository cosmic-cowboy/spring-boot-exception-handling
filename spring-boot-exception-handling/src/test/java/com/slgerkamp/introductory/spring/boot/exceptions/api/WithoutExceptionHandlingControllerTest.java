package com.slgerkamp.introductory.spring.boot.exceptions.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slgerkamp.introductory.spring.boot.exceptions.App;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class WithoutExceptionHandlingControllerTest {

	@Value("${local.server.port}")
    private int port;

	private String apiEndPoint;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.apiEndPoint = "http://localhost:" + port + "/global/";
		template = new TestRestTemplate();
	}


	@Test
	public void アプリケーション共通の例外を通知する_デフォルト() throws Exception {
		// 操作
		ResponseEntity<String> response = 
				template.exchange(apiEndPoint+"order", HttpMethod.GET, null, String.class);
		
		// 確認
		Map<String, String> propertyMap = getBodyProperties(response);
		// ボディ
		assertThat(propertyMap.get("message"), is("No message available"));
		// ステータスコード
		assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@Test
	public void アプリケーション共通の例外を通知する_OrderNotFoundExceptionの場合() throws Exception {
		// 操作
		ResponseEntity<String> response = 
				template.exchange(apiEndPoint+"orderNotFound", HttpMethod.GET, null, String.class);
		
		// 確認
		Map<String, String> propertyMap = getBodyProperties(response);
		// ボディ
		assertThat(propertyMap.get("message"), is("取り引きが見つかりません"));
		// ステータスコード
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}


	@Test
	public void アプリケーション共通の例外を通知する_引数が重複した場合() throws Exception {
		// 操作
		ResponseEntity<String> response = 
				template.exchange(apiEndPoint+"order/11", HttpMethod.GET, null, String.class);
		
		// 確認
		Map<String, String> propertyMap = getBodyProperties(response);
		// ボディ
		assertThat(propertyMap.get("message"), is("Global_IDが重複しています"));
		// ステータスコード
		assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
	}

	/**
	 * 
	 * ControllerAdviceでは、正常終了し、返却することができない。
	 * 例外として処理される。
	 * 返却値も例外になる。
	 * TODO 動きを理解する必要がある
	 * @throws Exception
	 */
	@Test
	public void アプリケーション共通の例外を通知する_DBアクセスに失敗した場合() throws Exception {
		// 操作
		ResponseEntity<String> response = 
				template.exchange(apiEndPoint+"databaseError", HttpMethod.GET, null, String.class);
		
		// 確認
		// ステータスコード
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
	}


	@Test
	public void アプリケーション共通の例外を通知する_追加権限を持たない場合() throws Exception {
		// 操作
		ResponseEntity<String> response = 
				template.exchange(apiEndPoint+"add", HttpMethod.GET, null, String.class);
		
		// 確認
		Map<String, String> propertyMap = getBodyProperties(response);
		// ボディ
		assertThat(propertyMap.get("message"), is("Global_追加できません"));
		// ステータスコード
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}

	/**
	 * レスポンスボディを取得
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private Map<String, String> getBodyProperties(
			ResponseEntity<String> response) throws IOException,
			JsonParseException, JsonMappingException {
		Map<String, String> propertyMap = new HashMap<>();
		propertyMap = new ObjectMapper().readValue(response.getBody(), new TypeReference<HashMap<String, String>>(){});
		return propertyMap;
	}

}




