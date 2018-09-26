/**
 * 
 */
package com.nacho.microservices.microservicestest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nacho
 *
 */
@Repository
public interface UserReporitory extends JpaRepository<User, Integer>{

}
