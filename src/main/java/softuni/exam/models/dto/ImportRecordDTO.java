package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportRecordDTO {

    @XmlElement(name = "book")
    @NotNull
    private BookDTO book;

    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberDTO member;

    @XmlElement(name = "borrow_date")
    @NotNull
    private String borrowDate;

    @XmlElement(name = "return_date")
    @NotNull
    private String returnDate;

    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

    public ImportRecordDTO() {
    }

    public BookDTO getBook() {
        return book;
    }

    public ImportRecordDTO setBook(BookDTO book) {
        this.book = book;
        return this;
    }

    public LibraryMemberDTO getMember() {
        return member;
    }

    public ImportRecordDTO setMember(LibraryMemberDTO member) {
        this.member = member;
        return this;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public ImportRecordDTO setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public ImportRecordDTO setReturnDate(String returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ImportRecordDTO setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }
}
