package com.org.qcc.dataapi.config.customer;

public interface AlertHandler {
	<T> void handleAlert(AlertInfo<T> alertInfo);
}