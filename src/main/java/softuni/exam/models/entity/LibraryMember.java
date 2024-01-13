package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "library_members")
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = true)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    public LibraryMember() {
    }

    public Long getId() {
        return id;
    }

    public LibraryMember setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public LibraryMember setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public LibraryMember setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LibraryMember setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LibraryMember setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


}
