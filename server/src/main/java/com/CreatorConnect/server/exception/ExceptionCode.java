package com.CreatorConnect.server.exception;

import lombok.Getter;

public enum ExceptionCode {
    INVALID_MEMBER(400, "Invalid member object. Please provide a valid member object."),
    MEMBER_NOT_ALLOWED(403, "Access denied. Please authenticate as a member."),
    MEMBER_NOT_FOUND(404, "Member not found. Please check the member ID and try again."),
    MEMBER_EXISTS(409, "Member already exists. Please provide a different member ID."),
    MEMBER_FIELD_NOT_FOUND(500, "Member field not found. Please check the object and try again."),
    CATEGORY_EXISTS(409, "Category already exists. Please provide a different category"),
    CATEGORY_NOT_FOUND(404, "Category not found, Please check the Category Name and try again"),
    FREEBOARD_NOT_FOUND(404, "FreeBoard not found, Please check the FreeBoard ID and try again.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
