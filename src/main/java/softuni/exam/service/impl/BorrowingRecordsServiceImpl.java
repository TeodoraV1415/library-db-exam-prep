package softuni.exam.service.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportRecordDTO;
import softuni.exam.models.dto.ImportRecordRootDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: Implement all methods
@Service
public class BorrowingRecordsServiceImpl  implements BorrowingRecordsService {

    private static final String RECORDS_FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;

    private final LibraryMemberRepository libraryMemberRepository;

    private final BookRepository bookRepository;

    private final Unmarshaller unmarshaller;

    private ModelMapper modelMapper;

    private final Validator validator;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, LibraryMemberRepository libraryMemberRepository, BookRepository bookRepository) throws JAXBException {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.bookRepository = bookRepository;
        JAXBContext context = JAXBContext.newInstance(ImportRecordRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(RECORDS_FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        String xmlContent = readBorrowingRecordsFromFile();

        List<String> result = new ArrayList<>();

        ImportRecordRootDTO importRecordRootDTO = (ImportRecordRootDTO) this.unmarshaller.unmarshal(new StringReader(xmlContent));

        List<ImportRecordDTO> recordDTOs = importRecordRootDTO.getRecordDTOs();

        for (ImportRecordDTO recordDTO: recordDTOs) {
            Set<ConstraintViolation<ImportRecordDTO>> errors = this.validator.validate(recordDTO);
            if (errors.isEmpty()){
                Optional<LibraryMember> byId = this.libraryMemberRepository.findById(recordDTO.getMember().getId());
                Optional<Book> byTitle = this.bookRepository.findByTitle(recordDTO.getBook().getTitle());
                if (byId.isEmpty() || byTitle.isEmpty()){
                    result.add("Invalid borrowing record");
                } else {
                    BorrowingRecord record = this.modelMapper.map(recordDTO, BorrowingRecord.class);

                    LocalDate borrowDate = LocalDate.parse(recordDTO.getBorrowDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    record.setBorrowDate(borrowDate);
                    LocalDate returnDate = LocalDate.parse(recordDTO.getReturnDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    record.setReturnDate(returnDate);

                    record.setBook(byTitle.get());
                    record.setMember(byId.get());

                    this.borrowingRecordRepository.save(record);
                    result.add(String.format("Successfully imported borrowing record %s - %s", recordDTO.getBook().getTitle(), recordDTO.getBorrowDate()));
                }
            } else {
                result.add("Invalid borrowing record");
            }
        }

        return String.join("\n", result);
    }

    @Override
    public String exportBorrowingRecords() {
        Set<BorrowingRecord> allByReturnDateIsNull = borrowingRecordRepository
                .findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate.parse("2021-09-10"),Genre.SCIENCE_FICTION);

        return allByReturnDateIsNull.stream()
                .map(record -> String.format("Book title: %s\n" +
                                "*Book author: %s\n" +
                                "**Date borrowed: %s\n" +
                                "***Borrowed by: %s %s",
                        record.getBook().getTitle(),
                        record.getBook().getAuthor(),
                        record.getBorrowDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        record.getMember().getFirstName(),
                        record.getMember().getLastName()))
                .collect(Collectors.joining("\n")).trim();
    }
}
