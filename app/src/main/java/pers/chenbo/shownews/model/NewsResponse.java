package pers.chenbo.shownews.model;

import java.util.List;

public class NewsResponse {
    public Integer totalResults;
    public List<Article> articles;
    public String status;
    public String code;
    public String message;

    @Override
    public String toString() {
        return "NewsResponse{" +
                "totalResults=" + totalResults +
                ", articles=" + articles +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
