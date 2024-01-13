package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportBookDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO: Implement all methods

@Service
public class BookServiceImpl implements BookService{

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files
                .readString(Path.of(BOOKS_FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        String json = this.readBooksFromFile();

        ImportBookDTO [] importBookDTOS = this.gson.fromJson(json, ImportBookDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportBookDTO bookDTO: importBookDTOS) {
            Set<ConstraintViolation<ImportBookDTO>> errors = this.validator.validate(bookDTO);

            if (errors.isEmpty()){
                Optional<Book> optBook = this.bookRepository.findByTitle(bookDTO.getTitle());
                if (optBook.isEmpty()){
                    Book book = this.modelMapper.map(bookDTO, Book.class);
                    this.bookRepository.save(book);
                    result.add(String.format("Successfully imported book %s - %s", bookDTO.getAuthor(), bookDTO.getTitle()));
                } else {
                    result.add("Invalid book");
                }
            } else {
                result.add("Invalid book");
            }
        }
        return String.join("\n", result);
    }
}
