package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(nullable = true)
    private String remarks;

    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    private LibraryMember member;

    public BorrowingRecord() {
    }

    public Long getId() {
        return id;
    }

    public BorrowingRecord setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecord setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BorrowingRecord setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public BorrowingRecord setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BorrowingRecord setBook(Book book) {
        this.book = book;
        return this;
    }

    public LibraryMember getMember() {
        return member;
    }

    public BorrowingRecord setMember(LibraryMember member) {
        this.member = member;
        return this;
    }
}
