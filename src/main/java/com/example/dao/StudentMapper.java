package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper {
	// @Select("select npm, name, gpa from student where npm = #{npm}")
	// StudentModel selectStudent(@Param("npm") String npm);

	@Select("select npm, name, gpa from student where npm = #{npm}")
	@Results(value = { @Result(property = "npm", column = "npm"), @Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm", javaType = List.class, many = @Many(select = "selectCourses")) })
	StudentModel selectStudent(@Param("npm") String npm);

	// @Select("select npm, name, gpa from student")
	// List<StudentModel> selectAllStudents();

	@Select("select npm, name, gpa from student")
	@Results(value = { @Result(property = "npm", column = "npm"), @Result(property = "name", column = "name"),
			@Result(property = "gpa", column = "gpa"),
			@Result(property = "courses", column = "npm", javaType = List.class, many = @Many(select = "selectCourses")) })
	List<StudentModel> selectAllStudents();

	@Insert("insert into student (npm, name, gpa) values (#{npm}, #{name}, #{gpa})")
	void addStudent(StudentModel student);

	@Delete("delete from student where npm = #{npm}")
	void deleteStudent(@Param("npm") String npm);

	@Select("update student set name = #{name}, gpa = #{gpa} where npm = #{npm}")
	void updateStudent(StudentModel student);

	@Select("select course.id_course, name, credits " + "from studentcourse join course "
			+ "on studentcourse.id_course = course.id_course " + "where studentcourse.npm = #{npm}")
	List<CourseModel> selectCourses(@Param("npm") String npm);

	@Select("select id_course, name, credits from course where id_course = #{id}")
	@Results(value = { @Result(property = "id", column = "id_course"), @Result(property = "name", column = "name"),
			@Result(property = "credits", column = "credits"),
			@Result(property = "students", column = "id_course", javaType = List.class, many = @Many(select = "selectStudents")) })
	CourseModel selectCourse(@Param("id") String id);
	
	@Select("select student.npm, name, gpa " + "from studentcourse join student "
			+ "on studentcourse.npm = student.npm " + "where studentcourse.id_course = #{id}")
	List<StudentModel> selectStudents(@Param("id") String id);
}