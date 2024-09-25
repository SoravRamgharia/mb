package com.mb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mb.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	// Extra Methods DB Related Operations

// Custom Finder Methods...
	Optional<User> findById(Long userId);

	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

	@Query("select u from user u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);

//	@Query("select u from User u where u.userId = :userId")
//	public User getUserByUserId(@Param("userId") String userId);

// Find Match User by Custom Criteria ----->
//	@Query("SELECT u FROM user u WHERE " +
//	        "(u.gender = :gender) AND " +
//	        "(u.religion = :religion) AND " +
//	        "(u.marriedStatus = :marriedStatus) AND " +
//	        "(u.place = :place) AND " +
//	        "(:caste IS NULL OR u.caste = :caste) AND " +
//	        "(:minAge IS NULL OR u.age >= :minAge) AND " +
//	        "(:maxAge IS NULL OR u.age <= :maxAge) AND " +
//	        "(:minHeight IS NULL OR u.height >= :minHeight) AND " +
//	        "(:maxHeight IS NULL OR u.height <= :maxHeight) AND " +
//	        "(:occupation IS NULL OR u.occupation = :occupation)")
	@Query("SELECT u FROM user u WHERE ( u.gender = :gender ) AND " + "( u.religion = :religion ) AND "
			+ "( u.caste = :caste ) AND " + "( u.age BETWEEN :minAge AND :maxAge ) AND "
			+ "( u.height BETWEEN :minHeight AND :maxHeight ) AND " + "( u.marriedStatus = :marriedStatus ) AND "
			+ "( u.place = :place ) AND " + "( u.occupation = :occupation)")
	List<User> findUsersByCustomCriterialist(@Param("gender") String gender, @Param("religion") String religion,
			@Param("caste") String caste, @Param("minAge") int minAge, @Param("maxAge") int maxAge,
			@Param("minHeight") int minHeight, @Param("maxHeight") int maxHeight,
			@Param("marriedStatus") String marriedStatus, @Param("place") String place,
			@Param("occupation") String occupation);

	@Query("SELECT u FROM user u WHERE ( u.gender = :gender ) AND " + "( u.religion = :religion ) AND "
			+ "( u.caste = :caste ) AND " + "( u.age BETWEEN :minAge AND :maxAge ) AND "
			+ "( u.height BETWEEN :minHeight AND :maxHeight ) AND " + "( u.marriedStatus = :marriedStatus ) AND "
			+ "( u.place = :place ) AND " + "( u.occupation = :occupation)")
	Page<User> findUsersByCustomCriteria(@Param("gender") String gender, @Param("religion") String religion,
			@Param("caste") String caste, @Param("minAge") int minAge, @Param("maxAge") int maxAge,
			@Param("minHeight") int minHeight, @Param("maxHeight") int maxHeight,
			@Param("marriedStatus") String marriedStatus, @Param("place") String place,
			@Param("occupation") String occupation, Pageable pageable);

//	List<User> findByAgeBetween(int minAge, int maxAge);

//	List<User> findByGenderAndReligionAndCasteAndMinAgeAndMaxAgeAndMinHeightAndMaxHeightAndMarriedStatusAndPlaceAndOccupation(
//			String gender, String religion, String caste, int minAge, int maxAge, int minHeight, int maxHeight,
//			String marriedStatus, String place, String occupation);

	// Custom Finder Method ----->
//	Page<User> findByUser(List<User> user, Pageable pageable);

	@EntityGraph(attributePaths = { "userId", "gender", "religion", "caste", "age", "height", "marriedStatus", "place",
			"occupation" })
	Page<User> findAll(Pageable pageable);

}
