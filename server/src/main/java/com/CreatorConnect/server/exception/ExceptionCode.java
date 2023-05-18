package com.CreatorConnect.server.exception;

import lombok.Getter;

public enum ExceptionCode {
    INVALID_MEMBER(400, "Invalid member object. Please provide a valid member object."),
    MEMBER_NOT_ALLOWED(403, "Access denied. Please authenticate as a member."),
    MEMBER_NOT_FOUND(404, "Member not found. Please check the member ID and try again."),
    CATEGORY_NOT_FOUND(404, "Category not found. Please check the Category and try again."),
    FEEDBACK_NOT_FOUND(404, "Feedback not found. Please check the Feedback ID and try again."),
    FREEBOARD_NOT_FOUND(404, "FreeBoard not found, Please check the FreeBoard ID and try again."),
    FEEDBACK_CATEGORY_NOT_FOUND(404, "FeedbackCategory not found. Please check the FeedbackCategory and try again."),
    COMMENT_NOT_FOUND(404, "Comment not found. Please check the CommentId and try again."),
    RE_COMMENT_NOT_FOUND(404, "ReComment not found. Please check the ReCommentId and try again."),
    MEMBER_EXISTS(409, "Member already exists. Please provide a different member ID."),
    FOLLOWING_ALREADY_EXISTS(409, "The member is already being followed."),
    FOLLOWING_ALREADY_DELETED(409,  "The member has already been unfollowed."),
    CATEGORY_EXISTS(409, "Category already exists. Please provide a different category"),
    MEMBER_FIELD_NOT_FOUND(500, "Member field not found. Please check the object and try again."),
    JOBBOARD_NOT_FOUND(404, "JobBoard not found, Please check the JobBoard ID and try again."),
    INVALID_TOKEN(403, "Invalid token. Please provide a valid token."),
    NOTICE_NOT_FOUND(404, "Notice not found, Please check the Notice Id and try again");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
