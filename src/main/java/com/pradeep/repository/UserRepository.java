package com.pradeep.repository;

import com.pradeep.entity.Role;
import com.pradeep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
