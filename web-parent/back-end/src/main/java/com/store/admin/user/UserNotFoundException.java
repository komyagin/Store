package com.store.admin.user;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException  {
    public UserNotFoundException(String s) {
        super(s);
    }
}
