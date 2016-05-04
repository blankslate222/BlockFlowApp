package edu.sjsu.team113.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

public class BlockchainService implements IBlockchainService {

	@Value("${node.server}")
	private String nodeServer;

	@Value("${chain.server}")
	private String openchainServer;

	private CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	/**
	 * @param body: should contain host, data, seed elements - json string
	 */
	public String createTransaction(String stringBody) {
		String mutationHash = null;
		StringEntity entity = null;
		String path = "createTransaction";
		try {
			entity = new StringEntity(stringBody);
			mutationHash = sendPostMessage(path, entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mutationHash;
	}

	@Override
	/**
	 * @param body: should contain only host element - json string
	 */
	public String getTransactionHashData(String mutationHashParam, String body) {
		String hashData = null;
		StringEntity entity = null;
		String path = "getTransactionHashData/" + mutationHashParam;
		try {
			entity = new StringEntity(body);
			hashData = sendPostMessage(path, entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashData;
	}

	@Override
	/**
	 * @param body: should contain only host element - json string
	 */
	public String validateTransactionData(String mutationHashParam, String body) {
		String response = null;
		StringEntity entity = null;
		String path = "validateTransactionData/" + mutationHashParam;
		try {
			entity = new StringEntity(body);
			response = sendPostMessage(path, entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	/**
	 * @param body: should contain only host element - json string
	 */
	public String getMutationHashList(String body) {
		String response = null;
		StringEntity entity = null;
		String path = "getMutationHashList";
		try {
			entity = new StringEntity(body);
			response = sendPostMessage(path, entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private String sendPostMessage(String path, StringEntity jsonEntity) {
		String jsonResp = null;
		String endpoint = nodeServer + path;
		CloseableHttpResponse response = null;
		HttpPost postReq = new HttpPost(endpoint);
		try {
			postReq.setEntity(jsonEntity);
			response = httpClient.execute(postReq);
			HttpEntity responseEntity = response.getEntity();
			jsonResp = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResp;
	}

	@Override
	public String getSeed() {
		String seed = null;
		try {
			seed = generateId();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seed;
	}

	private String generateId() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[5];
		random.nextBytes(bytes);
		return byteArrayToHex(bytes);
	}

	private String byteArrayToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String theHex = Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
			sb.append(theHex.length() == 1 ? "0" + theHex : theHex);
		}
		return sb.toString();
	}
}
