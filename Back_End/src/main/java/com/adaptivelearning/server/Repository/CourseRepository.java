package com.adaptivelearning.server.Repository;

import com.adaptivelearning.server.constants.Repos;
import org.springframework.data.jpa.repository.JpaRepository;
import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	Course findByCourseId(Integer courseId);

	Boolean existsByLearners(User user);

	Boolean existsByRaters(User user);

	@Query("SELECT a FROM Course a " +
			"ORDER BY a.numberOfStudents DESC")
	List<Course> findHotestCourses();

	@Query("SELECT a FROM Course a " +
			"ORDER BY a.publishDate DESC")
	List<Course> findNewestCourses();
}
