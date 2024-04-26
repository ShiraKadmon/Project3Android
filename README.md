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
<img width="960" alt="login_invalid_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0506db87-2536-4d53-b0bc-c4fadd7497bb">

New user sign up - Hadar:
<img width="960" alt="signup_done" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/6ef121f5-4aa9-4146-bbf5-97a472cee2f1">

Logging in - empty feed (no posts yet):
<img width="960" alt="feed_empty" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/96714667-3e4d-49d0-9c0e-7097abbb0809">

Creating a new post from Hadar - by pressing "what's on your mind?" at the top of the feed:
<img width="960" alt="newpost_page" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/edb15740-9c05-45d4-91ff-8607b3bc2734">

Seeing the existing posts back in the feed, specifically the new one:
<img width="960" alt="feed_new_post" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/5e1ff8fb-5295-42d1-9c97-6cefb740ad65">

Editing the existing post made by Hadar - by pressing the edit icon on the post:
<img width="960" alt="feed_post_edit" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/6453011f-7b74-4111-ad3d-b99f80bfcc8b">

Night-mode feed - switching by pressing the switch at the top right of the feed page:
<img width="960" alt="feed_nightmode" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0d8e73dd-a96e-49f9-936a-6e6fa50ceff9">

Creating another new user - Shira - by pressing Logout and then Sign Up:
<img width="960" alt="signup_new_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/fda543e8-c350-4760-a199-58db7be04617">

Creating a new post by Shira:
<img width="960" alt="feed_different_user" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/1e890829-52c9-4796-aa7f-c03f8c43be21">

Viewing Hadar's profile page from Shira's user - by pressing Hadar's icon on Hadar's post in the feed:
<img width="960" alt="profile_not_friend" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/c8d7e17b-ee9c-4965-a019-1301145323d1">

Sending friend request from Shira to Hadar:
<img width="960" alt="friendship_requset" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0cda5a54-2534-4884-a728-12168f19424b">

Viewing Shira's profile page from Hadar's user:
<img width="960" alt="friend_request_approve" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/fc2b19db-53c8-46e0-8cb3-2c905500a6d3">

Viewing Shira's posts after approving the friend request sent from Shira to Hadar:
<img width="960" alt="profile_page_friend" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/4eec004d-fd7e-4908-bc40-40f240a23295">

Hadar's user liking a post - by pressing the like icon:
<img width="960" alt="feed_like" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/f776fad3-5698-4abb-a834-460337c53952">

Viewing a post's share menu - by pressing the share icon:
<img width="960" alt="feed_share" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/83b6287d-40e9-45f4-8eb8-662388caf22c">

Viewing Shira's post's comment page - by pressing the comment icon:
<img width="960" alt="cooment_page_empty" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/afd46556-eea9-4f4d-933e-56ca24284fb7">

Adding a comment on Shira's post from Hadar's user:
<img width="960" alt="comment_new" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/1b0164c5-b6bf-4572-a239-a3492891c1ff">

Editing Hadar's comment on the post - by pressing the comment's edit icon:
<img width="960" alt="comment_edited" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/558ce482-04e7-48e6-9e42-c61fdde57930">

Deleting Hadar's comment - by pressing the comment's delete button:
<img width="960" alt="comment_delete" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/1e5a5cae-10af-4cbe-afbd-224bfafc23b2">

Viewing Hadar's user menu:
<img width="960" alt="feed_user_menu" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/5a9ca2fb-0a30-417d-b87e-2ae90c828901">

Editing Hadar's user (the name) - by changing the relevant field and pressing Sign Up:
<img width="960" alt="signup_edit_success" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/186e52e1-5ccd-4697-824f-05a5fca91977">

Viewing the changes to Hadar's user on the feed - after logging in to a different user:
<img width="960" alt="feed_user_edited" src="https://github.com/ShiraKadmon/Project3Android/assets/155539800/0c955e16-9e95-4046-8093-129ad54e2183">

It is also possible to do other things that aren't shown: delete a post, delete a friend, delete a user, view a full feed (25 posts) once there are enough existing posts saved on the server, etc.



# Work Process
We began by carefully reviewing the project assignment, ensuring a clear understanding of the requirements. Next, we leveraged JIRA to organize our workflow, creating a new sprint containing all relevant tasks for the exercise. Each task was meticulously prepared, outlining the steps needed to complete them by the project's conclusion.

Throughout the project, we remained adaptable, identifying additional requirements as they arose. These new tasks were promptly assigned to team members who were best equipped to handle them efficiently.

Collaboration was central to our approach, with all team members actively contributing to the project's codebase. Despite its challenges, our teamwork and dedication ensured successful progress at every stage of development.

