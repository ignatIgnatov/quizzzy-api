package com.game.quizzzy.repository;

import com.game.quizzzy.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayRepository extends JpaRepository<Question, Long> {
}
