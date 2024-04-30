# Run the Project
Git clone: https://github.com/ShiraKadmon/Project3Android.git

Open your project in Android Studio.

Locate the build.gradle file for your app module. This file is usually named build.gradle (Module: app) and is located in the app directory of the project.

Inside the dependencies block, use the following dependencies:

dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    implementation("com.google.android.material:material:1.11.0")
    
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    implementation("androidx.room:room-common:2.6.1")
    
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    
    implementation ("com.google.code.gson:gson:2.10")
    
    implementation ("com.squareup.picasso:picasso:2.8")
    
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    implementation("androidx.activity:activity-compose:1.8.2")
    
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    
    implementation("androidx.compose.ui:ui")
    
    implementation("androidx.compose.ui:ui-graphics")
    
    implementation("androidx.compose.ui:ui-tooling-preview")
    
    implementation("androidx.compose.material3:material3")
    
    testImplementation("junit:junit:4.13.2")
    
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    
    implementation ("androidx.room:room-runtime:2.6.1")
    
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    
    implementation ("org.mindrot:jbcrypt:0.4")
    
}

Sync your project with Gradle files, then run.

Make sure to simultaneously run the server using Docker Desktop.

Server repository: https://github.com/ShellyGendler/EX3-SHELLY-HADAR-SHIRA

(Below - a run demonstration)



# Database Migrations

AppDB (Feed Database) Migrations


Migration from version 1 to version 2:

    1. Added a new column userJson to the Post table.

    2. Created a temporary table Post_new with the updated schema.

    3. Copied data from the old Post table to the new Post_new table.

    4. Dropped the old Post table.

    5. Renamed the Post_new table to Post.

Migration from version 2 to version 3:

    1. Added a new column userJson to the Post table.

    2. Created a temporary table Post_new with the updated schema.

    3. Copied data from the old Post table to the new Post_new table.

    4. Dropped the old Post table.

    5. Renamed the Post_new table to Post.



UserAppDB (User Database) Migrations

Migration from version 1 to version 2:

    1. Added a new column _id to the User table.


Explanation:

The migrations ensure that the database schema is updated smoothly when the app is upgraded to a new version.

Each migration defines the necessary SQL commands to alter the database schema according to the changes introduced in the new version.

Migrations handle tasks such as adding new columns, creating temporary tables, copying data, dropping old tables, and renaming tables.

By following these migrations, the app's database structure is updated seamlessly, preserving existing data while accommodating new features and changes.



# Project Run Demonstration
Trying to log in with invalid user:

<img width="137" alt="login_invalid_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/d6e364e0-888d-43ba-aafa-cddb35b1904e">


New user sign up - Hadar:

<img width="216" alt="signup_done" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0313e7d4-03f3-4b10-83e0-ccfbd1f8519c">


Logging in - empty feed (no posts yet):

<img width="216" alt="feed_empty" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/3087c126-5aa5-4242-ac3e-305dc4e0cdab">


Creating a new post from Hadar - by pressing "what's on your mind?" at the top of the feed:

<img width="201" alt="newpost_page" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/cc88fe21-1fa1-4007-9dc3-ed26690f4f60">


Seeing the existing posts back in the feed, specifically the new one:

<img width="199" alt="feed_new_post" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/31ff6ce0-c018-4777-b3a7-42d4efdbeedd">


Editing the existing post made by Hadar - by pressing the edit icon on the post:

<img width="205" alt="feed_post_edit" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/883919c3-a229-4465-95e5-54e6e6b4bd53">


Night-mode feed - switching by pressing the switch at the top right of the feed page:

<img width="210" alt="feed_nightmode" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/473529f0-9aca-4ee6-b291-86fe8a259731">


Creating another new user - Shira - by pressing Logout and then Sign Up:

<img width="214" alt="signup_new_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/ed477313-1ae1-46c0-a3d8-9127db1f1782">


Creating a new post by Shira:

<img width="205" alt="feed_different_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0b8cff2d-d6c4-455f-8b87-fbfd1a3a70f9">


Viewing Hadar's profile page from Shira's user - by pressing Hadar's icon on Hadar's post in the feed:

<img width="216" alt="profile_not_friend" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/9a9c358f-0c4a-4351-a89f-006c96637821">


Sending friend request from Shira to Hadar:

<img width="201" alt="friendship_requset" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/d33df5a4-3abd-41ef-a493-d8212c817653">


Viewing Shira's profile page from Hadar's user:

<img width="209" alt="friend_request_approve" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/1f5d0aa6-27d4-4a78-94b9-775329005641">


Viewing Shira's posts after approving the friend request sent from Shira to Hadar:

<img width="203" alt="profile_page_friend" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/075bf5fb-0a87-4db5-8f0d-cebb364e5ce5">


Hadar's user liking a post - by pressing the like icon:

<img width="198" alt="feed_like" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/ed9147a2-391b-4b1e-89c7-3f49f136bb60">


Viewing a post's share menu - by pressing the share icon:

<img width="208" alt="feed_share" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/362eda86-8829-432a-94f3-79635632e36e">


Viewing Shira's post's comment page - by pressing the comment icon:

<img width="202" alt="cooment_page_empty" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/30caeb76-48c7-4855-8485-069c9afcabb7">


Adding a comment on Shira's post from Hadar's user:

<img width="209" alt="comment_new" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/f97d66ad-e2ea-46e2-963a-1b75e5632a22">


Editing Hadar's comment on the post - by pressing the comment's edit icon:

<img width="202" alt="comment_edited" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/12566a1f-e3d7-48bb-bc34-ed26166fbf55">


Deleting Hadar's comment - by pressing the comment's delete button:

<img width="213" alt="comment_delete" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/4d75e042-392b-4933-962f-fb6c171e18fe">


Viewing Hadar's user menu:

<img width="209" alt="feed_user_menu" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/918fbcfe-c507-40e1-acde-ba7db230912c">


Editing Hadar's user (the name) - by changing the relevant field and pressing Sign Up:

<img width="181" alt="signup_edit_success" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/b2398a2d-ff9a-444b-8cf3-c4b2c4571847">


Viewing the changes to Hadar's user on the feed - after logging in to a different user:

<img width="232" alt="feed_user_edited" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/71a9c0d9-8cb4-4f6d-8d0e-d2ef1febd5b7">


It is also possible to do other things that aren't shown: delete a post, delete a friend, delete a user, view a full feed (25 posts) once there are enough existing posts saved on the server, etc.



# Work Process
We began by carefully reviewing the project assignment, ensuring a clear understanding of the requirements. Next, we leveraged JIRA to organize our workflow, creating a new sprint containing all relevant tasks for the exercise. Each task was meticulously prepared, outlining the steps needed to complete them by the project's conclusion.

Throughout the project, we remained adaptable, identifying additional requirements as they arose. These new tasks were promptly assigned to team members who were best equipped to handle them efficiently.

Collaboration was central to our approach, with all team members actively contributing to the project's codebase. Despite its challenges, our teamwork and dedication ensured successful progress at every stage of development.

