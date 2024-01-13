package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportBookDTO;
import softuni.exam.models.dto.ImportLibraryMemberDTO;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;

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
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private static final String MEMBERS_FILE_PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(MEMBERS_FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        String json = this.readLibraryMembersFileContent();

        ImportLibraryMemberDTO[] importMembersDTOs = this.gson.fromJson(json, ImportLibraryMemberDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportLibraryMemberDTO memberDTO : importMembersDTOs){
            Set<ConstraintViolation<ImportLibraryMemberDTO>> errors = this.validator.validate(memberDTO);

            if (errors.isEmpty()){
                Optional<LibraryMember> optMember = this.libraryMemberRepository.findByPhoneNumber(memberDTO.getPhoneNumber());
                if (optMember.isEmpty()){
                    LibraryMember member = this.modelMapper.map(memberDTO, LibraryMember.class);
                    this.libraryMemberRepository.save(member);
                    result.add(String.format("Successfully imported library member %s - %s", member.getFirstName(), member.getLastName()));
                } else {
                    result.add("Invalid library member");
                }
            } else {
                result.add("Invalid library member");
            }
        }
        return String.join("\n", result);
    }
}
