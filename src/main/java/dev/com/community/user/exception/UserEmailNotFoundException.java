package dev.com.community.user.exception;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public class UserEmailNotFoundException extends RuntimeException {

    public UserEmailNotFoundException(String s) {
        super(s);
    }
}
