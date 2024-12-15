package com.example.chat_api;

import java.util.List;

public class ApiResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public static class Message {
            private String content;

            public String getContent() {
                return content;
            }
        }
    }
}
