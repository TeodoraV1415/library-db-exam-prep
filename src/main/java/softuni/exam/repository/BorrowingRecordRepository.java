package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.enums.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// TODO:
@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {


    Set<BorrowingRecord> findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate parse, Genre genre);
}
