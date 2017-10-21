package com.handicraft.core.repository.Authorities;

import com.handicraft.core.dto.Authorities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority , Long>{
}
