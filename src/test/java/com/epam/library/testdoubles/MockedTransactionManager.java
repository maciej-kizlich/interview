package com.epam.library.testdoubles;

import org.hibernate.TransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class MockedTransactionManager implements PlatformTransactionManager {

	@Override
	public TransactionStatus getTransaction(TransactionDefinition definition)
			throws TransactionException {
		return null;
	}

	@Override
	public void commit(TransactionStatus status) throws TransactionException {

	}

	@Override
	public void rollback(TransactionStatus status) throws TransactionException {
	}
}