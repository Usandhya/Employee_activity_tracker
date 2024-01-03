package com.example.EmployeeActivityTracker.exception;

import com.example.EmployeeActivityTracker.response.ResultResponse;


public class InvalidOtpException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7990148673717742670L;

	public InvalidOtpException(String message) {
        super(message);
    }

	public InvalidOtpException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidOtpException(ResultResponse result) {
		// TODO Auto-generated constructor stub
	}
}
