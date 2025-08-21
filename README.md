# 📚 Edumate – Your Learning Partner

<img src="https://github.com/user-attachments/assets/b29caaef-c337-4667-89f2-a3260ca170b8" alt="login" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/82a3bd19-a9bd-444d-89a7-8945a37bb5ab" alt="sign_up" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/2ff09586-578f-4f90-a39f-efda37b6be6a" alt="edu_home" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/007b153f-4f7c-48c9-a2ed-036f3d9ef678" alt="semester_list" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/21d18968-e933-47ea-a540-19c7f5667e2a" alt="notes_list" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/03a7e59e-fdb7-43d9-8d3c-d23aa8d0f311" alt="study_playlist" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/3d210c83-08ac-4409-96ff-48289358ebac" alt="placement_series" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/97a2feb0-4e97-458f-862a-d51628db2b1e" alt="blogs" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/783e6429-1a09-4350-9a7e-7b2e7ab28c20" alt="blog_article" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/d7781b3d-df50-4e04-88db-f4b1e993e21a" alt="courses" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/91d4483a-b31e-42d2-84b0-e2b65447ad05" alt="courses_list" height="400" width="200"/>

<img src="https://github.com/user-attachments/assets/733e79e2-d10b-42ad-8afa-7e5ded6689c6" alt="web_dev_course_list" height="400" width="200"/>


**Edumate** is a mobile application designed to simplify and enhance the student learning experience. The app provides secure authentication, real-time data management, and an intuitive interface. It is built using **Kotlin**, **XML-based UI**, and powered by **Firebase** services.

This project demonstrates strong skills in Android development, backend logic integration, and real-time database management.

---

## ✨ Features

* 🔑 **Firebase Authentication** – Secure login & signup with email/password.
* 📡 **Realtime Database** – Instant synchronization of user data across devices.
* 🖥 **XML UI Design** – Clean, responsive, and user-friendly interface.
* ⚡ **Kotlin Backend** – Efficient business logic implementation.
* 🎥 **Demo Video** – [Click to watch](https://youtube.com/shorts/eMClF2unXFA?feature=share).

---

## 🛠 Tech Stack

* **Frontend (UI):** XML (Android Layouts)
* **Backend (Logic):** Kotlin
* **Database:** Firebase Realtime Database
* **Authentication:** Firebase Authentication
* **Tools & IDE:** Android Studio, Gradle

---

## 📂 Project Structure

```
.idea/
    ├── .gitignore
    ├── compiler.xml
    ├── deploymentTargetDropDown.xml
    ├── deploymentTargetSelector.xml
    ├── gradle.xml
    ├── kotlinc.xml
    ├── migrations.xml
    ├── misc.xml
    ├── runConfigurations.xml
    └── vcs.xml
app/
    ├── release/
        └── app-release.aab
    ├── src/
        ├── androidTest/
            └── java/
                └── com/
                    └── edumate/
                        └── fellowmate/
                            └── ExampleInstrumentedTest.kt
        ├── main/
            ├── java/
                └── com/
                    └── edumate/
                        └── fellowmate/
                            ├── activities/
                                ├── BlogView.kt
                                ├── CodingPlaylistActivity.kt
                                ├── CompleteCoursePackage.kt
                                ├── Course.kt
                                ├── ForgetPasswordActivity.kt
                                ├── LoginActivity.kt
                                ├── MainActivity.kt
                                ├── NotesActivity.kt
                                ├── PlacementSeriesActivity.kt
                                ├── SemesterActivity.kt
                                ├── SignUpActivity.kt
                                ├── SplashScreen.kt
                                ├── StudyPlaylistActivity.kt
                                └── SubjectActivity.kt
                            ├── adapters/
                                ├── BlogsAdapter.kt
                                ├── CompleteCoursePackageChildAdapter.kt
                                ├── CompleteCoursePackageParentAdapter.kt
                                ├── CourseAdapter.kt
                                ├── CoursesListAdapter.kt
                                ├── DepartmentAdapter.kt
                                ├── NotesChildAdapter.kt
                                ├── NotesParentAdapter.kt
                                ├── PlacementSeriesChildAdapter.kt
                                ├── PlacementSeriesParentAdapter.kt
                                ├── RecommendedCourseAdapter.kt
                                ├── SemesterAdapter.kt
                                ├── StudyPlaylistAdapter.kt
                                └── SubjectAdapter.kt
                            ├── fragments/
                                ├── BlogsList.kt
                                ├── Courses.kt
                                ├── Home.kt
                                ├── OurTeam.kt
                                └── UserProfile.kt
                            ├── models/
                                ├── Blogs.kt
                                ├── CompleteChildCoursePackage.kt
                                ├── CompleteParentCoursePackage.kt
                                ├── Course.kt
                                ├── CoursesList.kt
                                ├── Department.kt
                                ├── ImageSlide.kt
                                ├── NotesChildItem.kt
                                ├── NotesParentItem.kt
                                ├── PlacementChildItem.kt
                                ├── PlacementParentItem.kt
                                ├── RecommendedCourse.kt
                                ├── Semester.kt
                                ├── StudyPlaylist.kt
                                ├── Subject.kt
                                └── Users.kt
                            └── utils/
                                └── ColorPicker.kt
            ├── res/
                ├── anim/
                    └── splash_screen_anim.xml
                ├── color/
                    └── bottom_nav_selected_item_color.xml
                ├── drawable/
                    ├── ai_ds.xml
                    ├── ai_ml.xml
                    ├── alert_background.xml
                    ├── app_logo.png
                    ├── arrow_back.xml
                    ├── arrow_down.xml
                    ├── arrow_right.xml
                    ├── arrow_up.xml
                    ├── article.xml
                    ├── bca_branch.xml
                    ├── bca.xml
                    ├── blog.xml
                    ├── book_notes.xml
                    ├── book.xml
                    ├── btn_background_1.xml
                    ├── btn_background_2.xml
                    ├── card_background.xml
                    ├── coding.xml
                    ├── college.xml
                    ├── company.xml
                    ├── contact_us.xml
                    ├── cse_branch.xml
                    ├── cse.xml
                    ├── cursor_color.xml
                    ├── developer.xml
                    ├── ece_branch.xml
                    ├── ece.xml
                    ├── email.xml
                    ├── error.xml
                    ├── exam_notes.xml
                    ├── forget_password.xml
                    ├── founder.xml
                    ├── gradient_background.xml
                    ├── graduation_degree.xml
                    ├── handwritting_notes.xml
                    ├── home.xml
                    ├── horizontal_bar.xml
                    ├── ic_correct.xml
                    ├── ic_course.xml
                    ├── ic_hamburger.xml
                    ├── ic_launcher_background.xml
                    ├── ic_launcher_foreground.xml
                    ├── ic_wrong.xml
                    ├── important_questions.xml
                    ├── indicator_dot_active.xml
                    ├── indicator_dot_inactive.xml
                    ├── input_box.xml
                    ├── instagram.xml
                    ├── it_branch.xml
                    ├── it.xml
                    ├── lab_file.xml
                    ├── linkedin.xml
                    ├── log_out.xml
                    ├── log_out2.xml
                    ├── mail.xml
                    ├── me_branch.xml
                    ├── me.xml
                    ├── module.xml
                    ├── oval_shape.xml
                    ├── placement.xml
                    ├── playlist.xml
                    ├── ppt.xml
                    ├── profile_.xml
                    ├── profile_details.xml
                    ├── profile_top_background.xml
                    ├── profile.xml
                    ├── progress_bg.xml
                    ├── r_and_d.xml
                    ├── rate_us.xml
                    ├── search.xml
                    ├── share.xml
                    ├── svg_forget_password.xml
                    ├── svg_login.xml
                    ├── svg_loving.xml
                    ├── svg_sign_up.xml
                    ├── svg_signup.xml
                    ├── teacher_notes.xml
                    ├── team.xml
                    ├── time_watch.xml
                    ├── toolbar_design_background.xml
                    ├── toolbar_design.xml
                    ├── welcome_to_edumate.png
                    └── youtube_logo.xml
                ├── drawable-hdpi/
                    └── ic_action_name.png
                ├── drawable-mdpi/
                    └── ic_action_name.png
                ├── drawable-xhdpi/
                    └── ic_action_name.png
                ├── drawable-xxhdpi/
                    └── ic_action_name.png
                ├── drawable-xxxhdpi/
                    └── ic_action_name.png
                ├── font/
                    ├── inter_bold.xml
                    ├── inter_semibold.xml
                    └── inter.xml
                ├── layout/
                    ├── activity_blog.xml
                    ├── activity_coding_playlist.xml
                    ├── activity_complete_course_package.xml
                    ├── activity_course.xml
                    ├── activity_forget_password.xml
                    ├── activity_login.xml
                    ├── activity_main.xml
                    ├── activity_notes.xml
                    ├── activity_placement_series.xml
                    ├── activity_semester.xml
                    ├── activity_sign_up.xml
                    ├── activity_splash_screen.xml
                    ├── activity_study_playlist.xml
                    ├── activity_subject.xml
                    ├── blog_item.xml
                    ├── blogs_article_list.xml
                    ├── child_item_notes.xml
                    ├── child_item_placement_series.xml
                    ├── college_name_list_item.xml
                    ├── complete_package_child_item.xml
                    ├── complete_package_parent_item.xml
                    ├── courses_item.xml
                    ├── department_item.xml
                    ├── dialog_custom_alert.xml
                    ├── drawer_layout.xml
                    ├── fragment_courses.xml
                    ├── fragment_home.xml
                    ├── fragment_our_team.xml
                    ├── fragment_profile.xml
                    ├── one_course_item.xml
                    ├── parent_item_notes.xml
                    ├── parent_item_placement_series.xml
                    ├── progress_bar_layout.xml
                    ├── recommended_course_item.xml
                    ├── semester_item.xml
                    ├── study_playlist_item.xml
                    └── subject_item.xml
                ├── menu/
                    ├── bottom_nav.xml
                    └── option_menu.xml
                ├── mipmap-anydpi-v26/
                    ├── ic_launcher_round.xml
                    └── ic_launcher.xml
                ├── mipmap-hdpi/
                    ├── ic_launcher_foreground.webp
                    ├── ic_launcher_round.webp
                    └── ic_launcher.webp
                ├── mipmap-mdpi/
                    ├── ic_launcher_foreground.webp
                    ├── ic_launcher_round.webp
                    └── ic_launcher.webp
                ├── mipmap-xhdpi/
                    ├── ic_launcher_foreground.webp
                    ├── ic_launcher_round.webp
                    └── ic_launcher.webp
                ├── mipmap-xxhdpi/
                    ├── ic_launcher_foreground.webp
                    ├── ic_launcher_round.webp
                    └── ic_launcher.webp
                ├── mipmap-xxxhdpi/
                    ├── ic_launcher_foreground.webp
                    ├── ic_launcher_round.webp
                    └── ic_launcher.webp
                ├── values/
                    ├── colors.xml
                    ├── font_certs.xml
                    ├── ic_launcher_background.xml
                    ├── preloaded_fonts.xml
                    ├── strings.xml
                    └── themes.xml
                ├── values-night/
                    └── themes.xml
                └── xml/
                    ├── backup_rules.xml
                    └── data_extraction_rules.xml
            ├── AndroidManifest.xml
            └── ic_launcher-playstore.png
        └── test/
            └── java/
                └── com/
                    └── edumate/
                        └── fellowmate/
                            └── ExampleUnitTest.kt
    ├── .gitignore
    ├── build.gradle.kts
    ├── google-services.json
    └── proguard-rules.pro
gradle/
    └── wrapper/
        ├── gradle-wrapper.jar
        └── gradle-wrapper.properties
.gitignore
build.gradle.kts
gradle.properties
gradlew
gradlew.bat
README.md
settings.gradle.kts 
```

---

## ⚙️ Installation & Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/prince-kumar-dev/EDUMATE.git
   cd EDUMATE
   ```
2. Open in **Android Studio**.
3. Connect Firebase:

   * Add your `google-services.json` file.
   * Enable **Authentication** & **Realtime Database** in Firebase console.
4. Run the app on an emulator or physical device.

---

## 📈 Learning Outcomes

* Gained hands-on experience with **Firebase integration in Android**.
* Implemented **user authentication & session management**.
* Designed **scalable database structure** for real-time updates.
* Enhanced **problem-solving & app architecture skills**.

---

## 👤 Author

**Prince Kumar**

* 🎓 B.Tech CSE, Chandigarh Group of Colleges (2025)
* 💼 Aspiring Android Developer | Java | Kotlin | Firebase | Full-Stack Development
* 📫 [LinkedIn](https://www.linkedin.com/in/princekumar-dev/) | [GitHub](https://github.com/prince-kumar-dev)

---
