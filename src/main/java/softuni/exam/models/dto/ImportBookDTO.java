package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.Genre;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ImportBookDTO {

    @Size(min = 3, max = 40)
    private String title;

    @Size(min = 3, max = 40)
    private String author;

    @Size(min = 5)
    private String description;

    private boolean available;

    private Genre genre;

    @Positive
    private Double rating;

    public ImportBookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public ImportBookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ImportBookDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ImportBookDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public ImportBookDTO setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public ImportBookDTO setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public ImportBookDTO setRating(Double rating) {
        this.rating = rating;
        return this;
    }
}
