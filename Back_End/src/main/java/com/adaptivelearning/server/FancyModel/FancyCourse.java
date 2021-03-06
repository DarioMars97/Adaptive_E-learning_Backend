package com.adaptivelearning.server.FancyModel;

import com.adaptivelearning.server.Model.Course;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.StudentCourseRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FancyCourse {
    @JsonIgnore
    private StudentCourseRepository studentCourseRepository;
    // id
    private Long courseId;

    // title
    private String title;

    // detailed title
    private String detailedTitle;

    // description
    private String description;

    // rate 0->5
    private float rate;

    // privacy  1-> public  2-> secret (for classroom)
    private boolean isPublic;

    // level 0->5 from easier to harder
    private short level;

    // category
    private String category;

    // publish date
    private Date publishDate;

    // number of students
    private Integer numberOfStudents=0;

    // number of raters
    private Integer numberOfRaters=0;

    // publisher
    private FancyUser publisher;
    
    // sections
    private List<FancySection> sections;
    
    // course picture
    private FancyMediaFile course_picture;

    // who requested role
    private String role = "";
    
    public FancyCourse(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }
   
	public FancyCourse toFancyCourseMapping(Course course, User requester){
		FancyUser user= new FancyUser();
		FancySection sections=new FancySection();
		FancyMediaFile picture=new FancyMediaFile();
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.detailedTitle = course.getDetailedTitle();
        this.description = course.getDescription();
        this.publishDate = course.getPublishDate();
        this.level = course.getLevel();
        this.category = course.getCategory().getName();
        this.numberOfStudents = course.getNumberOfStudents();
        this.numberOfRaters = course.getNumberOfRaters();
        this.isPublic = course.isPublic();
        this.rate = course.getRate();
        this.publisher = user.toFancyUserMapper(course.getPublisher());
        if(course.getCourse_picture()!=null) {
        this.course_picture=picture.toFancyFileMapping(course.getCourse_picture());
        }
        if (requester != null){
            Float rank;
            if (course.getLearners().contains(requester)){
                this.role = "student";
                rank = studentCourseRepository.findByUserAndCourse(requester, course).getRank();
            }
            else if (course.getPublisher().equals(requester)){
                this.role = "teacher";
                rank = null;
            }
            else
                rank = null;
            this.sections=sections.toFancySectionListMapping(course.getSections(),course.getPublisher().equals(requester),rank);
        }
        else
            this.sections=sections.toFancySectionListMapping(course.getSections(),false,null);

        return this;
    }

    public List<FancyCourse> toFancyCourseListMapping(List<Course> courses, User requester){
        List<FancyCourse> FancyCourseList = new LinkedList<>();
        for (Course course:
                courses) {
            FancyCourse fancyCourse = new FancyCourse(this.studentCourseRepository);
            ((LinkedList<FancyCourse>) FancyCourseList).addLast(fancyCourse.toFancyCourseMapping(course, requester));
        }
        return FancyCourseList;
    }


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public FancyMediaFile getCourse_picture() {
		return course_picture;
	}


	public void setCourse_picture(FancyMediaFile course_picture) {
		this.course_picture = course_picture;
	}


	public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedTitle() {
        return detailedTitle;
    }

    public void setDetailedTitle(String detailedTitle) {
        this.detailedTitle = detailedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getNumberOfRaters() {
        return numberOfRaters;
    }

    public void setNumberOfRaters(Integer numberOfRaters) {
        this.numberOfRaters = numberOfRaters;
    }
    public FancyUser getPublisher() {
		return publisher;
	}

	public void setPublisher(FancyUser publisher) {
		this.publisher = publisher;
	}


	public List<FancySection> getSections() {
		return sections;
	}


	public void setSections(List<FancySection> sections) {
		this.sections = sections;
	}


	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
