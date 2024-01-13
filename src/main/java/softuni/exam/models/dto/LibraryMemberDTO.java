package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryMemberDTO {

    @XmlElement
    private Long id;

    public Long getId() {
        return id;
    }

    public LibraryMemberDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
