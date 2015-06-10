package org.me.server.model.Exceptions;


public class NoPrivilegeException extends BaseException {
    public NoPrivilegeException() { super("User does not have sufficient privilege to perform this task."); }
}
