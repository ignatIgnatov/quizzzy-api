package com.game.quizzzy.repository;

import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Question;
import com.game.quizzzy.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByRoomCategory(Category category);

    @Query("SELECT q FROM Question q WHERE q.room = :room AND q.approved = true")
    List<Question> findAllApprovedQuestionsByRoom(Room room);
}
