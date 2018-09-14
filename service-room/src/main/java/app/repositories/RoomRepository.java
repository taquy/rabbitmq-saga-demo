package app.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Room;

@Repository
@Transactional
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
