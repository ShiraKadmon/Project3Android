# Run the Project
Git clone: https://github.com/AdvaOren/Project2Android.git

Open your project in Android Studio.

Locate the build.gradle file for your app module. This file is usually named build.gradle (Module: app) and is located in the app directory of the project.

Inside the dependencies block, add the Room library dependency:

dependencies {

    // Other dependencies...
    
    implementation("androidx.room:room-runtime:2.4.0")
    
    annotationProcessor("androidx.room:room-compiler:2.4.0")
    
}

Sync your project with Gradle files, then run.


Existing user: 

userName: JohnDoe

password: John123!


# Project Process
We began by carefully reviewing the project assignment, ensuring a clear understanding of the requirements. Next, we leveraged JIRA to organize our workflow, creating a new sprint containing all relevant tasks for the exercise. Each task was meticulously prepared, outlining the steps needed to complete them by the project's conclusion.

Throughout the project, we remained adaptable, identifying additional requirements as they arose. These new tasks were promptly assigned to team members who were best equipped to handle them efficiently.

Collaboration was central to our approach, with all team members actively contributing to the project's codebase. Despite its challenges, our teamwork and dedication ensured successful progress at every stage of development.
