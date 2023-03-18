package com.ams.api.report.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transaction;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ams.Utility.AppUtil;
import com.ams.Utility.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionReportService {

//	private final TransactionRepository transactionRepository;
//
//	public List<TransactionDTO> searchTransaction(TransactionSearch transactionSearch) {
//
//		List<Transaction> searchTransaction = transactionRepository.searchTransaction(transactionSearch);
//		
//		return searchTransaction.stream().map(transaction -> {
//			TransactionDTO transactionDTO = new TransactionDTO();
//			BeanUtils.copyProperties(transaction, transactionDTO);
//			transactionDTO.setCardNumberEnc(AppUtil.mask(transaction.getCardNumberEnc(), 6, 4));
//			transactionDTO.setAmount(String.format("%.2f", transaction.getAmount()));
//			transactionDTO.setGmtTxnTime(DateUtils.formatDate(transaction.getGmtTxnTime()));
//			transactionDTO.setLocalTxnTime(DateUtils.formatDate(transaction.getLocalTxnTime()));
//			return transactionDTO;
//		}).collect(Collectors.toList());
//	}
}