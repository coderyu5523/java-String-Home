package com.example.conbasic.board;

import lombok.Data;

public class BoardRequest {
    @Data
    public static class saveDTO{

        private String title ;
        private String content ;
    }
}
