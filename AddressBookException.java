package com.sqldemo;

public class AddressBookException {
	enum ExceptionType {
    	DATABASE_EXCEPTION, NO_SUCH_CLASS, CONNECTION_FAILED    }
    public ExceptionType type;
    public AddressBookException(String message, ExceptionType type) {
        super();
        this.type = type;
    }
}

