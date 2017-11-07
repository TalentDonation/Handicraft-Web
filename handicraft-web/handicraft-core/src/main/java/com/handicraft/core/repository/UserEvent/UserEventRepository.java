package com.handicraft.core.repository.UserEvent;

import com.handicraft.core.dto.UserEvent.UserEvent;
import com.handicraft.core.id.UserEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent , UserEventId> {

    void deleteAllByUserEventId_Uid(long uid);

    void deleteAllByUserEventId_Eid(long eid);

    List<UserEvent> findAllByUserEventId_Uid(long uid);

    List<UserEvent> findAllByUserEventId_Eid(long uid);

}