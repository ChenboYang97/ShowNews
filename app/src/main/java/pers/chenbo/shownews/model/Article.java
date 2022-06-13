package pers.chenbo.shownews.model;

import java.util.Objects;

public class Article {
    public String author;
    public String content;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) && Objects.equals(content, article.content) && Objects.equals(description, article.description) && Objects.equals(url, article.url) && Objects.equals(urlToImage, article.urlToImage) && Objects.equals(publishedAt, article.publishedAt) && Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, description, url, urlToImage, publishedAt, title);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
