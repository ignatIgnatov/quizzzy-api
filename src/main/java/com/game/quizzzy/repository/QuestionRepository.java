package com.game.quizzzy.repository;

import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByRoomCategory(Category category);
}
