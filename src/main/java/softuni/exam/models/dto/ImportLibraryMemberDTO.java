package softuni.exam.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImportLibraryMemberDTO {

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @Size(min = 2, max = 40)
    private String address;

    @NotNull
    @Size(min = 2, max = 20)
    private String phoneNumber;

    public ImportLibraryMemberDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public ImportLibraryMemberDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ImportLibraryMemberDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ImportLibraryMemberDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ImportLibraryMemberDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
