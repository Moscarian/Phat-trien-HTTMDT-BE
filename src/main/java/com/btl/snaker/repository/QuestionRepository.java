package com.btl.snaker.repository;

import com.btl.snaker.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Tìm câu hỏi theo từ khóa (không phân biệt hoa thường)
    @Query("SELECT q FROM Question q WHERE LOWER(q.question) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Question> findByQuestionContainingIgnoreCase(String keyword);
}