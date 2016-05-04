package edu.sjsu.team113.service;

public interface IBlockchainService {

	String getSeed();

	/**
	 * @param body: should contain host, data, seed elements - json string
	 */
	String createTransaction(String jsonStringBody);

	/**
	 * @param body: should contain only host element - json string
	 */
	String getTransactionHashData(String mutationHashParam, String jsonStringBody);

	/**
	 * @param body: should contain only host element - json string
	 */
	String validateTransactionData(String mutationHashParam, String body);

	/**
	 * @param body: should contain only host element - json string
	 */
	String getMutationHashList(String body);

}
