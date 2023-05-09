package com.CreatorConnect.server.feedbackboard.dto;

import com.CreatorConnect.server.category.entity.Category;
import com.CreatorConnect.server.feedbackcategory.entity.FeedbackCategory;
import com.CreatorConnect.server.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class FeedbackBoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private String title;
        private String link;
        private String content;
        private String tag;
        private String categoryName;
        private String feedbackCategoryName;

        public Member getMember() {
            Member member = new Member();
            member.setMemberId(memberId);

            return member;
        }
        public Category getCategory() {
            Category category = new Category();
            category.setCategoryName(categoryName);

            return category;
        }

        public FeedbackCategory getFeedbackCategory() {
            FeedbackCategory feedbackCategory = new FeedbackCategory();
            feedbackCategory.setFeedbackCategoryName(feedbackCategoryName);

            return feedbackCategory;
        }
    }

    @Getter
    @Setter
    public static class Patch {
        private long feedbackBoardId;
        private String title;
        private String link;
        private String content;
        private String tag;
        private String category;
        private String feedbackCategory;
    }

}
