package com.game.quizzzy.repository;

import com.game.quizzzy.model.Category;
import com.game.quizzzy.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByCategory(Category category);
}
