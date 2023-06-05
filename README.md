<img width="700" alt="Capture" src="photo/main.png">

## Description

starTracker is an application that allows users to capture a photo or select an image from the gallery 
and it returns an image with stars surrounded by circles, with each star accompanied by its ID number. 
To find out the name of a star, users can enter its ID number and receive its corresponding name. 
All the photos are saved, allowing users to revisit the stars they have already discovered.


## Algorithm:



## Installation
To use the starTracker application, follow these steps:
1. Clone the repositorys:<br />
  1.1 git clone https://github.com/einav242/starTracker.git<br />
  1.2 git clone https://github.com/einav242/project_new_space.git<br />
2. Open the project in Android Studio.
3. Open the build.gradle file at the application level in  Android Studio and modify the following line:<br />
     python{<br />
              buildPython "C:/Users/e2402/AppData/Local/Microsoft/WindowsApps/python3.9.exe"<br />
        }<br />
to the Python path on your computer. <br />
4. Build and run the application on an Android device or emulator.<br />
5. In a separate open the project directory named "project_new_space" in a Python development environment, such as PyCharm and run the following command in the terminal to start the server:<br />
uvicorn server:app --reload


## Main Functionality
<img width="200" src="photo/צילום מסך 2023-06-01 170642.png">
On the main screen, the user will be able to:

- **Add image–** After clicking on this button, you can choose an image from the gallery or take a photo yourself. 
Then, you can click on the "Algorithm" button to see the identified stars.
In the next window, you can choose to delete, save, or take another photo. If you select "save," you will proceed to the next window where you can enter the star's ID using the keyboard, and its corresponding name will be displayed next to it. <br />
Also, you can see the names of all the stars by clicking on the "Show all the names" button.
<img width="450" src="photo/תמונה2.png">

- **your discoverd stars–** By clicking on this button, you can view all the captured star images. You can delete an image by using the "Delete" button and find the names of the stars by clicking on the "Search by ID" button.
<img width="400" src="photo/תמונה1.png">




