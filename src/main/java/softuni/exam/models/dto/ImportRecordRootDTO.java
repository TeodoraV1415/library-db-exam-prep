package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportRecordRootDTO {

    @XmlElement(name = "borrowing_record")
    private List<ImportRecordDTO> recordDTOs;

    public List<ImportRecordDTO> getRecordDTOs() {
        return recordDTOs;
    }

    public ImportRecordRootDTO setRecordDTOs(List<ImportRecordDTO> recordDTOs) {
        this.recordDTOs = recordDTOs;
        return this;
    }
}
